/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.Object
 *  java.lang.annotation.ElementType
 *  java.lang.annotation.Retention
 *  java.lang.annotation.RetentionPolicy
 *  java.lang.annotation.Target
 */
package org.jetbrains.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public final class Async {
    private Async() {
        throw new AssertionError((Object)"Async should not be instantiated");
    }

    @Retention(value=RetentionPolicy.CLASS)
    @Target(value={ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
    public static @interface Execute {
    }

    @Retention(value=RetentionPolicy.CLASS)
    @Target(value={ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
    public static @interface Schedule {
    }
}

