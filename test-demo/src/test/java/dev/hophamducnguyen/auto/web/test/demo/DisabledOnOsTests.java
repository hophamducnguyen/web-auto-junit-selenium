
package dev.hophamducnguyen.auto.web.test.demo;

import dev.hophamducnguyen.auto.web.common.annnotation.browser.NoneBrowser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;

import static org.junit.jupiter.api.condition.OS.*;

@NoneBrowser
class DisabledOnOsTests {

    @DisabledOnOs({MAC, LINUX})
    @Test
    void conditionalMacTest() {
        System.out.println("This test will be disabled on MAC and LINUX");
    }

    @DisabledOnOs({WINDOWS})
    @Test
    void conditionalWinTest() {
        System.out.println("This test will be disabled on MAC and LINUX");
    }

}
