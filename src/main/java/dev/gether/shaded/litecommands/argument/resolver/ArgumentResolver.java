package dev.gether.shaded.litecommands.argument.resolver;

import dev.gether.shaded.litecommands.argument.Argument;
import dev.gether.shaded.litecommands.argument.parser.ParseResult;
import dev.gether.shaded.litecommands.invocation.Invocation;
import dev.gether.shaded.litecommands.suggestion.SuggestionContext;
import dev.gether.shaded.litecommands.suggestion.SuggestionResult;

public abstract class ArgumentResolver<S, T> implements ArgumentResolverBase<S, T> {
    protected abstract ParseResult<T> parse(Invocation<S> invocation, Argument<T> context, String argument);

    public SuggestionResult suggest(Invocation<S> invocation, Argument<T> argument, SuggestionContext context) {
        return SuggestionResult.empty();
    }
}
