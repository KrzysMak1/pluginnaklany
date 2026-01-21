package dev.gether.shaded.litecommands.invalidusage;

import dev.gether.shaded.litecommands.schematic.Schematic;

public final class InvalidUsage<S> {
    private final Schematic schematic;

    public InvalidUsage(Schematic schematic) {
        this.schematic = schematic;
    }

    public Schematic getSchematic() {
        return this.schematic;
    }
}
