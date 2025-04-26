package com.analia.common.util;

public class SHA256Util {
    public final static String METHOD = "SHA-256";

    public static String SHA256(String SHA256) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance(METHOD);
            byte[] array = md.digest(SHA256.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new IllegalStateException("Encryption! Failed! " + e.getMessage(), e);
        }
    }
}