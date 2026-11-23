package fr.maxlego08.koth.zcore.utils.nms;

import fr.maxlego08.koth.zcore.utils.Base64;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

/**
 * @author sya-ri
 * Github: <a href="https://github.com/sya-ri/base64-itemstack/tree/master">https://github.com/sya-ri/base64-itemstack/tree/master</a>
 */
public class Base64ItemStack {

    private static final String PAPER_PREFIX = "paper:";

    public static String encode(ItemStack item) {
        return PAPER_PREFIX + Base64.encode(item.serializeAsBytes());
    }

    public static ItemStack decode(String data) {
        if (data.startsWith(PAPER_PREFIX)) {
            return ItemStack.deserializeBytes(Base64.decode(data.substring(PAPER_PREFIX.length())));
        }
        return decodeLegacy(data);
    }

    @SuppressWarnings("deprecation")
    private static ItemStack decodeLegacy(String data) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.decode(data));
            GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
            ObjectInputStream objectInputStream = new BukkitObjectInputStream(gzipInputStream);
            ItemStack item = (ItemStack) objectInputStream.readObject();
            objectInputStream.close();
            return item;
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
            return null;
        }
    }

}
