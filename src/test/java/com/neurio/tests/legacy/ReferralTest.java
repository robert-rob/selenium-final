package com.neurio.tests.legacy;

import com.neurio.tests.shared.*;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Robert on 6/20/2016.
 * Reseller Test Class
 */
public class ReferralTest extends BasicTest {

    @Test
    public void referralTest01() {
        String EMAIL = "admin@energy-aware.com";
        String PASSWORD = "bonny5_worktable";
        String SUB_ACCOUNT_SENSOR_ID = "0x1000000000089896";
        String NEW_PASSWORD = "kashani1234";
        Long currentTime = System.currentTimeMillis();
        String SUB_ACCOUNT_EMAIL = "robert+sub" + currentTime + "@neur.io";

        Report("Referral Page Test 01");

        When("I run the sensor simulator");
        CommandLine.runSensorSimulator(SUB_ACCOUNT_EMAIL, SUB_ACCOUNT_SENSOR_ID, 10);

        String adminToken = API.getAuthToken();

        Then("I create a new sub-account through the API Call");
        Common.wait(5);
        JSONObject responseSensor = API.getSensor(adminToken, SUB_ACCOUNT_SENSOR_ID);
        String installCode = responseSensor.get("installCode").toString();
        int registerResponse = API.registerNewUser(adminToken, installCode, "robert+sub" + currentTime, NEW_PASSWORD);

        Then("I should get a confirmation that the account is created");
        Assert.assertEquals(registerResponse, 204, registerResponse + " is not 204");
        Then("204 Response is received");

        When("I login");
        LoginPage.signIn(EMAIL, PASSWORD);

        Then("I select settings");
        UserBar.selectSettings();

        SettingsPage.selectSettingsTab(StringRef.SettingTab.RESELLERS);
        When("I go to the register a reseller page");
        ResellerPage.registerResellerClick();

        String newResellerName = "robert+reseller" + currentTime;
        String newResellerEmail = newResellerName + "@neur.io";
        Then("I register a new reseller");
        ResellerPage.registerReseller(newResellerName, newResellerEmail, NEW_PASSWORD);

        goBack();

        ResellerPage.selectReseller(newResellerName);

        ResellerPage.addSensorIDs(SUB_ACCOUNT_SENSOR_ID);

        String newResellerID = ResellerPage.getID();

        UserBar.adminSignOut();

        When("Co-branding is added");
        int cobrandResponse = API.addCobrand(adminToken, newResellerID);
        Assert.assertEquals(cobrandResponse, 200, cobrandResponse + " is not 200");
        Then("200 Response is received");

        LoginPage.signIn(newResellerEmail, NEW_PASSWORD);

        HomePage.checkCleanSlate();

        UserBar.signOut();

        int addUserResponse = API.addUserReleaseFeature(adminToken, newResellerID);

        Assert.assertEquals(204, addUserResponse, addUserResponse + " Returned. Adding Referral Feature Failed");

        LoginPage.signIn(SUB_ACCOUNT_EMAIL, NEW_PASSWORD);

        HomePage.inviteFriendClick();
    }
}
