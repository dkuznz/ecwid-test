package org.example.ipcounter.utils;

import org.examples.ipcounter.utils.Converter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ConverterTest {

    @ParameterizedTest
    @MethodSource("convertParams")
    public void convertTest(final String s, final long expected) {
        Assertions.assertEquals(expected, Converter.convertAndValidate(s));
    }

    private static Stream<Arguments> convertParams() {
        return Stream.of(
                Arguments.of(null, Converter.ERROR_EMPTY),
                Arguments.of("", Converter.ERROR_EMPTY),
                Arguments.of(" ", Converter.ERROR_BAD_FORMAT),
                Arguments.of("1.2.3", Converter.ERROR_BAD_FORMAT),
                Arguments.of("256.2.3.4", Converter.ERROR_BAD_RANGE),
                Arguments.of("1.-2.3.4", Converter.ERROR_BAD_RANGE),
                Arguments.of("0.0.0.0", 0),
                Arguments.of("0.0.0.1", 1),
                Arguments.of("0.0.1.0", 256),
                Arguments.of("0.1.0.0", 65536),
                Arguments.of("1.0.0.0", 256 * 256 * 256),
                Arguments.of("1.2.3.4", 256 * 256 * 256 + 2 * 256 * 256 + 3 * 256 + 4),
                Arguments.of("255.255.255.255", (1L << 32) - 1)
        );
    }
}
