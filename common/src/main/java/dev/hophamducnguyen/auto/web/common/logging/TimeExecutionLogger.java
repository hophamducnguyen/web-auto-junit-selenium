
package dev.hophamducnguyen.auto.web.common.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.springframework.core.env.AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;

@Tag("timed")
@ExtendWith(TimingExtension.class)
public interface TimeExecutionLogger {
    static final Logger logger = LogManager.getLogger();

    @BeforeAll
    static void beforeAllTest() {
        logger.info(String.format("RUNNING ENVIRONMENT <%s>", System.getProperty(ACTIVE_PROFILES_PROPERTY_NAME)));
    }

    @BeforeEach
    default void beforeEachTest(TestInfo testInfo) {
        logger.info("===========================================");
        logger.info("Testcase {}.", testInfo.getTestMethod().get().getName());
        logger.info("Start to execute {}", testInfo.getDisplayName());
    }

    @AfterEach
    default void afterEachTest(TestInfo testInfo) {
        logger.info("Finished executing {}", testInfo.getDisplayName());
        logger.info("===========================================");
    }
}