package dev.gether.shaded.litecommands.invocation;

public final class Invocation<S> {
    private final S sender;

    public Invocation(S sender) {
        this.sender = sender;
    }

    public S sender() {
        return this.sender;
    }
}
