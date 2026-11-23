package fr.maxlego08.koth.zcore.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Conversion des textes legacy (codes {@code &}/{@code §} et couleurs hex {@code #RRGGBB})
 * vers les composants Adventure, sans passer par l'API Bungee ni par {@code org.bukkit.ChatColor}.
 * <p>
 * Les couleurs hex sont représentées au format Bukkit {@code §x§R§R§G§G§B§B}, ce qui permet
 * un aller-retour texte → composant → texte sans perte de couleur.
 */
public final class LegacyText {

    /**
     * Sérialiseur {@code §} qui conserve les couleurs hex au format {@code §x§R§R§G§G§B§B}.
     */
    private static final LegacyComponentSerializer SECTION = LegacyComponentSerializer.builder()
            .character(LegacyComponentSerializer.SECTION_CHAR)
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    private static final Pattern HEX = Pattern.compile("#([a-fA-F0-9]{6})");
    private static final Pattern AMPERSAND_CODE = Pattern.compile("(?i)&([0-9A-FK-ORX])");
    private static final Pattern SECTION_HEX = Pattern.compile("(?i)§x((?:§[0-9A-F]){6})");
    private static final Pattern ANY_HEX = Pattern.compile("(?i)[§&]x(?:[§&][0-9A-F]){6}");
    private static final Pattern ANY_CODE = Pattern.compile("(?i)[§&][0-9A-FK-OR]");

    private LegacyText() {
    }

    /**
     * Convertit un texte contenant des codes {@code §} en composant.
     *
     * @param legacyText le texte, peut être null
     * @return le composant correspondant (vide si le texte est null)
     */
    public static Component component(String legacyText) {
        return SECTION.deserialize(legacyText == null ? "" : legacyText);
    }

    /**
     * Convertit un texte en composant pour un nom ou une lore d'item. L'italique est désactivé par défaut,
     * comme le faisait {@code ItemMeta#setDisplayName(String)} : sans cela, Minecraft affiche les noms et
     * les lores personnalisés en italique.
     *
     * @param legacyText le texte, peut être null
     * @return le composant correspondant
     */
    public static Component itemComponent(String legacyText) {
        return component(legacyText).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    }

    /**
     * Normalise un texte coloré en le faisant passer par un composant, pour comparer deux textes
     * qui s'affichent de la même façon (ex : {@code §l§a} et {@code §a}).
     *
     * @param legacyText le texte, peut être null
     * @return le texte normalisé
     */
    public static String normalize(String legacyText) {
        return serialize(component(legacyText));
    }

    /**
     * Convertit un composant en texte avec des codes {@code §}, en conservant les couleurs hex.
     *
     * @param component le composant, peut être null
     * @return le texte correspondant (vide si le composant est null)
     */
    public static String serialize(Component component) {
        return component == null ? "" : SECTION.serialize(component);
    }

    /**
     * Traduit uniquement les codes {@code &} (y compris {@code &x}) en codes {@code §},
     * comme le faisait {@code ChatColor.translateAlternateColorCodes('&', text)}.
     *
     * @param text le texte, peut être null
     * @return le texte traduit
     */
    public static String translateAmpersand(String text) {
        if (text == null) return null;
        return AMPERSAND_CODE.matcher(text).replaceAll("§$1");
    }

    /**
     * Traduit les couleurs hex {@code #RRGGBB} puis les codes {@code &} en codes {@code §}.
     *
     * @param text le texte, peut être null
     * @return le texte coloré
     */
    public static String colorize(String text) {
        if (text == null) return null;
        Matcher matcher = HEX.matcher(text);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            StringBuilder legacy = new StringBuilder("§x");
            for (char character : matcher.group(1).toCharArray()) legacy.append('§').append(character);
            matcher.appendReplacement(result, Matcher.quoteReplacement(legacy.toString()));
        }
        matcher.appendTail(result);
        return translateAmpersand(result.toString());
    }

    /**
     * Opération inverse de {@link #colorize(String)} : {@code §x§R§R§G§G§B§B} devient {@code #RRGGBB}
     * et les autres codes {@code §} deviennent des codes {@code &}.
     *
     * @param text le texte, peut être null
     * @return le texte avec des codes lisibles dans une configuration
     */
    public static String reverseColors(String text) {
        if (text == null) return null;
        Matcher matcher = SECTION_HEX.matcher(text);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(result, Matcher.quoteReplacement("#" + matcher.group(1).replace("§", "")));
        }
        matcher.appendTail(result);
        return result.toString().replace('§', '&');
    }

    /**
     * Supprime tous les codes couleur et de formatage ({@code §} ou {@code &}, hex compris).
     *
     * @param text le texte, peut être null
     * @return le texte sans couleur
     */
    public static String stripColors(String text) {
        if (text == null) return null;
        return ANY_CODE.matcher(ANY_HEX.matcher(text).replaceAll("")).replaceAll("");
    }
}
