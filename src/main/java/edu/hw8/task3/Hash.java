package edu.hw8.task3;

import java.util.Arrays;

class Hash {
    private static final int RADIX = 16;
    private static final int BYTE_MASK = 0xff;
    private final byte[] bytes;
    private int hash = 0;
    private boolean hashIsZero;

    Hash(byte[] bytes) {
        this.bytes = bytes;
    }

    Hash(String hexString) {
        if (hexString.length() % 2 != 0) {
            throw new IllegalArgumentException("Odd length byte string");
        }
        bytes = new byte[hexString.length() / 2];
        for (int i = 0; i + 2 <= hexString.length(); i += 2) {
            bytes[i / 2] = (byte) (Integer.parseInt(hexString.substring(i, i + 2), RADIX) & BYTE_MASK);
        }
    }

    @Override
    public int hashCode() {
        if (hash == 0 && !hashIsZero) {
            hash = Arrays.hashCode(bytes);
            hashIsZero = hash == 0;
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Hash that)) {
            return false;
        }
        return Arrays.equals(this.bytes, that.bytes);
    }
}
