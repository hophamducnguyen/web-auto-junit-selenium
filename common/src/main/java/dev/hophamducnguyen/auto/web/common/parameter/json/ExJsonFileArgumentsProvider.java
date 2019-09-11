package dev.hophamducnguyen.auto.web.common.parameter.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import dev.hophamducnguyen.auto.web.common.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.util.Preconditions;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static org.springframework.core.env.AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;

public class ExJsonFileArgumentsProvider implements AnnotationConsumer<ExJsonFileSource>, ArgumentsProvider {

    private final BiFunction<Class<?>, String, InputStream> inputStreamProvider;
    private String[] resources;
    private Class<?> type;

    ExJsonFileArgumentsProvider() {
        this(Class::getResourceAsStream);
    }

    ExJsonFileArgumentsProvider(BiFunction<Class<?>, String, InputStream> inputStreamProvider) {
        this.inputStreamProvider = inputStreamProvider;
    }

    @Override
    public void accept(ExJsonFileSource exJsonFileSource) {
        this.resources = exJsonFileSource.resources();
        this.type = exJsonFileSource.type();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Arrays.stream(resources)
                .map(resource -> openInputStream(context, resource))
                .flatMap(this::toStream);
    }

    private InputStream openInputStream(ExtensionContext context, String resource) {
        Preconditions.notBlank(resource, "Classpath resource [" + resource + "] must not be null or blank");
        String activeProfile = System.getProperty(ACTIVE_PROFILES_PROPERTY_NAME);
        String originalResource = resource;

        if (StringUtils.isNotBlank(activeProfile)) {
            resource = FileUtils.addInfix(Paths.get(resource), "-" + activeProfile).toString();
        }
        Class<?> testClass = context.getRequiredTestClass();
        testClass.getClassLoader().getResource(resource);
        InputStream tmp = this.inputStreamProvider.apply(testClass, resource);
        String finalResource = tmp != null ? resource : originalResource;
        return Preconditions.notNull(this.inputStreamProvider.apply(testClass, finalResource), () -> "Classpath resource [" + finalResource + "] does not exist");
    }

    private Stream<Arguments> toStream(InputStream inputStream) {
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        if (jsonElement.isJsonArray()) {
            Type typeOfT = TypeToken.getParameterized(ArrayList.class, this.type).getType();
            ArrayList<?> list = new Gson().fromJson(jsonElement.getAsJsonArray(), typeOfT);
            return Stream.of(Arguments.of(list, this.type));
        } else {
            return Stream.of(Arguments.of(new Gson().fromJson(jsonElement, this.type)));
        }
    }
}
