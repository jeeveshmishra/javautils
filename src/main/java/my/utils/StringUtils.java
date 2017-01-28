package my.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringUtils {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public static boolean isNull(String string) {
        return string == null;
    }

    public static boolean isEmpty(String string) {
        return string.isEmpty();
    }

    public static boolean isWhitespace(String string) {
        return string.trim().length() == 0;
    }

    public static boolean isNullOrEmpty(String string) {
        return isNull(string) || isEmpty(string);
    }

    public static boolean isNullEmptyOrWhitespace(String string) {
        return isNullOrEmpty(string) || isWhitespace(string);
    }

    public static InputStream toInputStream(String string) {
        return toInputStream(string, DEFAULT_CHARSET);
    }

    public static InputStream toInputStream(String string, Charset charset) {
        return new ByteArrayInputStream(string.getBytes(charset));
    }

    public static OutputStream toOutputStream(String string) throws Exception {
        return toOutputStream(string, DEFAULT_CHARSET);
    }

    public static OutputStream toOutputStream(String string, Charset charset) throws Exception {
        boolean isValidInput = !isNullEmptyOrWhitespace(string);
        if (!isValidInput) {
            throw new IllegalArgumentException("String is either null or empty");
        }

        byte bytesArrayFromString[] = string.getBytes(charset);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bytesArrayFromString.length);
        byteArrayOutputStream.write(bytesArrayFromString);

        return byteArrayOutputStream;
    }

    public static String toString(byte[] bytes) {
        return toString(bytes, DEFAULT_CHARSET);
    }

    public static String toString(byte[] bytes, Charset charsets) {
        return bytes == null ? "" : new String(bytes, charsets);
    }
}
