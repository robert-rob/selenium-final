package com.neurio.tests;

import com.neurio.tests.shared.*;
import j2html.tags.ContainerTag;
import j2html.tags.Tag;

import org.testng.annotations.Test;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static j2html.TagCreator.*;


/**
 * Created by jameslNeurio on 2016-12-16.
 * Power over Time Graph Test
 */
public class PowerGraphTest extends BasicTest {

    @Test
    public void PowerGraphTest01() {
        String ADMIN_LOGIN = "admin@energy-aware.com";
        String ADMIN_PASSWORD = "bonny5_worktable";
        boolean alertCheck = true;
        String testAccount = Common.getValue("account_name", StringRef.TIMOTHY).replaceAll("_", " ");
        String testAccountID = Common.getValue("account_id", StringRef.TIMOTHY_ID);
        String sensorID = Common.getValue("sensor_id", StringRef.SENSOR_ID_TIM);
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
        HistoryPage.selectPowerOverTimeGraph();

        String url = Common.getValue("url", StringRef.SHERLOCK_QA_URL);

        ContainerTag report = html().with(
                head().with(title("Report"),
                        link().withRel("stylesheet").withHref("/css/main.css")), body().with(
                        main().with(
                                h1("History Power Graph Test"),
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
            String fileLocation;
            String folderLocation;
            if(Common.isUnixOS()){
                folderLocation = "target/surefire-reports";
                fileLocation = "target/surefire-reports/PowerGraphReport.html";
            } else {
                folderLocation = "target\\surefire-reports";
                fileLocation = "target\\surefire-reports\\PowerGraphReport.html";
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
     * createTestCaseList function for PowerGraph Test
     */
    /*
        The current date might not have any data, so we might have to add backtrack a few days to find a day with
        data so we included a dayDifference variable
     */
    private List<Tag> createTestCaseList(String authToken, String testAccountID, String billingCycleDay,
                                         String timezone, boolean alertCheck) {
        int countBlank = 0;
        boolean isEOT = false;
        while ((HistoryPage.hasBlankCard() || HistoryPage.noGraphData()) && countBlank < 20) {
            HistoryPage.selectPrevButton();
            countBlank++;
            Common.wait(2);
        }
        List<Tag> divs = new ArrayList<>();
        String[] TestCaseArray = StringRef.POWER_GRAPH_TEST_CASES;
        int count = 1;
        for (String item : TestCaseArray) {
            String testCase = StringRef.POWER_OVER_TIME + item;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(StringRef.DAY_FORMAT);
            LocalDateTime currentDateTime = Common.getLocalTime(timezone);
            if (countBlank > 0) {
                currentDateTime = currentDateTime.minusDays(countBlank);
            }

            if (item.contains(StringRef.PREVIOUS)) {
                currentDateTime = currentDateTime.minusDays(1);
            } else if (item.contains(StringRef.TWO_DAYS_AGO)) {
                currentDateTime = currentDateTime.minusDays(2);
            } else if (item.contains(StringRef.DAYS_AGO_7)) {
                currentDateTime = currentDateTime.minusDays(7);
            } else if (item.contains(StringRef.DAYS_AGO_21)) {
                currentDateTime = currentDateTime.minusDays(21);
            }
            LocalDateTime nextDateTime = currentDateTime.plusDays(1);
            String formattedDateTime = currentDateTime.format(formatter);
            String formattedNextDateTime = nextDateTime.format(formatter);

            GraphTestHelper.createEntry(authToken, testAccountID, StringRef.DAYS, formattedDateTime, formattedNextDateTime, count,
                    testCase, divs, alertCheck, isEOT);
            count++;

        }

        return divs; //todo
    }

}
