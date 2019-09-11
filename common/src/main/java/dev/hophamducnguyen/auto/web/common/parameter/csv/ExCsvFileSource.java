package dev.hophamducnguyen.auto.web.common.parameter.csv;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@API(
        status = Status.EXPERIMENTAL,
        since = "5.0"
)
@ArgumentsSource(ExCsvFileArgumentsProvider.class)
public @interface ExCsvFileSource {
    String[] resources();

    String encoding() default "UTF-8";

    String lineSeparator() default "\n";

    char delimiter() default ',';

    @API(
            status = Status.EXPERIMENTAL,
            since = "5.1"
    )
    int numLinesToSkip() default 0;

    @API(
            status = Status.EXPERIMENTAL,
            since = "5.5"
    )
    String emptyValue() default "";
}
