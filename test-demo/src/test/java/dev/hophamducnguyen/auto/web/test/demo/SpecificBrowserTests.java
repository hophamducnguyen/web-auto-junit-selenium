package dev.hophamducnguyen.auto.web.test.demo;

import dev.hophamducnguyen.auto.web.common.annnotation.browser.Chrome;
import dev.hophamducnguyen.auto.web.common.annnotation.browser.MockBrowser;
import dev.hophamducnguyen.auto.web.common.annnotation.browser.NoneBrowser;
import dev.hophamducnguyen.auto.web.common.annnotation.browser.RemoteChrome;
import dev.hophamducnguyen.auto.web.common.core.BaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpecificBrowserTests extends BaseTest {

    @Test
    @MockBrowser
    void checkEnvTest() {
        assertEquals(settings.getWebAdminUrl(), "https://nguyenho.wedadmin.com/login.php");
    }

    @Test
    @Chrome
    void checkChromeBrowserTest() {
        logger.info("checkChromeBrowserTest");
    }

    @Test
    @NoneBrowser
    void checkNoneBrowserTest() {
        logger.info("checkNoneBrowserTest");
    }

    @Test
    @RemoteChrome
    void checkRemoteChromeTest() {
        logger.info("RemoteChrome");
    }
}
