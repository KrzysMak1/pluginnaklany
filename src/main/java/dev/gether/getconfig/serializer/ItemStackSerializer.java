/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.Override
 *  java.util.Map
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.inventory.ItemStack
 */
package dev.gether.getconfig.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import dev.gether.getconfig.snakeyaml.Yaml;
import java.io.IOException;
import java.util.Map;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class ItemStackSerializer
extends StdSerializer<ItemStack> {
    public ItemStackSerializer() {
        super(ItemStack.class);
    }

    @Override
    public void serialize(ItemStack itemStack, JsonGenerator gen, SerializerProvider provider) throws IOException {
        YamlConfiguration craftConfig = new YamlConfiguration();
        craftConfig.set("_", (Object)itemStack);
        Map root = (Map)new Yaml().load(craftConfig.saveToString());
        Map itemMap = (Map)root.get((Object)"_");
        itemMap.remove((Object)"==");
        gen.writeObject(itemMap);
    }
}

