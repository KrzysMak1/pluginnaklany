/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package dev.gether.getconfig.domain.config.particles;

public class DustOptions {
    private int red;
    private int green;
    private int blue;
    private int size;

    public DustOptions() {
    }

    public DustOptions(int red, int green, int blue, int size) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.size = size;
    }

    public int getRed() {
        return this.red;
    }

    public int getGreen() {
        return this.green;
    }

    public int getBlue() {
        return this.blue;
    }

    public int getSize() {
        return this.size;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

