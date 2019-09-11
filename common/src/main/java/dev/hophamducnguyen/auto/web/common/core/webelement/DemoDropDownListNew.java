package dev.hophamducnguyen.auto.web.common.core.webelement;

import dev.hophamducnguyen.auto.web.common.core.webelement.support.CustomWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// TODO: this class using for demo only, will be deleted after finish demo
// This element it not the normal Select. It using div, ul, li
public class DemoDropDownListNew extends CustomWebElement {
    public DemoDropDownListNew(WebDriver webDriver, By by) {
        super(webDriver, by);
    }

    public void selectByText(String text) {
        logger.info("selectByTextNEW-" + text);
    }
}
