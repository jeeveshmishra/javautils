package my.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

public class CryptographyUtils {
    private static final Logger LOG = LoggerFactory.getLogger(CryptographyUtils.class);
    private static final int PASSWORDITERATIONS = 1;
    private static final int KEYSIZE = 128;

    public static String encrypt(
        String strToEncrypt,
        String predefinedKey,
        byte[] saltBytes,
        Charset charset,
        String hashAlgorithm,
        String keyGenerationAlgorithm,
        String encryptionAlgorithm
    ) {
        try {
            return cipherInput (
                strToEncrypt,
                predefinedKey,
                saltBytes,
                charset,
                hashAlgorithm,
                keyGenerationAlgorithm,
                encryptionAlgorithm,
                Cipher.ENCRYPT_MODE
            );
        }
        catch (Exception e) {
            LOG.error("Error while encrypting: ", e);
            return null;
        }
    }

    public static String decrypt(
        String strToDecrypt,
        String predefinedKey,
        byte[] saltBytes,
        Charset charset,
        String hashAlgorithm,
        String keyGenerationAlgorithm,
        String encryptionAlgorithm
    ) {
        try {
            return cipherInput (
                strToDecrypt,
                predefinedKey,
                saltBytes,
                charset,
                hashAlgorithm,
                keyGenerationAlgorithm,
                encryptionAlgorithm,
                Cipher.DECRYPT_MODE
            );
        }
        catch (Exception e) {
            LOG.error("Error while decrypting: ", e);
            return null;
        }
    }

    private static String cipherInput(
        String stringToCipher,
        String secret,
        byte[] saltBytes,
        Charset charset,
        String hashAlgorithm,
        String keyGenerationAlgorithm,
        String encryptionAlgorithm,
        int cypherMode
    ) throws Exception {

        SecretKeySpec secretKey = setSaltedKey(secret, saltBytes, hashAlgorithm, keyGenerationAlgorithm);

        Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
        cipher.init(cypherMode, secretKey);

        switch(cypherMode) {
        case Cipher.ENCRYPT_MODE:
            LOG.trace("Encrypting String: {} with key: {} with salt bytes {}", stringToCipher, secret, Arrays.toString(saltBytes));
            byte[] bytesToEncrypt = stringToCipher.getBytes(charset);
            byte[] encryptedBytes = cipher.doFinal(bytesToEncrypt);
            String encodedEncryptedBytes = Base64.getEncoder().encodeToString(encryptedBytes);
            LOG.trace("encodedEncryptedBytes: {}", encodedEncryptedBytes);

            return encodedEncryptedBytes;

        case Cipher.DECRYPT_MODE:
            LOG.trace("Decrypting String: {} with key: {} with salt bytes {}", stringToCipher, secret, Arrays.toString(saltBytes));
            byte[] bytesToDecrypt = Base64.getDecoder().decode(stringToCipher);
            byte[] decryptedBytes = cipher.doFinal(bytesToDecrypt);
            String decryptedString = new String(decryptedBytes, charset);
            LOG.trace("decryptedString: {}", decryptedString);

            return decryptedString;

        default:
            return null;
        }
    }

    private static SecretKeySpec setSaltedKey(
        String predefinedKey,
        byte[] saltBytes,
        String hashAlgorithm,
        String keyGenerationAlgorithm
    ) throws Exception {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(hashAlgorithm);

            PBEKeySpec spec = new PBEKeySpec(
                predefinedKey.toCharArray(),
                saltBytes,
                PASSWORDITERATIONS,
                KEYSIZE
            );
            SecretKey secret = factory.generateSecret(spec);

            return new SecretKeySpec(secret.getEncoded(), keyGenerationAlgorithm);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOG.error(e.toString());
            throw e;
        }
    }
}
