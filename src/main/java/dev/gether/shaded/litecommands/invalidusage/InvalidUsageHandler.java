package dev.gether.shaded.litecommands.invalidusage;

import dev.gether.shaded.litecommands.handler.result.ResultHandlerChain;
import dev.gether.shaded.litecommands.invocation.Invocation;

public interface InvalidUsageHandler<S> {
    void handle(Invocation<S> invocation, InvalidUsage<S> result, ResultHandlerChain<S> resultHandlerChain);
}
