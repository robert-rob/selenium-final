package com.neurio.tests.shared;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by soroushnorouzi on 2016-10-24.
 */
public class AndroidWrapper {
    private AndroidDriver androidDriver;

    public AndroidWrapper() {
        startAndroidServer();
    }

    public WebElement getElementByID(String name) {
        return androidDriver.findElement(By.id(name));
    }

    public WebElement getElementByName(String name) {
        return androidDriver.findElement(By.name(name));
    }

    public WebElement getTextViewByText(String text) {
        List<WebElement> elements = androidDriver.findElements(By.xpath("//android.widget.TextView"));
        for (WebElement element : elements) {
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

    public AndroidDriver startAndroidServer() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("deviceName", "My New Phone");
        capabilities.setCapability("platformVersion", "7.1");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("newCommandTimeout", "3600");

        capabilities.setCapability("appPackage", "com.neurio.neuriohome");
        capabilities.setCapability("appActivity", ".activity.MainActivity");
        try {
            androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            int timeOut = Integer.parseInt(Common.getPropertyValue("timeout", "10"));
            androidDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
            return androidDriver;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void login(String username, String password) {
        getElementByID(StringRef.ANDROID_EMAIL_EDIT_TEST_ID).sendKeys(username);
        getElementByID(StringRef.ANDROID_PASSWORD_FIELD_ID).sendKeys(password);
        getElementByID(StringRef.ANDROID_SUBMIT_BUTTON_ID).click();
        Common.wait(5);
    }

    public void switchAccount(String accountHandle) {
        getElementByID(StringRef.ANDROID_MORE_BUTTON_ID).click();
        getTextViewByText("Settings").click();
        String androidAccountName;

        getElementByID(StringRef.ANDROID_SWTICH_LOCATION_BUTTON_ID).click();

        if (System.getProperty("android_account_name") == null) {
            androidAccountName = Common.getPropertyValue("android_account_name", accountHandle);
        } else {
            androidAccountName = System.getProperty("android_account_name", accountHandle);
        }

        getTextViewByText(androidAccountName).click();

        Common.wait(3);

        androidDriver.pressKeyCode(AndroidKeyCode.BACK);

        getElementByID(StringRef.ANDROID_HOME_BUTTON_ID).click();
    }

    /**
     * Take a screenshot during the test
     *
     * @param filename - The file name to save as
     */
    public String takeScreenshotLocal(String directory, String filename) {
        String fileName = directory + "/" + filename + ".png";
        try {
            File scrFile = ((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(fileName));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return fileName;
    }
}
