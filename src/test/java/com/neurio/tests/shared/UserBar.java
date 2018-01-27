package com.neurio.tests.shared;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

/**
 * Created by Robert on 2016-05-18.
 * Class to navigate through the User Bar
 */
public class UserBar extends Browser {

    /**
     * Selects tab in the user bar
     *
     * @param tab Selected tab
     */
    public static void selectTab(StringRef.Tab tab) {
        String selectedTab = StringRef.tabMapEnumToString.get(tab);
        List<WebElement> elementsList = getElementsByCSS(StringRef.TABS_CSS_SELECTOR);
        for (WebElement element : elementsList) {
            if (element.getText().equals(selectedTab)) {
                element.click();
                Common.wait(2);
                return;
            }
        }
        throw new NoSuchElementException("Tab not found");
    }

    /**
     * Selects the settings button in user bar
     */
    public static void selectSettings() {
        getElementByCSS(StringRef.SETTINGS_CSS_SELECTOR).click();
    }

    /**
     * Check if the username in the top right of the user bar is correct
     *
     * @param userName User Name given
     */
    public static void assertUserName(String userName) {
        String adminText = getElementByCSS(StringRef.USERNAME_TEXT_BOX_CSS_SELECTOR).getText();

        Assert.assertEquals(adminText, userName, userName + " != " + adminText);
    }

    /**
     * Toggle the User Menu in the User Bar to appear
     */
    public static void toggleUserMenu() {
        try {
            getElementByCSS(StringRef.ADMIN_DROPDOWN_TOGGLE_CSS_SELECTOR).click();
        } catch (Exception e) {
            getElementByID("multiple-selects").click();
        }
    }

    /**
     * Open an account as admin user
     *
     * @param name - Type in name in input to search for string
     * @param id   - Click on the correct ID found in the search
     */
    public static void adminSelectUserByNameID(String name, String id) {
        getElementByCSS(StringRef.ID_INPUT_CSS_SELECTOR).sendKeys(name);
        Common.wait(2);

        getElementByID(id).click();

        HomePage.waitForHomePage();
    }

    /**
     * FOR NON-ADMIN
     * Selects the user from the dropdown menu given location name
     *
     * @param name
     */
    public static void selectUserByLocationName(String name) {
        // if opened in full mode
        try {
            List<WebElement> locations = getElementsByCSS(".location-list-item");
            if (locations != null && locations.size() > 0) {
                for (WebElement location : locations) {
                    List<WebElement> list = location.findElements(By.tagName("span"));
                    for (WebElement span : list) {
                        if (span.getAttribute("innerHTML").equals(name)) {
                            location.click();
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Had an exception trying to find and click on a matching .location-list-item to " + name + " !... " + e.getMessage());
        }

        try {
            // if opened in no-side-bar mode
            List<WebElement> options = getElementByTag("option");
            if (options != null && options.size() > 0) {
                for (WebElement option : options) {
                    if (option.getAttribute("innerHTML").equals(name)) {
                        option.click();
                        return;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Had an exception trying to find and click on a matching option to " + name + " !... " + e.getMessage());
        }
    }

    /**
     * Open an account as admin user
     *
     * @param email - Type in email in input to open account
     */
    public static void adminSelectUserByEmail(String email) {
        toggleUserMenu();

        getElementByClassName(StringRef.ADMIN_INPUT_CLASS_NAME).sendKeys(email);

        getElementByClassName(StringRef.ADMIN_INPUT_CLASS_NAME).sendKeys(Keys.RETURN);

        Common.wait(2);
        HomePage.waitForHomePage();
    }

    /**
     * Sign out of the web app by admin
     */
    public static void adminSignOut() {
        WebElement adminMenuToggleElement = driver.findElement(
                By.cssSelector(StringRef.ADMIN_DROPDOWN_TOGGLE_CSS_SELECTOR));
        adminMenuToggleElement.click();

        WebElement adminMenuElement = driver.findElement(By.cssSelector(StringRef.ADMIN_DROPDOWN_MENU_CSS_SELECTOR));
        adminMenuElement.findElement(By.cssSelector(StringRef.ADMIN_SIGN_OUT_CSS_SELECTOR)).click();
    }

    /**
     * Sign out of the web app
     */
    public static void signOut() {
        getElementByLinkText(StringRef.SIGN_OUT).click();
    }
}
