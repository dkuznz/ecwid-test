package org.examples.ipcounter;

import org.examples.ipcounter.utils.Converter;
import org.examples.ipcounter.utils.Counter;
import org.examples.ipcounter.utils.LineFileReader;
import org.examples.ipcounter.utils.SimpleTimer;

public class IpCounter {

    public static void main(String[] args)  {

        if (args.length == 0) {
            System.out.println("Specify filename in argument");
            System.exit(1);
        }

        final Counter counter = new Counter();
        final SimpleTimer timer = new SimpleTimer();
        timer.start();
        try (final LineFileReader fileReader = new LineFileReader(args[0])) {
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
                if (counter.getProcessed() % 1_000_000 == 0) {
                    System.out.printf("Processed %d\r", counter.getProcessed());
                }
            }
            timer.stop();
            System.out.printf("\nTOTALS: lines = %d, processed = %d, unique = %d, time (ms) = %d\n",
                    fileReader.getTotal(), counter.getProcessed(), counter.getDistinct(), timer.getDeltaMs());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
