package com.neurio.tests.shared;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Robert T. on 5/24/2016.
 * Class to navigate the Settings Page
 */
public class SettingsPage extends Browser {
    /**
     * Selects a setting tab in the settings side bar
     *
     * @param tab Selected tab
     */
    public static void selectSettingsTab(StringRef.SettingTab tab) {
        String selectedTab = StringRef.settingTabMapEnumToString.get(tab);
        List<WebElement> elementsList = getElementsByCSS(StringRef.SETTINGS_LIST_ITEMS_CSS_SELECTOR);
        for (WebElement element : elementsList) {
            if (element.getText().equals(selectedTab)) {
                element.click();
                return;
            }
        }
        throw new NoSuchElementException("Tab not found");

    }

    public static void signOut() {
        getElementByLinkText(StringRef.SIGN_OUT).click();
    }
}
