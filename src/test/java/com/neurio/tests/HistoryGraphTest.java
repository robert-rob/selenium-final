package com.neurio.tests;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.neurio.tests.shared.*;
import j2html.tags.ContainerTag;
import j2html.tags.Tag;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static j2html.TagCreator.*;

/**
 * Created by Robert on 8/12/2016.
 * History Graph Test
 */
public class HistoryGraphTest extends BasicTest {

    /**
     * Test creates a report after it is complete in \target\surefire-reports\HistoryGraphReport.html
     */
    @Test
    public void HistoryGraphTest01() {
        String ADMIN_LOGIN = "admin@energy-aware.com";
        String ADMIN_PASSWORD = "bonny5_worktable";
        boolean alertCheck = true;
        String testAccount = Common.getValue("account_name", StringRef.WINDSOR).replaceAll("_", " ");
        String testAccountID = Common.getValue("account_id", StringRef.WINDSOR_ID);
        String sensorID = Common.getValue("sensor_id", StringRef.SENSOR_ID);
        String timezoneString = Common.getValue(StringRef.TIMEZONE, StringRef.TIMEZONE_PACIFIC_STRING);
        String billingDay = Common.getValue("billing_cycle_day", "1");

        String authToken = API.getAuthToken();

        JSONObject originalSettings;
        String userBillingType = "";
        try {
            originalSettings = API.getRequestJSON(authToken, "locations/" + testAccountID).getBody().getObject();
            userBillingType = originalSettings.getString("billingType");
        } catch (Exception e) {
            print(e.getMessage());
        }

        LoginPage.signIn(ADMIN_LOGIN, ADMIN_PASSWORD);

        When("I login as Admin");
        UserBar.toggleUserMenu();

        Then("I can change to a user");
        UserBar.adminSelectUserByNameID(testAccount, testAccountID);

        Common.wait(2);
        UserBar.selectTab(StringRef.Tab.HISTORY);

        HistoryPage.selectEnergyOverTimeGraph();

        String url = Common.getValue("url", StringRef.SHERLOCK_QA_URL);

        ContainerTag report = html().with(
                head().with(title("Report"),
                        link().withRel("stylesheet").withHref("/css/main.css")), body().with(
                        main().with(
                                h1("History Graph Test"),
                                p("User: " + testAccount),
                                p("ID: " + testAccountID),
                                p("SensorID: " + sensorID),
                                p("Billing Cycle Day: " + billingDay),
                                p("Billing Type: " + userBillingType),
                                p("Timezone: " + timezoneString),
                                p("URL: " + url),
                                p("Date: " + Common.getDate())
                        )
                )
        );

        report.with(
                createTestCaseList(authToken, sensorID, billingDay, timezoneString, alertCheck)
        );

        FileWriter fWriter;
        BufferedWriter writer;
        try {
            String reportString = report.toString();
            String cssString = "<style> .green {\n" +
                    "    background-color: #c7f3aa;\n" +
                    "}\n" +
                    "\n" +
                    ".red {\n" +
                    "    background-color: #f4abad;\n" +
                    "}\n" +
                    "\n" +
                    ".case {\n" +
                    "border-style: groove;\n" +
                    "}</style>";
            reportString = reportString.substring(0, 12) + cssString + reportString.substring(12);
            String folderLocation;
            String fileLocation;
            if(Common.isUnixOS()){
                folderLocation = "target/surefire-reports";
                fileLocation = "target/surefire-reports/HistoryGraphReport.html";
            } else {
                folderLocation = "target\\surefire-reports";
                fileLocation = "target\\surefire-reports\\HistoryGraphReport.html";
            }
            File dir = new File(folderLocation);
            dir.mkdirs();
            fWriter = new FileWriter(fileLocation);
            writer = new BufferedWriter(fWriter);
            writer.write(reportString);
            writer.close(); //close the writer object
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Runs through the Test Cases and processes the div elements for report
     */
    /*
        The current date might not have any data, so we might have to add backtrack a few days to find a day with
        data so we included a dayDifference variable
     */
    private List<Tag> createTestCaseList(String authToken, String testAccountID, String billingCycleDay,
                                         String timezone, boolean alertCheck) {
        int countBlank = 0;
        boolean isEOT = true;
        while (HistoryPage.hasBlankCard() && countBlank < 20) {
            HistoryPage.selectPrevButton();
            countBlank++;
            Common.wait(2);
        }

        List<Tag> divs = new ArrayList<>();
        String[] testCaseArray = StringRef.HISTORY_GRAPH_TEST_CASES;
        int count = 1;
        for (String item : testCaseArray) {
            String testCase;
            if (item.contains(StringRef.DAY)) {
                testCase = StringRef.ENERGY_OVER_TIME + item;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(StringRef.DAY_FORMAT);
                LocalDateTime currentDateTime = Common.getLocalTime(timezone);
                if (countBlank > 0) {
                    currentDateTime = currentDateTime.minusDays(countBlank);
                }
                if (item.contains(StringRef.PREVIOUS)) {
                    currentDateTime = currentDateTime.minusDays(1);
                } else if (item.contains(StringRef.TWO_DAYS_AGO)) {
                    currentDateTime = currentDateTime.minusDays(2);
                }
                LocalDateTime tomorrowDateTime = currentDateTime.plusDays(1);
                String formattedDateTime = currentDateTime.format(formatter);
                String formattedTomorrowDateTime = tomorrowDateTime.format(formatter);
                HistoryPage.selectGranularity(StringRef.Granularity.Day);
                GraphTestHelper.createEntry(authToken, testAccountID, StringRef.HOURS, formattedDateTime, formattedTomorrowDateTime,
                        count, testCase, divs, alertCheck, isEOT);
            } else if (item.contains(StringRef.WEEK)) {
                testCase = StringRef.ENERGY_OVER_TIME + item;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(StringRef.DAY_FORMAT);

                LocalDateTime currentDateTime = Common.getLocalTime(timezone);

                if (item.contains(StringRef.PREVIOUS)) {
                    currentDateTime = currentDateTime.minusWeeks(1);
                }
                DayOfWeek day_of_week = currentDateTime.getDayOfWeek();
                switch (day_of_week) {
                    case MONDAY:
                        break;
                    case TUESDAY:
                        currentDateTime = currentDateTime.minusDays(1);
                        break;
                    case WEDNESDAY:
                        currentDateTime = currentDateTime.minusDays(2);
                        break;
                    case THURSDAY:
                        currentDateTime = currentDateTime.minusDays(3);
                        break;
                    case FRIDAY:
                        currentDateTime = currentDateTime.minusDays(4);
                        break;
                    case SATURDAY:
                        currentDateTime = currentDateTime.minusDays(5);
                        break;
                    case SUNDAY:
                        currentDateTime = currentDateTime.minusDays(6);
                        break;
                    default:
                        break;
                }
                LocalDateTime lastDateTime = currentDateTime.plusDays(7);
                String formattedDateTime = currentDateTime.format(formatter);
                String formattedLastDateTime = lastDateTime.format(formatter);
                HistoryPage.selectGranularity(StringRef.Granularity.Week);
                GraphTestHelper.createEntry(authToken, testAccountID, StringRef.DAYS, formattedDateTime, formattedLastDateTime, count,
                        testCase, divs, alertCheck, isEOT);
            } else if (item.contains(StringRef.MONTH)) {
                testCase = StringRef.ENERGY_OVER_TIME + item;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(StringRef.MONTH_FORMAT);

                LocalDateTime currentDateTime = Common.getLocalTime(timezone);

                if (item.contains(StringRef.PREVIOUS)) {
                    currentDateTime = currentDateTime.minusMonths(1);
                }
                LocalDateTime targetDateTime = currentDateTime.plusMonths(1);
                String formattedDateTime = currentDateTime.format(formatter);
                String formattedTargetDateTime = targetDateTime.format(formatter);
                formattedDateTime += "-01";
                formattedTargetDateTime += "-01";
                HistoryPage.selectGranularity(StringRef.Granularity.Month);
                GraphTestHelper.createEntry(authToken, testAccountID, StringRef.DAYS, formattedDateTime, formattedTargetDateTime, count,
                        testCase, divs, alertCheck, isEOT);
            } else if (item.contains(StringRef.BILLING_CYCLE)) {
                testCase = StringRef.ENERGY_OVER_TIME + item;
                if (billingCycleDay.length() < 2) {
                    billingCycleDay = "0" + billingCycleDay;
                }
                DateTimeFormatter hourDayFormatter = DateTimeFormatter.ofPattern(StringRef.DAY_HOUR_FORMAT);
                DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern(StringRef.DAY_FORMAT);
                DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern(StringRef.MONTH_FORMAT);
                String formattedDateTime;
                String formattedTargetDateTime;

                LocalDateTime currentDateTime = Common.getLocalTime(timezone);

                String billingDateTimeString = currentDateTime.format(monthFormatter);
                billingDateTimeString += "-" + billingCycleDay + " 12:00";
                LocalDateTime billingDateTime = LocalDateTime.parse(billingDateTimeString, hourDayFormatter);

                if (item.contains(StringRef.PREVIOUS)) {
                    if (currentDateTime.isAfter(billingDateTime) ||
                            currentDateTime.getDayOfMonth() == billingDateTime.getDayOfMonth()) {
                        formattedDateTime = billingDateTime.minusMonths(1).format(dayFormatter);
                        formattedTargetDateTime = billingDateTimeString;
                    } else {
                        formattedDateTime = billingDateTime.minusMonths(2).format(dayFormatter);
                        formattedTargetDateTime = billingDateTime.minusMonths(1).format(dayFormatter);
                    }
                } else {
                    if (currentDateTime.isAfter(billingDateTime) ||
                            currentDateTime.getDayOfMonth() == billingDateTime.getDayOfMonth()) {
                        formattedDateTime = billingDateTimeString;
                        formattedTargetDateTime = billingDateTime.plusMonths(1).format(dayFormatter);
                    } else {
                        formattedDateTime = billingDateTime.minusMonths(1).format(dayFormatter);
                        formattedTargetDateTime = billingDateTimeString;
                    }
                }

                HistoryPage.selectGranularity(StringRef.Granularity.BillingCycle);
                GraphTestHelper.createEntry(authToken, testAccountID, StringRef.DAYS, formattedDateTime, formattedTargetDateTime, count,
                        testCase, divs, alertCheck, isEOT);
            } else if (item.contains(StringRef.YEAR)) {
                testCase = StringRef.ENERGY_OVER_TIME + item;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");

                LocalDateTime currentDateTime = Common.getLocalTime(timezone);

                if (item.contains(StringRef.PREVIOUS)) {
                    currentDateTime = currentDateTime.minusYears(1);
                }
                LocalDateTime lastDateTime = currentDateTime.plusYears(1);
                String formattedDateTime = currentDateTime.format(formatter);
                formattedDateTime += "-01-01";
                String formattedTargetDateTime = lastDateTime.format(formatter);
                formattedTargetDateTime += "-01-01";
                HistoryPage.selectGranularity(StringRef.Granularity.Year);
                GraphTestHelper.createEntry(authToken, testAccountID, StringRef.MONTHS, formattedDateTime, formattedTargetDateTime, count,
                        testCase, divs, alertCheck, isEOT);
            } else if (item.contains(StringRef.CUSTOM)) {
                testCase = StringRef.ENERGY_OVER_TIME + item;
                int numDays;
                if (item.contains("90")) {
                    numDays = 90;
                } else if (item.contains("91")) {
                    numDays = 91;
                } else {
                    numDays = 9;
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(StringRef.DAY_FORMAT);
                LocalDateTime currentDateTime = Common.getLocalTime(timezone);
                if (countBlank > 0) {
                    currentDateTime = currentDateTime.minusDays(countBlank);
                }
                if (item.contains(StringRef.PREVIOUS)) {
                    currentDateTime = currentDateTime.minusDays(numDays);
                }
                LocalDateTime startDateTime = currentDateTime.minusDays(numDays);
                String formattedDateTime = currentDateTime.format(formatter);
                String formattedStartDateTime = startDateTime.format(formatter);
                HistoryPage.selectGranularity(StringRef.Granularity.Custom);
                HistoryPage.selectCustomTimeFrame(formattedStartDateTime, formattedDateTime);
                /*
                    Error message included here because the error dialog only appears for 3 seconds and will
                    go away when checked later
                 */
                if (item.contains("91")) {
                    try {
                        Common.closeAlertDialog();
                    } catch (Exception e) {
                        /*
                            Find out why this step doesn't work
                         */
                        //alertCheck = false;
                    }
                }

                GraphTestHelper.createEntry(authToken, testAccountID, StringRef.DAYS, formattedStartDateTime, formattedDateTime, count,
                        testCase, divs, alertCheck, isEOT);
            }
            count++;
        }
        return divs;
    }

}
