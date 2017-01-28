package my.utils;

import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    public static byte[] generateRandomBytes(int numberOfBytes) {
        byte[] randomBytes = new byte[numberOfBytes];
        ThreadLocalRandom.current().nextBytes(randomBytes);
        return randomBytes;
    }

    public static String generateNonce(int numberOfBytes) {
        byte[] randomBytes = generateRandomBytes(numberOfBytes);
        return Base64.getUrlEncoder().encodeToString(randomBytes);
    }
}
