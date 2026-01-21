/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.HashMap
 *  java.util.Map
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package dev.gether.getconfig.serializer;

import dev.gether.getconfig.domain.Weapon;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WeaponSerializer
extends StdSerializer<Weapon> {
    public WeaponSerializer() {
        super(Weapon.class);
    }

    @Override
    public void serialize(Weapon weapon, JsonGenerator gen, SerializerProvider provider) throws IOException {
        HashMap itemMap = new HashMap();
        itemMap.put((Object)"name", (Object)weapon.getName());
        itemMap.put((Object)"weapon-type", (Object)weapon.getWeaponType().name());
        itemMap.put((Object)"sound", (Object)weapon.getSound().name());
        itemMap.put((Object)"sound-pitch", (Object)weapon.getSoundPitch());
        itemMap.put((Object)"sound-volume", (Object)weapon.getSoundVolume());
        itemMap.put((Object)"scope", (Object)weapon.isScope());
        itemMap.put((Object)"attack-cooldown", (Object)weapon.getAttackCooldown());
        itemMap.put((Object)"damage", (Object)weapon.getDamage());
        itemMap.put((Object)"amount-bullet", (Object)weapon.getAmountBullet());
        itemMap.put((Object)"bullet-magazine", (Object)weapon.getBulletMagazine());
        itemMap.put((Object)"reloading-cooldown", (Object)weapon.getReloadingCooldown());
        itemMap.put((Object)"ammo-item", this.serializeItemModelData(weapon.getAmmoItem().getItemStack()));
        itemMap.put((Object)"weapon-item", this.serializeItemModelData(weapon.getWeaponItem().getItemStack()));
        gen.writeObject(itemMap);
    }

    public Map<String, Object> serializeItemModelData(ItemStack itemStack) {
        HashMap itemMap = new HashMap();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMap.put((Object)"material", (Object)itemStack.getType().name().toUpperCase());
        itemMap.put((Object)"displayname", (Object)(itemMeta.hasDisplayName() ? itemMeta.getDisplayName() : itemStack.getType().name()));
        itemMap.put((Object)"lore", itemMeta.hasLore() ? new ArrayList((Collection)itemMeta.getLore()) : Collections.emptyList());
        itemMap.put((Object)"glow", (Object)itemMeta.hasItemFlag(ItemFlag.HIDE_ENCHANTS));
        itemMap.put((Object)"unbreakable", (Object)itemMeta.isUnbreakable());
        itemMap.put((Object)"model-data", (Object)itemMeta.getCustomModelData());
        return itemMap;
    }
}

