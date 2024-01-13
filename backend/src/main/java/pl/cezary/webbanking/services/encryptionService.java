package pl.cezary.webbanking.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/*
### Dlaczego to podejście jest bezpieczne:
1. AES-256:
Jest to jedna z najbezpieczniejszych dostępnych metod szyfrowania
symetrycznego i jest uznawana za wystarczająco silną, aby chronić dane rządowe
o najwyższym stopniu tajności.

2. Tryb CBC z IV:
Zastosowanie trybu CBC (Cipher Block Chaining) z losowym IV(Initialization Vector)
dla każdego szyfrowania zapewnia, że te same dane wejściowe będą dawały różne dane wyjściowe,
co utrudnia analizę wzorców w zaszyfrowanych danych.

3. Bezpieczeństwo implementacji:
Wszystkie operacje kryptograficzne są wykonywane
przy użyciu sprawdzonych bibliotek, co minimalizuje ryzyko błędów w implementacji.

4. Ochrona klucza:
Klucz szyfrowania powinien być przechowywany w bezpieczny sposóbW,
np. przy użyciu menedżera sekretów, aby zapobiec jego przechwyceniu lub
nieautoryzowanemu dostępowi.
*/
@Service
public class encryptionService {

//    @Value("${cezarys8.app.encryptionKey}")
//    private String encryptionKey;

//    private final String encryptionKey = "0I6YDGUd0zVE7oiJxkGIqgAG0YP5BYrkDopXp7j8v33/Om9Phy8WyLD+9HKNxsP=";

    private final String base64EncryptionKey = "bDo4fovkfuvyq5w8x5L3J63n78CpW1yf2NQphmkL2oE=";

    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(base64EncryptionKey), "AES");
            IvParameterSpec ivSpec = generateIv();
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            byte[] encryptedIVAndText = concatenateIvAndEncryptedData(ivSpec.getIV(), encrypted);
            return Base64.getEncoder().encodeToString(encryptedIVAndText);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting: " + e.getMessage());
        }
    }

    public String decrypt(String encryptedData) {
        try {
            byte[] ivAndEncryptedData = Base64.getDecoder().decode(encryptedData);
            IvParameterSpec ivSpec = extractIvFromEncryptedData(ivAndEncryptedData);
            byte[] encryptedDataWithoutIv = extractEncryptedDataWithoutIv(ivAndEncryptedData);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(base64EncryptionKey), "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decrypted = cipher.doFinal(encryptedDataWithoutIv);

            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting: " + e.getMessage());
        }
    }

    private IvParameterSpec generateIv() {
        byte[] iv = new byte[16]; // AES używa bloków 16-bajtowych
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    private byte[] concatenateIvAndEncryptedData(byte[] iv, byte[] encrypted) {
        byte[] combined = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);
        return combined;
    }

    private IvParameterSpec extractIvFromEncryptedData(byte[] ivAndEncryptedData) {
        byte[] iv = new byte[16];
        System.arraycopy(ivAndEncryptedData, 0, iv, 0, 16);
        return new IvParameterSpec(iv);
    }

    private byte[] extractEncryptedDataWithoutIv(byte[] ivAndEncryptedData) {
        byte[] encrypted = new byte[ivAndEncryptedData.length - 16];
        System.arraycopy(ivAndEncryptedData, 16, encrypted, 0, encrypted.length);
        return encrypted;
    }
}
