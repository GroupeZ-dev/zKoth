package fr.maxlego08.koth.zcore.utils.nms;

import org.bukkit.inventory.ItemStack;

public class ItemStackUtils {

    /**
     * Change {@link ItemStack} to {@link String}
     *
     * @return {@link String}
     */
    public static String serializeItemStack(ItemStack paramItemStack) {

        return paramItemStack == null ? "null" : Base64ItemStack.encode(paramItemStack);
    }

    /**
     * Change {@link String} to {@link ItemStack}
     *
     * @return {@link ItemStack}
     */
    public static ItemStack deserializeItemStack(String paramString) {

        if (paramString == null || paramString.equals("null")) {
            return null;
        }

        return Base64ItemStack.decode(paramString);
    }
}
