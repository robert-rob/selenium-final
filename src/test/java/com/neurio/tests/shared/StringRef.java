package com.neurio.tests.shared;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Robert on 2016-05-18.
 * This is a central repository for String values
 */

public class StringRef {

    //Login Page Variables
    public static final String SIGN_IN_BUTTON_CSS_SELECTOR = ".signin-button";
    public static final String ERROR_ALERT_CSS_SELECTOR = ".alert-error";
    public static final String LOGIN_SIGN_IN_CSS_SELECTOR = ".signin";
    //User Bar Variables
    public static final String USERNAME_TEXT_BOX_CSS_SELECTOR = ".userbar-text > span";
    public static final String ADMIN_DROPDOWN_TOGGLE_CSS_SELECTOR = ".dropdown-toggle";
    public static final String ADMIN_INPUT_CLASS_NAME = "admin-input";
    public static final String ID_INPUT_CSS_SELECTOR = ".input-wrapper > input";
    public static final String ADMIN_DROPDOWN_MENU_CSS_SELECTOR = ".userbar-text .dropdown-menu";
    public static final String ADMIN_SIGN_OUT_CSS_SELECTOR = "#multiple-selects > ul > li > a";
    public static final String SETTINGS_CSS_SELECTOR = ".userbar-text > a";
    //Home Page Variables
    public static final String MAIN_DASHBOARD_CSS_SELECTOR = ".main.dashboard";
    public static final String HOME_PAGE_CONSUMPTION_CLASS_NAME = "consumption";
    public static final String HOME_PAGE_GENERATION_CLASS_NAME = "generation";
    public static final String TABS_CSS_SELECTOR = ".header > nav > a > span";
    public static final String SOLAR_SAVINGS_TEXT_CSS_SELECTOR = ".savings > span:nth-child(2)";
    public static final String FORECAST_VALUE_SIGN_TEXT_CSS_SELECTOR = ".forecast-card .big-num span:nth-child(2)";
    public static final String FORECAST_VALUE_TEXT_CSS_SELECTOR = ".forecast-card .big-num span:nth-child(3)";
    public static final String LINK_BUTTON_CSS = "button.link.btn";
    public static final String INVITE_FRIEND = "invite a friend";
    public static final String SEE_DETAILS = "See details";
    //Setting Page Variables
    public static final String SETTINGS_LIST_ITEMS_CSS_SELECTOR = ".settings-sidebar > .list > a";
    public static final String ACCOUNT_DETAILS_NAME_INPUT_CSS_SELECTOR = ".first-card input";
    public static final String ACCOUNT_DETAILS_SAVE_CHANGES_BUTTON_CSS_SELECTOR = ".first-card .loading-button";
    public static final String ACCOUNT_DETAILS_HELP_BLOCK_CSS_SELECTOR = ".first-card .help-block";
    public static final String CURRENT_PASSWORD_NAME = "currentPassword";
    public static final String CONFIRM_PASSWORD_NAME = "confirmPassword";
    public static final String NEW_EMAIL_NAME = "newEmail";
    public static final String CONFIRM_EMAIL_NAME = "confirmEmail";
    public static final String PASSWORD_INPUT_CLASS_NAME = "password-input";
    public static final String CHANGE_PASSWORD_BUTTON_CSS_SELECTOR = ".loading-button:nth-child(4)";
    public static final String CHANGE_EMAIL_BUTTON_CSS_SELECTOR = ".btn-primary:nth-child(1)";
    public static final String HAS_ERROR_CLASS_NAME = "has-error";
    public static final String CHANGE_EMAIL_DIALOG_CLASS_NAME = "modal-body";
    public static final String CONFIRM_BUTTON_CLASS_NAME = "confirm-button";
    public static final String CLOSE_BUTTON_CLASS_NAME = "close-button";
    public static final String SETTINGS_SIGN_OUT_CSS_SELECTOR = ".userbar-text span:nth-child(3)";
    //Location Page Variables
    public static final String LOCATION_LIST_ITEMS_CLASS_NAME = "settings-locations-item";
    public static final String LOCATION_LIST_ITEMS_CSS_SELECTOR = ".settings-text span";
    public static final String SAVE_LOCATION_CHANGES_BUTTON_CSS_SELECTOR =
            "body > div > div > div.content > div > div.main.settings > form > div > div.body > button";
    public static final String TIMEZONE = "timezone";
    public static final String POSTAL_CODE = "postalCode";
    public static final String HOME_TYPE = "homeType";
    public static final String HOME_SIZE = "homeSize";
    public static final String RESIDENTS = "residents";
    public static final String ALWAYS_ON_TARGET = "alwaysOnTarget";
    public static final String BUDGET = "budget";
    public static final String BILLING_CYCLE_DAY = "billingCycleDay";
    public static final String FIXED_CHARGE = "fixedCharge";
    public static final String BILLING_TYPE = "billingType";
    public static final String TAX_RATE = "taxRate";
    public static final String ENERGY_RATE = "energyRate";
    public static final String NUM_TIERS = "numTiers";
    public static final String WEEKENDS = "weekends";
    public static final String OFF_PEAK_PRICE = "offPeakPrice";
    public static final String ADD_PEAK_BUTTON = ".pricing button";
    public static final String PRICING_TIERS = "pricingTiers-";
    public static final String PEAK = "peak-";
    //Reseller Page Variables
    public static final String RESELLER_INPUT_CSS = ".panel-body input";
    public static final String RESELLER_SPAN_CSS = ".settings-reseller-item span";
    //Apps Page Variables
    public static final String APP_PAGE_REGISTER_INPUT_CSS = ".first-card input";
    public static final String APP_PAGE_REGISTER_TEXT_CSS = ".first-card textarea";
    //Export Data Page Variables
    public static final String EXPORT_DATA_GRANULARITY_CSS = "select.form-control";
    //History Page Variables
    public static final String DROP_DOWN_MENU_CLASS_NAME = "fa-ellipsis-v";
    public static final String OVER_TIME = "Over Time";
    public static final String SOLAR_SAVINGS = "Solar Savings";
    public static final String POWER_OVER_TIME_CSS_SELECTOR =
            "body > div > div > div.content > div > aside > div > div > ul > li:nth-child(5) > a";
    public static final String LEFT_GRAPH_ARROW_CSS =
            "body > div > div > div.content > div > div.main.trends > div.card.first-card > " +
                    "div.pager.pager-period-selector > div > a.pager-button-prev > i";
    public static final String RIGHT_GRAPH_ARROW_CSS =
            "body > div > div > div.content > div > div.main.trends > div.card.first-card > " +
                    "div.pager.pager-period-selector > div > a.pager-button-next > i";

