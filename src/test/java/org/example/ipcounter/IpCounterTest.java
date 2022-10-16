package org.example.ipcounter;

import org.examples.ipcounter.utils.Converter;
import org.examples.ipcounter.utils.Counter;
import org.examples.ipcounter.utils.LineFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URL;

public class IpCounterTest {

    @Test
    public void testIT() throws Exception {
        final Counter counter = new Counter();
        final URL url = getClass().getClassLoader().getResource("sample.txt");
        Assertions.assertNotNull(url);
        final LineFileReader fileReader = new LineFileReader(url.getFile());
        while (true) {
            final String sip = fileReader.next();
            if (sip == null) {
                break;
            }
            final long ip4 = Converter.convertAndValidate(sip);
            if (ip4 < 0) {
                continue;
            }
            counter.mark(ip4);
        }
        fileReader.close();
        Assertions.assertEquals(12, fileReader.getTotal());
        Assertions.assertEquals(12, counter.getProcessed());
        Assertions.assertEquals(10, counter.getDistinct());
    }
}
