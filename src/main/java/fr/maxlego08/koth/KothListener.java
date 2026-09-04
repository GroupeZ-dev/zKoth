package fr.maxlego08.koth;

import fr.maxlego08.koth.api.Koth;
import fr.maxlego08.koth.board.Board;
import fr.maxlego08.koth.board.ColorBoard;
import fr.maxlego08.koth.inventory.KothHolder;
import fr.maxlego08.koth.listener.ListenerAdapter;
import fr.maxlego08.koth.save.Config;
import fr.maxlego08.koth.zcore.enums.Message;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class KothListener extends ListenerAdapter {

    private static final long SHULKER_REMOVE_DELAY_TICKS = 20L * 60L; // 1 minute in ticks

    private final KothPlugin plugin;
    private final KothManager manager;
    private final Board board = new ColorBoard();
    private long playerMoveEventCooldown = 0;

    public KothListener(KothPlugin plugin, KothManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    protected void onInteract(PlayerInteractEvent event, Player player) {

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack != null && event.getClickedBlock() != null && same(itemStack, Message.AXE_NAME.getMessage())) {

            event.setCancelled(true);

            if (event.getHand() == EquipmentSlot.OFF_HAND) return;

            Optional<Selection> optional = this.manager.getSelection(player.getUniqueId());
            Selection selection = null;

            if (!optional.isPresent()) {
                selection = new Selection();
                this.manager.createSelection(player.getUniqueId(), selection);
            } else {
                selection = optional.get();
            }

            Location location = event.getClickedBlock().getLocation();
            org.bukkit.event.block.Action action = event.getAction();

            LivingEntity entity = null;

            Shulker shulker = location.getWorld().spawn(location, Shulker.class);
            shulker.setInvulnerable(true);
            shulker.setAI(false);
            shulker.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 99999, 1, false, false));
            shulker.setInvisible(true);
            shulker.setCollidable(false);

            entity = shulker;

            Bukkit.getScheduler().runTaskLater(this.plugin, shulker::remove, SHULKER_REMOVE_DELAY_TICKS);

            selection.action(action, location, entity);

            this.board.addEntity(selection.isCorrect(), selection.getLeftEntity());
            this.board.addEntity(selection.isCorrect(), selection.getRightEntity());

            Message message = action.equals(org.bukkit.event.block.Action.LEFT_CLICK_BLOCK) ? Message.AXE_POS1 : Message.AXE_POS2;
            message(player, message, "%x%", String.valueOf(location.getBlockX()), "%y%", String.valueOf(location.getBlockY()), "%z%", String.valueOf(location.getBlockZ()), "%world%", location.getWorld().getName());

            if (selection.isValid()) {
                message(player, selection.isCorrect() ? Message.AXE_VALID : Message.AXE_ERROR);
            }
        }
    }

    @Override
    protected void onMove(PlayerMoveEvent event, Player player) {

        if (System.currentTimeMillis() > this.playerMoveEventCooldown) {

            this.playerMoveEventCooldown = System.currentTimeMillis() + Config.playerMoveEventCooldown;
            List<Koth> koths = this.manager.getActiveKoths();
            koths.forEach(koth -> koth.playerMove(player, this.manager.getKothTeam()));
        }
    }

    @Override
    protected void onQuit(PlayerQuitEvent event, Player player) {
        // Clean up player selection data
        this.manager.removeSelection(player.getUniqueId());
        // Clean up player scoreboard
        this.plugin.getScoreBoardManager().delete(player);
    }

    @Override
    protected void onInventoryClose(InventoryCloseEvent event, Player player) {
        Inventory inventory = event.getInventory();
        if (inventory.getHolder() instanceof KothHolder) {
            KothHolder kothHolder = (KothHolder) inventory.getHolder();
            Koth koth = kothHolder.getKoth();
            int page = kothHolder.getPage();

            List<ItemStack> itemStacks = new ArrayList<>(koth.getItemStacks());

            while (itemStacks.size() < 54 * page) {
                itemStacks.add(new ItemStack(Material.AIR));
            }

            for (int index = 0; index != 54; index++) {
                ItemStack itemStack = event.getInventory().getContents()[index];
                itemStacks.set(index + ((page - 1) * 54), itemStack);
            }

            itemStacks.removeIf(itemStack -> itemStack == null || itemStack.getType() == Material.AIR);
            koth.setItemStacks(itemStacks);
            message(player, Message.LOOT_CHANGE, "%name%", koth.getName());

            manager.saveKoth(koth);
        }
    }
}
