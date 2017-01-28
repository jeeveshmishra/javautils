package my.utils;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.testng.Assert.*;

public class SequenceTest {

    @Test
    public void test_sequence_ofArray() throws Exception {
        String[] source = new String[] { "one", "two", "three" };

        Sequence<String> sequence = Sequence.of(source);
        List<String> result = sequence.all();

        assertEquals(result.size(), 3);
        assertEquals(result.get(0), source[0]);
        assertEquals(result.get(1), source[1]);
        assertEquals(result.get(2), source[2]);
    }

    @Test
    public void test_sequence_ofList() throws Exception {
        List<String> source = new ArrayList<>();
        source.add("one");
        source.add("two");
        source.add("three");

        Sequence<String> sequence = Sequence.of(source);
        List<String> result = sequence.all();

        assertEquals(result.size(), 3);
        assertEquals(result.get(0), source.get(0));
        assertEquals(result.get(1), source.get(1));
        assertEquals(result.get(2), source.get(2));
    }

    @Test
    public void test_sequence_ofArray_whenSourceIsNull() throws Exception {
        String[] source = null;
        Sequence<String> sequence = Sequence.of(source);
        List<String> result = sequence.all();

        assertEquals(result.size(), 0);
    }

    @Test
    public void test_sequence_ofList_whenSourceIsNull() throws Exception {
        List<String> source = null;
        Sequence<String> sequence = Sequence.of(source);
        List<String> result = sequence.all();

        assertEquals(result.size(), 0);
    }

