package dev.hophamducnguyen.auto.web.common.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.aerogear.security.otp.Totp;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertFalse;

public abstract class BasePage {
    protected WebDriver driver;
    protected TestSettings settings;
    protected Logger logger = LogManager.getLogger(this.getClass());

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public BasePage(WebDriver driver, TestSettings settings) {
        this.driver = driver;
        this.settings = settings;
    }

    public String getTwoFactorCode() {

        String otpKeyStr = "asdfsaf";
        Totp totp = new Totp(otpKeyStr);
        String twoFactorCode = totp.now();
        logger.info(twoFactorCode);
        return twoFactorCode;
    }

    public void navigateTo(String url) {
        this.driver.navigate().to(url);
    }

    public void waitForPageLoaded(int timeout) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equalsIgnoreCase("complete");
            }
        };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(this.driver, timeout);
            wait.until(expectation);
        } catch (Throwable error) {
            assertFalse(false, "Timeout waiting for Page Load Request to complete.");
        }
    }

    public void setFileUpload() {
        RemoteWebDriver remoteWebDriver = (RemoteWebDriver)driver;
        remoteWebDriver.setFileDetector(new LocalFileDetector());
    }
}