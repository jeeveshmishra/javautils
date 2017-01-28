package my.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadUtils.class);

    public static void runAsync(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
