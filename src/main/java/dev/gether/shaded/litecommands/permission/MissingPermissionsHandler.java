package dev.gether.shaded.litecommands.permission;

import dev.gether.shaded.litecommands.handler.result.ResultHandlerChain;
import dev.gether.shaded.litecommands.invocation.Invocation;

public interface MissingPermissionsHandler<S> {
    void handle(Invocation<S> invocation, MissingPermissions missingPermissions, ResultHandlerChain<S> resultHandlerChain);
}
