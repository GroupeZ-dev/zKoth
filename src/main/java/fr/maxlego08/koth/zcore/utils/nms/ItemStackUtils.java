package fr.maxlego08.koth.zcore.utils.nms;

import fr.maxlego08.koth.zcore.logger.Logger;
import org.bukkit.inventory.ItemStack;

import java.util.Base64;

/**
 * Sérialisation des items en texte pour les fichiers de configuration.
 * <p>
 * Les items sont écrits au format {@code paper:<base64>} avec {@link ItemStack#serializeAsBytes()} : le
 * format NBT de Paper contient la version des données, ce qui permet au serveur de migrer les items lors
 * des mises à jour de Minecraft. Les anciens formats (sérialisation Bukkit de 1.20.5 à 1.21, NBT brut
 * avant 1.20.5) restent lisibles et sont réécrits au nouveau format à la prochaine sauvegarde.
 */
public final class ItemStackUtils {

    private static final String PAPER_PREFIX = "paper:";
    private static final String NULL_ITEM = "null";

    private ItemStackUtils() {
    }

    /**
     * Convertit un item en texte.
     *
     * @param itemStack l'item, peut être null
     * @return le texte {@code paper:<base64>}, ou {@code "null"} si l'item est null ou vide
     */
    public static String serializeItemStack(ItemStack itemStack) {
        if (itemStack == null || itemStack.isEmpty()) return NULL_ITEM;
        return PAPER_PREFIX + Base64.getEncoder().encodeToString(itemStack.serializeAsBytes());
    }

    /**
     * Convertit un texte en item, quel que soit le format avec lequel il a été sauvegardé.
     *
     * @param value le texte, peut être null
     * @return l'item, ou null si le texte est vide ou illisible (un avertissement est alors affiché)
     */
    public static ItemStack deserializeItemStack(String value) {
        if (value == null || value.isBlank() || value.equals(NULL_ITEM)) return null;

        try {
            if (value.startsWith(PAPER_PREFIX)) {
                return ItemStack.deserializeBytes(Base64.getDecoder().decode(value.substring(PAPER_PREFIX.length())));
            }
        } catch (Exception exception) {
            Logger.info("Unable to read an item: " + exception.getMessage(), Logger.LogType.ERROR);
            return null;
        }

        // Format utilisé de 1.20.5 à 1.21 (sérialisation Java de Bukkit).
        try {
            ItemStack itemStack = Base64ItemStack.decode(value);
            if (itemStack != null) return itemStack;
        } catch (Exception ignored) {
        }

        // Format NBT brut utilisé avant 1.20.5 : Paper migre l'item avec ses convertisseurs de données.
        try {
            ItemStack itemStack = ItemStack.deserializeBytes(fr.maxlego08.koth.zcore.utils.Base64.decode(value));
            Logger.info("An item saved with an old Minecraft version was migrated, check it in game and save it again.", Logger.LogType.WARNING);
            return itemStack;
        } catch (Exception exception) {
            Logger.info("Unable to read an item saved with an old Minecraft version, it will be ignored: " + exception.getMessage(), Logger.LogType.ERROR);
            return null;
        }
    }
}
