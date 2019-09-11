package dev.hophamducnguyen.auto.web.common.parameter.csv;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.CsvParsingException;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvFormat;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParser;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParserSettings;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.PreconditionViolationException;
import org.junit.platform.commons.util.BlacklistedExceptions;
import org.junit.platform.commons.util.Preconditions;

public class CsvArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<CsvSource> {
    private static final String LINE_SEPARATOR = "\n";
    private CsvSource annotation;

    CsvArgumentsProvider() {
    }

    public void accept(CsvSource annotation) {
        this.annotation = annotation;
    }

    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        CsvParserSettings settings = new CsvParserSettings();
        ((CsvFormat)settings.getFormat()).setDelimiter(this.annotation.delimiter());
        ((CsvFormat)settings.getFormat()).setLineSeparator("\n");
        ((CsvFormat)settings.getFormat()).setQuote('\'');
        ((CsvFormat)settings.getFormat()).setQuoteEscape('\'');
        settings.setEmptyValue(this.annotation.emptyValue());
        settings.setAutoConfigurationEnabled(false);
        CsvParser csvParser = new CsvParser(settings);
        AtomicLong index = new AtomicLong(0L);
        return Arrays.stream(this.annotation.value()).map((line) -> {
            String[] parsedLine = null;

            try {
                parsedLine = csvParser.parseLine(line + "\n");
            } catch (Throwable var6) {
                handleCsvException(var6, this.annotation);
            }

            Preconditions.notNull(parsedLine, () -> {
                return "Line at index " + index.get() + " contains invalid CSV: \"" + line + "\"";
            });
            return parsedLine;
        }).peek((values) -> {
            index.incrementAndGet();
        }).map(Arguments::of);
    }

    static void handleCsvException(Throwable throwable, Annotation annotation) {
        BlacklistedExceptions.rethrowIfBlacklisted(throwable);
        if (throwable instanceof PreconditionViolationException) {
            throw (PreconditionViolationException)throwable;
        } else {
            throw new CsvParsingException("Failed to parse CSV input configured via " + annotation, throwable);
        }
    }
}
