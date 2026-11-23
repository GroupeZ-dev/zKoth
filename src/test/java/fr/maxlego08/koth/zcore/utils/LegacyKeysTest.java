package fr.maxlego08.koth.zcore.utils;

import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LegacyKeysTest {

    @Test
    void legacyEnchantmentNamesAreMapped() {
        assertEquals(NamespacedKey.minecraft("sharpness"), LegacyKeys.enchantmentKey("DAMAGE_ALL"));
        assertEquals(NamespacedKey.minecraft("unbreaking"), LegacyKeys.enchantmentKey("durability"));
    }

    @Test
    void modernEnchantmentKeysAreKept() {
        assertEquals(NamespacedKey.minecraft("sharpness"), LegacyKeys.enchantmentKey("SHARPNESS"));
        assertEquals(NamespacedKey.minecraft("mending"), LegacyKeys.enchantmentKey("minecraft:mending"));
        assertEquals(NamespacedKey.fromString("custom:enchant"), LegacyKeys.enchantmentKey("custom:enchant"));
    }

    @Test
    void legacyPotionNamesAreMapped() {
        assertEquals(NamespacedKey.minecraft("strength"), LegacyKeys.potionEffectKey("INCREASE_DAMAGE"));
        assertEquals(NamespacedKey.minecraft("speed"), LegacyKeys.potionEffectKey("SPEED"));
    }

    @Test
    void invalidNamesReturnNull() {
        assertNull(LegacyKeys.enchantmentKey(null));
        assertNull(LegacyKeys.potionEffectKey(" "));
        assertNull(LegacyKeys.potionEffectKey("not a key!"));
    }
}
