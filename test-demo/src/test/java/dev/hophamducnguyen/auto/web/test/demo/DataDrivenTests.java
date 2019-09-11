package dev.hophamducnguyen.auto.web.test.demo;

import dev.hophamducnguyen.auto.web.common.core.BaseTest;
import dev.hophamducnguyen.auto.web.common.parameter.csv.ExCsvFileSource;
import dev.hophamducnguyen.auto.web.common.parameter.json.ExJsonFileSource;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.*;

public class DataDrivenTests extends BaseTest {

    static class Person {

        String firstName;
        String lastName;

        @Override
        public String toString() {
            return "Person{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + '}';
        }
    }

    @ParameterizedTest
    @ExJsonFileSource(resources = "/testdata/specific-object.json", type = Person.class)
    void specificJsonObjectFileTest(Person person) {
        logger.info(person);
        assertAll(
                () -> assertEquals("Nguyen", person.firstName),
                () -> assertEquals("Ho", person.lastName));
    }

    @ParameterizedTest
    @ExCsvFileSource(resources =  "/testdata/input.csv")
    void testWithExCsvFileSource(String first, int second) {
        logger.info("\nCSV File Parameterized test."
                + "\nPara1 (String): " + first
                + "\nPara2 (int): " + second);

        assertNotNull(first);
        assertNotEquals(0, second);
    }
}
