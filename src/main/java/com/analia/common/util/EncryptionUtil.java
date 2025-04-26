package com.analia.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class EncryptionUtil {
    public static final Log LOG = LogFactory.getLog(EncryptionUtil.class);
    private static final String KEY_PROPERTY = "encryption.key";
    private static final String IV_PROPERTY = "encryption.iv";
    private static final String ALGORITHM_PROPERTY = "encryption.algorithm";
    private static final String CIPHER_SUFFIX = "/CBC/PKCS5Padding";
    private static final String DEVELOPER_INIT_VECTOR = "smEtcjirKQbVPr2Zu+fqdA==";
    private static final String DEVELOPER_KEY = "mQCiJPXZ4soIgBuEIR0lGw==";
    private static final ThreadLocal<EncryptionUtil> threadLocal = new ThreadLocal<EncryptionUtil>();
    public static String DEFAULT_ALGORITHM = "AES";
    private static String iv;
    private static String keyString;
    private static String algorithm;
    private static final IvParameterSpec ivParam;
    private static final SecretKeySpec key;

    static {
        algorithm = System.getProperty(ALGORITHM_PROPERTY);
        if (algorithm == null) {
            algorithm = DEFAULT_ALGORITHM;
            LOG.info("EncryptionUtil using default algorithm");
        }

        iv = System.getProperty(IV_PROPERTY);
        if (iv == null) {
            iv = DEVELOPER_INIT_VECTOR;
            LOG.warn("[[[[[[[[[[[[[[[[[ Using DEVELOPER IV ]]]]]]]]]]]]]]]]]");
        }

        keyString = System.getProperty(KEY_PROPERTY);

        if (keyString == null) {
            keyString = DEVELOPER_KEY;
            LOG.warn("[[[[[[[[[[[[[[[[[ Using DEVELOPER KEY ]]]]]]]]]]]]]]]]]");
        }

        byte[] keyBytes = Base64.decodeBase64(keyString);
        key = new SecretKeySpec(keyBytes, algorithm);

        ivParam = new IvParameterSpec(Base64.decodeBase64(iv));
    }


    private final Cipher cipher;

    /**
     * new instance of EncryptionUtils. Init params from system properties or default.
     */
    public EncryptionUtil() {
        try {
            this.cipher = Cipher.getInstance(algorithm + CIPHER_SUFFIX);
        } catch (Exception e) {
            LOG.error("Unable to instantiate cipher for Algorithm '" + algorithm + "'. Due to exception: " + e.getMessage(), e);
            throw new IllegalArgumentException("Unable to instantiate cipher!", e);
        }
    }

    public static EncryptionUtil getEncObjectFromThreadLocal() {
        if (threadLocal.get() != null) {
            throw new IllegalStateException("EncryptionUtil Object is already created");
        }
        EncryptionUtil encryptionUtilObejct = new EncryptionUtil();
        threadLocal.set(encryptionUtilObejct);
        return encryptionUtilObejct;
    }

    /**
     * Encrypts a string using the configured cipher.
     *
     * @param input The string to encrypt
     * @return An encrypted string (Base64)
     * @throws IllegalArgumentException if an error occurs.
     */
    public String encrypt(String input) {
        String result = null;
        if (input != null) {
            try {
                byte[] inputBytes = input.getBytes();
                // init the cipher
                this.cipher.init(Cipher.ENCRYPT_MODE, key, ivParam);
                byte[] encrypted = new byte[cipher.getOutputSize(inputBytes.length)];
                int encLength = cipher.update(inputBytes, 0, inputBytes.length, encrypted);
                encLength += cipher.doFinal(encrypted, encLength);
                result = new String(Base64.encodeBase64(encrypted), StandardCharsets.UTF_8);
            } catch (Exception e) {
                LOG.error("Unable to encrypt", e);
                throw new IllegalArgumentException("Unable to encrypt due to exception!", e);
            }
        }
        return result;
    }

    /**
     * De-encrypt a Base64 encrypted string into its original content
     *
     * @param input The encrypted string
     * @return the original string
     * @throws IllegalArgumentException if an error occurs.
     */
    public String decrypt(String input) {
        String result = null;
        if (input != null) {
            try {
                byte[] encrypted = Base64.decodeBase64(input);

                // init the cipher
                this.cipher.init(Cipher.DECRYPT_MODE, key, ivParam);

                byte[] decrypted = new byte[cipher.getOutputSize(encrypted.length)];
                int decLength = cipher.update(encrypted, 0, encrypted.length, decrypted);
                decLength += cipher.doFinal(decrypted, decLength);
                result = new String(decrypted);
            } catch (Exception e) {
                LOG.error("Unable to decrypt", e);
                throw new IllegalArgumentException("Unable to encrypt due to exception!", e);
            }
        }
        return result;
    }

}
