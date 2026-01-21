package dev.gether.shaded.litecommands.suggestion;

import java.util.Collections;
import java.util.List;

public final class SuggestionResult {
    private final List<String> suggestions;

    private SuggestionResult(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public static SuggestionResult of(Iterable<String> suggestions) {
        return new SuggestionResult(suggestions instanceof List
            ? (List<String>) suggestions
            : toList(suggestions));
    }

    public static SuggestionResult empty() {
        return new SuggestionResult(Collections.emptyList());
    }

    public List<String> suggestions() {
        return this.suggestions;
    }

    private static List<String> toList(Iterable<String> suggestions) {
        java.util.ArrayList<String> list = new java.util.ArrayList<>();
        for (String suggestion : suggestions) {
            list.add(suggestion);
        }
        return list;
    }
}
