/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.List
 *  org.bukkit.Material
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package dev.gether.getconfig.selector;

import dev.gether.getconfig.selector.SelectorListeners;
import dev.gether.getconfig.selector.SelectorManager;
import dev.gether.getconfig.utils.ItemBuilder;
import dev.gether.getconfig.utils.MessageUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SelectorAddon {
    private SelectorManager selectorManager;
    private SelectorListeners selectorListeners;
    private final ItemStack selectorItem = ItemBuilder.create(Material.STICK, "#bbff45 \u00d7 Selector", (List<String>)new ArrayList((Collection)List.of((Object)"&7", (Object)"#96ffa8 \u00bb #45ff65Left-click &7-#96ffa8 select first location", (Object)"#96ffa8 \u00bb #45ff65Right-click &7-#96ffa8 select second location", (Object)"")), true);

    public void enable(JavaPlugin plugin) {
        this.selectorManager = new SelectorManager();
        this.selectorListeners = new SelectorListeners(this);
        plugin.getServer().getPluginManager().registerEvents((Listener)this.selectorListeners, (Plugin)plugin);
        MessageUtil.logMessage("\u001b[0;32m", "Selector addon successfully initialized");
    }

    public void disable() {
        HandlerList.unregisterAll((Listener)this.selectorListeners);
        MessageUtil.logMessage("\u001b[0;31m", "Selector addon has been disabled");
    }

    public ItemStack getSelectorItem() {
        return this.selectorItem;
    }

    public SelectorManager getSelectorManager() {
        return this.selectorManager;
    }
}

