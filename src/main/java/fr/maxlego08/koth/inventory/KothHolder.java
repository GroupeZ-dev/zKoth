package fr.maxlego08.koth.inventory;

import fr.maxlego08.koth.api.Koth;
import fr.maxlego08.koth.zcore.utils.inventory.Pagination;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class KothHolder implements InventoryHolder {

    private final Koth koth;
    private final int page;
    private final Inventory inventory;

    public KothHolder(Koth koth, int page) {
        this.koth = koth;
        this.page = page;
        this.inventory = Bukkit.createInventory(this, 54, "§8Loot §7" + koth.getName());

        Pagination<ItemStack> pagination = new Pagination<>();
        List<ItemStack> itemStacks = new ArrayList<>(koth.getItemStacks());
        while (itemStacks.size() < 54 * page) {
            itemStacks.add(new ItemStack(Material.AIR));
        }

        int slot = 0;
        for (ItemStack itemStack : pagination.paginate(itemStacks, 54, page)) {
            inventory.setItem(slot++, itemStack);
        }
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    public Koth getKoth() {
        return koth;
    }

    public int getPage() {
        return page;
    }
}
