package dev.gether.shaded.litecommands.schematic;

import java.util.Collections;
import java.util.List;

public class Schematic {
    private final List<String> usages;

    public Schematic(List<String> usages) {
        this.usages = usages;
    }

    public boolean isOnlyFirst() {
        return this.usages == null || this.usages.size() <= 1;
    }

    public String first() {
        if (this.usages == null || this.usages.isEmpty()) {
            return "";
        }
        return this.usages.get(0);
    }

    public List<String> usages() {
        return this.usages == null ? Collections.emptyList() : this.usages;
    }
}
