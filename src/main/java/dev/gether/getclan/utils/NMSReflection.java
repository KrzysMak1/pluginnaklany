/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.ClassNotFoundException
 *  java.lang.Exception
 *  java.lang.NoSuchFieldException
 *  java.lang.NoSuchMethodException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.Field
 *  java.lang.reflect.Method
 *  org.bukkit.Bukkit
 */
package dev.gether.getclan.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;

public class NMSReflection {
    public static Class<?> getClass(String name) {
        try {
            return Class.forName((String)name);
        }
        catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static void setValue(Object packet, String fieldName, Object value) {
        try {
            Field field = packet.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(packet, value);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static Method getMethod(Class<?> clazz, String nameMethod) {
        try {
            return clazz.getMethod(nameMethod, new Class[0]);
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Field getField(Class<?> clazz, String nameField) {
        try {
            return clazz.getField(nameField);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public static Object getObject(Object object, Method getHandle) {
        try {
            return getHandle.invoke(object, new Object[0]);
        }
        catch (Exception e) {
            throw new RuntimeException((Throwable)e);
        }
    }

    public static Object getFieldValue(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }
}

