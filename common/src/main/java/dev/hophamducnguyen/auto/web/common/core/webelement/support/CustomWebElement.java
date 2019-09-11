package dev.hophamducnguyen.auto.web.common.core.webelement.support;

import dev.hophamducnguyen.auto.web.common.core.CommonConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public abstract class CustomWebElement {
    protected Logger logger = LogManager.getLogger(this.getClass());
    private WebDriver webDriver;
    private By locator;
    private WebElementTransformer transformer;
    protected int timeout; // time in second

    public CustomWebElement(WebDriver webDriver, By by) {
        this.webDriver = webDriver;
        locator = by;
        transformer = new WebElementTransformer();

        // set default timeout
        String timeoutProperty = System.getProperty(CommonConstants.TIMEOUT_PROPERTY);
        this.timeout = StringUtils.isNotBlank(timeoutProperty) ? Integer.parseInt(timeoutProperty) : 60;

        // Call the page factory on this object to initialize custom webelements in custom webelements
        PageFactory.initElements(new CustomElementFieldDecorator(webDriver), this);
    }

    //region WebDriver
    protected WebDriver getWebDriver() {
        return webDriver;
    }

    protected RemoteWebDriver getRemoteDriver() {
        //This MUST be cast to RemoteWebDriver to work
        return (RemoteWebDriver) webDriver;
    }
    //endregion

    //region Locator
    public By getBy() {
        return locator;
    }

    protected WebElementTransformer transformer() {
        return transformer;
    }

    protected WebElementTransformer.LocatorType getLocatorType() {
        return transformer.getLocatorType(getBy());
    }

    protected String getLocatorValue(WebElementTransformer.LocatorType type) {
        return transformer.getLocatorValue(getBy(), type);
    }

    public WebElement findElement() {
        return webDriver.findElement(getBy());
    }
    //endregion

    //region WebDriverWait
    public WebElement waitForPresent() {
        return waitForPresent(timeout);
    }

    public WebElement waitForPresent(int timeout) {
        WebDriverWait wait = new WebDriverWait(getWebDriver(), timeout);
        return wait.until(ExpectedConditions.presenceOfElementLocated(getBy()));
    }

    public WebElement waitForVisible() {
        return waitForVisible(timeout);
    }

    public WebElement waitForVisible(int timeout) {
        WebDriverWait wait = new WebDriverWait(getWebDriver(), timeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(getBy()));
        return findElement();
    }

    public WebElement waitForClickable() {
        return waitForClickable(timeout);
    }

    public WebElement waitForClickable(int timeout) {
        WebDriverWait wait = new WebDriverWait(getWebDriver(), timeout);
        wait.until(ExpectedConditions.elementToBeClickable(getBy()));
        return findElement();
    }

    public WebElement waitForAttributeContains(String attribute, String value) {
        return waitForAttributeContains(attribute, value, timeout);
    }

    public WebElement waitForAttributeContains(String attribute, String value, int timeout) {
        WebDriverWait wait = new WebDriverWait(getWebDriver(), timeout);
        wait.until(ExpectedConditions.attributeContains(getBy(), attribute, value));
        return findElement();
    }
    //endregion

}