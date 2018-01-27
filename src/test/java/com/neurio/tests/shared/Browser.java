package com.neurio.tests.shared;

/**
 * Created by Robert on 2016-05-18.
 * This class setups the browser web driver before testing can begin
 */

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Browser {

    public static WebDriver driver;
    private static String browser;

    /**
     * The following are helper functions to get web elements from the web page using ID, Class Names, etc.
     */
    public static WebElement getElementByClassName(String name) {
        return driver.findElement(By.className(name));
    }

    public static WebElement getElementByID(String name) {
        return driver.findElement(By.id(name));
    }

    public static WebElement getElementByCSS(String name) {
        return driver.findElement(By.cssSelector(name));
    }

    public static WebElement getElementByName(String name) {
        return driver.findElement(By.name(name));
    }

    public static WebElement getElementByLinkText(String name) {
        return driver.findElement(By.linkText(name));
    }

    public static List<WebElement> getElementByTag(String tag) {
        return driver.findElements(By.tagName(tag));
    }

    public static WebElement getElementByXpath(String name) {
        return driver.findElement(By.xpath(name));
    }

    public static List<WebElement> getElementsByCSS(String name) {
        return driver.findElements(By.cssSelector(name));
    }

    public static List<WebElement> getElementsByClassName(String name) {
        return driver.findElements(By.className(name));
    }

    public static WebElement getElementFromParentByClass(WebElement element, String name) {
        return element.findElement(By.className(name));
    }

    public static WebElement getElementFromParentByCSS(WebElement element, String name) {
        return element.findElement(By.cssSelector(name));
    }

    public static WebElement getElementFromParentByName(WebElement element, String name) {
        return element.findElement(By.name(name));
    }

    public static List<WebElement> getElementsFromParentByCSS(WebElement element, String name) {
        return element.findElements(By.cssSelector(name));
    }

    /**
     * Gets the title of the web page
     */
    public static String getTitle() {
        return driver.getTitle();
    }

    /**
     * These methods help the browser navigate
     */
    public static void refresh() {
        driver.navigate().refresh();
    }

    public static void goTo(String url) {
        driver.get(url);
    }

    public static void goBack() {
        driver.navigate().back();
    }

    public static void goForward() {
        driver.navigate().forward();
    }

    public static void close() {
        driver.close();
    }

    public static void quit() {
        driver.quit();
    }

    public static void goHome() {
        driver.get(Common.getModeURL());
        HomePage.waitForHomePage();
    }

    /**
     * Reads the arguments in the config.properties file
     * Setups the browser based on the browser entry given in
     * the config file
     */
    @BeforeSuite
    public void initializeBrowser() {
        // Default value is set to firefox here, if not found
        if (System.getProperty("browser") == null) {
            browser = Common.getPropertyValue("browser", StringRef.FIREFOX);
        } else {
            browser = System.getProperty("browser");
        }

        if (browser.contains(StringRef.CHROME)) {
            if (Common.isLinuxOS()) {
                File chromeDriver = new File("/home/ubuntu/chromedriver");
                System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
                driver = new ChromeDriver();
            } else {
                try {
                    URL url = new URL("http://localhost:9515");
                    driver = new RemoteWebDriver(url, DesiredCapabilities.chrome());
                } catch (MalformedURLException e) {
                    System.out.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } else if (browser.contains(StringRef.HTMLUNIT)) {
            driver = new HtmlUnitDriver();
        } else if (browser.contains(StringRef.INTERNET_EXPLORER)) {
            File file = new File("C:\\Program Files (x86)\\Selenium\\IEDriverServer.exe");
            System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
            DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
            caps.setCapability("ignoreZoomSetting", true);
            driver = new InternetExplorerDriver(caps);
        } else {
            System.setProperty("webdriver.gecko.driver", "C:\\Program Files\\geckodriver-v0.11.1-win64" +
                    "\\geckodriver.exe");
            driver = new FirefoxDriver();
        }

        // Default value is set to 10 here, if not found
        int timeOut = Integer.parseInt(Common.getPropertyValue("timeout", "10"));
        driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    /**
     * Closes browser after suite is complete; If the version is mobile, stop the Appium server
     */
    @AfterSuite
    public void closeBrowser() {
        driver.quit();
    }
}
