package com.neurio.tests.shared;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

/**
 * Created by soroushnorouzi on 2016-11-01.
 */
public class IosWrapper {
    private AppiumDriver iosDriver;
    private int port = 4723;

    public IosWrapper() {
        this.iosDriver = startIosDriver(this.port);
    }

    public IosWrapper(int port) {
        this.port = port;
        this.iosDriver = startIosDriver(this.port);
    }

    public void login(String userName, String password) {
        WebElement inputUserName = iosDriver.findElementByAccessibilityId("editText/userName");
        WebElement inputPassword = iosDriver.findElementByAccessibilityId("editText/password");
        WebElement buttonLogin = iosDriver.findElementByAccessibilityId("button/login");
        inputUserName.clear();
        inputUserName.sendKeys(userName);

        inputPassword.clear();
        inputPassword.sendKeys(password);

        buttonLogin.click();
    }

    public String getForecast() {
        try {
            return iosDriver.findElementByAccessibilityId("UILabel/forecast").getAttribute("label").toString();
        } catch (Exception e) {
            System.out.println("Exception while getting forecast from the iOS driver:  " + e.getMessage());
            return null;
        }
    }

    public void refreshHome() {
        Common.wait(1);
        iosDriver.findElementByAccessibilityId("button/more").click();
        Common.wait(1);
        iosDriver.findElementByAccessibilityId("button/home").click();
        Common.wait(1);
    }

    public void signOut() {
        Common.wait(1);
        iosDriver.findElementByAccessibilityId("button/more").click();
        Common.wait(1);
        iosDriver.findElementByAccessibilityId("button/sign out").click();
        Common.wait(1);
    }

    private AppiumDriver startIosDriver(int port) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium-version", "1.0");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "9.3");
        capabilities.setCapability("deviceName", "iPhone Simulator");
        capabilities.setCapability("newCommandTimeout", "3600");
        capabilities.setCapability("automationName", "XCUItest");

        try {
            iosDriver = new IOSDriver(new URL("http://0.0.0.0:" + port + "/wd/hub"), capabilities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iosDriver;
    }
}
