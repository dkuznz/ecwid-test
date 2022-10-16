package org.examples.ipcounter.utils;

/**
 * Counter for unique IP-address
 */
public class Counter {
    /**
     * Total bit count for IPv4 addresses [0-255].[0-255].[0-255].[0-255]
     */
    private static final int IP4_BIT_COUNT = 32;
    /**
     * Bits per one block (32-bit int) for store flags
     */
    private static final int BLOCK_BIT_COUNT = 32;
    /**
     * Total count for IPv4 addresses 2^32 = 4294967296
     */
    private static final long IPV4_ADDRESS_COUNT = 1L << IP4_BIT_COUNT;

    private static final int[] ONES_COUNT_IN_BYTE;

    static {
        final byte[] onesCountInHalfByte = {
                0, // 0b0000
                1, // 0b0001
                1, // 0b0010
                2, // 0b0011
                1, // 0b0100
                2, // 0b0101
                2, // 0b0110
                3, // 0b0111
                1, // 0b1000
                2, // 0b1001
                2, // 0b1010
                3, // 0b1011
                2, // 0b1100
                3, // 0b1101
                3, // 0b1110
                4  // 0b1111
        };

        ONES_COUNT_IN_BYTE = new int[256];
        for (int i = 0; i < ONES_COUNT_IN_BYTE.length; i++) {
            ONES_COUNT_IN_BYTE[i] = onesCountInHalfByte[i >> 4] + onesCountInHalfByte[i & 0x0F];
        }
    }

    /**
     * Use array of blocks (32-bit int) for store flags - 1 bit for 1 ipv4 address
     */
    private final int[] blocks;
    private long processed;

    public Counter() {
        final int size = Math.toIntExact(IPV4_ADDRESS_COUNT / BLOCK_BIT_COUNT);
        blocks = new int[size];
    }

    public void mark(final long ip4) {
        final int idx = Math.toIntExact(ip4 / BLOCK_BIT_COUNT);
        final int insideBlockIdx = (int) (ip4 % BLOCK_BIT_COUNT);
        final int mask = 1 << insideBlockIdx;
        blocks[idx] = blocks[idx] | mask;
        processed++;
    }

    public long getProcessed() {
        return processed;
    }

    public long getDistinct() {
        long count = 0;
        for (int i : blocks) {
            count += ONES_COUNT_IN_BYTE[i >>> 24]
                    + ONES_COUNT_IN_BYTE[(i >>> 16) & 0xFF]
                    + ONES_COUNT_IN_BYTE[(i >>> 8) & 0xFF]
                    + ONES_COUNT_IN_BYTE[i & 0xFF];
        }
        return count;
    }

}
