/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.util.function.Consumer
 */
package dev.gether.getconfig;

import dev.gether.getconfig.GetConfig;
import java.util.function.Consumer;

public class ConfigManager {
    public static <T extends GetConfig> T create(Class<T> clazz, Consumer<T> configConsumer) {
        try {
            T configInstance = clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            configConsumer.accept(configInstance);
            return configInstance;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
