/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Set
 *  org.bukkit.inventory.ItemStack
 */
package dev.gether.getconfig.domain.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.gether.getconfig.domain.Item;
import java.util.Set;
import org.bukkit.inventory.ItemStack;

public class ItemDecoration {
    private Item item;
    private Set<Integer> slots;

    @JsonIgnore
    public ItemStack getItemStack() {
        return this.item.getItemStack();
    }

    public static ItemDecorationBuilder builder() {
        return new ItemDecorationBuilder();
    }

    public ItemDecoration() {
    }

    public ItemDecoration(Item item, Set<Integer> slots) {
        this.item = item;
        this.slots = slots;
    }

    public Item getItem() {
        return this.item;
    }

    public Set<Integer> getSlots() {
        return this.slots;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setSlots(Set<Integer> slots) {
        this.slots = slots;
    }

    public static class ItemDecorationBuilder {
        private Item item;
        private Set<Integer> slots;

        ItemDecorationBuilder() {
        }

        public ItemDecorationBuilder item(Item item) {
            this.item = item;
            return this;
        }

        public ItemDecorationBuilder slots(Set<Integer> slots) {
            this.slots = slots;
            return this;
        }

        public ItemDecoration build() {
            return new ItemDecoration(this.item, this.slots);
        }

        public String toString() {
            return "ItemDecoration.ItemDecorationBuilder(item=" + this.item + ", slots=" + this.slots + ")";
        }
    }
}

