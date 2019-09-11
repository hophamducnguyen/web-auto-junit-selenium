package dev.hophamducnguyen.auto.web.test.common;

import dev.hophamducnguyen.auto.web.common.core.BaseTest;
import dev.hophamducnguyen.auto.web.common.parameter.csv.ExCsvFileSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;

public class CsvDataSourceTests extends BaseTest {

    private Environment env;

    @ParameterizedTest
    @CsvSource({"hello, 1", "hello, 2", "'happy, testing', 3"})
    void testWithCsvSource(String first, int second) {

        logger.info("\nCSV Parameterized test."
                + "\nPara1 (String): " + first
                + "\nPara2 (int): " + second);
        assertNotNull(first);
        assertNotEquals(0, second);
    }

    @ParameterizedTest
    @CsvFileSource(resources =  "/input.csv")
    void testWithCsvFileSource(String first, int second) {
        logger.info("\nCSV File Parameterized test."
                + "\nPara1 (String): " + first
                + "\nPara2 (int): " + second);

        assertNotNull(first);
        assertNotEquals(0, second);
    }

    @ParameterizedTest
    @ExCsvFileSource(resources =  "/input.csv")
    void testWithExCsvFileSource(String first, int second) {
        logger.info("\nCSV File Parameterized test."
                + "\nPara1 (String): " + first
                + "\nPara2 (int): " + second);

        assertNotNull(first);
        assertNotEquals(0, second);
    }
}
