/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.Override
 *  java.util.HashMap
 *  java.util.Map
 *  org.bukkit.Color
 */
package dev.gether.getconfig.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Color;

public class ColorDeserializer
extends JsonDeserializer<Color> {
    @Override
    public Color deserialize(JsonParser json, DeserializationContext ctxt) throws IOException {
        JsonNode node = (JsonNode)json.getCodec().readTree(json);
        int alpha = node.get("ALPHA").asInt();
        int red = node.get("RED").asInt();
        int green = node.get("GREEN").asInt();
        int blue = node.get("BLUE").asInt();
        HashMap<String, Integer> color = new HashMap<>(Map.of("ALPHA", alpha, "RED", red, "GREEN", green, "BLUE", blue));
        return Color.deserialize((Map)color);
    }
}
