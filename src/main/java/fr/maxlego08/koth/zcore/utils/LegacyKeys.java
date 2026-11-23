package fr.maxlego08.koth.zcore.utils;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;

import java.util.Locale;
import java.util.Map;

/**
 * Recherche des enchantements et des effets de potion dans les registres Paper, à partir d'une clé
 * ({@code sharpness}, {@code minecraft:sharpness}) ou d'un ancien nom Bukkit ({@code DAMAGE_ALL}),
 * pour que les anciennes configurations continuent de fonctionner.
 */
public final class LegacyKeys {

    private static final Map<String, String> ENCHANTMENTS = Map.ofEntries(
            Map.entry("PROTECTION_ENVIRONMENTAL", "protection"),
            Map.entry("PROTECTION_FIRE", "fire_protection"),
            Map.entry("PROTECTION_FALL", "feather_falling"),
            Map.entry("PROTECTION_EXPLOSIONS", "blast_protection"),
            Map.entry("PROTECTION_PROJECTILE", "projectile_protection"),
            Map.entry("OXYGEN", "respiration"),
            Map.entry("WATER_WORKER", "aqua_affinity"),
            Map.entry("DAMAGE_ALL", "sharpness"),
            Map.entry("DAMAGE_UNDEAD", "smite"),
            Map.entry("DAMAGE_ARTHROPODS", "bane_of_arthropods"),
            Map.entry("LOOT_BONUS_MOBS", "looting"),
            Map.entry("SWEEPING", "sweeping_edge"),
            Map.entry("DIG_SPEED", "efficiency"),
            Map.entry("DURABILITY", "unbreaking"),
            Map.entry("LOOT_BONUS_BLOCKS", "fortune"),
            Map.entry("ARROW_DAMAGE", "power"),
            Map.entry("ARROW_KNOCKBACK", "punch"),
            Map.entry("ARROW_FIRE", "flame"),
            Map.entry("ARROW_INFINITE", "infinity"),
            Map.entry("LUCK", "luck_of_the_sea")
    );

    private static final Map<String, String> POTION_EFFECTS = Map.ofEntries(
            Map.entry("SLOW", "slowness"),
            Map.entry("FAST_DIGGING", "haste"),
            Map.entry("SLOW_DIGGING", "mining_fatigue"),
            Map.entry("INCREASE_DAMAGE", "strength"),
            Map.entry("HEAL", "instant_health"),
            Map.entry("HARM", "instant_damage"),
            Map.entry("JUMP", "jump_boost"),
            Map.entry("CONFUSION", "nausea"),
            Map.entry("DAMAGE_RESISTANCE", "resistance")
    );

    private LegacyKeys() {
    }

    /**
     * Retourne l'enchantement correspondant à une clé ou à un ancien nom Bukkit.
     *
     * @param name la clé ou l'ancien nom, peut être null
     * @return l'enchantement, ou null s'il n'existe pas
     */
    public static Enchantment enchantment(String name) {
        NamespacedKey key = enchantmentKey(name);
        return key == null ? null : RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).get(key);
    }

    /**
     * Retourne l'effet de potion correspondant à une clé ou à un ancien nom Bukkit.
     *
     * @param name la clé ou l'ancien nom, peut être null
     * @return l'effet, ou null s'il n'existe pas
     */
    public static PotionEffectType potionEffect(String name) {
        NamespacedKey key = potionEffectKey(name);
        return key == null ? null : RegistryAccess.registryAccess().getRegistry(RegistryKey.MOB_EFFECT).get(key);
    }

    /**
     * Convertit un nom d'enchantement en clé de registre.
     *
     * @param name la clé ou l'ancien nom, peut être null
     * @return la clé, ou null si le nom n'est pas une clé valide
     */
    static NamespacedKey enchantmentKey(String name) {
        return toKey(name, ENCHANTMENTS);
    }

    /**
     * Convertit un nom d'effet de potion en clé de registre.
     *
     * @param name la clé ou l'ancien nom, peut être null
     * @return la clé, ou null si le nom n'est pas une clé valide
     */
    static NamespacedKey potionEffectKey(String name) {
        return toKey(name, POTION_EFFECTS);
    }

    private static NamespacedKey toKey(String name, Map<String, String> legacyNames) {
        if (name == null || name.isBlank()) return null;
        String trimmed = name.trim();
        String legacy = legacyNames.get(trimmed.toUpperCase(Locale.ROOT));
        return NamespacedKey.fromString(legacy != null ? legacy : trimmed.toLowerCase(Locale.ROOT));
    }
}
