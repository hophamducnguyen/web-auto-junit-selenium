package dev.hophamducnguyen.auto.web.common.pageobject;

import dev.hophamducnguyen.auto.web.common.core.BasePage;
import dev.hophamducnguyen.auto.web.common.core.webelement.NWebElement;
import dev.hophamducnguyen.auto.web.common.core.webelement.DemoDropDownListNew;
import dev.hophamducnguyen.auto.web.common.core.webelement.support.CustomElementFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

// TODO: this page using for demo only, will be deleted after finish demo
public class DemoPage extends BasePage {
    public DemoPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new CustomElementFieldDecorator(driver), this);
    }

    //    public DemoDropDownList dropdownList;
    public DemoDropDownListNew dropdownList;
    public NWebElement submitButton;

}
