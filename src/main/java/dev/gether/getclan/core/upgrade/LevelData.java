/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  lombok.Generated
 */
package dev.gether.getclan.core.upgrade;

import lombok.Generated;

public class LevelData {
    private int level;
    private double depositAmount;

    public void deposit(double amount) {
        this.depositAmount += amount;
    }

    public void nextLevel() {
        ++this.level;
        this.depositAmount = 0.0;
    }

    public void reset() {
        this.level = 0;
        this.depositAmount = 0.0;
    }

    @Generated
    public static LevelDataBuilder builder() {
        return new LevelDataBuilder();
    }

    @Generated
    public int getLevel() {
        return this.level;
    }

    @Generated
    public double getDepositAmount() {
        return this.depositAmount;
    }

    @Generated
    public void setLevel(int level) {
        this.level = level;
    }

    @Generated
    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    @Generated
    public LevelData(int level, double depositAmount) {
        this.level = level;
        this.depositAmount = depositAmount;
    }

    @Generated
    public LevelData() {
    }

    @Generated
    public static class LevelDataBuilder {
        @Generated
        private int level;
        @Generated
        private double depositAmount;

        @Generated
        LevelDataBuilder() {
        }

        @Generated
        public LevelDataBuilder level(int level) {
            this.level = level;
            return this;
        }

        @Generated
        public LevelDataBuilder depositAmount(double depositAmount) {
            this.depositAmount = depositAmount;
            return this;
        }

        @Generated
        public LevelData build() {
            return new LevelData(this.level, this.depositAmount);
        }

        @Generated
        public String toString() {
            return "LevelData.LevelDataBuilder(level=" + this.level + ", depositAmount=" + this.depositAmount + ")";
        }
    }
}

