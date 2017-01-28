package my.utils;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static my.utils.test.Assert.assertContains;
import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class FunctionalMapTest {

    @Test
    public void test_of_entrySet() {
        Map<String, String> map = new HashMap<>();
        map.put("key-one", "value-one");
        map.put("key-two", "value-two");

        Set<Map.Entry<String, String>> source = map.entrySet();

        Map<String, String> result = FunctionalMap.of(source).all();

        assertEquals(result.size(), 2);
        assertEquals(result.get("key-one"), "value-one");
        assertEquals(result.get("key-two"), "value-two");
    }

    @Test
    public void test_of_map() {
        Map<String, String> source = new HashMap<>();
        source.put("key-one", "value-one");
        source.put("key-two", "value-two");

        Map<String, String> result = FunctionalMap.of(source).all();

        assertEquals(result.size(), 2);
        assertEquals(result.get("key-one"), "value-one");
        assertEquals(result.get("key-two"), "value-two");
    }

    @Test
    public void test_put() {
        Map<String, String> source = new HashMap<>();
        source.put("key-one", "value-one");
        source.put("key-two", "value-two");

        Map<String, String> result = FunctionalMap.ofMap(source).put("key-three", "value-three").all();

        assertEquals(result.size(), 3);
        assertEquals(result.get("key-one"), "value-one");
        assertEquals(result.get("key-two"), "value-two");
        assertEquals(result.get("key-three"), "value-three");
    }

    @Test
    public void test_merge() {
        Map<String, String> source = new HashMap<>();
        source.put("key-one", "value-one");
        source.put("key-two", "value-two");

        Map<String, String> otherSource = new HashMap<>();
        otherSource.put("key-two", "new-value-two");
        otherSource.put("key-three", "value-three");

        Map<String, String> result = FunctionalMap.ofMap(source).merge(otherSource).all();

        assertEquals(result.size(), 3);
        assertEquals(result.get("key-one"), "value-one");
        assertEquals(result.get("key-two"), "new-value-two");
        assertEquals(result.get("key-three"), "value-three");
    }

    @Test
    public void test_merge_whenSourceIsNull() {
        Map<String, String> source = new HashMap<>();
        source.put("key-one", "value-one");
        source.put("key-two", "value-two");

        Map<String, String> otherSource = null;

        Map<String, String> result = FunctionalMap.ofMap(source).merge(otherSource).all();

        assertEquals(result.size(), 2);
        assertEquals(result.get("key-one"), "value-one");
        assertEquals(result.get("key-two"), "value-two");
    }

    @Test
    public void test_remove() {
        Map<String, String> source = new HashMap<>();
        source.put("key-one", "value-one");
        source.put("key-two", "value-two");
        source.put("key-three", "value-three");

        Map<String, String> result = FunctionalMap.ofMap(source).remove("key-two").all();

        assertEquals(result.size(), 2);
        assertEquals(result.get("key-one"), "value-one");
        assertEquals(result.get("key-three"), "value-three");
    }

    @Test
    public void test_filter() {
        Map<String, String> source = new HashMap<>();
        source.put("key-one", "value-one");
        source.put("key-two", "value-two-to-be-removed");
        source.put("key-three", "value-three");
        source.put("key-four", "value-four-to-be-removed");

        Map<String, String> result = FunctionalMap.ofMap(source).filter(value -> !value.contains("to-be-removed")).all();

        assertEquals(result.size(), 2);
        assertEquals(result.get("key-one"), "value-one");
        assertEquals(result.get("key-three"), "value-three");
    }

    @Test
    public void test_filterNot() {
        Map<String, String> source = new HashMap<>();
        source.put("key-one", "value-one");
        source.put("key-two", "value-two-to-be-removed");
        source.put("key-three", "value-three");
        source.put("key-four", "value-four-to-be-removed");

        Map<String, String> result = FunctionalMap.ofMap(source).filterNot(value -> value.contains("to-be-removed")).all();

        assertEquals(result.size(), 2);
        assertEquals(result.get("key-one"), "value-one");
        assertEquals(result.get("key-three"), "value-three");
    }

    @Test
    public void test_values() {
        Map<String, String> source = new HashMap<>();
        source.put("key-one", "one");
        source.put("key-two", "two");
        source.put("key-three", "three");

        List<String> result = FunctionalMap.ofMap(source).values().all();

        assertEquals(result.size(), 3);
        assertContains(result, "one");
        assertContains(result, "two");
        assertContains(result, "three");
    }

    @Test
    public void test_foreach_whenNoException() {
        Map<String, String> result = new HashMap<>();
        Map<String, String> source = new HashMap<>();
        source.put("key-one", "value-one");
        source.put("key-two", "value-two");
        source.put("key-three", "value-three");

        FunctionalMap.ofMap(source).forEach(result::put);

        assertEquals(result.size(), 3);
        assertEquals(result.get("key-one"), "value-one");
        assertEquals(result.get("key-two"), "value-two");
        assertEquals(result.get("key-three"), "value-three");
    }

    @Test (expectedExceptions = RuntimeException.class)
    public void test_foreach_whenThrowException() {
        Map<String, String> source = new HashMap<>();
        source.put("key-one", "one");
        source.put("key-two", "two");
        source.put("key-three", "three");

        FunctionalMap.ofMap(source).forEach(this::handle);
    }

    private void handle(String key, String value) throws Exception {
        throw new Exception();
    }
}