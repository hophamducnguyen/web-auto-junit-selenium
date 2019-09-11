package dev.hophamducnguyen.auto.web.test.common;

import dev.hophamducnguyen.auto.web.common.core.BaseTest;
import dev.hophamducnguyen.auto.web.common.parameter.json.ExJsonFileSource;
import dev.hophamducnguyen.auto.web.common.parameter.json.JsonSource;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonDataSourceTests extends BaseTest {

    static class Person {

        String firstName;
        String lastName;

        @Override
        public String toString() {
            return "Person{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + '}';
        }
    }

    @ParameterizedTest
    @JsonSource(value = "{\"key\":\"value\"}")
    void defaultJsonObjectTest(JsonObject object) {
        assertEquals(object.get("key").getAsString(), "value");
    }

    @ParameterizedTest
    @JsonSource(value = "{firstName:'Jane', lastName: 'Doe'}", type = Person.class)
    void specificObjectTest(Person person) {
        logger.info(person);
        assertAll(
                () -> assertEquals("Jane", person.firstName),
                () -> assertEquals("Doe", person.lastName));
    }

    @ParameterizedTest
    @JsonSource(value = "[{\"key\":\"value1\"},{\"key\":\"value2\"}]", type = JsonArray.class)
    void arrayOfObjectsTest(JsonArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject object = jsonArray.get(i).getAsJsonObject();
            assertEquals(object.get("key").getAsString(), String.format("value%d", i + 1));
        }
    }

    @ParameterizedTest
    @ExJsonFileSource(resources = "/single_object.json")
    void defaultObjectFileTest(JsonObject object) {
        assertEquals(object.get("key").getAsString(), "value");
    }

    @ParameterizedTest
    @ExJsonFileSource(resources = "/specific_object.json", type = Person.class)
    void specificObjectFileTest(Person person) {
        logger.info(person);
        assertAll(
                () -> assertEquals("Jane", person.firstName),
                () -> assertEquals("Doe", person.lastName));
    }

    @ParameterizedTest
    @ExJsonFileSource(resources = "/array_specific_objects.json", type = Person.class)
    void specificArrayObjectFileTest(ArrayList<Person> persons) {
        for(Person person : persons) {
            logger.info(person);
            assertNotNull(person);
        }
    }

//
//    @ParameterizedTest
//    @JsonSource("[1,2]")
//    void arrayOfNumbersTest(Integer number) {
//        assertThat(number.intValue()).isGreaterThan(0);
//    }
//
//    @ParameterizedTest
//    @JsonSource("[\"value1\",\"value2\"]")
//    void arrayOfStringsTest(String string) {
//        assertThat(string).startsWith("value");
//    }
//

//
//    @ParameterizedTest
//    @JsonFileSource(resources = "/array_objects.json")
//    void arrayOfObjectsFileTest(JsonObject object) {
//        assertThat(object.getString("key")).startsWith("value");
//    }
//
//    @ParameterizedTest
//    @JsonFileSource(resources = "/array_numbers.json")
//    void arrayOfNumbersFileTest(JsonNumber number) {
//        assertThat(number.intValue()).isGreaterThan(0);
//    }
//
//    @ParameterizedTest
//    @JsonFileSource(resources = "/array_strings.json")
//    void arrayOfStringsFileTest(JsonString string) {
//        assertThat(string.getString()).startsWith("value");
//    }

}