    public static final String CONSUMPTION_DIV_PREFIX_CSS = "body > div > div > div.content > div > " +
            "div.main.trends > div:nth-child(2) > div.body > div > ";
    public static final String CONSUMPTION_HIGHEST_DIV_CSS = CONSUMPTION_DIV_PREFIX_CSS +
            "div:nth-child(1) > div > span.summary-box-value-box > span.summary-box-value";
    public static final String CONSUMPTION_LOWEST_DIV_CSS = CONSUMPTION_DIV_PREFIX_CSS +
            "div:nth-child(2) > div > span.summary-box-value-box > span.summary-box-value";
    public static final String CONSUMPTION_AVERAGE_DIV_CSS = CONSUMPTION_DIV_PREFIX_CSS +
            "div:nth-child(3) > div > span.summary-box-value-box > span.summary-box-value";
    public static final String CONSUMPTION_TOTAL_DIV_CSS = CONSUMPTION_DIV_PREFIX_CSS +
            "div:nth-child(4) > div > span.summary-box-value-box > span.summary-box-value";

    public static final String GENERATION_DIV_PREFIX_CSS = "body > div > div > div.content > div > " +
            "div.main.trends > div:nth-child(3) > div.body > div > ";
    public static final String GENERATION_HIGHEST_DIV_CSS = GENERATION_DIV_PREFIX_CSS +
            "div:nth-child(1) > div > span.summary-box-value-box > span.summary-box-value";
    public static final String GENERATION_LOWEST_DIV_CSS = GENERATION_DIV_PREFIX_CSS +
            "div:nth-child(2) > div > span.summary-box-value-box > span.summary-box-value";
    public static final String GENERATION_AVERAGE_DIV_CSS = GENERATION_DIV_PREFIX_CSS +
            "div:nth-child(3) > div > span.summary-box-value-box > span.summary-box-value";
    public static final String GENERATION_TOTAL_DIV_CSS = GENERATION_DIV_PREFIX_CSS +
            "div:nth-child(4) > div > span.summary-box-value-box > span.summary-box-value";

