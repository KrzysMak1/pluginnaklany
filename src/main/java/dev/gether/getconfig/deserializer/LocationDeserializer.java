/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.Override
 *  java.util.Map
 *  org.bukkit.Location
 */
package dev.gether.getconfig.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.Map;
import org.bukkit.Location;

public class LocationDeserializer
extends JsonDeserializer<Location> {
    @Override
    public Location deserialize(JsonParser json, DeserializationContext ctxt) throws IOException {
        Map itemMap = json.readValueAs(Map.class);
        return Location.deserialize((Map)itemMap);
    }
}

