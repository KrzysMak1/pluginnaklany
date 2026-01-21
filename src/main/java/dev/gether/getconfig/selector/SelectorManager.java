/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.HashSet
 *  java.util.Optional
 *  java.util.Set
 *  java.util.UUID
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 */
package dev.gether.getconfig.selector;

import dev.gether.getconfig.selector.SelectorPlayer;
import dev.gether.getconfig.utils.MessageUtil;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class SelectorManager
implements Listener {
    private Set<SelectorPlayer> selectorPlayers = new HashSet<>();

    public void setFirstLocation(Player player, Location location) {
        Optional<SelectorPlayer> selectorPlayerByUUID = this.findSelectorPlayerByUUID(player.getUniqueId());
        if (selectorPlayerByUUID.isEmpty()) {
            this.selectorPlayers.add(SelectorPlayer.builder().player(player).firstLocation(location).build());
        } else {
            SelectorPlayer selectorPlayer = (SelectorPlayer)selectorPlayerByUUID.get();
            selectorPlayer.setFirstLocation(location);
        }
        MessageUtil.sendMessage(player, "&aSuccessfully selected first location!");
    }

    public void setSecondLocation(Player player, Location location) {
        Optional<SelectorPlayer> selectorPlayerByUUID = this.findSelectorPlayerByUUID(player.getUniqueId());
        if (selectorPlayerByUUID.isEmpty()) {
            this.selectorPlayers.add(SelectorPlayer.builder().player(player).secondLocation(location).build());
        } else {
            SelectorPlayer selectorPlayer = (SelectorPlayer)selectorPlayerByUUID.get();
            selectorPlayer.setSecondLocation(location);
        }
        MessageUtil.sendMessage(player, "&aSuccessfully selected second location!");
    }

    public Optional<SelectorPlayer> findSelectorPlayerByUUID(UUID uuid) {
        return this.selectorPlayers.stream().filter(selectorPlayer -> selectorPlayer.getPlayer().getUniqueId().equals(uuid)).findFirst();
    }

    public void cleanCache(Player player) {
        this.selectorPlayers.removeIf(selectorPlayer -> selectorPlayer.getPlayer().getUniqueId().equals(player.getUniqueId()));
    }
}
