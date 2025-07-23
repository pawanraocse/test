package com.practise.random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {

    public static void main(String[] args) {
        byte[] key = new byte[32]; // 32 bytes for 256 bits
        new SecureRandom().nextBytes(key);

        SecretKeySpec secretKey = new SecretKeySpec(key, "HmacSHA256");

        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKey);
            byte[] hmacHash = mac.doFinal(key); // Using the original key as input for demonstration

            String encodedKey = Base64.getEncoder().encodeToString(hmacHash);

            System.out.println(encodedKey);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }



    }
}