    public static final String NET_DIV_PREFIX_CSS = "body > div > div > div.content > div > " +
            "div.main.trends > div:nth-child(4) > div.body > div > ";
    public static final String NET_HIGHEST_DIV_CSS = NET_DIV_PREFIX_CSS +
            "div:nth-child(1) > div > span.summary-box-value-box > span.summary-box-value";
    public static final String NET_LOWEST_DIV_CSS = NET_DIV_PREFIX_CSS +
            "div:nth-child(2) > div > span.summary-box-value-box > span.summary-box-value";
    public static final String NET_AVERAGE_DIV_CSS = NET_DIV_PREFIX_CSS +
            "div:nth-child(3) > div > span.summary-box-value-box > span.summary-box-value";
    public static final String NET_TOTAL_DIV_CSS = NET_DIV_PREFIX_CSS +
            "div:nth-child(4) > div > span.summary-box-value-box > span.summary-box-value";
    public static final String BLANK_CARD_CLASS_NAME = "blank-right";
    public static final String NO_GRAPH_DATA = "div.no-data";
    public static final String HEADING_CSS = ".heading h1";
    public static final String HISTORY_GRAPH_PREV_BUTTON = ".pager-period-selector .pager-button-prev";
    public static final String START_DATE_INPUT_ID = "startDate";
    public static final String END_DATE_INPUT_ID = "endDate";
    public static final String CUSTOM_DATE_DIALOG_CLASS = "budget";
    public static final String HISTORY_GRAPH_CLASS = "graph-history";
    //Alert Dialog Variables
    public static final String ALERT_DIALOG_CLOSE_CSS = ".alert-error .close";
    //No Data Variables
    public static final String NO_DATAICON_CLASS = "no-data-icon";

    //General Variables
    public static final String STAGING_HOME_PAGE = "https://staging.neur.io/";
    public static final String QA_HOME_PAGE = "https://qa.neur.io/";
    public static final String TEST_HOME_PAGE = "https://sherlock-test.neur.io/";
    public static final String DEV_HOME_PAGE = "https://dev.neur.io/";
    public static final String PRODUCTION_HOME_PAGE = "https://my.neur.io/";
    public static final String API_STAGING_URL_PREFIX = "https://api-staging.neur.io/v1/";
    public static final String API_QA_URL_PREFIX = "https://api-qa.neur.io/v1/";
    public static final String API_DEV_URL_PREFIX = "https://dev.neur.io/v1/";
    public static final String API_URL_PREFIX = "https://api.neur.io/v1/";
    public static final String SHERLOCK_QA_URL = "https://qa.neur.io/";
    public static final String GRANT_TYPE = "grant_type";
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String CLIENT_CREDENTIALS = "client_credentials";
    public static final String ADMIN_CLIENT_ID = "m3jIFI3BQd6ZkdgYIBxrXg";
    public static final String ADMIN_CLIENT_SECRET = "d7TF8aQ8THWdz03Q-Pf27g";
    public static final String BUTTON_PRIMARY_CLASS_NAME = "btn-primary";
    public static final String LOADING_BUTTON_CLASS_NAME = "loading-button";
    public static final String SPAN = "span";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String VALUE = "value";
    public static final String UNITS = "unit";
    public static final String NAME = "name";
    public static final String COST = "cost";
    public static final String HELP_BLOCK_CLASS_NAME = "help-block";
    public static final String OPTION = "option";
    public static final String ALERT_SUCCESS = "alert-success";
    public static final String ADD = "Add";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String ABOUT = "About";
    public static final String SUPPORT_CENTRE = "Support Centre";
    public static final String TERMS = "Terms";
    public static final String PRIVACY = "Privacy";
    public static final String PORTAL = "Portal";
    public static final String ABOUT_PAGE_TITLE = "Neurio - Home Energy Monitor";
    public static final String TERMS_PAGE_TITLE = "Terms of Use – Neurio";
    public static final String PRIVACY_PAGE_TITLE = "Privacy – Neurio";
    public static final String APPS = "Apps";
    public static final String RESELLERS = "Resellers";
    public static final String TEXT_AREA = "textarea";
    public static final String RELE = "TAZfy1RHQ_2VuytMpAtSOA";
    public static final String RELEASE_FEATURE_ID = "TAZfy1RHQ_2VuytMpAtSOA";
    public static final String SIGN_OUT = "Sign Out";
    public static final String WINDSOR = "windsor";
    public static final String TIMOTHY = "Timothy Freeland";
    public static final String TIMOTHY_ID = "E9Lo99KASfyy3ZSseBrI4Q";
    public static final String SENSOR_ID_TIM = "0x0000C47F51020BB9";
    public static final String TIMEZONE_PACIFIC = "-07:00";
    public static final String TIMEZONE_PACIFIC_STRING = "America/Pacific";
    public static final String WINDSOR_ID = "yVqQsLETRk2C1D1EjlgEjA";
    public static final String SENSOR_ID = "0x1000000000099998";
    public static final String ANDROID = "android";
    public static final String IOS = "iOS";
    public static final String FIREFOX = "firefox";
    public static final String LOCAL = "local";
    public static final String HTMLUNIT = "htmlunit";
    public static final String INTERNET_EXPLORER = "IE";
    public static final String CHROME = "chrome";
    public static final String PREVIOUS = "previous";
    public static final String GENERATION = "Generation";
    public static final String CONSUMPTION = "Consumption";
    public static final String NET = "Net";
    public static final String HOURS = "hours";
    public static final String DAY = "day";
    public static final String DAYS = "days";
    public static final String CUSTOM = "custom";
    public static final String ENERGY_OVER_TIME = "EnergyOverTime||";
    public static final String POWER_OVER_TIME = "PowerOverTime||";
    public static final String TWO_DAYS_AGO = "two_days_ago";
    public static final String DAYS_AGO_21 = "21_days_ago";
    public static final String DAYS_AGO_7 = "7_days_ago";
    public static final String WEEK = "week";
    public static final String MONTH = "month";
    public static final String MONTHS = "months";
    public static final String YEAR = "year";
    public static final String HIGHEST = "highest";
    public static final String LOWEST = "lowest";
    public static final String AVERAGE = "average";
    public static final String TOTAL = "total";
    public static final String GENERATION_LOWER_CASE = "generation";
    public static final String CONSUMPTION_LOWER_CASE = "consumption";
    public static final String NET_LOWER_CASE = "net";
    public static final String BILLING_CYCLE = "billingCycle";
    public static final String DAY_HOUR_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DAY_FORMAT = "yyyy-MM-dd";
    public static final String MONTH_FORMAT = "yyyy-MM";
    public static final String RED = "red";
    public static final String GREEN = "green";
    public static final String WINDOWS = "Windows";
    public static final String LINUX = "Linux";
    public static final String MAC = "Mac";
    public static final Double HIGHER_THRESHOLD = 0.1;
    public static final Double THRESHOLD = 0.01;
    public static final int BOTTOM_LIMIT = -100000000;

