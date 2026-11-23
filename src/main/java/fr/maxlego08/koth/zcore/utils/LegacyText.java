package fr.maxlego08.koth.zcore.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LegacyText {

    private static final LegacyComponentSerializer SECTION = LegacyComponentSerializer.legacySection();
    private static final Pattern HEX = Pattern.compile("#([a-fA-F0-9]{6})");
    private static final Pattern AMPERSAND_CODE = Pattern.compile("(?i)&([0-9A-FK-OR])");
    private static final Pattern SECTION_HEX = Pattern.compile("(?i)§x((?:§[0-9A-F]){6})");

    private LegacyText() {
    }

    public static Component component(String legacyText) {
        return SECTION.deserialize(legacyText == null ? "" : legacyText);
    }

    public static String serialize(Component component) {
        return component == null ? "" : SECTION.serialize(component);
    }

    public static String colorize(String text) {
        if (text == null) return null;
        Matcher matcher = HEX.matcher(text);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String hex = matcher.group(1);
            StringBuilder legacy = new StringBuilder("§x");
            for (char character : hex.toCharArray()) legacy.append('§').append(character);
            matcher.appendReplacement(result, Matcher.quoteReplacement(legacy.toString()));
        }
        matcher.appendTail(result);
        return AMPERSAND_CODE.matcher(result.toString()).replaceAll("§$1");
    }

    public static String reverseColors(String text) {
        if (text == null) return null;
        Matcher matcher = SECTION_HEX.matcher(text);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String hex = matcher.group(1).replace("§", "");
            matcher.appendReplacement(result, Matcher.quoteReplacement("#" + hex));
        }
        matcher.appendTail(result);
        return result.toString().replace('§', '&');
    }

    public static String stripColors(String text) {
        if (text == null) return null;
        return text.replaceAll("(?i)[§&]x(?:[§&][0-9A-F]){6}", "")
                .replaceAll("(?i)[§&][0-9A-FK-OR]", "");
    }
}
