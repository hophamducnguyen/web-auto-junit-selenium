package dev.hophamducnguyen.auto.web.test.demo;

import dev.hophamducnguyen.auto.web.common.core.BaseTest;
import dev.hophamducnguyen.auto.web.common.pageobject.DemoPage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

// this class using for demo only, not for running
@Disabled
public class DemoElementTests extends BaseTest {

    @Test
    void testCase1(){
        logger.info("testCase1");
        DemoPage demoPage = new DemoPage(driver);
        demoPage.dropdownList.selectByText("abc");
        demoPage.submitButton.click();
    }

    @Test
    void testCase2(){
        logger.info("testCase2");
        DemoPage demoPage = new DemoPage(driver);
        demoPage.dropdownList.selectByText("abc");
        demoPage.submitButton.click();
    }

    @Test
    void testCase3(){
        logger.info("testCase3");
        DemoPage demoPage = new DemoPage(driver);
        demoPage.dropdownList.selectByText("abc");
        demoPage.submitButton.click();
    }
}
