package com.docuscan.ckyc.service.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class AESEncryptionService {

    private SecretKey secretKey;
    private IvParameterSpec iv;

    // Generate a new AES Key and IV when the service is initialized
    public AESEncryptionService() throws Exception {
        this.secretKey = generateAESKey();
        this.iv = generateIV();
    }

    // Generate a 256-bit AES session key step 1
    private SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }

    // Generate a random IV (16 bytes for AES)
    private IvParameterSpec generateIV() {
        byte[] ivBytes = new byte[16];
        new SecureRandom().nextBytes(ivBytes);
        return new IvParameterSpec(ivBytes);
    }

    // Encrypt a given input string using AES
    public String encrypt(String input) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] encryptedBytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt an encrypted string
    public String decrypt(String encryptedInput) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedInput);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    // Get AES key in Base64 format for storage or transmission
    public String getKeyAsBase64() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    // Get IV in Base64 format
    public String getIVAsBase64() {
        return Base64.getEncoder().encodeToString(iv.getIV());
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }
}


