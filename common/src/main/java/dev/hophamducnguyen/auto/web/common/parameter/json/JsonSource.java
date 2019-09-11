package dev.hophamducnguyen.auto.web.common.parameter.json;

import com.google.gson.JsonObject;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(JsonArgumentsProvider.class)
public @interface JsonSource {
    String[] value();

    Class<?> type() default JsonObject.class;
}
