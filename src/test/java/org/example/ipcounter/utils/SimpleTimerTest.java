package org.example.ipcounter.utils;

import org.examples.ipcounter.utils.SimpleTimer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimpleTimerTest {

    @Test
    public void normalFlowTest() throws InterruptedException {
        final long waitMs = 1000L;
        final SimpleTimer timer = new SimpleTimer();
        timer.start();
        Thread.sleep(waitMs);
        timer.stop();
        final long firstDelta = timer.getDeltaMs();
        Assertions.assertTrue(firstDelta >= waitMs);
        timer.start();
        Thread.sleep(waitMs * 2);
        timer.stop();
        timer.getDeltaMs();
        final long secondDelta = timer.getDeltaMs();
        Assertions.assertTrue(secondDelta >= waitMs * 2);
    }

    @Test
    public void doubleStartTest() {
        final SimpleTimer timer = new SimpleTimer();
        timer.start();
        Assertions.assertThrows(RuntimeException.class, timer::start);
    }

    @Test
    public void stopBeforeStartTest() {
        final SimpleTimer timer = new SimpleTimer();
        Assertions.assertThrows(RuntimeException.class, timer::stop);
    }

    @Test
    public void doubleStopTest() {
        final SimpleTimer timer = new SimpleTimer();
        timer.start();
        timer.stop();
        Assertions.assertThrows(RuntimeException.class, timer::stop);
    }

    @Test
    public void deltaBeforeStartTest() {
        final SimpleTimer timer = new SimpleTimer();
        Assertions.assertEquals(0L, timer.getDeltaMs());
    }

    @Test
    public void deltaBeforeStopTest() {
        final SimpleTimer timer = new SimpleTimer();
        timer.start();
        Assertions.assertThrows(RuntimeException.class, timer::getDeltaMs);
    }

}
