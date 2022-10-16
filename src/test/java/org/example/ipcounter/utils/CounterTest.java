package org.example.ipcounter.utils;

import org.examples.ipcounter.utils.Counter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class CounterTest {

    @ParameterizedTest
    @MethodSource("counterProvider")
    public void counterTest(final List<Long> ips, final long expectedProcessed, final long expectedDistinct) {
        final Counter counter = new Counter();
        ips.forEach(counter::mark);
        Assertions.assertEquals(expectedProcessed, counter.getProcessed());
        Assertions.assertEquals(expectedDistinct, counter.getDistinct());
    }

    private static Stream<Arguments> counterProvider() {
        return Stream.of(
                Arguments.of(List.of(0L, 1L, 2L, 3L, 4L), 5L, 5L),
                Arguments.of(List.of(0L, 1L, 2L, 3L, 4L, 0L, 1L, 2L), 8L, 5L),
                Arguments.of(List.of(0L, 0x01010101L, 0x08080808L, 0x08080808L), 4L, 3L),
                Arguments.of(List.of(0xFFFFFFFFL, 0x08080808L, 0x08080808L), 3L, 2L)
        );
    }
}
