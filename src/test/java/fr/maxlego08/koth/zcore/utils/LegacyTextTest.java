package fr.maxlego08.koth.zcore.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LegacyTextTest {

    @Test
    void translatesLegacyAndHexColorsWithoutBungeeApi() {
        assertEquals("§aGreen §x§1§2§A§b§E§fHex", LegacyText.colorize("&aGreen #12AbEfHex"));
    }

    @Test
    void reversesAndStripsLegacyColors() {
        String colored = LegacyText.colorize("&lBold #12AbEfHex");

        assertEquals("&lBold #12AbEfHex", LegacyText.reverseColors(colored));
        assertEquals("Bold Hex", LegacyText.stripColors(colored));
    }
}
