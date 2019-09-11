package dev.hophamducnguyen.auto.web.common.pageobject;

import dev.hophamducnguyen.auto.web.common.core.BasePage;
import dev.hophamducnguyen.auto.web.common.core.TestSettings;
import dev.hophamducnguyen.auto.web.common.pageobject.webadmin.*;
import org.openqa.selenium.WebDriver;

public class WebAdmin extends BasePage {

    public WebAdmin(WebDriver driver) {
        super(driver);
    }

    public WebAdmin(WebDriver driver, TestSettings testSettings) {
        super(driver, testSettings);
    }

    public LoginPage login = new LoginPage(driver, settings);
    public HomePage home = new HomePage(driver, settings);

}
