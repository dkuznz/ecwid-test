package org.examples.ipcounter.utils;

/**
 * Converter string value with dot separator to long value.
 * e.g. "1.2.3.4" -> 16909060 (1 * 256 * 256 * 256 + 2 * 256 * 256 + 3 * 256 + 4)
 */
public class Converter {

    public static final int GROUP_COUNT = 4;
    public static final long ERROR_EMPTY = -1;
    public static final long ERROR_BAD_FORMAT = -2;
    public static final long ERROR_BAD_RANGE = -3;

    public static long convertAndValidate(final String ip) {
        if (ip == null || ip.isEmpty()) {
            return ERROR_EMPTY;
        }
        final String[] s = ip.split("\\.");
        if (s.length != GROUP_COUNT) {
            return ERROR_BAD_FORMAT;
        }
        long ip4 = 0;
        long shift = 0;
        for (int i = GROUP_COUNT - 1; i >= 0; i--) {
            final int b = Integer.parseInt(s[i]);
            if (b < 0 || b > 255) {
                return ERROR_BAD_RANGE;
            }
            ip4 += (long) b << shift;
            shift += 8;
        }
        return ip4;
    }

}
