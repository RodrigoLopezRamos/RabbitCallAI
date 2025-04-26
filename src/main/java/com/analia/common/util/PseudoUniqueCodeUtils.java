package com.analia.common.util;

import java.util.concurrent.ThreadLocalRandom;

public class PseudoUniqueCodeUtils {
    public static String PREFIX_ZEROS = "0000000000";

    public static String generatePseudoUniqueCode(PseudoUniqueCode pseudoUniqueCode) {
        StringBuilder sb = new StringBuilder(PREFIX_ZEROS);
        sb.append(System.currentTimeMillis()).append(Math.abs(ThreadLocalRandom.current().nextLong()));
        return sb.substring(sb.length() - pseudoUniqueCode.getSize());
    }

    public enum PseudoUniqueCode {
        PSEUDO_SHORT(4), PSEUDO_CODE_STANDARD(10), PSEUDO_CODE_LARGE(15), PSEUDO_CODE_XTRA_LARGE(20);

        private int size;

        PseudoUniqueCode(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}
