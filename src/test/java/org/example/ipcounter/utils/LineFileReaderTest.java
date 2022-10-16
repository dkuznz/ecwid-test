package org.example.ipcounter.utils;

import org.examples.ipcounter.utils.LineFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.FileNotFoundException;
import java.net.URL;

public class LineFileReaderTest {

    @Test
    public void readDummyFileTest() {
        final URL url = getClass().getClassLoader().getResource("dummy.txt");
        Assertions.assertNotNull(url);
        try (final LineFileReader reader = new LineFileReader(url.getFile())) {
            final String s = reader.next();
            Assertions.assertNull(s);
            Assertions.assertEquals(0, reader.getTotal());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void readFileTest() throws Exception {
        final URL url = getClass().getClassLoader().getResource("sample.txt");
        Assertions.assertNotNull(url);
        try (final LineFileReader reader = new LineFileReader(url.getFile())) {
            String line;
            do {
                line = reader.next();
            } while (line != null);
            Assertions.assertEquals(12, reader.getTotal());
        }
    }

    @Test
    public void readNonExistFileTest() {
        final Executable executable = () -> {
            try (final LineFileReader reader = new LineFileReader("file-not-found.txt")) {
                reader.next();
            }
        };
        Assertions.assertThrows(FileNotFoundException.class, executable);
    }

}
