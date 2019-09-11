package dev.hophamducnguyen.auto.web.common.pageobject.webadmin;

import dev.hophamducnguyen.auto.web.common.core.BasePage;
import dev.hophamducnguyen.auto.web.common.core.TestSettings;
import dev.hophamducnguyen.auto.web.common.core.webelement.NWebElement;
import dev.hophamducnguyen.auto.web.common.core.webelement.support.CustomElementFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new CustomElementFieldDecorator(driver), this);
    }

    public LoginPage(WebDriver driver, TestSettings settings) {
        super(driver, settings);
        PageFactory.initElements(new CustomElementFieldDecorator(driver), this);
    }

    @FindBy(css = "body > div > form > button")
    private NWebElement btnSignInWithGoogle;

    @FindBy(id = "identifierId")
    private NWebElement txtGoogleEmail;

    @FindBy(name = "password")
    private NWebElement txtPassword;

    @FindBy(id = "totpPin")
    private NWebElement txtTotPin;

    @FindBy(xpath = "//*[contains(@id,'Next')]")
    private NWebElement btnNext;

    @FindBy(name = "email_address")
    private NWebElement txtEdgeUserName;

    @FindBy(xpath = "//input[@value='Sign In']")
    private NWebElement btnEdgeSignIn;

    public HomePage logInAsSuperTwoFactor() {
        String url = settings.getWebAdminUrl() + "/login.php";
        String userName = settings.getAdminUserName();
        String password = settings.getAdminPassword();
        try {
            driver.get(url);
            btnSignInWithGoogle.click();
            txtGoogleEmail.setText(userName);
            btnNext.click();
            txtPassword.setText(password);
            btnNext.click();

            txtTotPin.setText(getTwoFactorCode());
            btnNext.click();
            txtEdgeUserName.setText(userName);
            txtPassword.setText(password);
            btnEdgeSignIn.click();
        } catch (Exception e) {
            logger.info("OTP may have timed out, trying again... {}", e.getMessage());
            logInAsSuperTwoFactor();
        }

        HomePage homePage = new HomePage(driver, settings);
        homePage.divSearchImage.waitForVisible();

        return homePage;
    }
}