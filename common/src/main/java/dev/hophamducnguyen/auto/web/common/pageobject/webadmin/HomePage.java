package dev.hophamducnguyen.auto.web.common.pageobject.webadmin;

import dev.hophamducnguyen.auto.web.common.api.StoreApi;
import dev.hophamducnguyen.auto.web.common.core.BasePage;
import dev.hophamducnguyen.auto.web.common.core.TestSettings;
import dev.hophamducnguyen.auto.web.common.core.webelement.NWebElement;
import dev.hophamducnguyen.auto.web.common.core.webelement.support.CustomElementFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new CustomElementFieldDecorator(driver), this);
    }

    public HomePage(WebDriver driver, TestSettings settings) {
        super(driver, settings);
        PageFactory.initElements(new CustomElementFieldDecorator(driver), this);
    }

    @FindBy(id = "search_image_div")
    public NWebElement divSearchImage;

    @FindBy(xpath = "//input[contains(@name,'email')]")
    public NWebElement txtEmail1;

    @FindBy(xpath = "//*[contains(text(),'Create New Store')]")
    public NWebElement txtCreateNewStore;

    @FindBy(name = "test_stores")
    public NWebElement txtTestStores;

    @FindBy(name = "search_phrase")
    public NWebElement txtSearchPhrase;

    @FindBy(name = "go")
    public NWebElement btnSearch;

    public void searchStore(String otTest) {
        txtTestStores.click();
        txtSearchPhrase.setText(otTest);
        btnSearch.click();
    }

}
