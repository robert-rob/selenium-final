package com.neurio.tests.shared;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Robert on 5/24/2016.
 * Class to navigate the Locations Page
 */
public class LocationsPage extends Browser {

    /**
     * Helper function for selecting an item from a drop down form
     *
     * @param formName The name attribute of the select web element
     * @param item     Item to be found and selected
     */
    private static void selectorFindHelper(String formName, String item) {
        WebElement selector = getElementByName(formName);
        selector.click();
        List<WebElement> elementsList = selector.findElements(By.cssSelector(StringRef.OPTION));
        elementListHelper(elementsList, item);
    }


    /**
     * Helper function for selecting an item from a drop down form
     *
     * @param elementsList The select web element
     * @param item         Item to be found and selected
     */
    private static void elementListHelper(List<WebElement> elementsList, String item) {
        for (WebElement element : elementsList) {
            String test = element.getText();
            if (element.getText().contains(item)) {
                element.click();
                return;
            }
        }
        throw new NoSuchElementException(item + " not found");
    }

    /**
     * Selects first location found with the given location in the locations list
     *
     * @param location given location
     */
    public static void selectLocation(String location) {
        List<WebElement> elementsList = getElementsByCSS(StringRef.LOCATION_LIST_ITEMS_CLASS_NAME);
        elementListHelper(elementsList, location);
    }

    /**
     * Selects first location found with the given index in the locations list (index 1 based)
     *
     * @param number given index
     */
    public static void selectLocation(int number) {
        List<WebElement> elementsList = getElementsByCSS(StringRef.LOCATION_LIST_ITEMS_CSS_SELECTOR);
        WebElement locationElement = elementsList.get(number + 1);
        locationElement.click();
    }

    /**
     * Inputs a new location name
     *
     * @param newName New location name
     */
    public static void setLocationName(String newName) {
        getElementByName(StringRef.NAME).clear();
        getElementByName(StringRef.NAME).sendKeys(newName);
    }

    /**
     * Inputs a new time zone
     *
     * @param timezone New time zone
     */
    public static void setTimezone(String timezone) {
        selectorFindHelper(StringRef.TIMEZONE, timezone);
    }

    /**
     * Inputs a new postal code
     *
     * @param postalCode New postal code
     */
    public static void setPostalCode(String postalCode) {
        Common.enterValueInElementByName(StringRef.POSTAL_CODE, postalCode);
    }

    /**
     * Inputs a new home type
     *
     * @param homeType New home type
     */
    public static void setTypeOfHome(String homeType) {
        selectorFindHelper(StringRef.HOME_TYPE, homeType);
    }

    /**
     * Inputs a new home size
     *
     * @param homeSize New home size
     */
    public static void setSizeOfHome(String homeSize) {
        Common.enterValueInElementByName(StringRef.HOME_SIZE, homeSize);
    }

    /**
     * Inputs new number of people
     *
     * @param number New number of people
     */
    public static void setNumberOfPeople(String number) {
        Common.enterValueInElementByName(StringRef.RESIDENTS, number);
    }

    /**
     * Inputs a new always on target
     *
     * @param target New always on target
     */
    public static void setAlwaysOnTarget(String target) {
        Common.enterValueInElementByName(StringRef.ALWAYS_ON_TARGET, target);
    }

    /**
     * Inputs a new budget
     *
     * @param budget New Budget
     */
    public static void setBudget(String budget) {
        Common.enterValueInElementByName(StringRef.BUDGET, budget);
    }

    /**
     * Inputs a new biling cycle start day
     *
     * @param day New start day
     */
    public static void setBillingCycleStart(int day) {
        selectorFindHelper(StringRef.BILLING_CYCLE_DAY, String.valueOf(day));
    }

    /**
     * Inputs a new fixed charges
     *
     * @param cost New fixed charges cost
     */
    public static void setFixedCharges(String cost) {
        Common.enterValueInElementByName(StringRef.FIXED_CHARGE, cost);
    }

    /**
     * Inputs a new billing type
     *
     * @param type New billing plan type
     */
    public static void setBillingPlanType(StringRef.BillingType type) {
        selectorFindHelper(StringRef.BILLING_TYPE, StringRef.billingTypeMapEnumToString.get(type));
    }

    /**
     * Inputs a new energy price rate
     *
     * @param rate New energy price rate
     */
    public static void setEnergyPrice(String rate) {
        Common.enterValueInElementByName(StringRef.ENERGY_RATE, rate);
    }

    /**
     * Inputs a new number of tiers
     *
     * @param tier New number of tiers
     */
    public static void setNumberOfTiers(int tier) {
        selectorFindHelper(StringRef.NUM_TIERS, String.valueOf(tier));
    }

    /**
     * Inputs an off peak price
     *
     * @param price
     */
    public static void setOffPeakPrice(String price) {
        Common.enterValueInElementByName(StringRef.OFF_PEAK_PRICE, price);
    }

    /**
     * Toggles the weekend option on or off
     *
     * @param toggleOn Choose to toggle on or off
     */
    public static void toggleWeekendPeakPricing(boolean toggleOn) {
        if (getElementByName(StringRef.WEEKENDS).isSelected() != toggleOn) {
            getElementByName(StringRef.WEEKENDS).click();
        }
    }

    /**
     * Inputs a new Tier detail
     *
     * @param tier   Tier
     * @param detail New Tier Detail
     */
    public static void setTierDetail(int tier, String detail) {
        Common.enterValueInElementByName(StringRef.getTierDetailsNameString(tier), detail);
    }

    /**
     * Inputs a new Tier rate
     *
     * @param tier Tier
     * @param rate New Tier Rate
     */
    public static void setTierEnergyRates(int tier, String rate) {
        Common.enterValueInElementByName(StringRef.getTierEnergyRateNameString(tier), rate);
    }

    /**
     * Inputs a new Peak rate
     *
     * @param peak Peak
     * @param rate New Tier Rate
     */
    public static void setPeakRate(int peak, String rate) {
        Common.enterValueInElementByName(StringRef.getPeaPriceString(peak), rate);
    }

    /**
     * Remove Peak Period
     */
    public static void RemovePeakPeriod() {
        getElementsByCSS(StringRef.ADD_PEAK_BUTTON).get(0).click();
    }

    /**
     * Adds a new peak period
     */
    public static void addPeakPeriod() {
        getElementByCSS(StringRef.ADD_PEAK_BUTTON).click();
    }

    /**
     * Remove Peak Period
     */
    public static boolean checksIfAddPeriodIsThere() {
        List<WebElement> elementList = getElementsByCSS(StringRef.ADD_PEAK_BUTTON);
        boolean found = false;
        for (WebElement element : elementList) {
            if (element.getText().contains(StringRef.ADD)) {
                found = true;
                break;
            }
        }
        return found;
    }

    public static boolean checksIfPeriodToPeriodIsEditable(int peak) {
        return getElementsByCSS(StringRef.getPeakToString(peak)).size() == 0;
    }

    /**
     * Inputs a new tax rate
     *
     * @param tax New tax rate
     */
    public static void setTaxes(String tax) {
        Common.enterValueInElementByName(StringRef.TAX_RATE, tax);
    }

    /**
     * Save location changes
     */
    public static void saveChanges() {
        getElementByCSS(StringRef.SAVE_LOCATION_CHANGES_BUTTON_CSS_SELECTOR).click();
    }
}
