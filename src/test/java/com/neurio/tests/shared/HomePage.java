package com.neurio.tests.shared;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by robert on 2016-05-18.
 * Class to navigate the Home Page
 */
public class HomePage extends Browser {

    /**
     * Implicitly wait for the Home Page to appear
     */
    public static void waitForHomePage() {
        // Wait for Element
        Common.waitForElement(StringRef.MAIN_DASHBOARD_CSS_SELECTOR);
    }

    /**
     * Gets the Current Consumption Value on the Home Page
     */
    public static int getCurrentConsumptionValue() {
        WebElement consumptionElement = getElementByClassName(StringRef.HOME_PAGE_CONSUMPTION_CLASS_NAME);
        return Integer.parseInt(getElementFromParentByClass(consumptionElement, StringRef.VALUE).getText());
    }

    /**
     * Gets the Current Consumption Cost on the Home Page
     */
    public static String getCurrentConsumptionCost() {
        WebElement consumptionElement = getElementByClassName(StringRef.HOME_PAGE_CONSUMPTION_CLASS_NAME);
        return getElementFromParentByClass(consumptionElement, StringRef.COST).getText();
    }

    /**
     * Gets the Current Generation Value on the Home Page
     */
    public static int getCurrentGenerationValue() {
        WebElement consumptionElement = getElementByClassName(StringRef.HOME_PAGE_GENERATION_CLASS_NAME);
        return Integer.parseInt(getElementFromParentByClass(consumptionElement, StringRef.VALUE).getText());
    }

    /**
     * Gets the Current Generation Cost on the Home Page
     */
    public static String getCurrentGenerationCost() {
        WebElement consumptionElement = getElementByClassName(StringRef.HOME_PAGE_GENERATION_CLASS_NAME);
        return getElementFromParentByClass(consumptionElement, StringRef.COST).getText();
    }

    public static String getSolarSavings() {
        return getElementByCSS(StringRef.SOLAR_SAVINGS_TEXT_CSS_SELECTOR).getText();
    }

    public static String getForecastValue() {
        Common.waitForElement(StringRef.FORECAST_VALUE_TEXT_CSS_SELECTOR);
        return getElementByCSS(StringRef.FORECAST_VALUE_SIGN_TEXT_CSS_SELECTOR).getText() +
                getElementByCSS(StringRef.FORECAST_VALUE_TEXT_CSS_SELECTOR).getText();
    }

    /**
     * Checks if it is a clean slate
     */
    public static void checkCleanSlate() {
        Common.waitForElement(".always-on-card.clearfix");
    }

    /**
     * Click on Invite a friend
     */
    public static void inviteFriendClick() {
        List<WebElement> list = getElementsByCSS(StringRef.LINK_BUTTON_CSS);
        for (WebElement element : list) {
            if (element.getText().toLowerCase().contains(StringRef.INVITE_FRIEND)) {
                element.click();
                return;
            }
        }
        throw new NoSuchElementException("Invite Friend Button Not Found");
    }

    /**
     * Click on Solar Savings Details
     */
    public static void clickSolarSavingsDetails() {
        getElementByLinkText(StringRef.SEE_DETAILS).click();
    }


}
