/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.Override
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.HashMap
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package dev.gether.getconfig.serializer;

import dev.gether.getconfig.domain.ItemModelData;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemModelDataSerializer
extends StdSerializer<ItemModelData> {
    public ItemModelDataSerializer() {
        super(ItemModelData.class);
    }

    @Override
    public void serialize(ItemModelData ItemModelData2, JsonGenerator gen, SerializerProvider provider) throws IOException {
        ItemStack itemStack = ItemModelData2.getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        HashMap itemMap = new HashMap();
        itemMap.put((Object)"material", (Object)itemStack.getType().name().toUpperCase());
        itemMap.put((Object)"displayname", (Object)(itemMeta.hasDisplayName() ? itemMeta.getDisplayName() : itemStack.getType().name()));
        itemMap.put((Object)"lore", itemMeta.hasLore() ? new ArrayList((Collection)itemMeta.getLore()) : Collections.emptyList());
        itemMap.put((Object)"glow", (Object)itemMeta.hasItemFlag(ItemFlag.HIDE_ENCHANTS));
        itemMap.put((Object)"unbreakable", (Object)itemMeta.isUnbreakable());
        itemMap.put((Object)"model-data", (Object)itemMeta.getCustomModelData());
        gen.writeObject(itemMap);
    }
}

