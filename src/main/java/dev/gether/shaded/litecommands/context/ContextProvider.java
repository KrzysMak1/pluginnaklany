package dev.gether.shaded.litecommands.context;

import dev.gether.shaded.litecommands.invocation.Invocation;

public interface ContextProvider<S, T> {
    ContextResult<T> provide(Invocation<S> invocation);
}
