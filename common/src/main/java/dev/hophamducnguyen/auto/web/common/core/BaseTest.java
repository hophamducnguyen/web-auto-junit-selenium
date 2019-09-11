package dev.hophamducnguyen.auto.web.common.core;

import dev.hophamducnguyen.auto.web.common.logging.TimeExecutionLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestSettings.class)
//@ExtendWith(TestExtension.class)
public abstract class BaseTest implements TimeExecutionLogger {
    protected Logger logger = LogManager.getLogger(this.getClass());
    protected WebDriver driver;

    @Autowired
    protected TestSettings settings;

    @BeforeEach
    void initDriver(TestInfo testInfo) throws MalformedURLException {
        Browser browserType = null;
        // get browser type from test annotation
        List<String> tags = new ArrayList<>(testInfo.getTags());
        List<String> browsers = Stream.of(Browser.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        Collection matched = CollectionUtils.intersection(browsers, tags);

        // if test case browser had value, get it and ignore the test setting
        if (CollectionUtils.isEmpty(matched)) {
            browserType = Browser.valueOf(settings.getBrowserType().toUpperCase());
        } else {
            browserType = Browser.valueOf(CollectionUtils.intersection(browsers, tags).toArray()[0].toString());
        }

        // init web driver
        logger.info(browserType);
        DesiredCapabilities capabilities;
        switch (browserType) {
            case MOCK_BROWSER:
                driver = mock(WebDriver.class);
                break;
            case REMOTE_CHROME:
                capabilities = new DesiredCapabilities();
                capabilities.setCapability("username", settings.getSauceUserName());
                capabilities.setCapability("accessKey", settings.getSauceAccessKey());
                capabilities.setCapability("browserName", "Chrome");
                capabilities.setCapability("platform", "Windows 10");
                capabilities.setCapability("version", "59.0");
                capabilities.setCapability("name", testInfo.getTestMethod().get().getName());
                driver = new RemoteWebDriver(new URL(settings.getSauceURL()), capabilities);
                break;
            case REMOTE_EDGE:
                capabilities = DesiredCapabilities.edge();
                capabilities.setCapability("platform", "Windows 10");
                capabilities.setCapability("version", "14.14393");
                capabilities.setCapability("screenResolution", "1280x1024");
                capabilities.setCapability("name", "EDGE " + getTimeStamp());
                driver = new RemoteWebDriver(new URL(settings.getSauceURL()), capabilities);
                break;
            case REMOTE_SAFARI:
                capabilities = DesiredCapabilities.safari();
                capabilities.setCapability("platform", "macOS 10.12");
                capabilities.setCapability("version", "11.0");
                //TODO Safari unable to set screen res
                //caps.setCapability("screenResolution", "1280x1024");
                capabilities.setCapability("name", "SAFARI " + getTimeStamp());
                capabilities.setCapability("name", testInfo.getTestMethod().get().getName());
                driver = new RemoteWebDriver(new URL(settings.getSauceURL()), capabilities);
                break;
            case HTML:
                Class<? extends WebDriver> webDriverClass;
                webDriverClass = HtmlUnitDriver.class;
                WebDriverManager.getInstance(webDriverClass).setup();
                try {
                    driver = webDriverClass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            case CHROME:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }
    }

    @AfterEach
    public void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    private Timestamp getTimeStamp() {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        System.out.println(time);
        return time;
    }

}
