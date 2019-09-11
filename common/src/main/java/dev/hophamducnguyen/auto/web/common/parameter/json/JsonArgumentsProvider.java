package dev.hophamducnguyen.auto.web.common.parameter.json;

import com.google.gson.Gson;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.Arrays;
import java.util.stream.Stream;

public class JsonArgumentsProvider implements AnnotationConsumer<JsonSource>, ArgumentsProvider {
    private String[] values;
    private Class<?> type;

    @Override
    public void accept(JsonSource jsonSource) {
        this.values = jsonSource.value();
        this.type = jsonSource.type();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
        return Arrays.stream(values)
                .map(value -> new Gson().fromJson(value, type))
                .map(Arguments::of);
    }
}
