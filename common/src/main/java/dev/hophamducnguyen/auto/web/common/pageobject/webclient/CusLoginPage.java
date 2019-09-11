package dev.hophamducnguyen.auto.web.common.pageobject.webclient;

import dev.hophamducnguyen.auto.web.common.core.BasePage;
import dev.hophamducnguyen.auto.web.common.core.TestSettings;
import dev.hophamducnguyen.auto.web.common.core.webelement.NWebElement;
import dev.hophamducnguyen.auto.web.common.core.webelement.support.CustomElementFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CusLoginPage extends BasePage {
    public CusLoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new CustomElementFieldDecorator(driver), this);
    }

    public CusLoginPage(WebDriver driver, TestSettings settings) {
        super(driver, settings);
        PageFactory.initElements(new CustomElementFieldDecorator(driver), this);
    }

    @FindBy(id = "username-input")
    public NWebElement txtEmail;

    @FindBy(id = "password-input")
    public NWebElement txtPassword;

    @FindBy(className = "at-sign-in-btn")
    public NWebElement btnSignIn;

    public void login() {
        logger.info("Login to client page");
        String url = settings.getWebCustomerUrl();
        String userName = settings.getAdminUserName();
        String password = settings.getAdminPassword();
        driver.get(url);
        txtEmail.setText(userName);
        txtPassword.setText(password);
        btnSignIn.click();
    }
}
