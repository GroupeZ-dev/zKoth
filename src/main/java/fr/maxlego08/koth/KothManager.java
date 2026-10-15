package fr.maxlego08.koth;

import fr.maxlego08.koth.api.Koth;
import fr.maxlego08.koth.api.KothStatus;
import fr.maxlego08.koth.api.KothTeam;
import fr.maxlego08.koth.api.KothType;
import fr.maxlego08.koth.api.events.KothCreateEvent;
import fr.maxlego08.koth.api.events.KothMoveEvent;
import fr.maxlego08.koth.hook.TeamPlugin;
import fr.maxlego08.koth.hook.teams.NoneHook;
import fr.maxlego08.koth.inventory.KothHolder;
import fr.maxlego08.koth.loader.KothLoader;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.logger.Logger;
import fr.maxlego08.koth.api.utils.Cuboid;
import fr.maxlego08.koth.zcore.utils.LegacyText;
import fr.maxlego08.koth.zcore.utils.ZUtils;
import fr.maxlego08.koth.zcore.utils.builder.ItemBuilder;
import fr.maxlego08.koth.zcore.utils.loader.Loader;
import fr.maxlego08.koth.zcore.utils.storage.Persist;
import fr.maxlego08.koth.zcore.utils.storage.Savable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class KothManager extends ZUtils implements Savable {

    private final Loader<Koth> kothLoader;
    private final List<Koth> koths = new ArrayList<>();
    private final Map<UUID, Selection> selections = new HashMap<>();
    private final KothPlugin plugin;
    private final File folder;
    private KothTeam kothTeam = new NoneHook();

    public KothManager(KothPlugin plugin) {
        this.plugin = plugin;
        this.kothLoader = new KothLoader(plugin);
        this.folder = new File(plugin.getDataFolder(), "koths");
        if (!this.folder.exists()) this.folder.mkdir();

        for (TeamPlugin value : TeamPlugin.values()) {
            if (value.isEnable()) {
                this.kothTeam = value.init(plugin);
                Logger.info("Register " + value.getPluginName() + " team implementation.", Logger.LogType.INFO);
            }
        }

        Bukkit.getPluginManager().registerEvents(this.kothTeam, plugin);
    }

    @Override
    public void save(Persist persist) {
        this.koths.forEach(Koth::stop);
        this.selections.values().forEach(Selection::clear);
    }

    @Override
    public void load(Persist persist) {

        this.koths.forEach(Koth::stop);

        File[] files = this.folder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) return;

        this.koths.clear();

        for (File file : files) {
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            Koth koth = this.kothLoader.load(configuration, "", file);
            this.koths.add(koth);
        }
        Logger.info("Load " + this.koths.size() + " koths");
    }

    public Optional<Selection> getSelection(UUID uuid) {
        return Optional.ofNullable(this.selections.getOrDefault(uuid, null));
    }

    public ItemStack getKothAxe() {
        ItemBuilder builder = new ItemBuilder(Material.STONE_AXE, Message.AXE_NAME.getMessage());
        Message.AXE_DESCRIPTION.getMessages().forEach(builder::addLine);
        return builder.build();
    }

    public void createSelection(UUID uniqueId, Selection selection) {
        this.selections.put(uniqueId, selection);
    }

    public void removeSelection(UUID uniqueId) {
        Selection selection = this.selections.remove(uniqueId);
        if (selection != null) {
            selection.clear();
        }
    }

    public void saveKoth(Koth koth) {

        File file = new File(this.folder, koth.getFileName() + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                Logger.info("Failed to create koth file " + file.getName() + ": " + exception.getMessage(), Logger.LogType.ERROR);
            }
        }

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        this.kothLoader.save(koth, configuration, "");
        try {
            configuration.save(file);
        } catch (IOException exception) {
            Logger.info("Failed to save koth file " + file.getName() + ": " + exception.getMessage(), Logger.LogType.ERROR);
        }
    }


    public void createKoth(Player player, String name, Location minLocation, Location maxLocation, int capture, KothType kothType) {

        String fileName = name.replace(" ", "_");
        Optional<Koth> optional = getKoth(fileName);
        if (optional.isPresent()) {
            message(player, Message.ALREADY_EXIST, "%name%", name);
            return;
        }

        int distanceX = Math.abs(minLocation.getBlockX() - maxLocation.getBlockX());
        int distanceZ = Math.abs(minLocation.getBlockZ() - maxLocation.getBlockZ());
        if (distanceX <= 0 || distanceZ <= 0) {
            message(player, Message.KOTH_SIZE);
            return;
        }

        Koth koth = new ZKoth(this.plugin, fileName, kothType, name, capture, minLocation, maxLocation);

        KothCreateEvent event = new KothCreateEvent(koth);
        event.call();

        if (event.isCancelled()) return;

        Optional<Selection> optionalSelection = getSelection(player.getUniqueId());
        optionalSelection.ifPresent(Selection::clear);

        this.koths.add(koth);
        message(player, Message.CREATE_SUCCESS, "%name%", name);

        this.saveKoth(koth);
    }

    public Optional<Koth> getKoth(String name) {
        return this.koths.stream().filter(koth -> name != null && koth.getFileName().equalsIgnoreCase(name)).findFirst();
    }

    public KothTeam getKothTeam() {
        return kothTeam;
    }

    public List<Koth> getActiveKoths() {
        return koths.stream().filter(koth -> koth.getStatus() == KothStatus.START).collect(Collectors.toList());
    }

    public List<Koth> getEnableKoths() {
        return koths.stream().filter(koth -> koth.getStatus() == KothStatus.COOLDOWN).collect(Collectors.toList());
    }

    public List<Koth> getStartKoths() {
        return koths.stream().filter(koth -> koth.getStatus() != KothStatus.STOP).collect(Collectors.toList());
    }

    public List<String> getNameKoths() {
        return this.koths.stream().map(Koth::getFileName).collect(Collectors.toList());
    }

    public void spawnKoth(CommandSender sender, String name, boolean now) {

        Optional<Koth> optional = getKoth(name);
        if (!optional.isPresent()) {
            message(sender, Message.DOESNT_EXIST, "%name%", name);
            return;
        }

        Koth koth = optional.get();
        koth.spawn(sender, now);
    }

    public void stopKoth(CommandSender sender, String name) {

        Optional<Koth> optional = getKoth(name);
        if (!optional.isPresent()) {
            message(sender, Message.DOESNT_EXIST, "%name%", name);
            return;
        }

        Koth koth = optional.get();
        koth.stop(sender);
    }

    public void moveKoth(Player player, Location maxLocation, Location minLocation, String name) {

        Optional<Koth> optional = getKoth(name);
        if (!optional.isPresent()) {
            message(player, Message.DOESNT_EXIST, "%name%", name);
            return;
        }

        Koth koth = optional.get();
        KothMoveEvent event = new KothMoveEvent(koth, maxLocation, minLocation);
        event.call();

        if (event.isCancelled()) return;

        Optional<Selection> optionalSelection = getSelection(player.getUniqueId());
        optionalSelection.ifPresent(Selection::clear);

        koth.move(minLocation, maxLocation);
        message(player, Message.MOVE_SUCCESS, "%name%", name);

        this.saveKoth(koth);
    }

    public List<Koth> getKoths() {
        return this.koths;
    }

    public void sendKothList(CommandSender sender) {

        if (koths.isEmpty()) {
            message(sender, Message.LIST_EMPTY);
            return;
        }

        if (sender instanceof ConsoleCommandSender) {

            String string = toList(koths.stream().map(Koth::getName).collect(Collectors.toList()), "§8", "§7");
            message(sender, Message.LIST_CONSOLE, "%koth%", string);

        } else {

            Player player = (Player) sender;
            message(player, Message.LIST_PLAYER);
            koths.forEach(koth -> buildKothMessage(player, koth));
        }
    }

    private void buildKothMessage(Player player, Koth koth) {

        Cuboid cuboid = koth.getCuboid();
        Location center = cuboid.getCenter();
        String location = center.getWorld().getName() + ", " + center.getBlockX() + ", " + center.getBlockY() + ", " + center.getBlockZ();
        Component component = LegacyText.component("§7§l» §f" + koth.getName() + " ")
                .hoverEvent(HoverEvent.showText(LegacyText.component("§7Location: §f" + location)))
                .append(action("§8(§aSpawn§8)", "/zkoth spawn " + koth.getName(), "§7Click for spawn koth"))
                .append(action(" §8(§eSpawn now§8)", "/zkoth now " + koth.getName(), "§7Click for spawn koth now"))
                .append(action(" §8(§cDelete§8)", "/zkoth delete " + koth.getName(), "§7Click for delete koth"));
        player.sendMessage(component);

    }

    private Component action(String text, String command, String hover) {
        return LegacyText.component(text)
                .clickEvent(ClickEvent.suggestCommand(command))
                .hoverEvent(HoverEvent.showText(LegacyText.component(hover)));
    }

    public void deleteKoth(CommandSender sender, String name) {

        Optional<Koth> optional = getKoth(name);
        if (!optional.isPresent()) {
            message(sender, Message.DOESNT_EXIST, "%name%", name);
            return;
        }

        Koth koth = optional.get();
        koths.remove(koth);
        message(sender, Message.DELETE_SUCCESS, "%name%", name);

        File file = new File(folder, koth.getFileName() + ".yml");
        file.delete();
    }

    public void updateLoots(Player player, String name, int page) {

        Optional<Koth> optional = getKoth(name);
        if (!optional.isPresent()) {
            message(player, Message.DOESNT_EXIST, "%name%", name);
            return;
        }

        Koth koth = optional.get();
        KothHolder kothHolder = new KothHolder(koth, page);
        kothHolder.open(player);
    }

    public void addCommand(CommandSender sender, String name, String type, StringBuilder command) {

        Optional<Koth> optional = getKoth(name);
        if (!optional.isPresent()) {
            message(sender, Message.DOESNT_EXIST, "%name%", name);
            return;
        }

        Koth koth = optional.get();

        if (type.equalsIgnoreCase("start")) koth.getStartCommands().add(command.toString());
        else koth.getEndCommands().add(command.toString());

        message(sender, Message.COMMAND_CREATE, "%command%", command);
        this.saveKoth(koth);
    }

    public List<String> getActiveNameKoths() {
        return this.getActiveKoths().stream().map(Koth::getFileName).collect(Collectors.toList());
    }
}
