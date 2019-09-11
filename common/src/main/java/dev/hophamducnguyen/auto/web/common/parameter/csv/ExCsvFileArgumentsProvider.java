
package dev.hophamducnguyen.auto.web.common.parameter.csv;

import dev.hophamducnguyen.auto.web.common.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvFormat;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParser;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParserSettings;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.PreconditionViolationException;
import org.junit.platform.commons.util.Preconditions;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.springframework.core.env.AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;

public class ExCsvFileArgumentsProvider implements AnnotationConsumer<ExCsvFileSource>, ArgumentsProvider {
    private final BiFunction<Class<?>, String, InputStream> inputStreamProvider;
    private ExCsvFileSource annotation;
    private String[] resources;
    private Charset charset;
    private CsvParserSettings settings;
    private int numLinesToSkip;

    ExCsvFileArgumentsProvider() {
        this(Class::getResourceAsStream);
    }

    ExCsvFileArgumentsProvider(BiFunction<Class<?>, String, InputStream> inputStreamProvider) {
        this.inputStreamProvider = inputStreamProvider;
    }

    public void accept(ExCsvFileSource annotation) {
        this.annotation = annotation;
        this.resources = annotation.resources();

        try {
            this.charset = Charset.forName(annotation.encoding());
        } catch (Exception var3) {
            throw new PreconditionViolationException("The charset supplied in " + this.annotation + " is invalid", var3);
        }

        this.numLinesToSkip = annotation.numLinesToSkip();
        this.settings = new CsvParserSettings();
        ((CsvFormat) this.settings.getFormat()).setDelimiter(annotation.delimiter());
        ((CsvFormat) this.settings.getFormat()).setLineSeparator(annotation.lineSeparator());
        ((CsvFormat) this.settings.getFormat()).setQuote('"');
        ((CsvFormat) this.settings.getFormat()).setQuoteEscape('"');
        this.settings.setEmptyValue(annotation.emptyValue());
        this.settings.setAutoConfigurationEnabled(false);
    }

    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Arrays.stream(this.resources).map((resource) -> {
            return this.openInputStream(context, resource);
        }).map(this::createCsvParser).flatMap(this::toStream);
    }

    private InputStream openInputStream(ExtensionContext context, String resource) {
        Preconditions.notBlank(resource, "Classpath resource [" + resource + "] must not be null or blank");
        String activeProfile = System.getProperty(ACTIVE_PROFILES_PROPERTY_NAME);
        String originalResource = resource;
        if (StringUtils.isNotBlank(activeProfile)) {
            resource = FileUtils.addInfix(Paths.get(resource), "-" + activeProfile).toString();
        }
        Class<?> testClass = context.getRequiredTestClass();
        InputStream tmp = (InputStream) this.inputStreamProvider.apply(testClass, resource);
        String finalResource = tmp != null ? resource : originalResource;
        return (InputStream) Preconditions.notNull((InputStream) this.inputStreamProvider.apply(testClass, finalResource), () -> {
            return "Classpath resource [" + finalResource + "] does not exist";
        });
    }

    private CsvParser createCsvParser(InputStream inputStream) {
        CsvParser csvParser = new CsvParser(this.settings);

        try {
            csvParser.beginParsing(inputStream, this.charset);
        } catch (Throwable var4) {
            CsvArgumentsProvider.handleCsvException(var4, this.annotation);
        }

        return csvParser;
    }

    private Stream<Arguments> toStream(CsvParser csvParser) {
        ExCsvFileArgumentsProvider.CsvParserIterator iterator = new ExCsvFileArgumentsProvider.CsvParserIterator(csvParser, this.annotation);
        return (Stream) StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 16), false).skip((long) this.numLinesToSkip).onClose(() -> {
            try {
                csvParser.stopParsing();
            } catch (Throwable var3) {
                CsvArgumentsProvider.handleCsvException(var3, this.annotation);
            }

        });
    }

    private static class CsvParserIterator implements Iterator<Arguments> {
        private final CsvParser csvParser;
        private final ExCsvFileSource annotation;
        private Object[] nextCsvRecord;

        CsvParserIterator(CsvParser csvParser, ExCsvFileSource annotation) {
            this.csvParser = csvParser;
            this.annotation = annotation;
            this.advance();
        }

        public boolean hasNext() {
            return this.nextCsvRecord != null;
        }

        public Arguments next() {
            Arguments result = Arguments.arguments(this.nextCsvRecord);
            this.advance();
            return result;
        }

        private void advance() {
            try {
                this.nextCsvRecord = this.csvParser.parseNext();
            } catch (Throwable var2) {
                CsvArgumentsProvider.handleCsvException(var2, this.annotation);
            }

        }
    }
}
