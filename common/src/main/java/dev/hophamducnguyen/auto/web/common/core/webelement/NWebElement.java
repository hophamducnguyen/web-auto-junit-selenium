package dev.hophamducnguyen.auto.web.common.core.webelement;

import dev.hophamducnguyen.auto.web.common.core.webelement.support.CustomWebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;

public class NWebElement extends CustomWebElement {

    private Logger logger = LogManager.getLogger(this.getClass());

    public NWebElement(WebDriver webDriver, By by) {
        super(webDriver, by);
    }

    public void click() {
        waitForVisible().click();
    }

    public void clear() {
        waitForVisible().clear();
    }

    public void submit() {
        waitForVisible().submit();
    }

    public void setText(CharSequence text) {
        waitForVisible().clear();
        waitForVisible().sendKeys(text);
    }

    public String getText() {
        return waitForVisible().getText();
    }

    public void shouldHaveText(String expectedText){
        assertEquals(expectedText, getText());
    }

    public void shouldContainText(String expectedText){
        assertTrue(getText().contains(expectedText));
    }

    public void shouldDisplay(){
        assertTrue(isDisplay());
    }

    public void selectByText(String text) {
        Select dropDown = new Select(findElement());
        dropDown.selectByVisibleText(text);
    }

    public void selectByValue(String value) {
        Select dropDown = new Select(findElement());
        dropDown.selectByValue(value);
    }

    public void clickElementByAction(int timeout) {
        WebElement webElement = waitForVisible();
        Actions actions = new Actions(getWebDriver());
        actions.moveToElement(webElement).click().perform();
    }

    public boolean isDisplay() {
        return waitForVisible().isDisplayed();
    }

    public String getAttribute(String attribute) {
        return waitForVisible().getAttribute(attribute);
    }

    public boolean isSelected() {
        return waitForVisible().isSelected();
    }

    public void clickByJavascript() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getWebDriver();
        jsExecutor.executeScript("arguments[0].click();", findElement());
    }

    public void hideWidget() {
        JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
        js.executeScript("if arguments[0].style.display = 'none';", findElement());
        js.executeScript("arguments[0].parentNode.removeChild(arguments[0]);", findElement());
    }

    public void waitForContainsText(String text) {
        WebDriverWait wait = new WebDriverWait(getWebDriver(), timeout);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(getBy(), text));
    }

    public void hoverElementUsingAction() {
        WebElement element = waitForVisible();
        Actions actions = new Actions(getWebDriver());
        actions.moveToElement(element).perform();
    }

    public void checkForTextTrue(String text, String tagName) {
        String bodyText = getWebDriver().findElement(getBy()).getText();
        assertTrue(bodyText.contains(text), String.valueOf(true));
        logger.info(tagName + " does contain " + text);
    }

    public void remoteFileUpload() {
        getRemoteDriver().setFileDetector(new LocalFileDetector());
    }
}
