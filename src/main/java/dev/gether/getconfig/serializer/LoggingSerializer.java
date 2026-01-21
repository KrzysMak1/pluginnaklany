/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.System
 */
package dev.gether.getconfig.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class LoggingSerializer
extends JsonSerializer<Object> {
    private final JsonSerializer<Object> defaultSerializer;

    public LoggingSerializer(JsonSerializer<Object> defaultSerializer) {
        this.defaultSerializer = defaultSerializer;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        System.out.println("Serializing class: " + value.getClass().getName());
        this.defaultSerializer.serialize(value, gen, serializers);
    }
}