    @Test
    public void test_sequence_ofValues() throws Exception {
        Sequence<String> sequence = Sequence.of("one", "two", "three");
        List<String> result = sequence.all();

        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "one");
        assertEquals(result.get(1), "two");
        assertEquals(result.get(2), "three");
    }

    @Test
    public void test_sequence_add() throws Exception {
        Sequence<String> sequence = Sequence.of("one", "two");
        List<String> result = sequence.add("three").all();

        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "one");
        assertEquals(result.get(1), "two");
        assertEquals(result.get(2), "three");
    }

    @Test
    public void test_sequence_remove() throws Exception {
        Sequence<String> sequence = Sequence.of("one", "two", "three");
        List<String> result = sequence.remove("three").all();

        assertEquals(result.size(), 2);
        assertEquals(result.get(0), "one");
        assertEquals(result.get(1), "two");
    }

    @Test
    public void test_sequence_removeByArray() throws Exception {
        Sequence<String> sequence = Sequence.of("one", "two", "three");
        List<String> result = sequence.remove("one", "three").all();

        assertEquals(result.size(), 1);
        assertEquals(result.get(0), "two");
    }

    @Test
    public void test_sequence_removeByEmptyArray() throws Exception {
        Sequence<String> sequence = Sequence.of("one", "two", "three");
        List<String> result = sequence.remove().all();

        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "one");
        assertEquals(result.get(1), "two");
        assertEquals(result.get(2), "three");
    }

    @Test
    public void test_sequence_removeByIndex() throws Exception {
        Sequence<String> sequence = Sequence.of("one", "two", "three");
        List<String> result = sequence.remove(2).all();

        assertEquals(result.size(), 2);
        assertEquals(result.get(0), "one");
        assertEquals(result.get(1), "two");
    }

    @Test
    public void test_sequence_single() throws Exception {
        String[] source = new String[] { "one" };
        String result = Sequence.ofArray(source).single();

        assertEquals(result, source[0]);
    }

    @Test
    public void test_sequence_merge_array() throws Exception {
        String[] source = new String[] { "one" };
        String[] other = new String[] { "two", "three" };

        List<String> result = Sequence.ofArray(source).merge(other).all();

        assertEquals(result.size(), 3);
        assertEquals(result.get(0), source[0]);
        assertEquals(result.get(1), other[0]);
        assertEquals(result.get(2), other[1]);
    }

    @Test
    public void test_sequence_merge_collection() throws Exception {
        String[] source = new String[] { "one" };
        List<String> other = new ArrayList<>();
        other.add("two");
        other.add("three");

        List<String> result = Sequence.ofArray(source)
            .merge(other)
            .all();

        assertEquals(result.size(), 3);
        assertEquals(result.get(0), source[0]);
        assertEquals(result.get(1), other.get(0));
        assertEquals(result.get(2), other.get(1));
    }

    @Test
    public void test_sequence_isEmpty_whenNullSource() throws Exception {
        List<String> source = null;
        Sequence<String> sequence = Sequence.of(source);

        assertTrue(sequence.isEmpty());
    }

    @Test
    public void test_sequence_isEmpty_whenSourceIsEmpty() throws Exception {
        List<String> source = new ArrayList<>();
        Sequence<String> sequence = Sequence.of(source);

        assertTrue(sequence.isEmpty());
    }

    @Test
    public void test_sequence_isEmpty_whenSourceIsNotEmpty() throws Exception {
        String[] source = new String[] { "one" };
        Sequence<String> sequence = Sequence.of(source);

        assertFalse(sequence.isEmpty());
    }

    @Test
    public void test_size() throws Exception {
        String[] source = new String[] { "one", "two" };
        Sequence<String> sequence = Sequence.of(source);

        assertEquals(sequence.size(), source.length);
    }

    @Test
    public void test_get() throws Exception {
        String[] source = new String[] { "one", "two", "three" };
        Sequence<String> sequence = Sequence.of(source);

        assertEquals(sequence.get(1), "two");
    }

    @Test
    public void test_map() throws Exception {
        TestObject[] source = new TestObject[] {
            new TestObject("one"),
            new TestObject("two"),
            new TestObject("three")
        };

        Sequence<String> sequence = Sequence.of(source).map(TestObject::getValue);

        assertEquals(sequence.get(0), "one");
        assertEquals(sequence.get(1), "two");
        assertEquals(sequence.get(2), "three");
    }

    @Test
    public void test_removeNullValues() {
        String[] source = new String[] { "one", null, "three" };

        List<String> result = Sequence.ofArray(source).removeNullValues().all();

        assertEquals(result.size(), 2);
        assertEquals(result.get(0), "one");
        assertEquals(result.get(1), "three");
    }

    @Test
    public void test_filter() {
        String[] source = new String[] { "one", "two", "three" };

        List<String> result = Sequence.ofArray(source)
            .filter(value -> Objects.equals(value, "three"))
            .all();

        assertEquals(result.size(), 1);
        assertEquals(result.get(0), "three");
    }

    @Test
    public void test_filterNot() {
        String[] source = new String[] { "one", "two", "three" };

        List<String> result = Sequence.ofArray(source)
            .filterNot(value -> Objects.equals(value, "three"))
            .all();

        assertEquals(result.size(), 2);
        assertEquals(result.get(0), "one");
        assertEquals(result.get(1), "two");
    }

    @Test
    public void test_remove() {
        String[] source = new String[] { "one", "two", "three" };

        List<String> result = Sequence.ofArray(source)
            .remove("two")
            .all();

        assertEquals(result.size(), 2);
        assertEquals(result.get(0), "one");
        assertEquals(result.get(1), "three");
    }

    @Test
    public void test_remove_whenSeveralValues() {
        String[] source = new String[] { "one", "two", "three", "four" };

        List<String> result = Sequence.ofArray(source)
            .remove("two", "four")
            .all();

        assertEquals(result.size(), 2);
        assertEquals(result.get(0), "one");
        assertEquals(result.get(1), "three");
    }

    @Test
    public void test_remove_whenFilterArrayOfValues() {
        String[] source = new String[] { "one", "two", "three", "four", "five" };
        String[] filter = new String[] { "two", "four", "five" };

        List<String> result = Sequence.ofArray(source)
            .remove(filter)
            .all();

        assertEquals(result.size(), 2);
        assertEquals(result.get(0), "one");
        assertEquals(result.get(1), "three");
    }

    @Test
    public void test_take() {
        String[] source = new String[] { "one", "two", "three", "four", "five" };

        Sequence<String> original = Sequence.ofArray(source);
        Sequence<String> result = original.take(0, 2);

        assertEquals(original.size(), 3);
        assertEquals(original.get(0), "three");
        assertEquals(original.get(1), "four");
        assertEquals(original.get(2), "five");

        assertEquals(result.size(), 2);
        assertEquals(result.get(0), "one");
        assertEquals(result.get(1), "two");
    }

    @Test
    public void test_take_whenSizeIsMoreThenSource() {
        String[] source = new String[] { "one", "two" };

        Sequence<String> original = Sequence.ofArray(source);
        Sequence<String> result = original.take(0, 10);

        assertTrue(original.isEmpty());

        assertEquals(result.size(), 2);
        assertEquals(result.get(0), "one");
        assertEquals(result.get(1), "two");
    }

    @Test
    public void test_take_fromWithSize() {
        String[] source = new String[] { "one", "two", "three" };

        Sequence<String> original = Sequence.ofArray(source);
        Sequence<String> result = original.take(1, 3);

        assertEquals(original.size(), 1);
        assertEquals(original.get(0), "one");

        assertEquals(result.size(), 2);
        assertEquals(result.get(0), "two");
        assertEquals(result.get(1), "three");
    }

    @Test
    public void test_last() {
        String[] source = new String[] { "one", "two", "three" };
        String result = Sequence.of(source).last();

        assertEquals(result, "three");
    }

    @Test
    public void test_lastOrDefault() {
        String[] source = new String[] { "one", "two", "three" };
        String result = Sequence.of(source).lastOrDefault(null);

        assertEquals(result, "three");
    }

    @Test
    public void test_lastOrDefault_whenSourceIsEmpty() {
        String[] source = new String[] { };
        String result = Sequence.of(source).lastOrDefault(null);

        assertEquals(result, null);
    }

    @Test
    public void test_max() {
        int[] source = new int[] { 1, 0, 3, 2 };
        int result = Sequence.ofArray(source).max();

        assertEquals(result, 3);
    }

    @Test
    public void test_min() {
        int[] source = new int[] { 4, 1, 3, 2 };
        int result = Sequence.ofArray(source).min();

        assertEquals(result, 1);
    }

    @Test
    public void test_max_ofInnerValue() {
        TestObject[] source = {
            new TestObject("1"),
            new TestObject("0"),
            new TestObject("3"),
            new TestObject("2")
        };

        TestObject result = Sequence.ofArray(source).max(TestObject::getValue);

        assertEquals(result.getValue(), "3");
    }

    @Test
    public void test_min_ofInnerValue() {
        TestObject[] source = {
            new TestObject("4"),
            new TestObject("1"),
            new TestObject("3"),
            new TestObject("2")
        };

        TestObject result = Sequence.ofArray(source).min(TestObject::getValue);

        assertEquals(result.getValue(), "1");
    }

    @Test
    public void test_sort() {
        int[] source = new int[] { 1, 4, 3, 2 };

        List<Integer> result = Sequence.ofArray(source)
            .sort()
            .all();

        assertEquals((int) result.get(0), 1);
        assertEquals((int) result.get(1), 2);
        assertEquals((int) result.get(2), 3);
        assertEquals((int) result.get(3), 4);
    }

    @Test
    public void test_sort_desc() {
        int[] source = new int[] { 1, 4, 3, 2 };

        List<Integer> result = Sequence.ofArray(source)
            .sort(SortOrder.DESC)
            .all();

        assertEquals((int) result.get(0), 4);
        assertEquals((int) result.get(1), 3);
        assertEquals((int) result.get(2), 2);
        assertEquals((int) result.get(3), 1);
    }

    @Test
    public void test_sortBy() {
        TestObject[] source = {
            new TestObject("4"),
            new TestObject("1"),
            new TestObject("3"),
            new TestObject("2")
        };

        List<TestObject> result = Sequence.ofArray(source)
            .sortBy(TestObject::getValue)
            .all();

        assertEquals(result.get(0).getValue(), "1");
        assertEquals(result.get(1).getValue(), "2");
        assertEquals(result.get(2).getValue(), "3");
        assertEquals(result.get(3).getValue(), "4");
    }

    @Test
    public void test_sortBy_desc() {
        TestObject[] source = {
            new TestObject("4"),
            new TestObject("1"),
            new TestObject("3"),
            new TestObject("2")
        };

        List<TestObject> result = Sequence.ofArray(source)
            .sortBy(TestObject::getValue, SortOrder.DESC)
            .all();

        assertEquals(result.get(0).getValue(), "4");
        assertEquals(result.get(1).getValue(), "3");
        assertEquals(result.get(2).getValue(), "2");
        assertEquals(result.get(3).getValue(), "1");
    }

    @Test
    public void test_first_byCondition() {
        String[] source = new String[] { "one", "two", "three", "four", "three", "two" };
        String result = Sequence.of(source).first(value -> value.equals("three"));

        assertEquals(result, "three");
    }

    @Test
    public void test_firstOrDefault_byCondition() {
        String[] source = new String[] { "one", "two", "four", "two" };
        String result = Sequence.of(source).firstOrDefault(value -> value.equals("three"), null);

        assertEquals(result, null);
    }

    private class TestObject {
        private String value;

        private TestObject(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}