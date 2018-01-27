package com.neurio.tests.shared;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert on 5/24/2016.
 * Class to navigate the Account Page
 */
public class AccountPage extends Browser {
    /**
     * Account Tab
     */

    /**
     * Changes the User Name in the Account Details Page
     *
     * @param newName New user name to input
     */
    public static void changeAccountName(String newName) {
        getElementByCSS(StringRef.ACCOUNT_DETAILS_NAME_INPUT_CSS_SELECTOR).clear();
        getElementByCSS(StringRef.ACCOUNT_DETAILS_NAME_INPUT_CSS_SELECTOR).sendKeys(newName);
        getElementByCSS(StringRef.ACCOUNT_DETAILS_SAVE_CHANGES_BUTTON_CSS_SELECTOR).click();
    }

    /**
     * Changes the User Name in the Account Details Page
     *
     * @param message Help Block Message
     */
    public static boolean checkHelpBlockMessage(String message) {
        String helpMessage = getElementByCSS(StringRef.ACCOUNT_DETAILS_HELP_BLOCK_CSS_SELECTOR).getText();
        return helpMessage.contains(message);
    }

    /**
     * Enter current password
     *
     * @param password Current password
     */
    public static void enterCurrentPassword(String password) {
        getElementByName(StringRef.CURRENT_PASSWORD_NAME).clear();
        getElementByName(StringRef.CURRENT_PASSWORD_NAME).sendKeys(password);
    }

    /**
     * Enter new password
     *
     * @param password New password
     */
    public static void enterNewPassword(String password) {
        getElementByClassName(StringRef.PASSWORD_INPUT_CLASS_NAME).clear();
        getElementByClassName(StringRef.PASSWORD_INPUT_CLASS_NAME).sendKeys(password);
    }

    /**
     * Confirm new password
     *
     * @param password New password
     */
    public static void confirmNewPassword(String password) {
        getElementByName(StringRef.CONFIRM_PASSWORD_NAME).clear();
        getElementByName(StringRef.CONFIRM_PASSWORD_NAME).sendKeys(password);
    }

    /**
     * Press change password button
     */
    public static void submitNewPassword() {
        getElementByCSS(StringRef.CHANGE_PASSWORD_BUTTON_CSS_SELECTOR).click();
    }

    /**
     * Change Password
     *
     * @param currentPassword Current password
     * @param newPassword     New password
     */
    public static void changePassword(String currentPassword, String newPassword) {
        enterCurrentPassword(currentPassword);
        enterNewPassword(newPassword);
        confirmNewPassword(newPassword);
        submitNewPassword();
    }

    /**
     * Check if error message occurs on page
     *
     * @param message Message expected to be shown
     */
    public static boolean checkErrorMessage(String message) {
        List<WebElement> list = getElementsByClassName(StringRef.HELP_BLOCK_CLASS_NAME);
        List<String> messageList = new ArrayList<String>();
        for (WebElement element : list) {
            messageList.add(element.getText());
        }
        return messageList.contains(message);
    }

    /**
     * Returns the current email on the Settings Page
     */
    public static String getCurrentEmailText() {
        return getElementByClassName(StringRef.EMAIL).getText();
    }

    /**
     * Click on change email button
     */
    public static void pressChangeEmailButton() {
        getElementByCSS(StringRef.CHANGE_EMAIL_BUTTON_CSS_SELECTOR).click();
    }

}
