package com.docuscan.ckyc.service.encryption;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class RSAEncryptionService {

    @Value("${rsa.cer.filepath}")
    private String publicKeyPath; // Injected from properties or JVM args

    private PublicKey publicKey;

    // Load RSA Public Key lazily when first accessed
    private PublicKey getPublicKey() throws Exception {
        if (publicKey == null) {
            synchronized (this) {
                if (publicKey == null) {
                    try (FileInputStream fis = new FileInputStream(publicKeyPath)) {
                        CertificateFactory factory = CertificateFactory.getInstance("X.509");
                        X509Certificate certificate = (X509Certificate) factory.generateCertificate(fis);
                        publicKey = certificate.getPublicKey();
                        log.info("RSA Public Key loaded from: {}", publicKeyPath);
                    }
                }
            }
        }
        return publicKey;
    }

    // Encrypt AES Key using RSA step 4 and 5
    public String encryptAESKey(SecretKey aesKey) throws Exception {
        PublicKey rsaPublicKey = getPublicKey();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        byte[] encryptedKey = cipher.doFinal(aesKey.getEncoded());
        return Base64.getEncoder().encodeToString(encryptedKey);
    }
}

