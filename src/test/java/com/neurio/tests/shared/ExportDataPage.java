package com.neurio.tests.shared;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Robert on 6/20/2016.
 * Export Data Page
 */
public class ExportDataPage extends Browser {
    /**
     * Selects the granularity from the dropdown menu
     *
     * @param select - Selected granularity
     */
    public static void selectGranularity(String select) {
        WebElement dropdownMenu = getElementByCSS(StringRef.EXPORT_DATA_GRANULARITY_CSS);
        dropdownMenu.click();
        List<WebElement> list = getElementsFromParentByCSS(dropdownMenu, StringRef.OPTION);
        for (WebElement element : list) {
            if (element.getText().contains(select)) {
                element.click();
                break;
            }
        }
        throw new NoSuchElementException(select + " not found");
    }

    public static void download() {
        getElementByClassName(StringRef.LOADING_BUTTON_CLASS_NAME).click();
        Common.wait(2);
    }
}
