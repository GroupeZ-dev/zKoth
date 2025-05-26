package fr.maxlego08.koth.zcore.utils;

import java.util.HashMap;
import java.util.Map;

public class ColorTransformer {

    private static final Map<String, String> colorMap = new HashMap<>();

    static {
        colorMap.put("black", "§0");
        colorMap.put("dark_blue", "§1");
        colorMap.put("dark_green", "§2");
        colorMap.put("dark_aqua", "§3");
        colorMap.put("dark_red", "§4");
        colorMap.put("dark_purple", "§5");
        colorMap.put("gold", "§6");
        colorMap.put("gray", "§7");
        colorMap.put("dark_gray", "§8");
        colorMap.put("blue", "§9");
        colorMap.put("green", "§a");
        colorMap.put("aqua", "§b");
        colorMap.put("red", "§c");
        colorMap.put("light_purple", "§d");
        colorMap.put("yellow", "§e");
        colorMap.put("white", "§f");

        colorMap.put("obfuscated", "§k");
        colorMap.put("bold", "§l");
        colorMap.put("strikethrough", "§m");
        colorMap.put("underline", "§n");
        colorMap.put("italic", "§o");
        colorMap.put("reset", "§r");
    }

    public static String transformColors(String input) {
        for (Map.Entry<String, String> entry : colorMap.entrySet()) {
            String colorTag = "<" + entry.getKey() + ">";
            String colorCode = entry.getValue();
            input = input.replace(colorTag, colorCode);
        }
        return input;
    }
}
