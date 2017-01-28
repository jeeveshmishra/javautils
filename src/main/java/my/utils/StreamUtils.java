package my.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StreamUtils {

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];

            int bytesRead = inputStream.read(buffer);
            while (bytesRead != -1) {
                output.write(buffer, 0, bytesRead);
                bytesRead = inputStream.read(buffer);
            }

            return output.toByteArray();
        }
    }

    public static String toString(InputStream inputStream, Charset charset) throws IOException {
        return new String(getBytes(inputStream), charset);
    }

    public static String toString(InputStream inputStream) throws IOException {
        return toString(inputStream, StandardCharsets.UTF_8);
    }
}