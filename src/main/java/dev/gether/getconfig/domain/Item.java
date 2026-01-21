/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package dev.gether.getconfig.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Item {
    private Material material;
    private String displayname;
    private List<String> lore = new ArrayList();
    private boolean unbreakable = false;
    private boolean glow = false;
    private int modelData;

    @JsonIgnore
    public ItemStack getItemStack() {
        return dev.gether.getconfig.utils.ItemBuilder.create(this.material, this.displayname, this.lore, this.glow, this.unbreakable, this.modelData);
    }

    public static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public Item() {
    }

    public Item(Material material, String displayname, List<String> lore, boolean unbreakable, boolean glow, int modelData) {
        this.material = material;
        this.displayname = displayname;
        this.lore = lore;
        this.unbreakable = unbreakable;
        this.glow = glow;
        this.modelData = modelData;
    }

    public Material getMaterial() {
        return this.material;
    }

    public String getDisplayname() {
        return this.displayname;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public boolean isUnbreakable() {
        return this.unbreakable;
    }

    public boolean isGlow() {
        return this.glow;
    }

    public int getModelData() {
        return this.modelData;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }

    public void setGlow(boolean glow) {
        this.glow = glow;
    }

    public void setModelData(int modelData) {
        this.modelData = modelData;
    }

    public static class ItemBuilder {
        private Material material;
        private String displayname;
        private List<String> lore;
        private boolean unbreakable;
        private boolean glow;
        private int modelData;

        ItemBuilder() {
        }

        public ItemBuilder material(Material material) {
            this.material = material;
            return this;
        }

        public ItemBuilder displayname(String displayname) {
            this.displayname = displayname;
            return this;
        }

        public ItemBuilder lore(List<String> lore) {
            this.lore = lore;
            return this;
        }

        public ItemBuilder unbreakable(boolean unbreakable) {
            this.unbreakable = unbreakable;
            return this;
        }

        public ItemBuilder glow(boolean glow) {
            this.glow = glow;
            return this;
        }

        public ItemBuilder modelData(int modelData) {
            this.modelData = modelData;
            return this;
        }

        public Item build() {
            return new Item(this.material, this.displayname, this.lore, this.unbreakable, this.glow, this.modelData);
        }

        public String toString() {
            return "Item.ItemBuilder(material=" + this.material + ", displayname=" + this.displayname + ", lore=" + this.lore + ", unbreakable=" + this.unbreakable + ", glow=" + this.glow + ", modelData=" + this.modelData + ")";
        }
    }
}

