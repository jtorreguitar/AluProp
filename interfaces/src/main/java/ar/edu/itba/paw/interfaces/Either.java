package ar.edu.itba.paw.interfaces;

import java.util.NoSuchElementException;

public class Either<V, A> {

    private static final String ALTERNATIVE_NOT_FOUND = "the alternative does not exist.";
    private static final String VALUE_NOT_FOUND = "the value does not exist.";
    private V value;
    private A alternative;

    private Either(V value, A alternative) {
        this.value = value;
        this.alternative = alternative;
    }

    public static <V, A> Either<V, A> valueFrom(V value) {
        return new Either<V, A>(value, null);
    }

    public static <V, A> Either<V, A> alternativeFrom(A alternative) {
        return new Either<V, A>(null, alternative);
    }

    public boolean hasValue() {
        return this.value != null;
    }

    public V value() {
        if (this.value != null)
            return this.value;
        throw new NoSuchElementException(VALUE_NOT_FOUND);
    }

    public A alternative() {
        if (this.alternative != null)
            return this.alternative;
        throw new NoSuchElementException(ALTERNATIVE_NOT_FOUND);
    }
}