    /**
     * Maps User Bar Tab Enums to corresponding strings
     */
    public static final Map<Tab, String> tabMapEnumToString;
    /**
     * Maps Settings Bar Tab Enums to corresponding strings
     */
    public static final Map<SettingTab, String> settingTabMapEnumToString;
    /**
     * Maps Settings Bar Tab Enums to corresponding strings
     */
    public static final Map<BillingType, String> billingTypeMapEnumToString;
    /**
     * Maps Settings Bar Tab Enums to corresponding strings
     */
    public static final Map<Calculator, String> calculatorMapEnumToFile;
    /**
     * Maps Granularity Enums to corresponding strings
     */
    public static final Map<Granularity, String> granularityMapEnumToString;
    /**
     * Maps Energy Enums to corresponding integers
     */
    public static final Map<Energy, Integer> energyEnumToInt;
    /**
     * Maps Stats Enums to corresponding integers
     */
    public static final Map<Stats, Integer> statsEnumToInt;
    /**
     * History Graph Test Cases
     */
    public static final String[] HISTORY_GRAPH_TEST_CASES = {"day", "previous_day", "two_days_ago_day", "week",
            "previous_week", "month", "previous_month", "billingCycle", "previous_billingCycle", "year",
            "previous_year", "custom9", "previous_custom9", "custom90", "previous_custom90", "custom91"};
    /**
     *Power Graph Test Cases: to be completed with more test cases
     */
    public static final String[] POWER_GRAPH_TEST_CASES = {"day", "previous_day", "two_days_ago",
            "7_days_ago", "21_days_ago"};
    //Android Variables
    private static final String ANDROID_APP_ID = "com.neurio.neuriohome:id/";
    public static final String ANDROID_EMAIL_EDIT_TEST_ID = ANDROID_APP_ID + "editTextEmail";
    public static final String ANDROID_PASSWORD_FIELD_ID = ANDROID_APP_ID + "passwordField";
    public static final String ANDROID_SUBMIT_BUTTON_ID = ANDROID_APP_ID + "buttonSubmit";
    public static final String ANDROID_ADD_APPLIANCE_BUTTON_ID = ANDROID_APP_ID + "buttonAddAppliance";
    public static final String ANDROID_VIEW_TRAINING_ID = ANDROID_APP_ID + "imageViewTrainingDial";
    public static final String ANDROID_REMOVE_APPLIANCE_BUTTON_ID = ANDROID_APP_ID + "imageButtonRemoveAppliance";
    public static final String ANDROID_HOME_BUTTON_ID = ANDROID_APP_ID + "buttonEnergy";
    public static final String ANDROID_HISTORY_BUTTON_ID = ANDROID_APP_ID + "buttonHistory";
    public static final String ANDROID_APPLIANCE_BUTTON_ID = ANDROID_APP_ID + "buttonAppliances";
    public static final String ANDROID_EVENTS_BUTTON_ID = ANDROID_APP_ID + "buttonEvent";
    public static final String ANDROID_SETTINGS_BUTTON_ID = ANDROID_APP_ID + "buttonSettings";
    public static final String ANDROID_LOCATION_BUTTON_ID = ANDROID_APP_ID + "buttonCurrentLocation";
    public static final String ANDROID_SIGN_OUT_BUTTON_ID = ANDROID_APP_ID + "buttonSignOut";
    public static final String ANDROID_SWTICH_LOCATION_BUTTON_ID = ANDROID_APP_ID + "buttonSwitchLocation";
    public static final String ANDROID_MORE_BUTTON_ID = ANDROID_APP_ID + "buttonMore";
    public static final String ANDROID_BACK_BUTTON_ID = ANDROID_APP_ID + "imageViewLeftButton";
    public static final String ANDROID_EDIT_TEXT_ID = ANDROID_APP_ID + "editTextEmail";
    public static final String ANDROID_PASSWORD_TEXT_ID = ANDROID_APP_ID + "passwordField";
    public static final String ANDROID_SIGIN_IN_BUTTON_ID = ANDROID_APP_ID + "buttonSubmit";
    public static final String ANDROID_SIGIN_CONTAINER_ID = ANDROID_APP_ID + "layoutSignInContainer";
    public static final String ANDROID_BOTTOM_MENU_SETTINGS_ID = ANDROID_APP_ID + "buttonSettings";
    public static final String ANDROID_SIGN_OUT_ID = ANDROID_APP_ID + "buttonSignOut";
    public static final String ANDROID_FORECAST_VALUE = ANDROID_APP_ID + "textViewCurrentForecast";


