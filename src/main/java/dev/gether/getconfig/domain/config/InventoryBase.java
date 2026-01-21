/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Set
 */
package dev.gether.getconfig.domain.config;

import dev.gether.getconfig.domain.config.ItemDecoration;
import java.util.Set;

public class InventoryBase {
    private int size;
    private String title;
    private Set<ItemDecoration> itemsDecoration;

    public static InventoryBaseBuilder builder() {
        return new InventoryBaseBuilder();
    }

    public int getSize() {
        return this.size;
    }

    public String getTitle() {
        return this.title;
    }

    public Set<ItemDecoration> getItemsDecoration() {
        return this.itemsDecoration;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setItemsDecoration(Set<ItemDecoration> itemsDecoration) {
        this.itemsDecoration = itemsDecoration;
    }

    public InventoryBase(int size, String title, Set<ItemDecoration> itemsDecoration) {
        this.size = size;
        this.title = title;
        this.itemsDecoration = itemsDecoration;
    }

    public InventoryBase() {
    }

    public static class InventoryBaseBuilder {
        private int size;
        private String title;
        private Set<ItemDecoration> itemsDecoration;

        InventoryBaseBuilder() {
        }

        public InventoryBaseBuilder size(int size) {
            this.size = size;
            return this;
        }

        public InventoryBaseBuilder title(String title) {
            this.title = title;
            return this;
        }

        public InventoryBaseBuilder itemsDecoration(Set<ItemDecoration> itemsDecoration) {
            this.itemsDecoration = itemsDecoration;
            return this;
        }

        public InventoryBase build() {
            return new InventoryBase(this.size, this.title, this.itemsDecoration);
        }

        public String toString() {
            return "InventoryBase.InventoryBaseBuilder(size=" + this.size + ", title=" + this.title + ", itemsDecoration=" + this.itemsDecoration + ")";
        }
    }
}

