/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Map
 *  lombok.Generated
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package dev.gether.getclan.config.domain;

import dev.gether.getclan.config.domain.LangType;
import dev.gether.getclan.core.CostType;
import dev.gether.getconfig.GetConfig;
import dev.gether.getconfig.annotation.Comment;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Config
extends GetConfig {
    @Comment(value={"Language | J\u0119zyk  [PL, EN]", "Language | Language selection [PL, EN]"})
    private LangType langType = LangType.EN;
    @Comment(value={"Napis na \u015brodku ekranu po \u015bmierci", "Show title after death"})
    private boolean titleAlert = true;
    private int fadeIn = 10;
    private int stay = 40;
    private int fadeOut = 10;
    @Comment(value={"Time in seconds after which a hit counts as a kill", "Czas po ilu sekundach o trafienie zalicza zabojstwo"})
    private int killCountDuration = 30;
    @Comment(value={"System punktacji", "Points conversion system"})
    private String calcPoints = "{old_rating} + (30 * ({score} - (1 / (1 + pow(10, ({opponent_rating} - {old_rating}) / 400)))))";
    @Comment(value={"Domy\u015blna ilo\u015b\u0107 punkt\u00f3w dla gracza", "Default points for players"})
    private int defaultPoints = 500;
    @Comment(value={"Limit sojusznikow dla klanu", "Alliance limit for clans"})
    private int limitAlliance = 2;
    @Comment(value={"Czy ma by\u0107 w\u0142\u0105czone pvp dla klanu", "Enable clan PvP"})
    private boolean pvpClan = true;
    @Comment(value={"Czy ma by\u0107 w\u0142\u0105czone pvp dla sojusznik\u00f3w", "Enable alliance PvP"})
    private boolean pvpAlliance = true;
    @Comment(value={"Czy ma by\u0107 w\u0142\u0105czona wiadomo\u015b\u0107 po \u015bmierci", "Enable broadcast message after the death"})
    private boolean deathMessage = true;
    @Comment(value={"System Antiabuse (Nabijanie rankingu)", "Anti-abuse system (Prevent ranking abuse)"})
    private boolean systemAntiabuse = true;
    @Comment(value={"Co ile sekund nadal mozna zabic tego samego gracza", "Cooldown time in seconds for killing the same player"})
    private int cooldown = 300;
    @Comment(value={"Czy wlaczyc platne tworzenie klanu (true | false)", "Enable paid clan creation (true | false)"})
    private boolean enablePayment = true;
    @Comment(value={"Jezeli opcja powyzej jest wlaczona to jaka metoda platnosci (ITEM|VAULT)", "If the above option is enabled, specify payment method (ITEM|VAULT)"})
    private CostType costType = CostType.VAULT;
    @Comment(value={"Koszt - VAULT | ITEM", "Cost - VAULT | ITEM"})
    private double costCreate = 10.0;
    private ItemStack itemCost = new ItemStack(Material.STONE);
    @Comment(value={"ilo\u015b\u0107 os\u00f3b potrzebnych do liczenia rankingu klanu"})
    private int membersRequiredForRanking = 3;
    @Comment(value={"co ma zwracac placeholder gdy klan nie posida tylu czonkow do liczenia pkt"})
    private String placeholderNeedMembers = "&eneed 3 users";
    @Comment(value={"Limit os\u00f3b w klanie", "Clan member limit"})
    public Map<String, Integer> permissionLimitMember = Map.of("getclan.default", 5);
    @Comment(value={"Ograniczenia d\u0142ugo\u015bci tagu klanu", "Clan tag length restrictions"})
    private int clansTagLengthMin = 2;
    private int clansTagLengthMax = 6;
    private String colorOnlinePlayer = "&a";
    private String colorOfflinePlayer = "&7";
    @Comment(value={"Gdy zast\u0119pca lidera nie jest ustawiony", "When deputy leader is not set"})
    private String noneDeputy = "None";
    @Comment(value={"Placeholder %getclan_user_has_clan% kiedy nie/posiadasz klanu", "Placeholder %getclan_user_has_clan% when you have/not a clan"})
    private String hasNotClan = "false";
    private String hasClan = "true";
    @Comment(value={"Placeholder FORMAT kiedy nie posiadasz TAGU klanu", "Placeholder format when you don't have a clan TAG"})
    private String noneTag = "None";
    @Comment(value={"Placeholder FORMAT kiedy nie posidasz punkt\u00f3w klanu", "Placeholder format when you don't have clan points"})
    private String nonePointsClan = "None";
    @Comment(value={"[%getclan_user_format_points%] Format dla punkt\u00f3w gracza", "[%getclan_user_format_points%] Format for player points"})
    private String formatUserPoints = "&7[&f{points}&7]";
    @Comment(value={"[%getclan_clan_format_points%] Format dla punkt\u00f3w klanu", "[%getclan_clan_format_points%] Format for clan points"})
    private String formatClanPoints = "&7[&f{points}&7]";
    @Comment(value={"[%getclan_clan_format_tag%] Format dla tagu", "[%getclan_clan_format_tag%] Format for clan tag"})
    private String formatTag = "&7[&f{tag}&7]";
    @Comment(value={"[%rel_getclan_tag%] Format dla sojusznik\u00f3w", "[%rel_getclan_tag%] Format for alliances"})
    private String formatAlliance = "#147aff{tag}";
    @Comment(value={"[%rel_getclan_tag%] Format dla cz\u0142onka z klanu", "[%rel_getclan_tag%] Format for clan members"})
    private String formatMember = "#48ff05{tag}";
    @Comment(value={"[%rel_getclan_tag%] Format normalny dla tagu | {tag} {player}", "Normal format for tags | {tag} {player}"})
    private String formatNormal = "&c{tag}";
    @Comment(value={"Format do wiadomo\u015bci wys\u0142anej do gildii | {tag} {player}", "Format for messages sent to the clan | {tag} {player}"})
    private String formatClanMessage = "&6{player} -> \u2690 | &e{message}";
    @Comment(value={"Format do wiadomo\u015bci wys\u0142anej do sojusznik\u00f3w | {tag} {player}", "Format for messages sent to alliances | {tag} {player}"})
    private String formatAllianceMessage = "#006eff{player} -> \u2690 | #78d4ff{message}";
    private String noneClan = "None";
    private Map<Material, String> translate = new HashMap(Map.of(Material.STONE, "Kamie\u0144"));
    @Comment(value={"Messages settings", "Ustawienia wiadomo\u015bci", "Example MiniMessage: <gradient:#ff7a18:#af002d>Klany</gradient>", "Example legacy: &aZielona wiadomo\u015b\u0107"})
    private Messages messages = new Messages();

    public static class Messages {
        @Comment(value={"true = allows MiniMessage tags in messages; legacy & formatting still works", "true = pozwala u\u017cywa\u0107 MiniMessage w wiadomo\u015bciach; legacy & dalej dzia\u0142a"})
        private boolean useMiniMessage = true;

        @Generated
        public boolean isUseMiniMessage() {
            return this.useMiniMessage;
        }
    }

    @Generated
    public LangType getLangType() {
        return this.langType;
    }

    @Generated
    public boolean isTitleAlert() {
        return this.titleAlert;
    }

    @Generated
    public int getFadeIn() {
        return this.fadeIn;
    }

    @Generated
    public int getStay() {
        return this.stay;
    }

    @Generated
    public int getFadeOut() {
        return this.fadeOut;
    }

    @Generated
    public int getKillCountDuration() {
        return this.killCountDuration;
    }

    @Generated
    public String getCalcPoints() {
        return this.calcPoints;
    }

    @Generated
    public int getDefaultPoints() {
        return this.defaultPoints;
    }

    @Generated
    public int getLimitAlliance() {
        return this.limitAlliance;
    }

    @Generated
    public boolean isPvpClan() {
        return this.pvpClan;
    }

    @Generated
    public boolean isPvpAlliance() {
        return this.pvpAlliance;
    }

    @Generated
    public boolean isDeathMessage() {
        return this.deathMessage;
    }

    @Generated
    public boolean isSystemAntiabuse() {
        return this.systemAntiabuse;
    }

    @Generated
    public int getCooldown() {
        return this.cooldown;
    }

    @Generated
    public boolean isEnablePayment() {
        return this.enablePayment;
    }

    @Generated
    public CostType getCostType() {
        return this.costType;
    }

    @Generated
    public double getCostCreate() {
        return this.costCreate;
    }

    @Generated
    public ItemStack getItemCost() {
        return this.itemCost;
    }

    @Generated
    public int getMembersRequiredForRanking() {
        return this.membersRequiredForRanking;
    }

    @Generated
    public String getPlaceholderNeedMembers() {
        return this.placeholderNeedMembers;
    }

    @Generated
    public Map<String, Integer> getPermissionLimitMember() {
        return this.permissionLimitMember;
    }

    @Generated
    public int getClansTagLengthMin() {
        return this.clansTagLengthMin;
    }

    @Generated
    public int getClansTagLengthMax() {
        return this.clansTagLengthMax;
    }

    @Generated
    public String getColorOnlinePlayer() {
        return this.colorOnlinePlayer;
    }

    @Generated
    public String getColorOfflinePlayer() {
        return this.colorOfflinePlayer;
    }

    @Generated
    public String getNoneDeputy() {
        return this.noneDeputy;
    }

    @Generated
    public String getHasNotClan() {
        return this.hasNotClan;
    }

    @Generated
    public String getHasClan() {
        return this.hasClan;
    }

    @Generated
    public String getNoneTag() {
        return this.noneTag;
    }

    @Generated
    public String getNonePointsClan() {
        return this.nonePointsClan;
    }

    @Generated
    public String getFormatUserPoints() {
        return this.formatUserPoints;
    }

    @Generated
    public String getFormatClanPoints() {
        return this.formatClanPoints;
    }

    @Generated
    public String getFormatTag() {
        return this.formatTag;
    }

    @Generated
    public String getFormatAlliance() {
        return this.formatAlliance;
    }

    @Generated
    public String getFormatMember() {
        return this.formatMember;
    }

    @Generated
    public String getFormatNormal() {
        return this.formatNormal;
    }

    @Generated
    public String getFormatClanMessage() {
        return this.formatClanMessage;
    }

    @Generated
    public String getFormatAllianceMessage() {
        return this.formatAllianceMessage;
    }

    @Generated
    public String getNoneClan() {
        return this.noneClan;
    }

    @Generated
    public Map<Material, String> getTranslate() {
        return this.translate;
    }

    @Generated
    public Messages getMessages() {
        return this.messages;
    }

    @Generated
    public void setLangType(LangType langType) {
        this.langType = langType;
    }

    @Generated
    public void setTitleAlert(boolean titleAlert) {
        this.titleAlert = titleAlert;
    }

    @Generated
    public void setFadeIn(int fadeIn) {
        this.fadeIn = fadeIn;
    }

    @Generated
    public void setStay(int stay) {
        this.stay = stay;
    }

    @Generated
    public void setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
    }

    @Generated
    public void setKillCountDuration(int killCountDuration) {
        this.killCountDuration = killCountDuration;
    }

    @Generated
    public void setCalcPoints(String calcPoints) {
        this.calcPoints = calcPoints;
    }

    @Generated
    public void setDefaultPoints(int defaultPoints) {
        this.defaultPoints = defaultPoints;
    }

    @Generated
    public void setLimitAlliance(int limitAlliance) {
        this.limitAlliance = limitAlliance;
    }

    @Generated
    public void setPvpClan(boolean pvpClan) {
        this.pvpClan = pvpClan;
    }

    @Generated
    public void setPvpAlliance(boolean pvpAlliance) {
        this.pvpAlliance = pvpAlliance;
    }

    @Generated
    public void setDeathMessage(boolean deathMessage) {
        this.deathMessage = deathMessage;
    }

    @Generated
    public void setSystemAntiabuse(boolean systemAntiabuse) {
        this.systemAntiabuse = systemAntiabuse;
    }

    @Generated
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    @Generated
    public void setEnablePayment(boolean enablePayment) {
        this.enablePayment = enablePayment;
    }

    @Generated
    public void setCostType(CostType costType) {
        this.costType = costType;
    }

    @Generated
    public void setCostCreate(double costCreate) {
        this.costCreate = costCreate;
    }

    @Generated
    public void setItemCost(ItemStack itemCost) {
        this.itemCost = itemCost;
    }

    @Generated
    public void setMembersRequiredForRanking(int membersRequiredForRanking) {
        this.membersRequiredForRanking = membersRequiredForRanking;
    }

    @Generated
    public void setPlaceholderNeedMembers(String placeholderNeedMembers) {
        this.placeholderNeedMembers = placeholderNeedMembers;
    }

    @Generated
    public void setPermissionLimitMember(Map<String, Integer> permissionLimitMember) {
        this.permissionLimitMember = permissionLimitMember;
    }

    @Generated
    public void setClansTagLengthMin(int clansTagLengthMin) {
        this.clansTagLengthMin = clansTagLengthMin;
    }

    @Generated
    public void setClansTagLengthMax(int clansTagLengthMax) {
        this.clansTagLengthMax = clansTagLengthMax;
    }

    @Generated
    public void setColorOnlinePlayer(String colorOnlinePlayer) {
        this.colorOnlinePlayer = colorOnlinePlayer;
    }

    @Generated
    public void setColorOfflinePlayer(String colorOfflinePlayer) {
        this.colorOfflinePlayer = colorOfflinePlayer;
    }

    @Generated
    public void setNoneDeputy(String noneDeputy) {
        this.noneDeputy = noneDeputy;
    }

    @Generated
    public void setHasNotClan(String hasNotClan) {
        this.hasNotClan = hasNotClan;
    }

    @Generated
    public void setHasClan(String hasClan) {
        this.hasClan = hasClan;
    }

    @Generated
    public void setNoneTag(String noneTag) {
        this.noneTag = noneTag;
    }

    @Generated
    public void setNonePointsClan(String nonePointsClan) {
        this.nonePointsClan = nonePointsClan;
    }

    @Generated
    public void setFormatUserPoints(String formatUserPoints) {
        this.formatUserPoints = formatUserPoints;
    }

    @Generated
    public void setFormatClanPoints(String formatClanPoints) {
        this.formatClanPoints = formatClanPoints;
    }

    @Generated
    public void setFormatTag(String formatTag) {
        this.formatTag = formatTag;
    }

    @Generated
    public void setFormatAlliance(String formatAlliance) {
        this.formatAlliance = formatAlliance;
    }

    @Generated
    public void setFormatMember(String formatMember) {
        this.formatMember = formatMember;
    }

    @Generated
    public void setFormatNormal(String formatNormal) {
        this.formatNormal = formatNormal;
    }

    @Generated
    public void setFormatClanMessage(String formatClanMessage) {
        this.formatClanMessage = formatClanMessage;
    }

    @Generated
    public void setFormatAllianceMessage(String formatAllianceMessage) {
        this.formatAllianceMessage = formatAllianceMessage;
    }

    @Generated
    public void setNoneClan(String noneClan) {
        this.noneClan = noneClan;
    }

    @Generated
    public void setTranslate(Map<Material, String> translate) {
        this.translate = translate;
    }
}
