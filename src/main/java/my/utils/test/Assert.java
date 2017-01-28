package my.utils.test;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

public class Assert {
    public static  <T> void assertContains(List<T> list, T object) {
        String message = String.format("Failed to assert that list contains object: %s - ", object.toString());
        boolean containsObject = list.contains(object);
        assertTrue(containsObject, message);
    }

    public static <T> void assertNotContains(List<T> list, T object) {
        String message = String.format("Failed to assert that list not contains object: %s - ", object.toString());
        boolean containsObject = list.contains(object);
        assertFalse(containsObject, message);
    }

    public static <Key, Value> void assertContainsKey(Map<Key, Value> map, Key key) {
        String message = String.format("Failed to assert that map contains key: %s - ", key.toString());
        boolean containsObject = map.containsKey(key);
        assertTrue(containsObject, message);
    }

    public static <Key, Value> void assertNotContainsKey(Map<Key, Value> map, Key key) {
        String message = String.format("Failed to assert that map not contains key: %s - ", key.toString());
        boolean containsObject = map.containsKey(key);
        assertFalse(containsObject, message);
    }
}