    static {
        tabMapEnumToString = new HashMap<>();
        tabMapEnumToString.put(Tab.HOME, "My Home");
        tabMapEnumToString.put(Tab.LOCATION, "Location");
        tabMapEnumToString.put(Tab.HISTORY, "History");
    }

    static {
        settingTabMapEnumToString = new HashMap<>();
        settingTabMapEnumToString.put(SettingTab.ACCOUNT, "Account");
        settingTabMapEnumToString.put(SettingTab.LOCATIONS, "Locations");
        settingTabMapEnumToString.put(SettingTab.APPLIANCES_PROFILE, "Appliances Profile");
        settingTabMapEnumToString.put(SettingTab.APPS, "Apps");
        settingTabMapEnumToString.put(SettingTab.RESELLERS, "Resellers");
        settingTabMapEnumToString.put(SettingTab.SENSORS, "Recently Commissioned Sensors");
        settingTabMapEnumToString.put(SettingTab.EXPORT_DATA, "Export Data");
        settingTabMapEnumToString.put(SettingTab.MY_REFERRALS, "My Referrals");
        settingTabMapEnumToString.put(SettingTab.INVITE_FRIEND, "Invite a Friend");
    }

    static {
        billingTypeMapEnumToString = new HashMap<>();
        billingTypeMapEnumToString.put(BillingType.FLAT, "Flat");
        billingTypeMapEnumToString.put(BillingType.TIERED, "Tiered");
        billingTypeMapEnumToString.put(BillingType.TIME_OF_USE, "Time of Use");
    }

