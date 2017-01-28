package my.utils;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface ThrowableFunction<T, R> {

    R apply(T t) throws Exception;

    default <V> Function<V, R> compose(Function<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> {
            try {
                return apply(before.apply(v));
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        };
    }

    default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> {
            try {
                return after.apply(apply(t));
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        };
    }

    static <T> Function<T, T> identity() {
        return t -> t;
    }

    default Function<T, R> toFunction() {
        return (T t) -> {
            try {
                return apply(t);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        };
    }
}
