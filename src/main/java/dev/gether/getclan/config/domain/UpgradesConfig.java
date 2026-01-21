/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.HashSet
 *  java.util.List
 *  java.util.Map
 *  java.util.Optional
 *  java.util.Set
 *  lombok.Generated
 *  org.bukkit.Material
 */
package dev.gether.getclan.config.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.gether.getclan.core.CostType;
import dev.gether.getclan.core.upgrade.Upgrade;
import dev.gether.getclan.core.upgrade.UpgradeCost;
import dev.gether.getclan.core.upgrade.UpgradeType;
import dev.gether.getconfig.GetConfig;
import dev.gether.getconfig.domain.Item;
import dev.gether.getconfig.domain.config.InventoryBase;
import dev.gether.getconfig.domain.config.ItemDecoration;
import dev.gether.getconfig.utils.ItemBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.Generated;
import org.bukkit.Material;

public class UpgradesConfig
extends GetConfig {
    private boolean upgradeEnable = true;
    private List<Material> whitelistMaterial = new ArrayList((Collection)List.of((Object)Material.STONE, (Object)Material.DIAMOND_ORE));
    private InventoryBase inventoryBase = InventoryBase.builder().title("&8&l\u1d1c\u029f\u1d07\u1d18\ua731\u1d22\u1d07\u0274\u026a\u1d00 \u1d0b\u029f\u1d00\u0274\u1d1c").size(45).itemsDecoration((Set<ItemDecoration>)new HashSet((Collection)Set.of((Object)ItemDecoration.builder().slots((Set<Integer>)new HashSet((Collection)Set.of((Object)0, (Object)8, (Object)36, (Object)44))).item(Item.builder().material(Material.LIME_STAINED_GLASS_PANE).displayname("").lore((List<String>)new ArrayList()).build()).build(), (Object)ItemDecoration.builder().slots((Set<Integer>)new HashSet((Collection)Set.of((Object)2, (Object)3, (Object)5, (Object)6, (Object)18, (Object)26, (Object)36, (Object)37, (Object)39, (Object)40))).item(Item.builder().material(Material.WHITE_STAINED_GLASS_PANE).displayname("").lore((List<String>)new ArrayList()).build()).build(), (Object)ItemDecoration.builder().slots((Set<Integer>)new HashSet((Collection)Set.of((Object)1, (Object)7, (Object)9, (Object)17, (Object)27, (Object)35, (Object)37, (Object)43))).item(Item.builder().material(Material.GREEN_STAINED_GLASS_PANE).displayname("").lore((List<String>)new ArrayList()).build()).build()))).build();
    private Set<Upgrade> upgrades = new HashSet((Collection)Set.of((Object)Upgrade.builder().enabled(true).slot(20).upgradeType(UpgradeType.DROP_BOOST).upgradesCost((Map<Integer, UpgradeCost>)new HashMap(Map.of((Object)0, (Object)UpgradeCost.builder().itemStack(ItemBuilder.create(Material.BOOK, "", true)).item(Item.builder().material(Material.DIAMOND_PICKAXE).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 0").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualny bonus: &f0%", (Object)"", (Object)"&7Co otrzymasz&8:", (Object)"&8\u2192 &7Zwi\u0119kszony drop o &e50%", (Object)"&7", (Object)"&7Aby awansowa\u0107 na kolejny poziom&8:", (Object)"&8\u2192 &f{amount}&8/&7{need-amount}", (Object)"&7"))).glow(false).build()).build(), (Object)1, (Object)UpgradeCost.builder().boostValue(0.5).itemStack(ItemBuilder.create(Material.BOOK, "XYZ", true)).item(Item.builder().material(Material.DIAMOND_PICKAXE).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 1").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualny bonus: &f50%", (Object)"", (Object)"&7Co otrzymasz&8:", (Object)"&8\u2192 &7Zwi\u0119kszony drop o &e100%", (Object)"&7", (Object)"&7Aby awansowa\u0107 na kolejny poziom&8:", (Object)"&8\u2192 &f{amount}&8/&7{need-amount}", (Object)"&7"))).glow(false).build()).cost(10.0).costType(CostType.ITEM).build(), (Object)2, (Object)UpgradeCost.builder().boostValue(1.0).itemStack(ItemBuilder.create(Material.BOOK, "XYZ", true)).item(Item.builder().material(Material.DIAMOND_PICKAXE).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 2").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualny bonus: &f100%", (Object)"", (Object)"&7Co otrzymasz&8:", (Object)"&8\u2192 &7Zwi\u0119kszony drop o &e150%", (Object)"&7", (Object)"&7Aby awansowa\u0107 na kolejny poziom&8:", (Object)"&8\u2192 &f{amount}&8/&7{need-amount}", (Object)"&7"))).glow(false).build()).cost(20.0).costType(CostType.ITEM).build(), (Object)3, (Object)UpgradeCost.builder().boostValue(1.5).itemStack(ItemBuilder.create(Material.BOOK, "XYZ", true)).item(Item.builder().material(Material.DIAMOND_PICKAXE).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 3").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualny bonus: &f150%", (Object)"", (Object)"&7Co otrzymasz&8:", (Object)"&8\u2192 &7Zwi\u0119kszony drop o &e200%", (Object)"&7", (Object)"&7Aby awansowa\u0107 na kolejny poziom&8:", (Object)"&8\u2192 &f{amount}&8/&7{need-amount}", (Object)"&7"))).glow(false).build()).cost(30.0).costType(CostType.ITEM).build(), (Object)4, (Object)UpgradeCost.builder().boostValue(2.0).itemStack(ItemBuilder.create(Material.BOOK, "XYZ", true)).item(Item.builder().material(Material.DIAMOND_PICKAXE).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 4").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualny bonus: &f200%", (Object)"", (Object)"&7Co otrzymasz&8:", (Object)"&8\u2192 &7Zwi\u0119kszony drop o &e250%", (Object)"&7", (Object)"&7Aby awansowa\u0107 na kolejny poziom&8:", (Object)"&8\u2192 &f{amount}&8/&7{need-amount}", (Object)"&7"))).glow(false).build()).cost(40.0).costType(CostType.ITEM).build(), (Object)5, (Object)UpgradeCost.builder().boostValue(2.5).itemStack(ItemBuilder.create(Material.BOOK, "XYZ", true)).item(Item.builder().material(Material.DIAMOND_PICKAXE).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 5").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualny bonus: &f250%", (Object)"", (Object)"&7Co otrzymasz&8:", (Object)"&8\u2192 &7Zwi\u0119kszony drop o &e300%", (Object)"&7", (Object)"&7Aby awansowa\u0107 na kolejny poziom&8:", (Object)"&8\u2192 &f{amount}&8/&7{need-amount}", (Object)"&7"))).glow(false).build()).cost(50.0).costType(CostType.ITEM).build(), (Object)6, (Object)UpgradeCost.builder().boostValue(3.0).itemStack(ItemBuilder.create(Material.BOOK, "XYZ", true)).item(Item.builder().material(Material.DIAMOND_PICKAXE).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 6 &7- #e80023&L\u1d0d\u1d00x").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualna warto\u015b\u0107 ulepszenia&8:", (Object)"&8\u2192 &7Zwi\u0119kszony drop o &e300%", (Object)"&7"))).glow(false).build()).cost(60.0).costType(CostType.ITEM).build()))).build(), (Object)Upgrade.builder().enabled(true).slot(22).upgradeType(UpgradeType.MEMBERS).upgradesCost((Map<Integer, UpgradeCost>)new HashMap(Map.of((Object)0, (Object)UpgradeCost.builder().itemStack(ItemBuilder.create(Material.BOOK, "", true)).item(Item.builder().material(Material.PLAYER_HEAD).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 0").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualna ilo\u015b\u0107: &f6", (Object)"", (Object)"&7Co otrzymasz&8:", (Object)"&8\u2192 &7Ilo\u015b\u0107 cz\u0142onk\u00f3w&8: &e8", (Object)"&7", (Object)"&7Aby awansowa\u0107 na kolejny poziom&8:", (Object)"&8\u2192 &f{amount}&8/&7{need-amount}", (Object)"&7"))).glow(false).build()).boostValue(6.0).build(), (Object)1, (Object)UpgradeCost.builder().boostValue(8.0).itemStack(ItemBuilder.create(Material.BOOK, "XYZ", true)).item(Item.builder().material(Material.PLAYER_HEAD).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 1").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualna ilo\u015b\u0107: &f8", (Object)"", (Object)"&7Co otrzymasz&8:", (Object)"&8\u2192 &7Ilo\u015b\u0107 cz\u0142onk\u00f3w&8: &e10", (Object)"&7", (Object)"&7Aby awansowa\u0107 na kolejny poziom&8:", (Object)"&8\u2192 &f{amount}&8/&7{need-amount}", (Object)"&7"))).glow(false).build()).cost(10.0).costType(CostType.ITEM).build(), (Object)2, (Object)UpgradeCost.builder().boostValue(10.0).itemStack(ItemBuilder.create(Material.BOOK, "XYZ", true)).item(Item.builder().material(Material.PLAYER_HEAD).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 2").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualna ilo\u015b\u0107: &f10", (Object)"", (Object)"&7Co otrzymasz&8:", (Object)"&8\u2192 &7Ilo\u015b\u0107 cz\u0142onk\u00f3w&8: &e12", (Object)"&7", (Object)"&7Aby awansowa\u0107 na kolejny poziom&8:", (Object)"&8\u2192 &f{amount}&8/&7{need-amount}", (Object)"&7"))).glow(false).build()).cost(20.0).costType(CostType.ITEM).build(), (Object)3, (Object)UpgradeCost.builder().boostValue(12.0).itemStack(ItemBuilder.create(Material.BOOK, "XYZ", true)).item(Item.builder().material(Material.PLAYER_HEAD).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 3").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualna ilo\u015b\u0107: &f12", (Object)"", (Object)"&7Co otrzymasz&8:", (Object)"&8\u2192 &7Ilo\u015b\u0107 cz\u0142onk\u00f3w&8: &e14", (Object)"&7", (Object)"&7Aby awansowa\u0107 na kolejny poziom&8:", (Object)"&8\u2192 &f{amount}&8/&7{need-amount}", (Object)"&7"))).glow(false).build()).cost(30.0).costType(CostType.ITEM).build(), (Object)4, (Object)UpgradeCost.builder().boostValue(14.0).itemStack(ItemBuilder.create(Material.BOOK, "XYZ", true)).item(Item.builder().material(Material.PLAYER_HEAD).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 4").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualna ilo\u015b\u0107: &f14", (Object)"", (Object)"&7Co otrzymasz&8:", (Object)"&8\u2192 &7Ilo\u015b\u0107 cz\u0142onk\u00f3w&8: &e16", (Object)"&7", (Object)"&7Aby awansowa\u0107 na kolejny poziom&8:", (Object)"&8\u2192 &f{amount}&8/&7{need-amount}", (Object)"&7"))).glow(false).build()).cost(40.0).costType(CostType.ITEM).build(), (Object)5, (Object)UpgradeCost.builder().boostValue(16.0).itemStack(ItemBuilder.create(Material.BOOK, "XYZ", true)).item(Item.builder().material(Material.PLAYER_HEAD).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 5").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualna ilo\u015b\u0107: &f16", (Object)"", (Object)"&7Co otrzymasz&8:", (Object)"&8\u2192 &7Ilo\u015b\u0107 cz\u0142onk\u00f3w&8: &e18", (Object)"&7", (Object)"&7Aby awansowa\u0107 na kolejny poziom&8:", (Object)"&8\u2192 &f{amount}&8/&7{need-amount}", (Object)"&7"))).glow(false).build()).cost(50.0).costType(CostType.ITEM).build(), (Object)6, (Object)UpgradeCost.builder().boostValue(18.0).itemStack(ItemBuilder.create(Material.BOOK, "XYZ", true)).item(Item.builder().material(Material.PLAYER_HEAD).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 6 &7- #e80023&L\u1d0d\u1d00x").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualna warto\u015b\u0107 ulepszenia&8:", (Object)"&8\u2192 &7Ilo\u015b\u0107 cz\u0142onk\u00f3w&8: &e18", (Object)"&7"))).glow(false).build()).cost(60.0).costType(CostType.ITEM).build()))).build(), (Object)Upgrade.builder().enabled(true).slot(24).upgradeType(UpgradeType.POINTS_BOOST).upgradesCost((Map<Integer, UpgradeCost>)new HashMap(Map.of((Object)0, (Object)UpgradeCost.builder().itemStack(ItemBuilder.create(Material.BOOK, "", true)).item(Item.builder().material(Material.IRON_SWORD).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 0").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualna bonus: &f0%", (Object)"", (Object)"&7Co otrzymasz&8:", (Object)"&8\u2192 &7Dodatkowe punkty&8: &e5%", (Object)"&7", (Object)"&7Aby awansowa\u0107 na kolejny poziom&8:", (Object)"&8\u2192 &f{amount}&8/&7{need-amount}", (Object)"&7"))).glow(false).build()).build(), (Object)1, (Object)UpgradeCost.builder().boostValue(0.05).itemStack(ItemBuilder.create(Material.BOOK, "XYZ", true)).item(Item.builder().material(Material.IRON_SWORD).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 2").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualna bonus: &f5%", (Object)"", (Object)"&7Co otrzymasz&8:", (Object)"&8\u2192 &7Dodatkowe punkty&8: &e10%", (Object)"&7", (Object)"&7Aby awansowa\u0107 na kolejny poziom&8:", (Object)"&8\u2192 &f{amount}&8/&7{need-amount}", (Object)"&7"))).glow(false).build()).cost(10.0).costType(CostType.ITEM).build(), (Object)2, (Object)UpgradeCost.builder().boostValue(0.1).itemStack(ItemBuilder.create(Material.BOOK, "XYZ", true)).item(Item.builder().material(Material.IRON_SWORD).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 2").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualna bonus: &f10%", (Object)"", (Object)"&7Co otrzymasz&8:", (Object)"&8\u2192 &7Dodatkowe punkty&8: &e15%", (Object)"&7", (Object)"&7Aby awansowa\u0107 na kolejny poziom&8:", (Object)"&8\u2192 &f{amount}&8/&7{need-amount}", (Object)"&7"))).glow(false).build()).cost(20.0).costType(CostType.ITEM).build(), (Object)3, (Object)UpgradeCost.builder().boostValue(0.15).itemStack(ItemBuilder.create(Material.BOOK, "XYZ", true)).item(Item.builder().material(Material.IRON_SWORD).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 3").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualna bonus: &f15%", (Object)"", (Object)"&7Co otrzymasz&8:", (Object)"&8\u2192 &7Dodatkowe punkty&8: &e20%", (Object)"&7", (Object)"&7Aby awansowa\u0107 na kolejny poziom&8:", (Object)"&8\u2192 &f{amount}&8/&7{need-amount}", (Object)"&7"))).glow(false).build()).cost(30.0).costType(CostType.ITEM).build(), (Object)4, (Object)UpgradeCost.builder().boostValue(0.2).itemStack(ItemBuilder.create(Material.BOOK, "XYZ", true)).item(Item.builder().material(Material.IRON_SWORD).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 4").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualna bonus: &f20%", (Object)"", (Object)"&7Co otrzymasz&8:", (Object)"&8\u2192 &7Dodatkowe punkty&8: &e25%", (Object)"&7", (Object)"&7Aby awansowa\u0107 na kolejny poziom&8:", (Object)"&8\u2192 &f{amount}&8/&7{need-amount}", (Object)"&7"))).glow(false).build()).cost(40.0).costType(CostType.ITEM).build(), (Object)5, (Object)UpgradeCost.builder().boostValue(0.25).itemStack(ItemBuilder.create(Material.BOOK, "XYZ", true)).item(Item.builder().material(Material.IRON_SWORD).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 5").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualna bonus: &f25%", (Object)"", (Object)"&7Co otrzymasz&8:", (Object)"&8\u2192 &7Dodatkowe punkty&8: &e30%", (Object)"&7", (Object)"&7Aby awansowa\u0107 na kolejny poziom&8:", (Object)"&8\u2192 &f{amount}&8/&7{need-amount}", (Object)"&7"))).glow(false).build()).cost(50.0).costType(CostType.ITEM).build(), (Object)6, (Object)UpgradeCost.builder().boostValue(0.3).itemStack(ItemBuilder.create(Material.BOOK, "XYZ", true)).item(Item.builder().material(Material.IRON_SWORD).displayname("&8\u2b1b #ffd573\u1d18\u1d0f\u1d22\u026a\u1d0f\u1d0d 6 &7- #e80023&L\u1d0d\u1d00x").lore((List<String>)new ArrayList((Collection)List.of((Object)"", (Object)"&7Aktualna warto\u015b\u0107 ulepszenia&8:", (Object)"&8\u2192 &7Dodatkowe punkty&8: &e30%", (Object)"&7"))).glow(false).build()).cost(60.0).costType(CostType.ITEM).build()))).build()));

    @JsonIgnore
    public Optional<Upgrade> findUpgradeByType(UpgradeType upgradeType) {
        return this.upgrades.stream().filter(upgrade -> upgrade.getUpgradeType() == upgradeType).findAny();
    }

    @JsonIgnore
    public Optional<Upgrade> findUpgradeTypeBySlot(int slot) {
        return this.upgrades.stream().filter(upgrade -> upgrade.getSlot() == slot).findAny();
    }

    @Generated
    public boolean isUpgradeEnable() {
        return this.upgradeEnable;
    }

    @Generated
    public List<Material> getWhitelistMaterial() {
        return this.whitelistMaterial;
    }

    @Generated
    public InventoryBase getInventoryBase() {
        return this.inventoryBase;
    }

    @Generated
    public Set<Upgrade> getUpgrades() {
        return this.upgrades;
    }

    @Generated
    public void setUpgradeEnable(boolean upgradeEnable) {
        this.upgradeEnable = upgradeEnable;
    }

    @Generated
    public void setWhitelistMaterial(List<Material> whitelistMaterial) {
        this.whitelistMaterial = whitelistMaterial;
    }

    @Generated
    public void setInventoryBase(InventoryBase inventoryBase) {
        this.inventoryBase = inventoryBase;
    }

    @Generated
    public void setUpgrades(Set<Upgrade> upgrades) {
        this.upgrades = upgrades;
    }

    @Generated
    public UpgradesConfig(boolean upgradeEnable, List<Material> whitelistMaterial, InventoryBase inventoryBase, Set<Upgrade> upgrades) {
        this.upgradeEnable = upgradeEnable;
        this.whitelistMaterial = whitelistMaterial;
        this.inventoryBase = inventoryBase;
        this.upgrades = upgrades;
    }

    @Generated
    public UpgradesConfig() {
    }
}

