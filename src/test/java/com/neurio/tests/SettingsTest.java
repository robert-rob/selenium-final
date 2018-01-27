package com.neurio.tests;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.neurio.tests.shared.*;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Robert on 5/24/2016.
 * Settings Test
 */
public class SettingsTest extends BasicTest {
    String LOGIN = "robert+89891@neur.io";
    String PASSWORD = "kashani1234";
    String ACCOUNT_NAME = "Robert89891";
    String NEW_ACCOUNT_NAME = ACCOUNT_NAME + " NEW";
    String BAD_ACCOUNT_NAME = "abcdefghijklmnopqrstuvuxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    String LA = "Los_Angeles";
    String NEW_YORK = "New_York";
    String POSTAL_CODE = "V6B 1G4";
    String NEW_POSTAL_CODE = "V5H 4N2";
    String HOME_TYPE = "House";
    String NEW_HOME_TYPE = "Condo";
    String ONE_MILLION_ONE = "1000001";
    String NEGATIVE_ONE = "-1";
    String ABC = "abc";
    String ONE = "1";
    String ONE_POINT_ONE = "1.1";
    String TWO = "2";

    @Test
    public void SettingsPageTest01() {

        String NEW_EMAIL = "robert+89891@neur.io";
        String BAD_PASSWORD = "123456";
        String NEW_PASSWORD = PASSWORD + "new";

        Report("Setting Page Test 01");

        When("I login");
        LoginPage.signIn(LOGIN, PASSWORD);
        UserBar.selectSettings();
        When("I change name to one longer than 50 characters");
        AccountPage.changeAccountName(BAD_ACCOUNT_NAME);
        Then("It should fail");
        Assert.assertTrue(Common.checkForError(), "Errors did not appear");
        When("I change the name, it should work and be checked later on in the test");
        AccountPage.changeAccountName(NEW_ACCOUNT_NAME);
        AccountPage.changePassword(PASSWORD, BAD_PASSWORD);
        Then("It should fail");
        Assert.assertTrue(Common.checkForError(), "Errors did not appear");
        When("I change the password, it should work and be checked later on in the test");
        AccountPage.changePassword(PASSWORD, NEW_PASSWORD);
        SettingsPage.signOut();
        When("I sign in with the new password and check the new account name");
        LoginPage.signIn(LOGIN, NEW_PASSWORD);
        UserBar.selectSettings();
        Then("It should work now with the new changes");
        UserBar.assertUserName(NEW_ACCOUNT_NAME);
        Report("The email and account name will be changed back");
        AccountPage.changeAccountName(ACCOUNT_NAME);
        AccountPage.changePassword(NEW_PASSWORD, PASSWORD);
        AccountPage.pressChangeEmailButton();
        When("I set new email to one already in use");
        ChangeEmailDialog.changeEmail(PASSWORD, LOGIN);
        Then("It should fail");
        Assert.assertTrue(Common.checkForError(), "Errors did not appear");
        When("I set new email with wrong password");
        ChangeEmailDialog.changeEmail(BAD_PASSWORD, LOGIN);
        Then("It should fail");
        Assert.assertTrue(Common.checkForError(), "Errors did not appear");
        When("I use new emails that do not match");
        ChangeEmailDialog.enterCurrentPassword(PASSWORD);
        ChangeEmailDialog.enterNewEmail(NEW_EMAIL);
        ChangeEmailDialog.confirmNewEmail("new" + NEW_EMAIL);
        ChangeEmailDialog.submitChangeEmail();
        Then("It should fail");
        Assert.assertTrue(Common.checkForError(), "Errors did not appear");
        ChangeEmailDialog.close();
        SettingsPage.signOut();
        Report("Setting Page Test 01 Passed!");
    }

