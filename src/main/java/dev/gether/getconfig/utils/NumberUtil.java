/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Random
 */
package dev.gether.getconfig.utils;

import java.util.Random;

public class NumberUtil {
    public static String getRandomNumber(int bound) {
        Random rnd = new Random();
        int number = rnd.nextInt(bound);
        return String.format((String)"%06d", (Object[])new Object[]{number});
    }
}

