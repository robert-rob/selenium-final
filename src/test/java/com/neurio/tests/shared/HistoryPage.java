package com.neurio.tests.shared;

import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Robert on 8/12/2016.
 * History Page
 */
public class HistoryPage extends Browser {
    /**
     * Selects the dropdown menu
     */
    private static void openDropDownMenu() {
        getElementByClassName(StringRef.DROP_DOWN_MENU_CLASS_NAME).click();
    }

    /**
     * Selects a particular Granularity graph
     *
     * @param graph - The Graph you want to select
     */
    public static void selectGranularity(StringRef.Granularity graph) {
        openDropDownMenu();
        getElementByLinkText(StringRef.granularityMapEnumToString.get(graph)).click();
        Common.waitForElementClass(StringRef.HISTORY_GRAPH_CLASS);
    }

    public static void selectCustomTimeFrame(String date1, String date2) {
        Common.waitForElement("#" + StringRef.START_DATE_INPUT_ID);
        Common.wait(2);
        getElementByID(StringRef.START_DATE_INPUT_ID).clear();
        getElementByID(StringRef.START_DATE_INPUT_ID).sendKeys(date1);
        getElementByID(StringRef.END_DATE_INPUT_ID).clear();
        getElementByID(StringRef.END_DATE_INPUT_ID).sendKeys(date2);
        getElementByClassName(StringRef.CUSTOM_DATE_DIALOG_CLASS).click();
        getElementByClassName(StringRef.LOADING_BUTTON_CLASS_NAME).click();

    }

    public static void selectEnergyOverTimeGraph() {
        getElementByLinkText(StringRef.OVER_TIME).click();
        Common.wait(2);
    }

    public static void selectSolarSavingsGraph() {
        getElementByLinkText(StringRef.SOLAR_SAVINGS).click();
    }

    public static void selectPowerOverTimeGraph() {
        getElementByCSS(StringRef.POWER_OVER_TIME_CSS_SELECTOR).click();
    }

    public static void selectLeft() {
        getElementByCSS(StringRef.LEFT_GRAPH_ARROW_CSS).click();
    }

    public static void selectRight() {
        getElementByCSS(StringRef.RIGHT_GRAPH_ARROW_CSS).click();
    }

    public static void selectPrevButton() {
        getElementByCSS(StringRef.HISTORY_GRAPH_PREV_BUTTON).click();
    }

    public static boolean hasGeneration() {
        List<WebElement> list = getElementsByCSS(StringRef.HEADING_CSS);
        for (WebElement item : list) {
            if (item.getText().contains("Generation")) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasBlankCard() {
        Common.changeTimeOut(Integer.parseInt(Common.getPropertyValue("quick_timeout", "3")));
        boolean value = getElementsByClassName(StringRef.BLANK_CARD_CLASS_NAME).size() != 0;
        Common.resetTimeOut();
        return value;
    }
    public static boolean noGraphData() {
        Common.changeTimeOut(Integer.parseInt(Common.getPropertyValue("quick_timeout", "3")));
        boolean value = getElementsByClassName(StringRef.NO_GRAPH_DATA).size() != 0;
        Common.resetTimeOut();
        return value;
    }

    public static String getValue(String type, String value) {
        try {
            switch (type) {
                case "generation":
                    if (value.contains(StringRef.LOWEST)) {
                        return getElementByCSS(StringRef.GENERATION_LOWEST_DIV_CSS).getText().replaceAll(",", "");
                    } else if (value.contains(StringRef.HIGHEST)) {
                        return getElementByCSS(StringRef.GENERATION_HIGHEST_DIV_CSS).getText().replaceAll(",", "");
                    } else if (value.contains(StringRef.AVERAGE)) {
                        return getElementByCSS(StringRef.GENERATION_AVERAGE_DIV_CSS).getText().replaceAll(",", "");
                    } else {
                        return getElementByCSS(StringRef.GENERATION_TOTAL_DIV_CSS).getText().replaceAll(",", "");
                    }
                case "consumption":
                    if (value.contains(StringRef.LOWEST)) {
                        return getElementByCSS(StringRef.CONSUMPTION_LOWEST_DIV_CSS).getText().replaceAll(",", "");
                    } else if (value.contains(StringRef.HIGHEST)) {
                        return getElementByCSS(StringRef.CONSUMPTION_HIGHEST_DIV_CSS).getText().replaceAll(",", "");
                    } else if (value.contains(StringRef.AVERAGE)) {
                        return getElementByCSS(StringRef.CONSUMPTION_AVERAGE_DIV_CSS).getText().replaceAll(",", "");
                    } else {
                        return getElementByCSS(StringRef.CONSUMPTION_TOTAL_DIV_CSS).getText().replaceAll(",", "");
                    }
                case "net":
                    if (value.contains(StringRef.LOWEST)) {
                        return getElementByCSS(StringRef.NET_LOWEST_DIV_CSS).getText().replaceAll(",", "");
                    } else if (value.contains(StringRef.HIGHEST)) {
                        return getElementByCSS(StringRef.NET_HIGHEST_DIV_CSS).getText().replaceAll(",", "");
                    } else if (value.contains(StringRef.AVERAGE)) {
                        return getElementByCSS(StringRef.NET_AVERAGE_DIV_CSS).getText().replaceAll(",", "");
                    } else {
                        return getElementByCSS(StringRef.NET_TOTAL_DIV_CSS).getText().replaceAll(",", "");
                    }
                default:
                    if (value.contains(StringRef.LOWEST)) {
                        return getElementByCSS(StringRef.CONSUMPTION_LOWEST_DIV_CSS).getText().replaceAll(",", "");
                    } else if (value.contains(StringRef.HIGHEST)) {
                        return getElementByCSS(StringRef.CONSUMPTION_HIGHEST_DIV_CSS).getText().replaceAll(",", "");
                    } else if (value.contains(StringRef.AVERAGE)) {
                        return getElementByCSS(StringRef.CONSUMPTION_AVERAGE_DIV_CSS).getText().replaceAll(",", "");
                    } else {
                        return getElementByCSS(StringRef.CONSUMPTION_TOTAL_DIV_CSS).getText().replaceAll(",", "");
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "-1000000000000.0";
        }
    }

    public static boolean getPositiveColourValue(StringRef.Energy energy, StringRef.Stats stats) {
        WebElement element;
        Common.changeTimeOut(3);
        try {
            element = getElementByCSS(StringRef.getSumaryValueDivStringByEnergyStats(energy, stats));

        } catch (Exception e) {
            System.out.println("cannot find element: print stack trace");
            e.printStackTrace();
            Common.resetTimeOut();
            return false;
        }
        boolean value = false;
        if (element != null) {
            value = element.getAttribute("class").contains(StringRef.GENERATION_LOWER_CASE);
        }
        Common.resetTimeOut();
        return value;
    }
}
