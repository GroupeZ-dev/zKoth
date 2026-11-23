package fr.maxlego08.koth.zcore.utils.nms;

import fr.maxlego08.koth.zcore.utils.Base64;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

/**
 * Lecture des items sauvegardés avec la sérialisation Java de Bukkit (de 1.20.5 à 1.21).
 * Ce format n'est plus écrit : voir {@link ItemStackUtils}.
 *
 * @author sya-ri
 * Github: <a href="https://github.com/sya-ri/base64-itemstack/tree/master">https://github.com/sya-ri/base64-itemstack/tree/master</a>
 */
public final class Base64ItemStack {

    private Base64ItemStack() {
    }

    /**
     * Lit un item sauvegardé avec l'ancien format.
     *
     * @param data le texte en base64
     * @return l'item
     * @throws IOException si le texte n'est pas dans ce format
     * @throws ClassNotFoundException si le contenu n'est pas un item
     */
    // BukkitObjectInputStream est déprécié par Paper, il n'est utilisé ici que pour relire les anciennes données.
    @SuppressWarnings("deprecation")
    public static ItemStack decode(String data) throws IOException, ClassNotFoundException {
        byte[] bytes = Base64.decode(data);
        if (bytes == null) throw new IOException("Invalid base64");
        try (ObjectInputStream objectInputStream = new BukkitObjectInputStream(new GZIPInputStream(new ByteArrayInputStream(bytes)))) {
            return (ItemStack) objectInputStream.readObject();
        }
    }

}