    @Test
    public void SettingsPageTest02() {
        testMethod sizeOfHome = (String input) -> {
            When("I enter " + input + " as the size of the home and submit it");
            LocationsPage.setSizeOfHome(input);
        };
        testMethod alwaysOnTaget = (String input) -> {
            When("I enter " + input + " as the always on target and submit it");
            LocationsPage.setAlwaysOnTarget(input);
        };
        testMethod budget = (String input) -> {
            When("I enter " + input + " as the budget and submit it");
            LocationsPage.setBudget(input);
        };
        testMethod fixedCharges = (String input) -> {
            When("I enter " + input + " as the fixed charge and submit it");
            LocationsPage.setFixedCharges(input);
        };
        testMethod setFlatCost = (String input) -> LocationsPage.setEnergyPrice(input);
        testMethod setTierEnergyRates = (String input) -> {
            When("I enter " + input + " as the tiered energy rate and submit it");
            LocationsPage.setTierEnergyRates(1, input);
            LocationsPage.setTierEnergyRates(2, input);
        };
        testMethod setTaxRate = (String input) -> {
            When("I enter " + input + " as the tax and submit it");
            LocationsPage.setTaxes(input);
        };
        testMethod setPeakPrice = (String input) -> {
            When("I enter " + input + " as the peak rate and submit it");
            LocationsPage.setPeakRate(1, input);
        };

        Report("Setting Page Test 02");

        When("I login");
        LoginPage.signIn(LOGIN, PASSWORD);

        Then("I get to the location settings");
        UserBar.selectSettings();
        SettingsPage.selectSettingsTab(StringRef.SettingTab.LOCATIONS);
        LocationsPage.selectLocation(1);

        When("I enter a new Time Zone and submit it");
        LocationsPage.setTimezone(NEW_YORK);
        LocationsPage.saveChanges();
        Assert.assertTrue(Common.checkForSuccess(), "Unable to save new Time Zone");
        Then("It passes");

        When("I enter a bad account name and submit it");
        LocationsPage.setLocationName(BAD_ACCOUNT_NAME);
        LocationsPage.saveChanges();
        Assert.assertTrue(Common.checkForError(), "Bad Location Name Passed");
        Then("A error is given");

        When("I enter a good account name and submit it");
        LocationsPage.setLocationName(NEW_ACCOUNT_NAME);
        LocationsPage.saveChanges();
        Assert.assertTrue(Common.checkForSuccess(), "Unable to save new Account Name");
        Then("It passes");

        When("I enter a new Postal Code and submit it");
        LocationsPage.setPostalCode(NEW_POSTAL_CODE);
        LocationsPage.saveChanges();
        Assert.assertTrue(Common.checkForSuccess(), "Unable to save new Postal Code");
        Then("It passes");

        When("I enter a new Home Type and submit it");
        LocationsPage.setTypeOfHome(NEW_HOME_TYPE);
        LocationsPage.saveChanges();
        Assert.assertTrue(Common.checkForSuccess(), "Unable to save new Home Type");
        Then("It passes");

        String[] badValues = {ONE_MILLION_ONE, NEGATIVE_ONE, ABC};
        locationPageInputLoop(badValues, sizeOfHome, true);

        When("I enter a valid home size and submit it");
        LocationsPage.setSizeOfHome(TWO);
        LocationsPage.saveChanges();
        Assert.assertTrue(Common.checkForSuccess(), "Unable to save new size of home");
        Then("It passes");

        badValues = new String[]{ONE_MILLION_ONE, NEGATIVE_ONE, ABC, ONE_POINT_ONE};
        locationPageInputLoop(badValues, alwaysOnTaget, true);

        When("I enter a valid always on target value and submit it");
        LocationsPage.setAlwaysOnTarget(TWO);
        LocationsPage.saveChanges();
        Assert.assertTrue(Common.checkForSuccess(), "Unable to save new always on target");
        Then("It passes");

        badValues = new String[]{ONE_MILLION_ONE, "-100000.99", ABC};
        locationPageInputLoop(badValues, budget, true);

        When("I enter a valid budget number and submit it");
        LocationsPage.setBudget(TWO);
        LocationsPage.saveChanges();
        Assert.assertTrue(Common.checkForSuccess(), "Unable to save new budget");
        Then("It passes");

        When("I enter a new billing cycle start date and submit it");
        LocationsPage.setBillingCycleStart(19);
        LocationsPage.saveChanges();
        Assert.assertTrue(Common.checkForSuccess(), "Unable to save new billing cycle");
        Then("It passes");

        badValues = new String[]{ONE_MILLION_ONE, NEGATIVE_ONE, ABC};
        locationPageInputLoop(badValues, fixedCharges, true);

        When("I enter a valid fixed charges vale and submit it");
        LocationsPage.setFixedCharges(TWO);
        LocationsPage.saveChanges();
        Assert.assertTrue(Common.checkForSuccess(), "Unable to save new fixed charges");
        Then("It passes");

        When("I enter a new Billing Type and submit it");
        LocationsPage.setBillingPlanType(StringRef.BillingType.FLAT);
        locationPageInputLoop(badValues, setFlatCost, true);

        When("I enter a new Energy Price and submit it");
        LocationsPage.setEnergyPrice(TWO);
        LocationsPage.saveChanges();
        Assert.assertTrue(Common.checkForSuccess(), "Unable to save new energy price");
        Then("It passes");

        LocationsPage.setBillingPlanType(StringRef.BillingType.TIERED);
        LocationsPage.setNumberOfTiers(2);

        LocationsPage.setTierDetail(1, ONE_MILLION_ONE);

        badValues = new String[]{ONE_MILLION_ONE, NEGATIVE_ONE};
        locationPageInputLoop(badValues, setTierEnergyRates, false);

        When("I enter a valid tier energy rate structure and submit it");
        LocationsPage.setTierDetail(1, TWO);
        LocationsPage.setTierEnergyRates(1, TWO);
        LocationsPage.setTierEnergyRates(2, TWO);
        LocationsPage.saveChanges();
        Assert.assertTrue(Common.checkForSuccess(), "Unable to save new tiered details");
        Then("It passes");

        LocationsPage.setBillingPlanType(StringRef.BillingType.TIME_OF_USE);

        When("I enter a valid Off Peak Price and submit it");
        LocationsPage.setOffPeakPrice(TWO);
        LocationsPage.saveChanges();
        Assert.assertTrue(Common.checkForSuccess(), "Unable to save new off peak pricing");
        Then("It passes");

        When("I enter toggle on Weekend Peak Price and submit it");
        LocationsPage.toggleWeekendPeakPricing(true);
        LocationsPage.saveChanges();
        Assert.assertTrue(Common.checkForSuccess(), "Unable to exclude weekends from off peak price");
        Then("It passes");

        badValues = new String[]{ONE_MILLION_ONE, NEGATIVE_ONE};
        locationPageInputLoop(badValues, setPeakPrice, false);

        When("I add a new peak period");
        LocationsPage.addPeakPeriod();
        Assert.assertTrue(LocationsPage.checksIfPeriodToPeriodIsEditable(2), "Peak 2 From Value can be changed");
        Then("The first value from the second peak period can't be changed");
        When("When I add a third peak period");
        LocationsPage.addPeakPeriod();
        Assert.assertTrue(LocationsPage.checksIfAddPeriodIsThere(), "Can add more than 3 peaks periods");
        Then("Then I cannot add another peak peroid");

        badValues = new String[]{ONE_MILLION_ONE, NEGATIVE_ONE, ABC};
        locationPageInputLoop(badValues, setTaxRate, true);


        Report("Resetting Location Settings");
        LocationsPage.setTimezone(LA);
        LocationsPage.setPostalCode(POSTAL_CODE);
        LocationsPage.setTypeOfHome(HOME_TYPE);
        LocationsPage.setSizeOfHome(ONE);
        LocationsPage.setAlwaysOnTarget(ONE);
        LocationsPage.setBudget(ONE);
        LocationsPage.setBillingCycleStart(1);
        LocationsPage.setFixedCharges(ONE);
        LocationsPage.saveChanges();
        SettingsPage.signOut();

        Report("Setting Page Test 02 Passed!");
    }

