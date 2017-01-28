package my.utils;

import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

import static org.testng.Assert.*;

public class CryptographyUtilsTest {
    @Test
    public void testCryptography () throws Exception {
        Random random = new Random();
        byte[] saltBytes = new byte[8];
        random.nextBytes(saltBytes);

        String key = "-,k5&sd*k4nfk3l5nml53rkml,msdc2mlkasm09lkfmw3ee";
        String hashAlgorithm = "PBKDF2WithHmacSHA1";
        String keyGenerationAlgorithm = "AES";
        String encryptionAlgorithm = "AES/ECB/PKCS5Padding";
        key = new String(Arrays.copyOf(key.getBytes(), 16));

        String testString = "Pasientsky";

        String encryptedString = CryptographyUtils.encrypt(testString, key, saltBytes, StandardCharsets.UTF_8, hashAlgorithm, keyGenerationAlgorithm, encryptionAlgorithm);

        assertNotNull(encryptedString);

        String decryptedString = CryptographyUtils.decrypt(encryptedString, key, saltBytes, StandardCharsets.UTF_8, hashAlgorithm, keyGenerationAlgorithm, encryptionAlgorithm);

        assertNotNull(decryptedString);

        assertEquals(decryptedString, testString);

    }

    @Test
    public void testCryptography_when_saltString_not_same () throws Exception {
        byte[] saltBytes = new byte[8];
        new Random().nextBytes(saltBytes);

        String key = "-,k5&sd*k4nfk3l5nml53rkml,msdc2mlkasm09lkfmw3ee";
        String hashAlgorithm = "PBKDF2WithHmacSHA1";
        String keyGenerationAlgorithm = "AES";
        String encryptionAlgorithm = "AES/ECB/PKCS5Padding";
        key = new String(Arrays.copyOf(key.getBytes(), 16));

        String testString = "Pasientsky";

        String encryptedString = CryptographyUtils.encrypt(testString, key, saltBytes, StandardCharsets.UTF_8, hashAlgorithm, keyGenerationAlgorithm, encryptionAlgorithm);

        assertNotNull(encryptedString);

        saltBytes = new byte[8];
        new Random().nextBytes(saltBytes);

        String decryptedString = CryptographyUtils.decrypt(encryptedString, key, saltBytes, StandardCharsets.UTF_8, hashAlgorithm, keyGenerationAlgorithm, encryptionAlgorithm);

        assertNull(decryptedString);
    }
}
