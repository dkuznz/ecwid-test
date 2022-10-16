package org.examples.ipcounter.utils;

public class SimpleTimer {

    private long start;
    private long stop;
    private boolean started = false;
    private boolean stopped = true;

    public void start() {
        if (started) {
            throw new RuntimeException("Already started");
        }
        started = true;
        stopped = false;
        start = System.currentTimeMillis();
    }

    public void stop() {
        if (!started) {
            throw new RuntimeException("Not started");
        }
        if (stopped) {
            throw new RuntimeException("Already stopped");
        }
        stop = System.currentTimeMillis();
        started = false;
        stopped = true;
    }

    public long getDeltaMs() {
        if (started) {
            throw new RuntimeException("Not stopped");
        }
        return stop - start;
    }
}
