package com.neurio.tests.shared;

import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import j2html.tags.ContainerTag;
import j2html.tags.Tag;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static j2html.TagCreator.*;
/**
 * Created by jameslNeurio on 2016-12-16.
 * Class of helper functions used to interact for graph tests
 * Like to build specific queries strings, process the responses
 * And create the html elements for the reports at end of test
 */
public class GraphTestHelper {

    /**
     * Builds the API request string and does the conversion for start and end time based on timezone
     * option depends checking for EOT of POT, consider method overloading
     */
    public static String requestStringBuilder(String id, String granularity, String startDate,
                                        String endDate, String timezoneString, boolean isEOT) {
        String startDateFormatted;
        String endDateFormatted;
        Date startDateObj = new Date();
        Date endDateObj = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(StringRef.DAY_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone(timezoneString));
        /*
            UTC is a special case
         */
        if (timezoneString.contains("UTC")) {
            String utcSuffix = "T00:00:00+00:00";
            try {
                startDateObj = sdf.parse(startDate + utcSuffix);
                endDateObj = sdf.parse(endDate + utcSuffix);
            } catch (Exception e) {
                e.printStackTrace();
            }
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssXXX");
            formatter.setTimeZone(TimeZone.getTimeZone(timezoneString));

            startDateFormatted = formatter.format(startDateObj).replaceFirst(" ", "T");
            endDateFormatted = formatter.format(endDateObj).replaceFirst(" ", "T");
        } else {
            try {
                startDateObj = sdf.parse(startDate);
                endDateObj = sdf.parse(endDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd XXX");
            formatter.setTimeZone(TimeZone.getTimeZone(timezoneString));

            String[] startDateStringArray = formatter.format(startDateObj).split(" ");
            startDateFormatted = String.join("T00:00:00", startDateStringArray);
            String[] endDateStringArray = formatter.format(endDateObj).split(" ");
            endDateFormatted = String.join("T00:00:00", endDateStringArray);
        }
        String requestString = (isEOT)? "samples/stats?sensorId=" + id + "&granularity=" + granularity +
                "&start=" + startDateFormatted + "&end=" + endDateFormatted
                + "&timezone=" + timezoneString :
                "samples?sensorId=" + id + "&granularity=" + granularity +
                        "&start=" + startDateFormatted + "&end=" + endDateFormatted +
                        "&timezone=" + timezoneString;
        return requestString.replaceAll("\\+", "%2B");
    }

    /**
     * Helper function to process and returns a div for each possible entry in each Test Case
     */
    public static ContainerTag processDiv(ContainerTag entry, Double webValue, boolean webPositiveValue, Double apiValue,
                                    Double apiRawValue, String title) {
        String webValueColour;
        if (webPositiveValue) {
            webValueColour = "Web Value Colour is Orange (It has generation class name)";
        } else {
            webValueColour = "Web Value Colour is Blue (It has consumption class name)";
        }

        /*
            Element not found check, if web element is not found then the web value returned is -1000000000000
            There should be a better way in doing this
         */
        if (webValue < StringRef.BOTTOM_LIMIT) {
            entry = entry.with(
                    div().with(
                            p(title),
                            p("API Raw Value: " + apiRawValue + " Ws"),
                            p(apiRawValue + " / 3600000 = " + apiValue),
                            p("API Value: " + apiValue + " kWh"),
                            p("Web Value: Web Element Not Found!")
                    ).withClass(StringRef.RED));
            return entry;
        }

        String webValueValidation = "Web Value Colour Validation: ";
        boolean colourValidation;
        if (title.contains(StringRef.NET)) {
            colourValidation = (webValue >= 0) != webPositiveValue;
            if ((webValue >= 0) && !webPositiveValue) {
                webValueValidation += "Net Value is Positive and Blue. Correct";
            } else if ((webValue < 0) && webPositiveValue) {
                webValueValidation += "Net Value is Negative and Orange. Correct";
            } else {
                webValueValidation += "Colour is Incorrect";
            }
        } else {
            colourValidation = title.contains(StringRef.GENERATION) == webPositiveValue;
            if (title.contains(StringRef.GENERATION) && webPositiveValue) {
                webValueValidation += "Generation Value is Orange. Correct";
            } else if (title.contains(StringRef.CONSUMPTION) && !webPositiveValue) {
                webValueValidation += "Consumption Value is Blue. Correct";
            } else {
                webValueValidation += "Colour is Incorrect";
            }
        }
        Double threshold = (webValue < 1.0 && webValue > -1.0) ? StringRef.HIGHER_THRESHOLD : StringRef.THRESHOLD;
        ContainerTag div = div().with(
                p(title),
                p("API Raw Value: " + apiRawValue + " Ws"),
                p(apiRawValue + " / 3600000 = " + apiValue),
                p("API Value: " + apiValue + " kWh"),
                p("Web Value: " + webValue + " kWh"),
                p(webValueColour),
                p(webValueValidation),
                p("Comparison: "),
                p(Common.getTwoNumbersComparisionString(webValue, apiValue, threshold))
        );
        boolean valueValidation = Common.compareTwoNumbers(webValue, apiValue, threshold);
        if (valueValidation && colourValidation) {
            entry = entry.with(div.withClass(StringRef.GREEN));
        } else {
            entry = entry.with(div.withClass(StringRef.RED));
        }
        return entry;
    }

    /**
     * Process and returns a div for each Test Case
     */
    public static void createEntry(String authToken, String testAccountID, String granularity, String startDate,
                             String endDate, int count, String testCase, List<Tag> divs, boolean alertCheck, boolean isEOT) {
        Double highGenValue = 0.0;
        Double lowGenValue = 0.0;
        Double avgGenValue = 0.0;
        Double totalGenValue = 0.0;
        Double highNetValue = 0.0;
        Double lowNetValue = 0.0;
        Double avgNetValue = 0.0;
        Double totalNetValue = 0.0;
        Double noJSONEntriesValue = 0.0;
        boolean highGenPositiveValue = false;
        boolean lowGenPositiveValue = false;
        boolean avgGenPositiveValue = false;
        boolean totalGenPositiveValue = false;
        boolean highNetPositiveValue = false;
        boolean lowNetPositiveValue = false;
        boolean avgNetPositiveValue = false;
        boolean totalNetPositiveValue = false;

        try {
            String timezoneString;
            if (System.getProperty(StringRef.TIMEZONE) == null) {
                timezoneString = Common.getPropertyValue(StringRef.TIMEZONE, StringRef.TIMEZONE_PACIFIC_STRING);
            } else {
                timezoneString = System.getProperty(StringRef.TIMEZONE, StringRef.TIMEZONE_PACIFIC_STRING);
            }
            Common.wait(2);
            int countBlank = 0;

            /*
                In the day cases, we go to the latest day with data. In all other cases, the script does
                not go to the last time with data
             */
            if (testCase.contains(StringRef.DAY)) {
                while (HistoryPage.hasBlankCard() && countBlank < 20) {
                    HistoryPage.selectPrevButton();
                    countBlank++;
                    Common.wait(2);
                }
            }

            Common.wait(5);

            if (testCase.contains(StringRef.PREVIOUS) && !testCase.contains(StringRef.CUSTOM)) {
                HistoryPage.selectPrevButton();
                Common.wait(5);
            } else if (testCase.contains(StringRef.TWO_DAYS_AGO)) {
                HistoryPage.selectPrevButton();
                Common.wait(5);
                HistoryPage.selectPrevButton();
                Common.wait(5);
            } else if (testCase.contains(StringRef.DAYS_AGO_7)) {
                System.out.println("seven days case");
                for (int c = 0; c < 7; c++) {
                    HistoryPage.selectPrevButton();
                    Common.wait(5);
                };
            }

            boolean hasGeneration = HistoryPage.hasGeneration();

            if (hasGeneration) {
                highGenValue = Double.parseDouble(HistoryPage.getValue(StringRef.GENERATION_LOWER_CASE,
                        StringRef.HIGHEST));
                lowGenValue = Double.parseDouble(HistoryPage.getValue(StringRef.GENERATION_LOWER_CASE,
                        StringRef.LOWEST));
                avgGenValue = Double.parseDouble(HistoryPage.getValue(StringRef.GENERATION_LOWER_CASE,
                        StringRef.AVERAGE));

                highGenPositiveValue = HistoryPage.getPositiveColourValue(StringRef.Energy.Generation,
                        StringRef.Stats.Highest);
                lowGenPositiveValue = HistoryPage.getPositiveColourValue(StringRef.Energy.Generation,
                        StringRef.Stats.Lowest);
                avgGenPositiveValue = HistoryPage.getPositiveColourValue(StringRef.Energy.Generation,
                        StringRef.Stats.Average);
                if (isEOT) {
                    totalGenValue = Double.parseDouble(HistoryPage.getValue(StringRef.GENERATION_LOWER_CASE,
                            StringRef.TOTAL));
                    totalGenPositiveValue = HistoryPage.getPositiveColourValue(StringRef.Energy.Generation,
                            StringRef.Stats.Total);

                    highNetValue = Double.parseDouble(HistoryPage.getValue(StringRef.NET_LOWER_CASE, StringRef.HIGHEST));
                    lowNetValue = Double.parseDouble(HistoryPage.getValue(StringRef.NET_LOWER_CASE, StringRef.LOWEST));
                    avgNetValue = Double.parseDouble(HistoryPage.getValue(StringRef.NET_LOWER_CASE, StringRef.AVERAGE));
                    totalNetValue = Double.parseDouble(HistoryPage.getValue(StringRef.NET_LOWER_CASE, StringRef.TOTAL));
                    highNetPositiveValue = HistoryPage.getPositiveColourValue(StringRef.Energy.Net,
                            StringRef.Stats.Highest);
                    lowNetPositiveValue = HistoryPage.getPositiveColourValue(StringRef.Energy.Net,
                            StringRef.Stats.Lowest);
                    avgNetPositiveValue = HistoryPage.getPositiveColourValue(StringRef.Energy.Net,
                            StringRef.Stats.Average);
                    totalNetPositiveValue = HistoryPage.getPositiveColourValue(StringRef.Energy.Net,
                            StringRef.Stats.Total);
                }
            }
            Double highConValue = Double.parseDouble(HistoryPage.getValue(StringRef.CONSUMPTION_LOWER_CASE,
                    StringRef.HIGHEST));
            Double lowConValue = Double.parseDouble(HistoryPage.getValue(StringRef.CONSUMPTION_LOWER_CASE,
                    StringRef.LOWEST));
            Double avgConValue = Double.parseDouble(HistoryPage.getValue(StringRef.CONSUMPTION_LOWER_CASE,
                    StringRef.AVERAGE));

            boolean highConPositiveValue = HistoryPage.getPositiveColourValue(StringRef.Energy.Consumption,
                    StringRef.Stats.Highest);
            boolean lowConPositiveValue = HistoryPage.getPositiveColourValue(StringRef.Energy.Consumption,
                    StringRef.Stats.Lowest);
            boolean avgConPositiveValue = HistoryPage.getPositiveColourValue(StringRef.Energy.Consumption,
                    StringRef.Stats.Average);

            Double totalConValue = (isEOT)? Double.parseDouble(HistoryPage.getValue(StringRef.CONSUMPTION_LOWER_CASE,
                    StringRef.TOTAL)): null;

            boolean totalConPositiveValue = (!isEOT)? false: HistoryPage.getPositiveColourValue(StringRef.Energy.Consumption,
                    StringRef.Stats.Total);
            String requestString = requestStringBuilder(testAccountID, granularity,
                    startDate, endDate, timezoneString, isEOT);
            HttpResponse<JsonNode> res = API.getRequestJSON(authToken, requestString);
            JSONArray array = res.getBody().getArray();
            ArrayList<Double> genList = new ArrayList<>();
            ArrayList<Double> conList = new ArrayList<>();
            System.out.println("output array length: " + array.length());
            if (array.length() == 0) {
                conList.add(noJSONEntriesValue);
                genList.add(noJSONEntriesValue);
            }
            else {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objectInArray = array.getJSONObject(i);

                    Double conFloatValue = (isEOT)? Double.parseDouble(objectInArray.get(StringRef.API_CONSUMPTIONENERGY).toString()) :
                            Double.parseDouble(objectInArray.get(StringRef.API_CONSUMPTIONPOWER).toString());
                    conList.add(conFloatValue);
                    if (isEOT && objectInArray.has(StringRef.API_GENERATIONENERGY)) {
                        Double genFloatValue = Double.parseDouble(objectInArray.get(StringRef.API_GENERATIONENERGY).toString());
                        genList.add(genFloatValue);
                        hasGeneration = true;
                    }
                    else if (objectInArray.has(StringRef.API_GENERATIONPOWER)){
                        Double genFloatValue = Double.parseDouble(objectInArray.get(StringRef.API_GENERATIONPOWER).toString());
                        if (genFloatValue != 0) {
                            genList.add(genFloatValue);
                            hasGeneration = true;
                        }

                    }
                }
            }



            Stats genStats = Stats.getMinMaxAverage(genList);
            Stats conStats = Stats.getMinMaxAverage(conList);
            Stats netStats = Stats.getMinMaxNet(conList, genList);

            ContainerTag entry;

            /*
             * Handles the unique case of Set Custom Date Selector to view 91 days,
              * which should give an error message
             */
            if (testCase.contains("custom91")) {
                entry = div().withClass("case").with(
                        h1("Test Case ").with(
                                h2("Case: " + count),
                                p("Case: " + testCase)
                        ));
                if (alertCheck) {
                    divs.add(entry.with(
                            div().with(
                                    p("Error Message")
                            ).withClass(StringRef.GREEN)));
                    return;
                } else {
                    divs.add(entry.with(
                            div().with(
                                    p("No Error Message")
                            ).withClass(StringRef.RED)));
                    return;
                }
            }

            entry = div().withClass("case").with(
                    h1("Test Case ").with(
                            h2("Case: " + count),
                            p("Case: " + testCase),
                            p("Request String: " + requestString),
                            p("Response Code: " + res.getStatus()),
                            p("Response: " + res.getBody().toString())
                    ));

            if (highConValue < StringRef.BOTTOM_LIMIT && lowConValue < StringRef.BOTTOM_LIMIT &&
                    avgConValue < StringRef.BOTTOM_LIMIT && totalConValue < StringRef.BOTTOM_LIMIT) {
                divs.add(entry.with(
                        div().with(
                                p("No Data")
                        ).withClass(StringRef.GREEN)));
                return;
            }

            entry = processDiv(entry, highConValue, highConPositiveValue, conStats.max, conStats.maxRaw,
                    "Highest Consumption");
            entry = processDiv(entry, lowConValue, lowConPositiveValue, conStats.min, conStats.minRaw,
                    "Lowest Consumption");
            entry = processDiv(entry, avgConValue, avgConPositiveValue, conStats.average,
                    conStats.averageRaw, "Average Consumption");
            if (isEOT) {
                entry = processDiv(entry, totalConValue, totalConPositiveValue, conStats.total,
                        conStats.totalRaw, "Total Consumption");
            }


            if (hasGeneration) {
                entry = processDiv(entry, highGenValue, highGenPositiveValue, genStats.max, genStats.maxRaw,
                        "Highest Generation");
                entry = processDiv(entry, lowGenValue, lowGenPositiveValue, genStats.min, genStats.minRaw,
                        "Lowest Generation");
                entry = processDiv(entry, avgGenValue, avgGenPositiveValue, genStats.average, genStats.averageRaw,
                        "Average Generation");
                entry = processDiv(entry, totalGenValue, totalGenPositiveValue, genStats.total, genStats.totalRaw,
                        "Total Generation");

                entry = processDiv(entry, highNetValue, highNetPositiveValue, netStats.max, netStats.maxRaw,
                        "Highest Net");
                entry = processDiv(entry, lowNetValue, lowNetPositiveValue, netStats.min, netStats.minRaw,
                        "Lowest Net");
                entry = processDiv(entry, avgNetValue, avgNetPositiveValue, netStats.average, netStats.averageRaw,
                        "Average Net");
                entry = processDiv(entry, totalNetValue, totalNetPositiveValue, netStats.total, netStats.totalRaw,
                        "Total Net");
            }

            divs.add(
                    entry
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
