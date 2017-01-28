package my.utils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sequence<T> implements Iterable<T> {
    private List<T> elements;

    public Sequence() {
        elements = new ArrayList<T>();
    }

    public static <T> Sequence<T> of(T... array) {
        return ofArray(array);
    }

    public static <T> Sequence<T> of(Collection<T> collection) {
        return ofCollection(collection);
    }

    public static <T> Sequence<T> ofCollection(Collection<T> collection) {
        Sequence<T> sequence = new Sequence<T>();
        sequence.elements = collection != null ? new ArrayList<T>(collection) : new ArrayList<T>();

        return sequence;
    }

    public static <T> Sequence<T> ofArray(T... array) {
        Collection<T> collection = array != null ? Arrays.asList(array) : null;

        return ofCollection(collection);
    }

    public static Sequence<Integer> ofArray(int... array) {
        List<Integer> elements = new ArrayList<>();
        for (int value : array) {
            elements.add(value);
        }

        return ofCollection(elements);
    }

    public static Sequence<Boolean> ofArray(boolean... array) {
        List<Boolean> elements = new ArrayList<>();
        for (boolean value : array) {
            elements.add(value);
        }

        return ofCollection(elements);
    }

    public Sequence<T> add(T element) {
        Sequence<T> result = copy();
        result.elements.add(element);

        return result;
    }

    public Sequence<T> remove(T element) {
        Sequence<T> result = copy();
        result.elements.remove(element);

        return result;
    }

    public Sequence<T> remove(int index) {
        Sequence<T> result = copy();
        result.elements.remove(index);

        return result;
    }

    public <NewType> Sequence<NewType> cast(Class<NewType> targetClass) {
        Sequence<NewType> sequence = new Sequence<NewType>();
        sequence.elements = CollectionUtils.cast(this.elements, targetClass);

        return sequence;
    }

    public Sequence<T> unique() {
        Sequence<T> result = copy();
        result.elements = CollectionUtils.unique(result.elements);

        return result;
    }

    public Sequence<T> merge(Collection<T> collection) {
        Sequence<T> result = copy();
        result.elements.addAll(collection);

        return result;
    }

    public Sequence<T> merge(T[] array) {
        Sequence<T> result = copy();
        result.elements.addAll(Arrays.asList(array));

        return result;
    }

    public Sequence<T> difference(Collection<T> collection) {
        Sequence<T> result = copy();
        result.elements = CollectionUtils.difference(result.elements, collection);

        return result;
    }

    public Sequence<T> difference(T[] array) {
        Sequence<T> result = copy();
        result.elements = CollectionUtils.difference(result.elements, Arrays.asList(array));

        return result;
    }

    public <MappedType> Sequence<MappedType> map(ThrowableFunction<? super T, ? extends MappedType> mapper) {
        List<MappedType> result = stream().map(mapper.toFunction()).collect(Collectors.toList());
        return ofCollection(result);
    }

    public T single() {
        return CollectionUtils.getSingle(elements);
    }

    public T singleOrDefault(T defaultValue) {
        return CollectionUtils.getSingleOrDefault(elements, defaultValue);
    }

    public T first() {
        return elements.get(0);
    }

    public T first(Predicate<? super T> condition) {
        T result = firstOrDefault(condition, null);
        if (result == null) {
            throw new RuntimeException("No elements match condition");
        }

        return result;
    }

    public T firstOrDefault(T defaultValue) {
        if (elements.isEmpty()) {
            return defaultValue;
        }

        return first();
    }

    public T firstOrDefault(Predicate<? super T> condition, T defaultValue) {
        for (T element : elements) {
            boolean matchCondition = condition.test(element);
            if (matchCondition) {
                return element;
            }
        }

        return defaultValue;
    }

    public T last() {
        int lastIndex = size() - 1;
        return elements.get(lastIndex);
    }

    public T lastOrDefault(T defaultValue) {
        if (elements.isEmpty()) {
            return defaultValue;
        }

        return last();
    }

    public T max() {
        return max(value -> (Comparable) value);
    }

    public T max(ThrowableFunction<T, ? extends Comparable> mapper) {
        return getValue(mapper, compareResult -> compareResult > 0);
    }

    public T min() {
        return min(value -> (Comparable) value);
    }

    public T min(ThrowableFunction<T, ? extends Comparable> mapper) {
        return getValue(mapper, compareResult -> compareResult < 0);
    }

    public T get(int index) {
        return elements.get(index);
    }

    public Sequence<T> sortBy(ThrowableFunction<T, ? extends Comparable> mapper, SortOrder sortOrder) {
        Function<T, ? extends Comparable> function = mapper.toFunction();
        Comparator<T> comparator = (object, other) -> {
            Comparable comparable = function.apply(object);
            Comparable otherComparable = function.apply(other);

            switch (sortOrder) {
                case ASC:
                    return comparable.compareTo(otherComparable);
                case DESC:
                    return otherComparable.compareTo(comparable);
                default:
                    return 0;
            }
        };

        return sort(comparator);
    }

    public Sequence<T> sortBy(ThrowableFunction<T, ? extends Comparable> mapper) {
        return sortBy(mapper, SortOrder.ASC);
    }

    public Sequence<T> sort(Comparator<T> comparator) {
        Sequence<T> result = copy();
        result.elements.sort(comparator);

        return result;
    }

    public Sequence<T> sort() {
        return sortBy(value -> (Comparable) value);
    }

    public Sequence<T> sort(SortOrder sortOrder) {
        return sortBy(value -> (Comparable) value, sortOrder);
    }

    public Sequence<T> removeNullValues() {
        return filterNot(Objects::isNull);
    }

    public boolean contains(T value) {
        return elements.contains(value);
    }

    public Sequence<T> filter(Predicate<? super T> filter) {
        return filterNot(filter.negate());
    }

    public Sequence<T> filterNot(Predicate<? super T> filter) {
        Sequence<T> result = copy();
        result.elements.removeIf(filter);

        return result;
    }

    public Sequence<T> remove(T... values) {
        return remove(Sequence.ofArray(values));
    }

    public Sequence<T> remove(Collection<T> filter) {
        Sequence<T> result = copy();
        result.elements.removeIf(filter::contains);

        return result;
    }

    public Sequence<T> remove(Sequence<T> filter) {
        Sequence<T> result = copy();
        result.elements.removeIf(filter::contains);

        return result;
    }

    public Sequence<T> take(int size) {
        return take(0, size);
    }

    public Sequence<T> take(int fromIndex, int size) {
        int toIndex = Math.min(fromIndex + size, elements.size());

        Sequence<T> result = ofCollection(elements.subList(fromIndex, toIndex));
        elements.removeAll(result.all());

        return result;
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public int size() {
        return elements.size();
    }

    public List<T> all() {
        return toList();
    }

    public T[] toArray(Class<T> targetClass) {
        return CollectionUtils.toArray(elements, targetClass);
    }

    public List<T> toList() {
        return new ArrayList<T>(elements);
    }

    public Stream<T> stream() {
        return elements.stream();
    }

    public Sequence<T> copy() {
        return Sequence.of(elements);
    }

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }

    private T getValue(ThrowableFunction<T, ? extends Comparable> mapper, Function<Integer, Boolean> comparison) {
        if (isEmpty()) {
            return null;
        }

        T result = first();

        Function<T, ? extends Comparable> function = mapper.toFunction();
        Comparable maxValueComparable = function.apply(result);

        for (T value : elements) {
            Comparable comparable = function.apply(value);
            int compareResult = comparable.compareTo(maxValueComparable);

            boolean update = comparison.apply(compareResult);
            if (update) {
                result = value;
                maxValueComparable = comparable;
            }
        }

        return result;
    }
}
