package dev.hophamducnguyen.auto.web.common.pageobject;

import dev.hophamducnguyen.auto.web.common.core.BasePage;
import dev.hophamducnguyen.auto.web.common.core.TestSettings;
import dev.hophamducnguyen.auto.web.common.pageobject.webclient.CusLoginPage;
import org.openqa.selenium.WebDriver;

public class WebClient extends BasePage {
    public WebClient(WebDriver driver) {
        super(driver);
    }

    public WebClient(WebDriver driver, TestSettings testSettings) {
        super(driver, testSettings);
    }

    public CusLoginPage login = new CusLoginPage(driver, settings);
}
