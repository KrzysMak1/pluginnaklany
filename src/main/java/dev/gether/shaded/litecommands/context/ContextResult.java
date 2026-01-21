package dev.gether.shaded.litecommands.context;

import java.util.Optional;
import java.util.function.Supplier;

public final class ContextResult<T> {
    private final Supplier<T> value;
    private final String error;

    private ContextResult(Supplier<T> value, String error) {
        this.value = value;
        this.error = error;
    }

    public static <T> ContextResult<T> ok(Supplier<T> value) {
        return new ContextResult<>(value, null);
    }

    public static <T> ContextResult<T> error(String error) {
        return new ContextResult<>(null, error);
    }

    public Optional<T> value() {
        return this.value == null ? Optional.empty() : Optional.ofNullable(this.value.get());
    }

    public Optional<String> error() {
        return Optional.ofNullable(this.error);
    }
}
