/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.LinkedHashMap
 *  java.util.Map
 *  org.bukkit.configuration.InvalidConfigurationException
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.inventory.ItemStack
 */
package dev.gether.getconfig.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class ItemStackDeserializer
extends JsonDeserializer<ItemStack> {
    @Override
    public ItemStack deserialize(JsonParser json, DeserializationContext ctxt) throws IOException {
        JsonNode node = (JsonNode)json.getCodec().readTree(json);
        LinkedHashMap itemMap = new LinkedHashMap();
        itemMap.put((Object)"==", (Object)"org.bukkit.inventory.ItemStack");
        itemMap.putAll(this.jsonNodeToMap(node));
        YamlConfiguration craftConfig = new YamlConfiguration();
        craftConfig.set("_", (Object)itemMap);
        try {
            craftConfig.loadFromString(craftConfig.saveToString());
        }
        catch (InvalidConfigurationException e) {
            throw new RuntimeException((Throwable)e);
        }
        return craftConfig.getItemStack("_");
    }

    public Map<String, Object> jsonNodeToMap(JsonNode jsonNode) {
        ObjectMapper mapper = new ObjectMapper();
        Map result = mapper.convertValue((Object)jsonNode, Map.class);
        return result;
    }
}

