package dev.gether.shaded.litecommands.bukkit;

import dev.gether.shaded.litecommands.LiteCommands;
import dev.gether.shaded.litecommands.argument.resolver.ArgumentResolverBase;
import dev.gether.shaded.litecommands.context.ContextProvider;
import dev.gether.shaded.litecommands.invalidusage.InvalidUsageHandler;
import dev.gether.shaded.litecommands.permission.MissingPermissionsHandler;
import org.bukkit.command.CommandSender;

public final class LiteBukkitFactory {
    private LiteBukkitFactory() {
    }

    public static Builder<CommandSender> builder(String command) {
        return new Builder<>();
    }

    public static final class Builder<S> {
        public Builder<S> commands(Object... commands) {
            return this;
        }

        public <T> Builder<S> context(Class<T> type, ContextProvider<S, T> provider) {
            return this;
        }

        public Builder<S> message(LiteBukkitMessages message, String value) {
            return this;
        }

        public <T> Builder<S> argument(Class<T> type, ArgumentResolverBase<S, T> resolver) {
            return this;
        }

        public Builder<S> missingPermission(MissingPermissionsHandler<S> handler) {
            return this;
        }

        public Builder<S> invalidUsage(InvalidUsageHandler<S> handler) {
            return this;
        }

        public LiteCommands<S> build() {
            return new LiteCommands<>();
        }
    }
}
