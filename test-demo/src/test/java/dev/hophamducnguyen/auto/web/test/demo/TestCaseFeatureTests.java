package dev.hophamducnguyen.auto.web.test.demo;

import dev.hophamducnguyen.auto.web.common.core.BaseTest;
import dev.hophamducnguyen.auto.web.common.annnotation.testtype.Functional;
import dev.hophamducnguyen.auto.web.common.annnotation.testtype.Regression;
import dev.hophamducnguyen.auto.web.common.annnotation.testtype.Smoke;
import dev.hophamducnguyen.auto.web.common.annnotation.testtype.Stress;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class TestCaseFeatureTests extends BaseTest {

    @Functional
    @Test
    void testFunctional1(TestInfo testInfo) {
        logger.info(testInfo.getTestMethod().get().getName());
    }

    @Functional
    @Test
    void testFunctional2(TestInfo testInfo) {
        logger.info(testInfo.getTestMethod().get().getName());
    }

    @Smoke
    @Test
    void testSmoke(TestInfo testInfo) {
        logger.info(testInfo.getTestMethod().get().getName());
    }

    @Regression
    @Test
    void testRegression(TestInfo testInfo) {
        logger.info(testInfo.getTestMethod().get().getName());
    }

    @Stress
    @Test
    void testStress(TestInfo testInfo) {
        logger.info(testInfo.getTestMethod().get().getName());
    }

    @Tag("custom")
    @Test
    void testCustom(TestInfo testInfo) {
        logger.info(testInfo.getTestMethod().get().getName());
    }

}
