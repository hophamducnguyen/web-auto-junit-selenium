package dev.hophamducnguyen.auto.web.test.demo;

import dev.hophamducnguyen.auto.web.common.core.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("nguyenho")
public class SpecificEnvironmentTests extends BaseTest {

    @Test
    void checkEnvTest() {
        assertEquals(settings.getWebAdminUrl(), "https://nguyenho.wedadmin.com/login.php");
    }

}
