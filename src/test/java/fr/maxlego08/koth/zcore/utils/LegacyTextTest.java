package fr.maxlego08.koth.zcore.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LegacyTextTest {

    @Test
    void colorizeTranslatesAmpersandAndHexColors() {
        assertEquals("§aGreen §x§1§2§A§b§E§fHex", LegacyText.colorize("&aGreen #12AbEfHex"));
    }

    @Test
    void colorizeTranslatesAmpersandHexFormat() {
        assertEquals("§x§f§f§0§0§0§0Red", LegacyText.colorize("&x&f&f&0&0&0&0Red"));
    }

    @Test
    void translateAmpersandMatchesBukkitBehavior() {
        assertEquals("§lBold §RReset & not a code #ffffff", LegacyText.translateAmpersand("&lBold &RReset & not a code #ffffff"));
    }

    @Test
    void reverseColorsIsTheInverseOfColorize() {
        String colored = LegacyText.colorize("&lBold #12AbEfHex &cRed");
        assertEquals("&lBold #12AbEfHex &cRed", LegacyText.reverseColors(colored));
    }

    @Test
    void stripColorsRemovesEveryCode() {
        assertEquals("Bold Hex Red", LegacyText.stripColors(LegacyText.colorize("&lBold #12AbEfHex &cRed")));
        assertEquals("Hex", LegacyText.stripColors("&x&f&f&0&0&0&0Hex"));
    }

    @Test
    void componentKeepsHexColor() {
        Component component = LegacyText.component(LegacyText.colorize("#ff8800zKoth axe"));
        assertEquals(TextColor.fromHexString("#ff8800"), component.children().isEmpty()
                ? component.color() : component.children().get(0).color());
    }

    @Test
    void hexColorSurvivesComponentRoundTrip() {
        String legacy = LegacyText.colorize("#ff8800zKoth axe");
        assertEquals(legacy, LegacyText.serialize(LegacyText.component(legacy)));
    }

    @Test
    void nullValuesAreHandled() {
        assertNull(LegacyText.colorize(null));
        assertNull(LegacyText.reverseColors(null));
        assertNull(LegacyText.stripColors(null));
        assertEquals("", LegacyText.serialize(null));
        assertEquals(Component.empty(), LegacyText.component(null));
    }
}
