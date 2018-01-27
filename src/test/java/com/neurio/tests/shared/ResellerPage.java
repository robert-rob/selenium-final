package com.neurio.tests.shared;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Robert on 6/20/2016.
 * Reseller Page Class
 */
public class ResellerPage extends Browser {
    /**
     * Press the register reseller button
     */
    public static void registerResellerClick() {
        getElementByClassName(StringRef.BUTTON_PRIMARY_CLASS_NAME).click();
    }

    /**
     * Register a reseller
     */
    public static void registerReseller(String name, String email, String password) {
        WebElement nameInput = getElementsByCSS(StringRef.RESELLER_INPUT_CSS).get(0);
        WebElement emailInput = getElementsByCSS(StringRef.RESELLER_INPUT_CSS).get(1);
        WebElement passwordInput = getElementsByCSS(StringRef.RESELLER_INPUT_CSS).get(2);

        nameInput.sendKeys(name);
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);

        getElementByClassName(StringRef.LOADING_BUTTON_CLASS_NAME).click();

        Common.wait(2);
    }

    /**
     * Select a reseller
     */
    public static void selectReseller(String name) {
        Common.wait(2);
        List<WebElement> list = getElementsByCSS(StringRef.RESELLER_SPAN_CSS);
        for (WebElement element : list) {
            if (element.getText().contains(name)) {
                element.click();
                return;
            }
        }
        throw new NoSuchElementException(name + " not found");
    }

    /**
     * Add Sensor IDs
     */
    public static void addSensorIDs(String id) {
        getElementByCSS(StringRef.TEXT_AREA).sendKeys(id);
        getElementsByClassName(StringRef.BUTTON_PRIMARY_CLASS_NAME).get(2).click();
    }

    /**
     * Add Sensor IDs
     */
    public static String getID() {
        String[] list = Common.getCurrentURL().split("/");
        return list[list.length - 1];
    }

}
