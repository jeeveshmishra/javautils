package my.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class CollectionUtilsTest {
    private static final Logger LOG = LoggerFactory.getLogger(CollectionUtilsTest.class);

    @Test
    public void test_difference() throws Exception {
        List<String> one = new ArrayList<>();
        one.add("one");
        one.add("two");
        one.add("three");

        List<String> other = new ArrayList<>();
        other.add("one");
        other.add("two");

        List<String> result = CollectionUtils.difference(one, other);
        assertEquals(result.size(),1);
        assertEquals(result.get(0),"three");
    }

    @Test
    public void test_toArray() throws Exception {
        List<String> toBeConverted = new ArrayList<>();
        toBeConverted.add("one");
        toBeConverted.add("two");
        toBeConverted.add("three");

        String[] converted = CollectionUtils.toArray(toBeConverted, String.class);
        assertEquals(converted.length, toBeConverted.size());
        assertEquals(toBeConverted.get(0), converted[0]);
        assertEquals(toBeConverted.get(1), converted[1]);
        assertEquals(toBeConverted.get(2), converted[2]);
    }

    @Test
    public void test_eliminateDuplicates() throws Exception {
        List<String> listToBeChecked = new ArrayList<>();
        listToBeChecked.add("one");
        listToBeChecked.add("two");
        listToBeChecked.add("three");
        listToBeChecked.add("four");
        listToBeChecked.add("two");
        listToBeChecked.add("three");

        List<String> originals = CollectionUtils.unique(listToBeChecked);
        assertEquals(originals.size(), 4);
        assertEquals(originals.get(0), "one");
        assertEquals(originals.get(1), "two");
        assertEquals(originals.get(2), "three");
        assertEquals(originals.get(3), "four");
    }

    @Test
    public void test_isNullOrEmpty_whenListIsNull() throws Exception {
        List<String> collection = null;

        boolean isNullOrEmpty = CollectionUtils.isNullOrEmpty(collection);

        assertTrue(isNullOrEmpty);
    }

    @Test
    public void test_isNullOrEmpty_whenListIsEmpty() throws Exception {
        List<String> collection = new ArrayList<>();

        boolean isNullOrEmpty = CollectionUtils.isNullOrEmpty(collection);

        assertTrue(isNullOrEmpty);
    }

    @Test
    public void test_isNullOrEmpty_whenListIsNotNullOrEmpty() throws Exception {
        List<String> collection = new ArrayList<>();
        collection.add("one");
        collection.add("two");
        collection.add("three");

        boolean isNullOrEmpty = CollectionUtils.isNullOrEmpty(collection);

        assertFalse(isNullOrEmpty);
    }

    @Test
    public void test_getSingle_whenSingleElement_willReturnElement() throws Exception {
        String expectedResult = "one";

        List<String> collection = new ArrayList<>();
        collection.add(expectedResult);

        String result = CollectionUtils.getSingle(collection);

        assertEquals(result, expectedResult);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void test_getSingle_whenListIsNull_willThrowException() throws Exception {
        List<String> collection = null;
        CollectionUtils.getSingle(collection);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void test_getSingle_whenListContainsMultipleElements_willThrowException() throws Exception {
        List<String> collection = new ArrayList<>();
        collection.add("one");
        collection.add("two");
        collection.add("three");

        CollectionUtils.getSingle(collection);
    }

    @Test
    public void test_getSingleOrDefault_whenListIsNull_willThrowException() throws Exception {
        List<String> collection = null;
        String result = CollectionUtils.getSingleOrDefault(collection, null);

        assertNull(result);
    }

    @Test
    public void test_getSingleOrDefault_whenListContainsMultipleElements_willThrowException() throws Exception {
        List<String> collection = new ArrayList<>();
        collection.add("one");
        collection.add("two");
        collection.add("three");

        String result = CollectionUtils.getSingleOrDefault(collection, null);

        assertEquals(result, null);
    }
}