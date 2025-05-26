package fr.maxlego08.koth.loader;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.api.Koth;
import fr.maxlego08.koth.api.KothLootType;
import fr.maxlego08.koth.api.KothType;
import fr.maxlego08.koth.api.discord.DiscordWebhookConfig;
import fr.maxlego08.koth.api.utils.HologramConfig;
import fr.maxlego08.koth.api.utils.RandomCommand;
import fr.maxlego08.koth.api.utils.ScoreboardConfiguration;
import fr.maxlego08.koth.zcore.utils.ProgressBar;
import fr.maxlego08.koth.zcore.utils.ZUtils;
import fr.maxlego08.koth.zcore.utils.loader.Loader;
import fr.maxlego08.koth.zcore.utils.nms.ItemStackUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KothLoader extends ZUtils implements Loader<Koth> {

    private final KothPlugin plugin;
    private final Loader<Location> locationLoader = new LocationLoader();
    private final Loader<ScoreboardConfiguration> scoreboardLoaderLoader = new ScoreboardLoader();
    private final Loader<HologramConfig> hologramConfigLoader = new HologramLoader();
    private final Loader<ProgressBar> progressBarLoader = new ProgressBarLoader();

    public KothLoader(KothPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Koth load(YamlConfiguration configuration, String path, File file) {

        String fileName = getFileNameWithoutExtension(file);
        KothType kothType;
        try {
            kothType = KothType.valueOf(configuration.getString("type", KothType.CAPTURE.name()).toUpperCase());
        } catch (IllegalArgumentException ignored) {
            kothType = KothType.CAPTURE;
        }

        String name = configuration.getString("name");
        int captureSeconds = configuration.getInt("capture");
        int cooldownStart = configuration.getInt("cooldownStart");
        int stopAfterSeconds = configuration.getInt("stopAfterSeconds");
        boolean enableStartCapMessage = configuration.getBoolean("enableStartCapMessage", true);
        boolean enableLooseCapMessage = configuration.getBoolean("enableLooseCapMessage", true);
        boolean enableEverySecondsCapMessage = configuration.getBoolean("enableEverySecondsCapMessage", false);
        boolean enableEverySecondsCooldownMessage = configuration.getBoolean("enableEverySecondsCooldownMessage", false);
        List<String> startCommands = configuration.getStringList("startCommands");
        List<String> endCommands = configuration.getStringList("endCommands");
        Location minLocation = locationLoader.load(configuration, "minLocation.", file);
        Location maxLocation = locationLoader.load(configuration, "maxLocation.", file);
        ScoreboardConfiguration cooldownScoreboard = scoreboardLoaderLoader.load(configuration, "scoreboard.cooldown.", file);
        ScoreboardConfiguration startScoreboard = scoreboardLoaderLoader.load(configuration, "scoreboard.start.", file);
        HologramConfig hologramConfig = hologramConfigLoader.load(configuration, "hologram.", file);

        DiscordWebhookConfig discordWebhookConfig = new DiscordWebhookConfig(configuration);

        KothLootType kothLootType;
        try {
            kothLootType = KothLootType.valueOf(configuration.getString("loot.type", KothLootType.NONE.name()).toUpperCase());
        } catch (IllegalArgumentException ignored) {
            kothLootType = KothLootType.NONE;
        }

        List<ItemStack> itemStacks = configuration.getStringList("loot.items").stream().map(ItemStackUtils::deserializeItemStack).collect(Collectors.toList());
        int randomItemStacks = configuration.getInt("loot.random", 0);

        List<String> blacklistTeamId = configuration.getStringList("blacklistTeamId");

        ProgressBar progressBar = progressBarLoader.load(configuration, "progressBar.", file);

        List<RandomCommand> randomCommands = new ArrayList<>();
        int maxRandomCommands = configuration.getInt("randomEndCommands.commandAmount", 0);
        List<?> list = configuration.getList("randomEndCommands.commands", null);
        if (list != null) {
            list.forEach(value -> {
                if (value instanceof Map<?, ?>) {
                    Map<?, ?> map = (Map<?, ?>) value;
                    randomCommands.add(new RandomCommand(((Number) map.get("percent")).intValue(), (List<String>) map.get("commands")));
                }
            });
        }

        return new ZKoth(this.plugin, fileName, kothType, name, captureSeconds, minLocation, maxLocation, startCommands, endCommands, cooldownScoreboard, startScoreboard, cooldownStart, stopAfterSeconds, enableStartCapMessage, enableLooseCapMessage, enableEverySecondsCapMessage, enableEverySecondsCooldownMessage, hologramConfig, itemStacks, kothLootType, discordWebhookConfig, randomItemStacks, blacklistTeamId, progressBar, randomCommands, maxRandomCommands);
    }

    @Override
    public void save(Koth koth, YamlConfiguration configuration, String path) {

        configuration.set("type", koth.getKothType().name());
        configuration.set("name", koth.getName());
        configuration.set("capture", koth.getCaptureSeconds());
        configuration.set("cooldownStart", koth.getCooldownStart());
        configuration.set("stopAfterSeconds", koth.getStopAfterSeconds());
        configuration.set("startCommands", koth.getStartCommands());
        configuration.set("endCommands", koth.getEndCommands());

        configuration.set("randomEndCommands.commandAmount", koth.getMaxRandomCommands());
        List<Map<String, Object>> endRandomCommands = new ArrayList<>();
        koth.getRandomCommands().forEach(randomCommand -> {
            Map<String, Object> map = new HashMap<>();
            map.put("percent", randomCommand.getPercent());
            map.put("commands", randomCommand.getCommands());
            endRandomCommands.add(map);
        });
        configuration.set("randomEndCommands.commands", endRandomCommands);

        configuration.set("enableStartCapMessage", koth.isEnableStartCapMessage());
        configuration.set("enableLooseCapMessage", koth.isEnableLooseCapMessage());
        configuration.set("enableEverySecondsCapMessage", koth.isEnableEverySecondsCapMessage());
        configuration.set("enableEverySecondsCooldownMessage", koth.isEnableEverySecondsCooldownMessage());
        locationLoader.save(koth.getMinLocation(), configuration, "minLocation.");
        locationLoader.save(koth.getMaxLocation(), configuration, "maxLocation.");
        scoreboardLoaderLoader.save(koth.getCooldownScoreboard(), configuration, "scoreboard.cooldown.");
        scoreboardLoaderLoader.save(koth.getStartScoreboard(), configuration, "scoreboard.start.");
        hologramConfigLoader.save(koth.getHologramConfig(), configuration, "hologram.");
        progressBarLoader.save(koth.getProgressBar(), configuration, "progressBar.");

        configuration.set("loot.type", koth.getLootType().name());
        configuration.set("loot.random", koth.getRandomItemStack());
        List<String> items = koth.getItemStacks().stream().map(ItemStackUtils::serializeItemStack).collect(Collectors.toList());
        configuration.set("loot.items", items);
    }
}
