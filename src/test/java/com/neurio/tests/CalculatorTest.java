package com.neurio.tests;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.neurio.tests.shared.*;
import j2html.tags.ContainerTag;
import j2html.tags.Tag;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static j2html.TagCreator.*;

/**
 * Created by Robert T. on 5/25/2016.
 * CalculatorTest
 */
public class CalculatorTest extends BasicTest {

    private String ADMIN_LOGIN;
    private String ADMIN_PASSWORD = "kashani1234";
    private String testAccount;
    private String testAccountID;
    private String billingType;
    private String url;

    private void initializeVariables() {
        ADMIN_LOGIN = Common.getValue("account_email", "soroush.norouzi@neur.io");
        testAccount = Common.getValue("account_name", StringRef.WINDSOR).replaceAll("_", " ");
        testAccountID = Common.getValue("account_id", StringRef.WINDSOR_ID);
        url = Common.getValue("url", "neur.io");
    }

    private JSONObject getOriginalSettings(String authToken) {
        try {
            return API.getRequestJSON(authToken, "locations/" + testAccountID).getBody().getObject();
        } catch (Exception e) {
            return null;
        }
    }

    private String getReportName() {
        String reportName;
        if (billingType.contains("flat")) {
            reportName = "Flat Forecast Calculator Report";
        } else if (billingType.contains("tiered")) {
            reportName = "Tiered Forecast Calculator Report";
        } else {
            reportName = "TOU Forecast Calculator Report";
        }
        return reportName;
    }

    private ContainerTag initializeReport(String reportName) {
        return html().with(
                head().with(title("Report"), link().withRel("stylesheet").withHref("/css/main.css")), body().with(
                        main().with(
                                h1(reportName),
                                p("User: " + testAccount + " ID: " + testAccountID),
                                p("URL: " + url),
                                p("Date: " + Common.getDate()),
                                p("Versions: mobile-release-v275_QA.apk and iOS v 1.16.0 Build 215")
                        )
                )
        );
    }

    private void writeReportToFile(ContainerTag report, String options) {
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
            File dir = new File("target/surefire-reports");
            dir.mkdirs();
            fWriter = new FileWriter("target/surefire-reports/ForecastReport" + options + ".html");
            writer = new BufferedWriter(fWriter);
            writer.write(reportString);
            writer.close(); //close the writer object
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<List<String>> getTestFile() {
        int bookNum;
        if (billingType.contains("flat")) {
            bookNum = 0;
        } else if (billingType.contains("tiered")) {
            bookNum = 1;
        } else {
            bookNum = 2;
        }
        String testCaseFile = Common.getPropertyValue("test_case_file", "CalculatorTestCase.xlsx");
        return JsonBuilder.extractExcelFile(testCaseFile, bookNum);
    }

    @Test
    public void ParallelAndroidWebForecastTest() {
        initializeVariables();
        String authToken = API.getAuthToken();

        // log into web
        LoginPage.signIn(ADMIN_LOGIN, ADMIN_PASSWORD);
        Common.wait(2);

        // start Android automation
        AndroidWrapper android = new AndroidWrapper();
        android.login(ADMIN_LOGIN, ADMIN_PASSWORD);
        Common.wait(2);

        // start iOS automation
        IosWrapper ios = new IosWrapper(4724);
        Common.wait(10);
        ios.login(ADMIN_LOGIN, ADMIN_PASSWORD);
        Common.wait(2);

        String billingTypes[] = {"flat", "tiered", "timeOfUse"};

        for (int index = 0; index < billingTypes.length; index++) {
            billingType = billingTypes[index];
            // prepare test controls
            List<List<String>> testFile = getTestFile();

            JSONObject originalSettings = getOriginalSettings(authToken);
            if (originalSettings != null) {
                Report("Original Settings:");
                Report(originalSettings.toString());
            }
            ContainerTag report = initializeReport(getReportName());
            List<Tag> testReportItems = new ArrayList<>();
            // run the test and generate the report
            System.out.println("Going to be running " + testFile.size() + " test cases for billingType: " + billingType);
            for (int i = 0; i < testFile.size(); i++) {
                try {
                    Report("-----------------------------------------------------------------------");
                    Report("\nTest Case " + (i + 1) + ":");
                    String excelLine = "";
                    for (String element : testFile.get(i)) {
                        excelLine += element + "||";
                    }
                    Report("Test Case - Excel Line:");
                    Report(excelLine);
                    JSONObject obj = JsonBuilder.getRequest(testFile.get(i), billingType);
                    Report("Test Case Settings: \n" + obj.toString() + "\n");
                    HttpResponse<JsonNode> res = API.patchRequestJSON(authToken, "locations/" + testAccountID, obj);
                    Report("Response: " + res.getStatus());
                    refresh();
                    Common.wait(2);
                    refresh();
                    ios.refreshHome();
                    Common.wait(20);

//                ContainerTag entry = useSolar ? solarCalculatorHelper(testAccountID, i + 1) :
//                        forecastCalculatorHelper(testAccountID, i + 1);
                    String webValue = "NaN";
                    String androidValue = "NaN";
                    String iosValue = "NaN";
                    try {
                        webValue = HomePage.getForecastValue();
                        if (webValue.contains("-")) {
                            webValue = webValue.replace("-", "-$");
                        } else {
                            webValue = "$" + webValue;
                        }
                        System.out.println("web value" + webValue);
                    } catch (Exception e) {
                        System.out.println("Exception happened trying to get web value for Forecast: " + e.getMessage());
                    }

                    try {
                        androidValue = android.getElementByID("textViewCurrentForecast").getText();
                    } catch (Exception e) {
                        System.out.println("Exception happened trying to get androidValue for forcast: " + e.getMessage());
                    }

                    try {
                        iosValue = ios.getForecast();
                    } catch (Exception e) {
                        System.out.println("Exception happened trying to get iosValue for forcast: " + e.getMessage());
                    }

                    String result = "FAILURE";
                    if (webValue.equals(androidValue) && iosValue.equals(androidValue)) result = "SUCCESS";

                    Report("-----------------------------------------------------------------------\n");

                    testReportItems.add(
                            h1("Test Case " + (i + 1) + ":").with(
                                    h2("Test Case - Excel Line:"),
                                    p(excelLine),
                                    p("Json Object Sent:\n" + obj.toString()),
                                    p("Response: " + res.getStatus()),
                                    p("Android: " + androidValue + " Web: " + webValue + " iOS: " + iosValue + " Result: " + result)
                            )
                    );
                } catch (Exception e) {
                    System.out.println("Exception happened" + e.getMessage());
                }
            }

            report.with(testReportItems);
            writeReportToFile(report, "_" + testAccountID + "_" + new SimpleDateFormat("YYMMddHHmm").format(new Date()) + "_" + billingType);
        }

        ios.signOut();
    }
}
