/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 */
package dev.gether.getclan.utils;

public class SystemPoint {
    public static int roundUpToMinutes(long milliseconds) {
        double minutes = (double)milliseconds / 60000.0;
        return (int)Math.ceil((double)minutes);
    }

    public static int calculateEloRating(int oldRating, int opponentRating, double score) {
        int K = 30;
        double expectedScore = SystemPoint.calculateExpectedScore(oldRating, opponentRating);
        return oldRating + (int)((double)K * (score - expectedScore));
    }

    private static double calculateExpectedScore(int playerRating, int opponentRating) {
        return 1.0 / (1.0 + Math.pow((double)10.0, (double)((double)(opponentRating - playerRating) / 400.0)));
    }
}

