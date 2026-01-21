package dev.gether.shaded.litecommands.argument.parser;

import java.util.Optional;

public final class ParseResult<T> {
    private final T value;
    private final String errorMessage;

    private ParseResult(T value, String errorMessage) {
        this.value = value;
        this.errorMessage = errorMessage;
    }

    public static <T> ParseResult<T> success(T value) {
        return new ParseResult<>(value, null);
    }

    public static <T> ParseResult<T> failure(String errorMessage) {
        return new ParseResult<>(null, errorMessage);
    }

    public Optional<T> value() {
        return Optional.ofNullable(this.value);
    }

    public Optional<String> errorMessage() {
        return Optional.ofNullable(this.errorMessage);
    }
}