    @Test
    public void SettingsPageTest03() {
        Report("Setting Page Test 03");

        String LOCATION_ID = "AY0Ku9ysR2uysWsYllpadQ";
        String adminToken = API.getAuthToken();

        When("I login");
        LoginPage.signIn(LOGIN, PASSWORD);
        UserBar.selectSettings();
        Then("I get to the location settings");
        UserBar.selectSettings();
        SettingsPage.selectSettingsTab(StringRef.SettingTab.LOCATIONS);
        LocationsPage.selectLocation(1);

        Report("Set Location Settings to Default");
        Report("Set Time Zone to LA");
        LocationsPage.setTimezone(LA);
        Report("Set the Postal Code to " + POSTAL_CODE);
        LocationsPage.setPostalCode(POSTAL_CODE);
        Report("Set the Home Type to " + HOME_TYPE);
        LocationsPage.setTypeOfHome(HOME_TYPE);
        Report("Set the Home Size to " + ONE);
        LocationsPage.setSizeOfHome(ONE);
        Report("Set the Budget to " + ONE);
        LocationsPage.setBudget(ONE);
        Report("Set the Billing Cycle Day to " + ONE);
        LocationsPage.setBillingCycleStart(1);
        Report("Set the Fixed Charages to " + ONE);
        LocationsPage.setFixedCharges(ONE);
        Report("Save Location Changes");
        LocationsPage.saveChanges();
        try {
            Report("Send the API Response to check the current location settings");
            HttpResponse<JsonNode> response = API.getRequestJSON(adminToken, "locations/" + LOCATION_ID);
            Report("Check if Response is 200");
            Assert.assertEquals(response.getStatus(), 200, "Response is not 200");
            JSONObject responseBody = response.getBody().getObject();
            Report("Set Postal Code to " + POSTAL_CODE);
            Assert.assertEquals(responseBody.get("postalCode"), POSTAL_CODE, "Postal Code is not " + POSTAL_CODE);
            Report("Check if Home Size is set to " + ONE);
            Assert.assertEquals(responseBody.get("homeSize"), 1, "Home Size is not " + ONE);
            Report("Check if Fixed Charges is set to " + ONE);
            Assert.assertEquals(responseBody.get("fixedCharge"), 1.0, "Fixed Charges is not " + 1.0);
            Report("Check if Home Type is set to " + HOME_TYPE);
            Assert.assertEquals(responseBody.get("homeType"), "house", "Home Type is not house");
            Report("Check if Billing Cycle Day is set to " + ONE);
            Assert.assertEquals(responseBody.get("billingCycleDay"), 1, "Billing Cycle Day is not " + ONE);
            Report("Check if Time Zone is set to LA");
            Assert.assertEquals(responseBody.get("timezone"), "America/Los_Angeles", "Timezone is not LA");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        SettingsPage.signOut();
        Report("Setting Page Test 03 Passed!");
    }

    private void locationPageInputLoop(String[] list, testMethod method, boolean warning) {
        for (String input : list) {
            Common.wait(1);
            method.runMethod(input);
            LocationsPage.saveChanges();
            //Error is an input warning
            if (warning) {
                Assert.assertTrue(Common.checkForError(), "Bad input passed: " + input + " and no error given");
                //Error is an alert banner
            } else {
                Assert.assertTrue(Common.assertError(), "Bad input passed: " + input + " and no error given");
            }
            Then("An a warning is given");
        }
    }

    /**
     * Using Lambda Functions to pass functions as parameter to loop through test cases
     */
    interface testMethod {
        void runMethod(String text);
    }

}
