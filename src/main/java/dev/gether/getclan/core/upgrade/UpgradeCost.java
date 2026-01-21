/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  lombok.Generated
 *  org.bukkit.inventory.ItemStack
 */
package dev.gether.getclan.core.upgrade;

import dev.gether.getclan.core.CostType;
import dev.gether.getconfig.domain.Item;
import lombok.Generated;
import org.bukkit.inventory.ItemStack;

public class UpgradeCost {
    private Item item;
    private double cost;
    private ItemStack itemStack;
    private CostType costType;
    private double boostValue;

    @Generated
    public static UpgradeCostBuilder builder() {
        return new UpgradeCostBuilder();
    }

    @Generated
    public Item getItem() {
        return this.item;
    }

    @Generated
    public double getCost() {
        return this.cost;
    }

    @Generated
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    @Generated
    public CostType getCostType() {
        return this.costType;
    }

    @Generated
    public double getBoostValue() {
        return this.boostValue;
    }

    @Generated
    public void setItem(Item item) {
        this.item = item;
    }

    @Generated
    public void setCost(double cost) {
        this.cost = cost;
    }

    @Generated
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Generated
    public void setCostType(CostType costType) {
        this.costType = costType;
    }

    @Generated
    public void setBoostValue(double boostValue) {
        this.boostValue = boostValue;
    }

    @Generated
    public UpgradeCost() {
    }

    @Generated
    public UpgradeCost(Item item, double cost, ItemStack itemStack, CostType costType, double boostValue) {
        this.item = item;
        this.cost = cost;
        this.itemStack = itemStack;
        this.costType = costType;
        this.boostValue = boostValue;
    }

    @Generated
    public static class UpgradeCostBuilder {
        @Generated
        private Item item;
        @Generated
        private double cost;
        @Generated
        private ItemStack itemStack;
        @Generated
        private CostType costType;
        @Generated
        private double boostValue;

        @Generated
        UpgradeCostBuilder() {
        }

        @Generated
        public UpgradeCostBuilder item(Item item) {
            this.item = item;
            return this;
        }

        @Generated
        public UpgradeCostBuilder cost(double cost) {
            this.cost = cost;
            return this;
        }

        @Generated
        public UpgradeCostBuilder itemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
            return this;
        }

        @Generated
        public UpgradeCostBuilder costType(CostType costType) {
            this.costType = costType;
            return this;
        }

        @Generated
        public UpgradeCostBuilder boostValue(double boostValue) {
            this.boostValue = boostValue;
            return this;
        }

        @Generated
        public UpgradeCost build() {
            return new UpgradeCost(this.item, this.cost, this.itemStack, this.costType, this.boostValue);
        }

        @Generated
        public String toString() {
            return "UpgradeCost.UpgradeCostBuilder(item=" + this.item + ", cost=" + this.cost + ", itemStack=" + this.itemStack + ", costType=" + this.costType + ", boostValue=" + this.boostValue + ")";
        }
    }
}

