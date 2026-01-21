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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.inventory.ItemStack;

public class ItemStackSerializer
extends StdSerializer<ItemStack> {
    public ItemStackSerializer() {
        super(ItemStack.class);
    }

    @Override
    public void serialize(ItemStack itemStack, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (itemStack == null) {
            gen.writeNull();
            return;
        }

        Map<String, Object> itemMap = new HashMap<>(itemStack.serialize());
        itemMap.remove("==");
        gen.writeObject(itemMap);
    }
}
