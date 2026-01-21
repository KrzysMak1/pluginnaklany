/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  org.bukkit.inventory.ItemStack
 */
package dev.gether.getconfig.domain.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.gether.getconfig.domain.Item;
import org.bukkit.inventory.ItemStack;

public class ItemInventory {
    private Item item;
    private int slot;

    @JsonIgnore
    public ItemStack getItemStack() {
        return this.item.getItemStack();
    }

    public static ItemInventoryBuilder builder() {
        return new ItemInventoryBuilder();
    }

    public ItemInventory() {
    }

    public ItemInventory(Item item, int slot) {
        this.item = item;
        this.slot = slot;
    }

    public Item getItem() {
        return this.item;
    }

    public int getSlot() {
        return this.slot;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public static class ItemInventoryBuilder {
        private Item item;
        private int slot;

        ItemInventoryBuilder() {
        }

        public ItemInventoryBuilder item(Item item) {
            this.item = item;
            return this;
        }

        public ItemInventoryBuilder slot(int slot) {
            this.slot = slot;
            return this;
        }

        public ItemInventory build() {
            return new ItemInventory(this.item, this.slot);
        }

        public String toString() {
            return "ItemInventory.ItemInventoryBuilder(item=" + this.item + ", slot=" + this.slot + ")";
        }
    }
}

