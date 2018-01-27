package com.neurio.tests.shared;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Robert on 2016-05-18.
 * This is a class for common methods used by all helper classes
 */
public class Common extends Browser {

    /**
     * Forces Selenium to wait a set amount of seconds
     *
     * @param seconds - how long in seconds to wait
     */
    public static void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Compares two integers to see if they are close in value or not
     *
     * @param numA First number to compare
     * @param numB Second number to compare
     */
    public static boolean compareTwoNumbers(Double numA, Double numB, double theshold) {
        boolean test;
        if (numA != 0 && (numB > 0.2 || numB < 0)) {
            /*
             * There is two cases; one where numbers > 0, another where numbers < 0
             */
            if (numB >= 0) {
                test = ((numA * (1.0 - theshold)) < numB) && (numB < (numA * (1.0 + theshold)));
            } else {
                test = ((numA * (1.0 - theshold)) > numB) && (numB > (numA * (1.0 + theshold)));
            }
        } else if (numA == 0.0 && numB > 0.2) {
            test = false;
        } else {
            test = true;
        }
        return test;
    }

    /**
     * Changes the timeout of the webdriver
     */
    public static void changeTimeOut(int seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    /**
     * Changes the timeout of the webdriver
     */
    public static void resetTimeOut() {
        int timeOut = Integer.parseInt(getPropertyValue("timeout", "10"));
        driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
    }


    /**
     * Returns String that shows the comparision between the two numbers
     *
     * @param numA First number to compare
     * @param numB Second number to compare
     */
    public static String getTwoNumbersComparisionString(Double numA, Double numB, double threshold) {
        if (numA != 0 && (numB > 0.2 || numB < 0)) {
            /*
             * There is two cases; one where numbers > 0, another where numbers < 0
             */
            String str = "";
            if (numB >= 0) {
                str = "(" + (numA * (1.0 - threshold)) + " < " + numB + ") & (" + numB + " < " +
                        (numA * (1.0 + threshold)) + ") = " + compareTwoNumbers(numA, numB, threshold) +
                        "; Threshold: " + threshold;
            } else {
                str = "(" + (numA * (1.0 - threshold)) + " > " + numB + ") & (" + numB + " > " +
                        (numA * (1.0 + threshold)) + ") = " + compareTwoNumbers(numA, numB, threshold) +
                        "; Threshold: " + threshold;
            }

            if (numA < 1.0 && numA > -1.0) {
                return str.concat(" (Threshold is now 0.1 because the numbers are less than 1.0)");
            } else {
                return str;
            }
        } else if (numA == 0.0 && numB > 0.2) {
            return "One value is 0 and the other value is above 0.2. Failure.";
        } else {
            return "Both values are less than 0.2 and greater than 0. Pass.";
        }
    }


    /**
     * Get properties from the the config properties file
     *
     * @param value        - The particular value wanted
     * @param defaultValue The default value given if method fails
     * @return String property value
     */
    public static String getPropertyValue(String value, String defaultValue) {
        Properties prop = new Properties();
        String propFileName = "config.properties";

        InputStream inputStream;

        try {
            inputStream = new FileInputStream(propFileName);
            prop.load(inputStream);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        // Default value is set to here, if not found
        return prop.getProperty(value, defaultValue);
    }

    /**
     * Get properties from the the config properties file
     *
     * @param value        - The particular value wanted
     * @param defaultValue The default value given if method fails
     * @return String property value
     */
    public static String getValue(String value, String defaultValue) {
        if (System.getProperty(value) == null) {
            return getPropertyValue(value, defaultValue);
        } else {
            return System.getProperty(value, defaultValue);
        }
    }

    /**
     * Gets the mode url
     *
     * @return String - The current mode URL
     */
    public static String getModeURL() {
        String mode;
        if (System.getProperty("mode") == null) {
            mode = Common.getPropertyValue("mode", "staging");
        } else {
            mode = System.getProperty("mode");
        }

        if (mode.contains("staging")) {
            return StringRef.STAGING_HOME_PAGE;
        } else if (mode.contains("qa")) {
            return StringRef.QA_HOME_PAGE;
        } else if (mode.contains("testing")) {
            return StringRef.TEST_HOME_PAGE;
        } else if (mode.contains("development")) {
            return StringRef.DEV_HOME_PAGE;
            // Custom URL used here
        } else if (mode.contains(StringRef.CUSTOM)) {
            String url;
            if (System.getProperty("url") == null) {
                url = Common.getPropertyValue("url", "https://sherlock-qa-env.elasticbeanstalk.com");
            } else {
                url = System.getProperty("url");
            }
            return url;
        } else {
            return StringRef.PRODUCTION_HOME_PAGE;
        }
    }

    /**
     * Gets the mode url
     *
     * @return String - The current mode URL
     */
    public static String getAPIModeURL() {
        String mode;
        if (System.getProperty("mode") == null) {
            mode = Common.getPropertyValue("mode", "staging");
        } else {
            mode = System.getProperty("mode");
        }

        if (mode.contains("staging")) {
            return StringRef.API_STAGING_URL_PREFIX;
        } else if (mode.contains("qa")) {
            return StringRef.API_QA_URL_PREFIX;
        } else if (mode.contains("development")) {
            return StringRef.API_DEV_URL_PREFIX;
            // Custom URL used here
        } else if (mode.contains(StringRef.CUSTOM)) {
            String url;
            if (System.getProperty("url") == null) {
                url = Common.getPropertyValue("apiURL", "https://sherlock-qa-env.elasticbeanstalk.com") + "/v1/";
            } else {
                url = System.getProperty("url") + "/v1/";
            }
            return url;
        } else {
            return StringRef.API_URL_PREFIX;
        }
    }

    /**
     * Returns if OS is Linux or not
     *
     * @return boolean
     */
    public static boolean isLinuxOS() {
        return System.getProperty("os.name").contains(StringRef.LINUX);
    }

    /**
     * Returns if History Page Data or not
     *
     * @return boolean
     */
    public static boolean isUnixOS() {
        return System.getProperty("os.name").contains(StringRef.LINUX) || System.getProperty("os.name").contains(StringRef.MAC);
    }
    public static boolean hasNoHistoryData() {
        changeTimeOut(Integer.parseInt(getPropertyValue("quick_timeout", "3")));
        boolean value = getElementsByClassName(StringRef.NO_DATAICON_CLASS).size() > 0;
        Common.resetTimeOut();
        return value;
    }

    /**
     * Gets the mode url
     *
     * @return String - The current mode URL
     */
    public static String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    /**
     * Check for element to be on web page
     *
     * @param selector - CSS selector for the element
     * @return boolean true or false
     */
    public static boolean checkForElement(String selector) {
        return driver.findElements(By.cssSelector(selector)).size() != 0;
    }

    /**
     * Check for element to be on web page
     *
     * @param selector - CSS selector for the element
     * @return boolean true or false
     */
    public static boolean checkForElementByID(String selector) {
        return driver.findElements(By.id(selector)).size() != 0;
    }

    /**
     * Implicitly wait an error alert to appear on page
     */
    public static boolean assertError() {
        try {
            Common.waitForElement(StringRef.ERROR_ALERT_CSS_SELECTOR);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Check for error to be on web page
     *
     * @return boolean true or false
     */
    public static boolean checkForError() {
        try {
            waitForErrors();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Check for success to be on web page
     *
     * @return boolean true or false
     */
    public static boolean checkForSuccess() {
        try {
            waitForSuccess();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Check if an error occurred on page
     */
    public static void waitForErrors() {
        Common.waitForElementClass(StringRef.HAS_ERROR_CLASS_NAME);
    }

    /**
     * Check if a success alert occurred on page
     */
    public static void waitForSuccess() {
        Common.waitForElementClass(StringRef.ALERT_SUCCESS);
    }

    /**
     * Enter value in a web element by name
     *
     * @param name  - Name selector
     * @param input - Input to be entered
     */
    public static void enterValueInElementByName(String name, String input) {
        getElementByName(name).clear();
        getElementByName(name).sendKeys(input);
    }

    /**
     * Implicitly wait for an element to appear for 10 seconds
     *
     * @param selector - CSS selector for the element
     */
    public static void waitForElement(final String selector) {
        int maxTime = Integer.parseInt(getPropertyValue("timeout", "10"));
        (new WebDriverWait(driver, maxTime)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElements(By.cssSelector(selector)).size() != 0;

            }
        });
    }

    /**
     * Implicitly wait for an element to appear for 10 seconds
     *
     * @param selector - Class name selector for the element
     */
    public static void waitForElementClass(final String selector) {
        int maxTime = Integer.parseInt(getPropertyValue("timeout", "10"));
        (new WebDriverWait(driver, maxTime)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElements(By.className(selector)).size() != 0;
            }
        });
    }

    /**
     * Implicitly wait for an element to appear for 10 seconds
     *
     * @param selector - ID selector for the element
     */
    public static void waitForElementID(final String selector) {
        int maxTime = Integer.parseInt(getPropertyValue("timeout", "10"));
        (new WebDriverWait(driver, maxTime)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElements(By.id(selector)).size() != 0;
            }
        });
    }

    /**
     * Implicitly wait for an element to appear for 10 seconds
     *
     * @param selector - link text selector for the element
     */
    public static void waitForElementByLink(final String selector) {
        int maxTime = Integer.parseInt(getPropertyValue("timeout", "10"));
        (new WebDriverWait(driver, maxTime)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElements(By.linkText(selector)).size() != 0;

            }
        });
    }

    /**
     * Take a screenshot during the test
     *
     * @param filename - The file name to save as
     */
    public static String takeScreenshot(String filename) {
        String str = "";
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            str = "target/web-screenshots/" + filename + ".png";
            FileUtils.copyFile(scrFile, new File(str));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * Take a screenshot during the test
     *
     * @param filename - The file name to save as
     */
    public static String takeScreenshotFile(String filename) {
        String str = "";
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            str = filename + ".png";
            FileUtils.copyFile(scrFile, new File(str));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * Return the Date as a string
     */
    public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Return the Local Time based on Timezone
     */
    public static LocalDateTime getLocalTime(String timezone) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timezone));

        LocalDateTime currentDateTime = LocalDateTime.of(calendar.get(Calendar.YEAR),
                (calendar.get(Calendar.MONTH) + 1),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE));

        return currentDateTime;
    }

    /**
     * Close the Alert Dialog
     */
    public static void closeAlertDialog() {
        getElementByCSS(StringRef.ALERT_DIALOG_CLOSE_CSS).click();
    }
}
