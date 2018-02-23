package com.snilloc.services.impl;

import com.snilloc.services.EncryptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class EncryptionServiceImpl implements EncryptionService {

    private static Logger log = LoggerFactory.getLogger(EncryptionServiceImpl.class.getName());
    private static SecretKeySpec secretKey;

    public EncryptionServiceImpl(String myKey) {
        String ALGO = "SHA-1";
        MessageDigest sha = null;
        try {
            byte [] key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance(ALGO);
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException ex) {
            log.error("Algorithm: " + ALGO + "IS not supported", ex);
        } catch (UnsupportedEncodingException ex) {
            log.error("Unsupported Encoding", ex);
        }
    }

    public String encrypt(String input) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(input.getBytes("UTF-8")));
        } catch (Exception ex) {
            log.error("Error in trying to encrypt: " + input, ex);
        }

        return null;
    }

    public String decrypt(String input) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(input)));
        } catch (Exception ex) {
            log.error("Error while decrypting: " + ex.toString());
        }

        return null;
    }

    /**
     * the byte [] returned by MessageDigest does not have a nice
     * textual representation, so some form of encoding is usually performed.
     *
     * <p>
     *     This implementation follows the example of David Flanagan's book
     *     "Java In a Nutshell", and converts a byte array into a String
     *     of hex characters.
     * </p>
     * Another popular alternative is to use a "Based64" encoding.
     *
     * @param input
     * @return
     */
    static private String hexEncode(byte [] input) {
        StringBuilder result = new StringBuilder();
        char [] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (int idx = 0; idx < input.length; ++idx) {
            byte b = input[idx];
            result.append(digits[(b & 0xf0) >> 4]);
            result.append(digits[b & 0x0f]);
        }

        return result.toString();
    }
}
