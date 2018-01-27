package com.neurio.tests.shared;

import org.openqa.selenium.WebElement;

/**
 * Created by Robert on 5/24/2016.
 * Class to navigate the Change Email Dialog
 */
public class ChangeEmailDialog extends Browser {
    /**
     * Enter current password in the dialog
     *
     * @param currentPassword Current Password
     */
    public static void enterCurrentPassword(String currentPassword) {
        WebElement dialog = getElementByClassName(StringRef.CHANGE_EMAIL_DIALOG_CLASS_NAME);
        getElementFromParentByName(dialog, StringRef.CURRENT_PASSWORD_NAME).clear();
        getElementFromParentByName(dialog, StringRef.CURRENT_PASSWORD_NAME).sendKeys(currentPassword);
    }

    /**
     * Enter new email in the dialog
     *
     * @param newEmail New Email to be entered
     */
    public static void enterNewEmail(String newEmail) {
        WebElement dialog = getElementByClassName(StringRef.CHANGE_EMAIL_DIALOG_CLASS_NAME);
        getElementFromParentByName(dialog, StringRef.NEW_EMAIL_NAME).clear();
        getElementFromParentByName(dialog, StringRef.NEW_EMAIL_NAME).sendKeys(newEmail);
    }

    /**
     * Enter current password in the dialog
     *
     * @param confirmEmail New Email to be entered
     */
    public static void confirmNewEmail(String confirmEmail) {
        WebElement dialog = getElementByClassName(StringRef.CHANGE_EMAIL_DIALOG_CLASS_NAME);
        getElementFromParentByName(dialog, StringRef.CONFIRM_EMAIL_NAME).clear();
        getElementFromParentByName(dialog, StringRef.CONFIRM_EMAIL_NAME).sendKeys(confirmEmail);
    }

    /**
     * Close the Change Email Dialog
     */
    public static void close() {
        WebElement dialog = getElementByClassName(StringRef.CHANGE_EMAIL_DIALOG_CLASS_NAME);
        getElementFromParentByClass(dialog, StringRef.CLOSE_BUTTON_CLASS_NAME).click();
    }

    /**
     * Press Change Email Button in Dialog
     */
    public static void submitChangeEmail() {
        WebElement dialog = getElementByClassName(StringRef.CHANGE_EMAIL_DIALOG_CLASS_NAME);
        getElementFromParentByClass(dialog, StringRef.CONFIRM_BUTTON_CLASS_NAME).click();
    }

    /**
     * Change Email Batch Method
     *
     * @param password Password
     * @param newEmail New email to input
     */
    public static void changeEmail(String password, String newEmail) {
        enterCurrentPassword(password);
        enterNewEmail(newEmail);
        confirmNewEmail(newEmail);
        submitChangeEmail();
    }
}
