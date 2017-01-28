package my.utils;

import java.util.*;
import java.util.function.Predicate;

public class FunctionalMap<Key, Value> implements Iterable<Value> {
    private Map<Key, Value> map;

    public FunctionalMap() {
        map = new HashMap<>();
    }

    public static <Key, Value> FunctionalMap<Key, Value> of(Map.Entry<Key, Value>... entries) {
        return FunctionalMap.of(Sequence.of(entries).all());
    }

    public static <Key, Value> FunctionalMap<Key, Value> of(Collection<Map.Entry<Key, Value>> entries) {
        HashMap<Key, Value> map = new HashMap<Key, Value>();
        for (Map.Entry<Key, Value> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }

        return FunctionalMap.ofMap(map);
    }

    public static <Key, Value> FunctionalMap<Key, Value> of(Map<? extends Key, ? extends Value> map) {
        return FunctionalMap.ofMap(map);
    }

    public static <Key, Value> FunctionalMap<Key, Value> ofMap(Map<? extends Key, ? extends Value> map) {
        FunctionalMap<Key, Value> instance = new FunctionalMap<>();
        instance.map = map != null ? new HashMap<>(map) : new HashMap<>();

        return instance;
    }

    public Value get(Key key) {
        return map.get(key);
    }

    public FunctionalMap<Key, Value> put(Key key, Value value) {
        FunctionalMap<Key, Value> result = copy();
        result.map.put(key, value);

        return result;
    }

    public FunctionalMap<Key, Value> merge(Map<Key, Value> map) {
        return merge(FunctionalMap.ofMap(map));
    }

    public FunctionalMap<Key, Value> merge(FunctionalMap<Key, Value> map) {
        FunctionalMap<Key, Value> result = copy();
        map.forEach(result.map::put);

        return result;
    }

    public FunctionalMap<Key, Value> remove(Key key) {
        FunctionalMap<Key, Value> result = copy();
        result.map.remove(key);

        return result;
    }

    public FunctionalMap<Key, Value> remove(Collection<Key> keysToRemove) {
        FunctionalMap<Key, Value> result = copy();
        for (Key key : keysToRemove) {
            result.map.remove(key);
        }

        return result;
    }

    public FunctionalMap<Key, Value> filter(Predicate<Value> condition) {
        List<Key> keysToRemove = new ArrayList<>();

        for (Map.Entry<Key, Value> entry : map.entrySet()) {
            Key key = entry.getKey();
            Value value = entry.getValue();

            boolean matchCondition = !condition.test(value);
            if (matchCondition) {
                keysToRemove.add(key);
            }
        }

        return remove(keysToRemove);
    }

    public FunctionalMap<Key, Value> filterNot(Predicate<Value> condition) {
        return filter(condition.negate());
    }

    public void forEach(ThrowableConsumer<? super Key, ? super Value> action) {
        map.forEach(action.toBiConsumer());
    }

    public Sequence<Value> values() {
        return Sequence.of(map.values());
    }

    public Set<Key> keys() {
        return map.keySet();
    }

    public Set<Map.Entry<Key, Value>> entrySet() {
        return map.entrySet();
    }

    public FunctionalMap<Key, Value> copy() {
        return FunctionalMap.ofMap(this.map);
    }

    public Map<Key, Value> all() {
        return copy().map;
    }

    @Override
    public Iterator<Value> iterator() {
        return map.values().iterator();
    }
}