    static {
        calculatorMapEnumToFile = new HashMap<>();
        calculatorMapEnumToFile.put(Calculator.Forecast, "ForecastCalculator.py ");
        calculatorMapEnumToFile.put(Calculator.SolarSavings, "SolarSavingsCalculator.py ");
        calculatorMapEnumToFile.put(Calculator.BudgetLine, "BudgetLineCalculator.py ");
    }

    static {
        granularityMapEnumToString = new HashMap<>();
        granularityMapEnumToString.put(Granularity.Day, "Day");
        granularityMapEnumToString.put(Granularity.Week, "Week");
        granularityMapEnumToString.put(Granularity.Month, "Month");
        granularityMapEnumToString.put(Granularity.BillingCycle, "Billing Cycle");
        granularityMapEnumToString.put(Granularity.Year, "Year");
        granularityMapEnumToString.put(Granularity.Custom, "Custom");
    }

    static {
        energyEnumToInt = new HashMap<>();
        energyEnumToInt.put(Energy.Consumption, 2);
        energyEnumToInt.put(Energy.Generation, 3);
        energyEnumToInt.put(Energy.Net, 4);
    }

    static {
        statsEnumToInt = new HashMap<>();
        statsEnumToInt.put(Stats.Highest, 1);
        statsEnumToInt.put(Stats.Lowest, 2);
        statsEnumToInt.put(Stats.Average, 3);
        statsEnumToInt.put(Stats.Total, 4);
    }

    /**
     * Return a name string of the web element for the imputed Tier's details
     *
     * @param tier Tier
     */
    public static String getTierDetailsNameString(int tier) {
        return PRICING_TIERS + (tier - 1) + "-maxConsumption";
    }

    /**
     * Return a name string of the web element for the imputed Tier's price
     *
     * @param tier Tier
     */
    public static String getTierEnergyRateNameString(int tier) {
        return PRICING_TIERS + (tier - 1) + "-price";
    }

    /**
     * Return a name string of the web element for the imputed Peak's From detail
     *
     * @param peak Peak
     */
    public static String getPeakFromString(int peak) {
        return PEAK + peak + "-from";
    }

    /**
     * Return a name string of the web element for the imputed Peak's To detail
     *
     * @param peak Peak
     */
    public static String getPeakToString(int peak) {
        return PEAK + peak + "-to";
    }

    /**
     * Return a name string of the web element for the imputed Peak's Price detail
     *
     * @param peak Peak
     */
    public static String getPeaPriceString(int peak) {
        return PEAK + peak + "-price";
    }

    /**
     * Return a name string of Android button web element for the imputed name
     *
     * @param text Text
     */
    public static String getAndroidButtonByText(String text) {
        return "//android.widget.Button[@text='" + text + "']";
    }

    /**
     * Returns CSS String for the summary value
     */
    public static String getSumaryValueDivStringByEnergyStats(Energy energy, Stats stats) {
        return "div.main.trends > div:nth-child(" + energyEnumToInt.get(energy) + ") > div.body > div > div:nth-child("
                + statsEnumToInt.get(stats) + ") > div > span.summary-box-value-box";
    }

    /**
     * User Bar Tab Enums
     */
    public enum Tab {
        HOME, LOCATION, HISTORY
    }

    /**
     * Settings Tab Enums
     */
    public enum SettingTab {
        ACCOUNT, LOCATIONS, SENSORS, APPLIANCES_PROFILE, APPS, RESELLERS, EXPORT_DATA, MY_REFERRALS, INVITE_FRIEND
    }

    /**
     * Billing Type Enums
     */
    public enum BillingType {
        FLAT, TIERED, TIME_OF_USE
    }

    /**
     * Calculator Enums
     */
    public enum Calculator {
        Forecast, BudgetLine, SolarSavings
    }

    /**
     * Granularity Enums
     */
    public enum Granularity {
        Day, Week, Month, BillingCycle, Year, Custom
    }

    /**
     * Graph Enums
     */
    public enum Graph {
        EnergyOverTime, SolarSavings, PowerOverTime
    }

    /**
     * Energy Enums
     */
    public enum Energy {
        Consumption, Generation, Net
    }

    /**
     * Stats Enums
     */
    public enum Stats {
        Highest, Lowest, Average, Total
    }

    /**
     * API return String variables
     *
     */
    public static final String API_CONSUMPTIONENERGY = "consumptionEnergy";
    public static final String API_GENERATIONENERGY = "generationEnergy";
    public static final String API_CONSUMPTIONPOWER = "consumptionPower";
    public static final String API_GENERATIONPOWER = "generationPower";
}
