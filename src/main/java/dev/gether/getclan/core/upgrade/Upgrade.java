/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 *  lombok.Generated
 */
package dev.gether.getclan.core.upgrade;

import dev.gether.getclan.core.upgrade.UpgradeCost;
import dev.gether.getclan.core.upgrade.UpgradeType;
import java.util.Map;
import lombok.Generated;

public class Upgrade {
    private boolean enabled;
    private int slot;
    private UpgradeType upgradeType;
    private Map<Integer, UpgradeCost> upgradesCost;

    @Generated
    public static UpgradeBuilder builder() {
        return new UpgradeBuilder();
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public int getSlot() {
        return this.slot;
    }

    @Generated
    public UpgradeType getUpgradeType() {
        return this.upgradeType;
    }

    @Generated
    public Map<Integer, UpgradeCost> getUpgradesCost() {
        return this.upgradesCost;
    }

    @Generated
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Generated
    public void setSlot(int slot) {
        this.slot = slot;
    }

    @Generated
    public void setUpgradeType(UpgradeType upgradeType) {
        this.upgradeType = upgradeType;
    }

    @Generated
    public void setUpgradesCost(Map<Integer, UpgradeCost> upgradesCost) {
        this.upgradesCost = upgradesCost;
    }

    @Generated
    public Upgrade() {
    }

    @Generated
    public Upgrade(boolean enabled, int slot, UpgradeType upgradeType, Map<Integer, UpgradeCost> upgradesCost) {
        this.enabled = enabled;
        this.slot = slot;
        this.upgradeType = upgradeType;
        this.upgradesCost = upgradesCost;
    }

    @Generated
    public static class UpgradeBuilder {
        @Generated
        private boolean enabled;
        @Generated
        private int slot;
        @Generated
        private UpgradeType upgradeType;
        @Generated
        private Map<Integer, UpgradeCost> upgradesCost;

        @Generated
        UpgradeBuilder() {
        }

        @Generated
        public UpgradeBuilder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        @Generated
        public UpgradeBuilder slot(int slot) {
            this.slot = slot;
            return this;
        }

        @Generated
        public UpgradeBuilder upgradeType(UpgradeType upgradeType) {
            this.upgradeType = upgradeType;
            return this;
        }

        @Generated
        public UpgradeBuilder upgradesCost(Map<Integer, UpgradeCost> upgradesCost) {
            this.upgradesCost = upgradesCost;
            return this;
        }

        @Generated
        public Upgrade build() {
            return new Upgrade(this.enabled, this.slot, this.upgradeType, this.upgradesCost);
        }

        @Generated
        public String toString() {
            return "Upgrade.UpgradeBuilder(enabled=" + this.enabled + ", slot=" + this.slot + ", upgradeType=" + this.upgradeType + ", upgradesCost=" + this.upgradesCost + ")";
        }
    }
}

