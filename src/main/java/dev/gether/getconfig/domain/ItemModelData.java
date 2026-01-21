/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  org.bukkit.inventory.ItemStack
 */
package dev.gether.getconfig.domain;

import org.bukkit.inventory.ItemStack;

public class ItemModelData {
    private ItemStack itemStack;

    public static ItemModelDataBuilder builder() {
        return new ItemModelDataBuilder();
    }

    public ItemModelData() {
    }

    public ItemModelData(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static class ItemModelDataBuilder {
        private ItemStack itemStack;

        ItemModelDataBuilder() {
        }

        public ItemModelDataBuilder itemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
            return this;
        }

        public ItemModelData build() {
            return new ItemModelData(this.itemStack);
        }

        public String toString() {
            return "ItemModelData.ItemModelDataBuilder(itemStack=" + this.itemStack + ")";
        }
    }
}

