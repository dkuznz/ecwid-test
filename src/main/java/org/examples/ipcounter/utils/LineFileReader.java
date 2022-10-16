package org.examples.ipcounter.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Line by line text file reader
 */
public class LineFileReader implements AutoCloseable {

    private final BufferedReader bufferedReader;
    private long total;

    public LineFileReader(final String filename) throws FileNotFoundException {
        bufferedReader = new BufferedReader(new FileReader(filename));
    }

    public String next() {
        try {
            final String line = bufferedReader.readLine();
            if (line != null) {
                total++;
                return line;
            } else {
                // EOF
                return null;
            }
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public void close() throws Exception {
        bufferedReader.close();
    }

    public long getTotal() {
        return total;
    }

}
