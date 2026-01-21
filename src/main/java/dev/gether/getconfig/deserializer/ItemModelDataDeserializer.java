/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package dev.gether.getconfig.deserializer;

import dev.gether.getconfig.domain.ItemModelData;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import dev.gether.getconfig.utils.ItemBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemModelDataDeserializer
extends JsonDeserializer<ItemModelData> {
    @Override
    public ItemModelData deserialize(JsonParser json, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = (JsonNode)json.getCodec().readTree(json);
        String material = node.get("material").asText();
        String displayname = node.get("displayname").asText();
        ArrayList lore = new ArrayList();
        node.get("lore").forEach(arg_0 -> ItemModelDataDeserializer.lambda$deserialize$0((List)lore, arg_0));
        boolean glow = node.get("glow").asBoolean();
        boolean unbreakable = node.get("unbreakable").asBoolean();
        Integer modelData = node.get("model-data").asInt();
        ItemStack itemStack = ItemBuilder.create(Material.valueOf((String)material), displayname, (List<String>)lore, glow, unbreakable, modelData);
        return ItemModelData.builder().itemStack(itemStack).build();
    }

    private static /* synthetic */ void lambda$deserialize$0(List lore, JsonNode loreNode) {
        lore.add((Object)loreNode.asText());
    }
}

