package dev.gether.shaded.litecommands.permission;

import java.util.Collection;
import java.util.List;

public class MissingPermissions {
    private final List<String> permissions;

    public MissingPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public Collection<String> getPermissions() {
        return this.permissions;
    }
}
