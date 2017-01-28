package my.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipUtils {

    public static byte[] compress(String string) throws Exception {
        byte[] bytes = string.getBytes(StringUtils.DEFAULT_CHARSET);
        return compress(bytes);
    }

    public static byte[] compress(byte[] bytes) throws Exception {
        validateBytesInput(bytes);

        boolean isZipped = isZipped(bytes);
        if (isZipped) {
            return bytes;
        }

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(bytes);
            gzipOutputStream.close();

            return byteArrayOutputStream.toByteArray();
        }
    }

    public static String decompress(byte[] bytes) throws Exception {
        validateBytesInput(bytes);

        boolean isZipped = isZipped(bytes);
        if (!isZipped) {
            return new String(bytes, StringUtils.DEFAULT_CHARSET);
        }

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
            GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gzipInputStream, StringUtils.DEFAULT_CHARSET));

            StringBuilder result = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }

                result.append(line);
            }

            bufferedReader.close();
            gzipInputStream.close();

            return result.toString();
        }
    }

    private static boolean isZipped(final byte[] bytes) {
        return (bytes[0] == (byte) (GZIPInputStream.GZIP_MAGIC)) && (bytes[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
    }

    private static void validateBytesInput(final byte[] bytes) {
        boolean isValidInput = bytes != null && bytes.length > 0;
        if (!isValidInput) {
            throw new IllegalArgumentException("Cannot zip/unzip null or empty bytes");
        }
    }
}
