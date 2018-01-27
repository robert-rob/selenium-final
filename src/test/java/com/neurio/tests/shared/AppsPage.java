package com.neurio.tests.shared;

import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Robert on 6/20/2016.
 * Apps Page
 */
public class AppsPage extends Browser {

    /**
     * Click on the register a new app button
     */
    public static void registerNewAppClick() {
        getElementByClassName(StringRef.BUTTON_PRIMARY_CLASS_NAME).click();
    }

    /**
     * Register a new app
     */
    public static void registerNewApp(String name, String desc, String homeUrl, String callUrl) {
        WebElement nameElement = getElementsByCSS(StringRef.APP_PAGE_REGISTER_INPUT_CSS).get(0);
        WebElement textElement = getElementByCSS(StringRef.APP_PAGE_REGISTER_TEXT_CSS);
        WebElement homeUrlElement = getElementsByCSS(StringRef.APP_PAGE_REGISTER_INPUT_CSS).get(1);
        WebElement callBackUrlElement = getElementsByCSS(StringRef.APP_PAGE_REGISTER_INPUT_CSS).get(2);

        nameElement.sendKeys(name);
        textElement.sendKeys(desc);
        homeUrlElement.sendKeys(homeUrl);
        callBackUrlElement.sendKeys(callUrl);

        getElementByClassName(StringRef.LOADING_BUTTON_CLASS_NAME).click();
    }

    public static boolean checkAppCreation(String name) {
        Common.wait(5);
        Common.waitForElementByLink(StringRef.APPS);
        List<WebElement> list = getElementsByCSS(StringRef.SPAN);
        for (WebElement item : list) {
            if (item.getText().contains(name)) {
                return true;
            }
        }
        return false;
    }
}
