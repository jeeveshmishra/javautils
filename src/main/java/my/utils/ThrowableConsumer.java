package my.utils;

import java.util.Objects;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface ThrowableConsumer<T, U> {

    void accept(T t, U u) throws Exception;

    default BiConsumer<T, U> andThen(BiConsumer<? super T, ? super U> after) {
        Objects.requireNonNull(after);

        return (l, r) -> {
            try {
                accept(l, r);
                after.accept(l, r);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        };
    }

    default BiConsumer<T, U> toBiConsumer() {
        return (key, value) -> {
            try {
                accept(key, value);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        };
    }
}
