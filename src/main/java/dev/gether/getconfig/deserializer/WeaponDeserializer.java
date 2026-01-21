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
 *  org.bukkit.Sound
 *  org.bukkit.inventory.ItemStack
 */
package dev.gether.getconfig.deserializer;

import dev.gether.getconfig.domain.ItemModelData;
import dev.gether.getconfig.domain.Weapon;
import dev.gether.getconfig.domain.WeaponType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import dev.gether.getconfig.utils.ItemBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public class WeaponDeserializer
extends JsonDeserializer<Weapon> {
    @Override
    public Weapon deserialize(JsonParser json, DeserializationContext ctxt) throws IOException {
        JsonNode node = (JsonNode)json.getCodec().readTree(json);
        String name = node.get("name").asText();
        String weaponType = node.get("weapon-type").asText();
        String sound = node.get("sound").asText();
        double soundPitch = node.get("sound-pitch").asDouble();
        double soundVolume = node.get("sound-volume").asDouble();
        boolean scope = node.get("scope").asBoolean();
        double attackCooldown = node.get("attack-cooldown").asDouble();
        double damage = node.get("damage").asDouble();
        int amountBullet = node.get("amount-bullet").asInt();
        int bulletMagazine = node.get("bullet-magazine").asInt();
        int reloadingCooldown = node.get("reloading-cooldown").asInt();
        ItemModelData ammoItem = this.deserializeItemModelData(node.get("ammo-item"));
        ItemModelData weaponItem = this.deserializeItemModelData(node.get("weapon-item"));
        return new Weapon(name, WeaponType.valueOf(weaponType), Sound.valueOf((String)sound), soundPitch, soundVolume, scope, attackCooldown, damage, amountBullet, bulletMagazine, reloadingCooldown, ammoItem, weaponItem);
    }

    public ItemModelData deserializeItemModelData(JsonNode jsonNode) {
        String material = jsonNode.get("material").asText();
        String displayname = jsonNode.get("displayname").asText();
        ArrayList lore = new ArrayList();
        jsonNode.get("lore").forEach(arg_0 -> WeaponDeserializer.lambda$deserializeItemModelData$0((List)lore, arg_0));
        boolean glow = jsonNode.get("glow").asBoolean();
        boolean unbreakable = jsonNode.get("unbreakable").asBoolean();
        Integer modelData = jsonNode.get("model-data").asInt();
        ItemStack itemStack = ItemBuilder.create(Material.valueOf((String)material), displayname, (List<String>)lore, glow, unbreakable, modelData);
        return ItemModelData.builder().itemStack(itemStack).build();
    }

    private static /* synthetic */ void lambda$deserializeItemModelData$0(List lore, JsonNode loreNode) {
        lore.add((Object)loreNode.asText());
    }
}

