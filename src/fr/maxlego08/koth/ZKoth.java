package fr.maxlego08.koth;

import fr.maxlego08.koth.api.Koth;
import fr.maxlego08.koth.api.KothEvent;
import fr.maxlego08.koth.api.KothLootType;
import fr.maxlego08.koth.api.KothStatus;
import fr.maxlego08.koth.api.KothTeam;
import fr.maxlego08.koth.api.KothType;
import fr.maxlego08.koth.api.discord.DiscordWebhookConfig;
import fr.maxlego08.koth.api.events.KothCapEvent;
import fr.maxlego08.koth.api.events.KothCatchEvent;
import fr.maxlego08.koth.api.events.KothLooseEvent;
import fr.maxlego08.koth.api.events.KothSpawnEvent;
import fr.maxlego08.koth.api.events.KothStartEvent;
import fr.maxlego08.koth.api.events.KothStopEvent;
import fr.maxlego08.koth.api.events.KothWinEvent;
import fr.maxlego08.koth.api.utils.HologramConfig;
import fr.maxlego08.koth.api.utils.PlayerResult;
import fr.maxlego08.koth.api.utils.RandomCommand;
import fr.maxlego08.koth.api.utils.ScoreboardConfiguration;
import fr.maxlego08.koth.hook.teams.NoneHook;
import fr.maxlego08.koth.save.Config;
import fr.maxlego08.koth.scoreboard.ScoreBoardManager;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.logger.Logger;
import fr.maxlego08.koth.zcore.utils.Cuboid;
import fr.maxlego08.koth.zcore.utils.ProgressBar;
import fr.maxlego08.koth.zcore.utils.ZUtils;
import fr.maxlego08.koth.zcore.utils.builder.TimerBuilder;
import fr.maxlego08.koth.zcore.utils.interfaces.CollectionConsumer;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ZKoth extends ZUtils implements Koth {

    private final KothPlugin plugin;
    private final String fileName;
    private final KothType kothType;
    private final ScoreboardConfiguration cooldownScoreboard;
    private final ScoreboardConfiguration startScoreboard;
    private final int cooldownStart;
    private final int stopAfterSeconds;
    private final int randomItemStacks;
    private final int scoreboardRadius;
    private final Map<UUID, Integer> playersValues = new HashMap<>();
    private final boolean enableStartCapMessage;
    private final boolean enableLooseCapMessage;
    private final boolean enableEverySecondsCapMessage;
    private final boolean enableEverySecondsCooldownMessage;
    private final HologramConfig hologramConfig;
    private final KothLootType kothLootType;
    private final List<String> blacklistTeamId;
    private final ProgressBar progressBar;
    private final List<RandomCommand> randomCommands;
    private final int maxRandomCommands;
    private final DiscordWebhookConfig discordWebhookConfig;
    private List<ItemStack> itemStacks;
    private String name;
    private int captureSeconds;
    private Location minLocation;
    private Location maxLocation;
    private List<String> startCommands = new ArrayList<>();
    private List<String> endCommands = new ArrayList<>();
    private KothStatus kothStatus = KothStatus.STOP;
    private KothTeam kothTeam = new NoneHook();
    private Player currentPlayer;
    private AtomicInteger remainingSeconds;
    private TimerTask timerTask;
    private TimerTask timerTaskStop;
    private List<PlayerResult> playerResults = new ArrayList<>();

    public ZKoth(KothPlugin plugin, String fileName, KothType kothType, String name, int captureSeconds, Location minLocation, Location maxLocation, List<String> startCommands, List<String> endCommands, ScoreboardConfiguration cooldownScoreboard, ScoreboardConfiguration startScoreboard, int cooldownStart, int stopAfterSeconds, boolean enableStartCapMessage, boolean enableLooseCapMessage, boolean enableEverySecondsCapMessage, boolean enableEverySecondsCooldownMessage, HologramConfig hologramConfig, List<ItemStack> itemStacks, KothLootType kothLootType, DiscordWebhookConfig discordWebhookConfig, int randomItemStacks, List<String> blacklistTeamId, ProgressBar progressBar, List<RandomCommand> randomCommands, int maxRandomCommands) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.kothType = kothType;
        this.name = name;
        this.captureSeconds = captureSeconds;
        this.minLocation = minLocation;
        this.maxLocation = maxLocation;
        this.startCommands = startCommands;
        this.endCommands = endCommands;
        this.startScoreboard = startScoreboard;
        this.cooldownScoreboard = cooldownScoreboard;
        this.cooldownStart = cooldownStart;
        this.stopAfterSeconds = stopAfterSeconds;
        this.enableStartCapMessage = enableStartCapMessage;
        this.enableLooseCapMessage = enableLooseCapMessage;
        this.enableEverySecondsCapMessage = enableEverySecondsCapMessage;
        this.enableEverySecondsCooldownMessage = enableEverySecondsCooldownMessage;
        this.hologramConfig = hologramConfig;
        this.itemStacks = itemStacks;
        this.kothLootType = kothLootType;
        this.discordWebhookConfig = discordWebhookConfig;
        this.randomItemStacks = randomItemStacks;
        this.blacklistTeamId = blacklistTeamId;
        this.progressBar = progressBar;
        this.randomCommands = randomCommands;
        this.maxRandomCommands = maxRandomCommands;
        
        this.scoreboardRadius = plugin.getConfig().getInt("scoreboardRadius", 50);
    }

    public ZKoth(KothPlugin plugin, String fileName, KothType kothType, String name, int captureSeconds, Location minLocation, Location maxLocation) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.kothType = kothType;
        this.name = name;
        this.captureSeconds = captureSeconds;
        this.minLocation = minLocation;
        this.maxLocation = maxLocation;
        this.startScoreboard = ScoreboardConfiguration.start();
        this.cooldownScoreboard = ScoreboardConfiguration.cooldown();
        this.cooldownStart = 300;
        this.stopAfterSeconds = 3600;
        this.enableStartCapMessage = true;
        this.enableLooseCapMessage = true;
        this.enableEverySecondsCapMessage = false;
        this.enableEverySecondsCooldownMessage = false;
        this.hologramConfig = new HologramConfig(false, new ArrayList<>(), getCenter());
        this.discordWebhookConfig = null;
        this.itemStacks = new ArrayList<>();
        this.kothLootType = KothLootType.NONE;
        this.randomItemStacks = 0;
        this.blacklistTeamId = new ArrayList<>();
        this.progressBar = new ProgressBar(10, '-', "&a", "&7");
        this.randomCommands = new ArrayList<>();
        this.maxRandomCommands = 0;
        
        this.scoreboardRadius = plugin.getConfig().getInt("scoreboardRadius", 50);
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public KothType getKothType() {
        return this.kothType;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Location getMinLocation() {
        return this.minLocation;
    }

    @Override
    public Location getMaxLocation() {
        return this.maxLocation;
    }

    @Override
    public Cuboid getCuboid() {
        return new Cuboid(this.maxLocation, this.minLocation);
    }

    @Override
    public boolean isEnableStartCapMessage() {
        return this.enableStartCapMessage;
    }

    @Override
    public boolean isEnableLooseCapMessage() {
        return this.enableLooseCapMessage;
    }

    @Override
    public boolean isEnableEverySecondsCapMessage() {
        return this.enableEverySecondsCapMessage;
    }

    @Override
    public int getStopAfterSeconds() {
        return this.stopAfterSeconds;
    }

    @Override
    public Location getCenter() {
        Cuboid cuboid = getCuboid();
        return cuboid.getCenter();
    }

    @Override
    public List<String> getStartCommands() {
        return this.startCommands;
    }

    @Override
    public List<String> getEndCommands() {
        return this.endCommands;
    }

    @Override
    public void move(Location minLocation, Location maxLocation) {
        this.minLocation = minLocation;
        this.maxLocation = maxLocation;
    }

    @Override
    public int getCaptureSeconds() {
        return captureSeconds;
    }

    @Override
    public void setCaptureSeconds(int captureSeconds) {
        this.captureSeconds = captureSeconds;
    }

    @Override
    public ScoreboardConfiguration getCooldownScoreboard() {
        return this.cooldownScoreboard;
    }

    @Override
    public ScoreboardConfiguration getStartScoreboard() {
        return this.startScoreboard;
    }

    @Override
    public KothStatus getStatus() {
        return this.kothStatus;
    }

    @Override
    public void spawn(CommandSender sender, boolean now) {

        if (this.minLocation == null || this.maxLocation == null) {
            message(sender, Message.SPAWN_ERROR);
        } else if (this.kothStatus == KothStatus.COOLDOWN) {
            message(sender, Message.SPAWN_COOLDOWN);
        } else if (this.kothStatus == KothStatus.START) {
            message(sender, Message.SPAWN_ALREADY);
        } else {
            if (now) spawnNow();
            else spawn();
        }

    }

    @Override
    public void spawn(boolean now) {
        if (this.minLocation == null || this.maxLocation == null || this.kothStatus != KothStatus.STOP) return;
        if (now) spawnNow();
        else spawn();
    }

    @Override
    public void stop() {

        if (this.kothStatus == KothStatus.STOP) return;

        KothStopEvent event = new KothStopEvent(this);
        event.call();

        if (event.isCancelled()) return;
        if (this.discordWebhookConfig != null) this.discordWebhookConfig.send(this.plugin, this, KothEvent.STOP);

        broadcast(Message.EVENT_STOP);

        if (this.timerTask != null) {
            this.timerTask.cancel();
        }

        this.kothStatus = KothStatus.STOP;
        this.currentPlayer = null;
        this.timerTask = null;
        this.remainingSeconds = null;
        this.playersValues.clear();
        this.plugin.getScoreBoardManager().clearBoard();
        // this.resetBlocks();
        if (this.timerTaskStop != null) this.timerTaskStop.cancel();

        this.plugin.getKothHologram().end(this);
    }

    @Override
    public void stop(CommandSender sender) {

        if (this.kothStatus == KothStatus.STOP) {
            message(sender, Message.EVENT_DISABLE);
            return;
        }

        this.stop();
    }

    private void spawn() {
        this.resetData();

        this.kothStatus = KothStatus.COOLDOWN;
        this.remainingSeconds = new AtomicInteger(this.cooldownStart);

        KothStartEvent event = new KothStartEvent(this);
        event.call();

        if (event.isCancelled()) return;

        if (this.discordWebhookConfig != null) this.discordWebhookConfig.send(this.plugin, this, KothEvent.START);

        if (this.cooldownScoreboard.isEnable()) {
            ScoreBoardManager scoreBoardManager = this.plugin.getScoreBoardManager();
            scoreBoardManager.setLinesAndSchedule(onScoreboard());
            for (Player player : Bukkit.getOnlinePlayers()) {
                scoreBoardManager.createBoard(player, color(this.cooldownScoreboard.getTitle()));
            }
        }

        this.timerTask = scheduleFix(this.plugin, 0, Config.enableDebug ? 10 : 1000, (task, isCancelled) -> {

            if (!isCancelled) {
                task.cancel();
                return;
            }

            if (this.kothStatus != KothStatus.COOLDOWN) {
                task.cancel();
                return;
            }

            int currentRemainingSeconds = this.remainingSeconds.get();

            if (Config.displayMessageCooldown.contains(currentRemainingSeconds)) {
                broadcast(Message.EVENT_COOLDOWN);
            }
            if (this.enableEverySecondsCapMessage) {
                broadcast(Message.EVENT_COOLDOWN_EVERYSECONDS);
            }

            if (currentRemainingSeconds <= 0) {
                this.timerTask.cancel();
                this.spawnNow();
                return;
            }

            this.plugin.getScoreBoardManager().update();
            this.remainingSeconds.decrementAndGet();
        });
    }

    private void spawnNow() {

        this.resetData();
        this.kothStatus = KothStatus.START;

        KothSpawnEvent event = new KothSpawnEvent(this);
        event.call();

        if (event.isCancelled()) return;
        if (this.discordWebhookConfig != null) this.discordWebhookConfig.send(this.plugin, this, KothEvent.SPAWN);

        this.remainingSeconds = new AtomicInteger(this.captureSeconds);

        broadcast(Message.EVENT_START);

        ScoreBoardManager scoreBoardManager = this.plugin.getScoreBoardManager();
        if (!this.cooldownScoreboard.isEnable() && this.startScoreboard.isEnable()) {
            scoreBoardManager.setLinesAndSchedule(onScoreboard());
            for (Player player : Bukkit.getOnlinePlayers()) {
                scoreBoardManager.createBoard(player, color(this.startScoreboard.getTitle()));
            }
        } else if (this.cooldownScoreboard.isEnable() && this.startScoreboard.isEnable()) {
            if (scoreBoardManager.getBoards().isEmpty()) {
                scoreBoardManager.setLinesAndSchedule(onScoreboard());
                for (Player player : Bukkit.getOnlinePlayers()) {
                    scoreBoardManager.createBoard(player, color(this.startScoreboard.getTitle()));
                }
            } else {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    FastBoard board = scoreBoardManager.getBoard(player);
                    if (board == null) scoreBoardManager.createBoard(player, color(this.startScoreboard.getTitle()));
                    else board.updateTitle(color(this.startScoreboard.getTitle()));
                }
            }
        }

        // this.changeBlocks(Config.noOneCapturingMaterial, true);

        /*if (this.kothType == KothType.POINT_COUNT) {
            this.startschedule();
        }*/

        Koth koth = this;
        Timer timer = new Timer();
        this.timerTaskStop = new TimerTask() {
            @Override
            public void run() {
                plugin.getKothHologram().end(koth);
                Bukkit.getScheduler().runTask(plugin, () -> stop(Bukkit.getConsoleSender()));
            }
        };
        timer.schedule(this.timerTaskStop, this.stopAfterSeconds * 1000L);

        this.plugin.getKothHologram().start(this);

        /*if (Config.discordWebhookConfig != null) {
            Config.discordWebhookConfig.send(this.plugin, this);
        }*/

        this.startCommands.forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replaceKothInformations(command)));
    }

    private void resetData() {
        this.playersValues.clear();
        this.currentPlayer = null;
    }

    @Override
    public void playerMove(Player player, KothTeam kothTeam) {

        if (this.kothStatus != KothStatus.START) return;

        this.kothTeam = kothTeam;

        if (this.blacklistTeamId.contains(this.kothTeam.getTeamId(player))) return;

        Cuboid cuboid = this.getCuboid();
        
        Location pLoc = player.getLocation();
        if (pLoc.distanceSquared(this.getCenter()) > Math.pow(scoreboardRadius, 2)) {
            plugin.getScoreBoardManager().delete(player);
            plugin.getScoreBoardManager().getBoards().remove(player);
        } else {
            plugin.getScoreBoardManager().createBoard(player, color(startScoreboard.getTitle()));
        }

        if (this.currentPlayer == null && cuboid.contains(player.getLocation())) {

            this.currentPlayer = player;
            this.startCap(player);
            updateDisplay();

        } else if (this.currentPlayer != null && !cuboid.contains(this.currentPlayer.getLocation())) {

            KothLooseEvent event = new KothLooseEvent(this.currentPlayer, this);
            event.call();

            if (this.discordWebhookConfig != null) this.discordWebhookConfig.send(this.plugin, this, KothEvent.LOOSE);

            if (event.isCancelled()) return;

            broadcast(Message.EVENT_LOOSE);

            if (this.timerTask != null) {
                this.timerTask.cancel();
            }

            // this.changeBlocks(Config.noOneCapturingMaterial, true);
            this.remainingSeconds = new AtomicInteger(this.captureSeconds);
            this.timerTask = null;
            this.currentPlayer = null;

            updateDisplay();
        }
    }

    @Override
    public int getCooldownStart() {
        return this.cooldownStart;
    }

    private void startCap(Player player) {

        if (this.currentPlayer == null) return;

        KothCatchEvent event = new KothCatchEvent(this, player, this.captureSeconds);
        event.call();

        if (event.isCancelled()) {
            this.currentPlayer = null;
            return;
        }

        if (this.discordWebhookConfig != null) this.discordWebhookConfig.send(this.plugin, this, KothEvent.START_CAP);

        if (enableStartCapMessage) {
            broadcast(Message.EVENT_CATCH);
        }

        int captureSeconds = event.getCaptureSeconds();
        captureSeconds = captureSeconds < 0 ? 30 : captureSeconds;
        this.remainingSeconds = new AtomicInteger(captureSeconds);
        Cuboid cuboid = getCuboid();

        // this.changeBlocks(Config.onePersonneCapturingMaterial, false);
        updateDisplay();

        scheduleFix(this.plugin, 0, 1000, (task, isCancelled) -> {

            this.timerTask = task;

            if (!isCancelled) {
                task.cancel();
                return;
            }

            if (this.kothStatus != KothStatus.START) {
                task.cancel();
                return;
            }

            int currentRemainingSeconds = this.remainingSeconds.get();

            if (this.currentPlayer != null) {
                if (!this.currentPlayer.isValid() || !this.currentPlayer.isOnline() || !cuboid.contains(this.currentPlayer.getLocation())) {
                    this.currentPlayer = null;
                    updateDisplay();
                }
            }

            if (this.currentPlayer == null) {

                KothLooseEvent kothLooseEvent = new KothLooseEvent(null, this);
                kothLooseEvent.call();

                if (kothLooseEvent.isCancelled()) return;

                if (this.discordWebhookConfig != null)
                    this.discordWebhookConfig.send(this.plugin, this, KothEvent.LOOSE);

                if (this.timerTask != null) {
                    this.timerTask.cancel();
                }

                // this.changeBlocks(Config.noOneCapturingMaterial, true);
                this.timerTask = null;
                this.currentPlayer = null;
                this.remainingSeconds = new AtomicInteger(this.captureSeconds);

                if (enableLooseCapMessage) {
                    broadcast(Message.EVENT_LOOSE);
                }

                updateDisplay();
                return;

            }

            if (this.hasWin()) {

                this.endKoth(task, cuboid, player);

            } else {

                KothCapEvent capEvent = new KothCapEvent(this, player, this.remainingSeconds.get(), this.kothTeam.getTeamName(player));
                capEvent.call();

                if (this.enableEverySecondsCapMessage) {
                    broadcast(Message.EVENT_EVERYSECONDS);
                }

                if (Config.displayMessageKothCap.contains(currentRemainingSeconds)) {
                    broadcast(Message.EVENT_TIMER);
                }

                updateDisplay();

                switch (this.kothType) {
                    case SCORE:
                        // case TIMER:
                        this.playersValues.put(this.currentPlayer.getUniqueId(), this.getValue(this.currentPlayer) + 1);
                        this.playerResults.clear(); // Clear cache
                        break;
                    case CAPTURE:
                        this.remainingSeconds.decrementAndGet();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void dispatchCommand(String command, Player player) {
        if (command.contains("%online-player%")) {
            for (Player cPlayer : this.kothTeam.getOnlinePlayer(player)) {
                String finaleCommand = replaceMessage(command);
                finaleCommand = finaleCommand.replace("%online-player%", cPlayer.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), papi(finaleCommand, cPlayer));
            }
        } else {
            command = replaceMessage(command);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), papi(command, player));
        }
    }

    public void endKoth(TimerTask task, Cuboid cuboid, Player player) {

        KothWinEvent kothWinEvent = new KothWinEvent(this, this.currentPlayer);
        kothWinEvent.call();

        if (kothWinEvent.isCancelled()) return;

        if (this.discordWebhookConfig != null) this.discordWebhookConfig.send(this.plugin, this, KothEvent.WIN);

        task.cancel();
        broadcast(Message.EVENT_WIN);

        this.plugin.getKothHologram().end(this);
        this.plugin.getScoreBoardManager().clearBoard();

        this.endCommands.forEach(command -> dispatchCommand(command, player));
        if (this.maxRandomCommands != 0 && !this.randomCommands.isEmpty()) {
            int executedCommands = 0;
            while (executedCommands < maxRandomCommands) {
                RandomCommand randomCommand = randomElement(this.randomCommands);
                if (randomCommand.canUse()) {
                    randomCommand.getCommands().forEach(command -> dispatchCommand(command, player));
                    executedCommands++;
                }
            }
        }

        Location center = cuboid.getCenter();
        World world = center.getWorld();
        while (center.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.AIR)) {
            center = center.getBlock().getRelative(BlockFace.DOWN).getLocation();
        }
        Location location = center.clone();

        if (!this.itemStacks.isEmpty()) {
            switch (this.kothLootType) {
                case CHEST:
                    location.getBlock().setType(Material.CHEST);
                    Chest chest = (Chest) location.getBlock().getState();

                    this.getRandomItemStacks().forEach(itemStack -> chest.getInventory().addItem(itemStack));

                    Bukkit.getScheduler().runTaskLater(this.plugin, () -> location.getBlock().setType(Material.AIR), 20L * Config.removeChestSec);
                    break;
                case DROP:
                    location.add(0.5, 0.3, 0.5);
                    this.getRandomItemStacks().forEach(itemStack -> {
                        Item item = world.dropItem(location, itemStack);
                        Vector vector = item.getVelocity();
                        vector.setZ(0);
                        vector.setY(0.5);
                        vector.setX(0);
                        item.setVelocity(vector);
                    });
                    break;
                case INVENTORY:
                    this.getRandomItemStacks().forEach(itemStack -> give(this.currentPlayer, itemStack));
                    break;
                default:
                    break;
            }
        }

        this.kothStatus = KothStatus.STOP;
        this.currentPlayer = null;
        this.timerTask = null;
        this.remainingSeconds = null;
        this.playersValues.clear();
        // this.resetBlocks();
        if (this.timerTaskStop != null) this.timerTaskStop.cancel();
    }

    public int getValue(Player player) {
        return player == null ? 0 : this.playersValues.getOrDefault(player.getUniqueId(), 0);
    }

    public boolean hasWin() {

        switch (this.kothType) {
            case CAPTURE:
                return this.remainingSeconds != null && this.remainingSeconds.get() <= 0;
            case SCORE:
                return this.currentPlayer != null && this.getValue(this.currentPlayer) >= this.captureSeconds;
            /*case TIMER:
                return this.currentPlayer == null ? false : this.getValue(this.currentPlayer) >= this.maxSecondsCap;*/
            default:
                return false;
        }
    }

    private void broadcast(Message message) {

        switch (message.getType()) {
            case ACTION: {
                if (message.getMessage() == null) {
                    Logger.info(message.name() + " is null, check your config plz !", Logger.LogType.ERROR);
                    return;
                }
                String realMessage = replaceMessage(message.getMessage());
                this.broadcastAction(realMessage);
                break;
            }
            case CENTER: {
                if (message.getMessages().isEmpty()) {
                    String realMessage = replaceMessage(message.getMessage());
                    broadcastCenterMessage(Collections.singletonList(realMessage));
                } else {
                    broadcastCenterMessage(message.getMessages().stream().map(this::replaceMessage).collect(Collectors.toList()));
                }
                break;
            }
            case TCHAT: {
                if (message.getMessages().isEmpty()) this.broadcast(replaceMessage(message.getMessage()));
                else message.getMessages().forEach(m -> this.broadcast(replaceMessage(m)));
                break;
            }
            case TITLE: {
                String title = replaceMessage(message.getTitle());
                String subTitle = replaceMessage(message.getSubTitle());
                int fadeInTime = message.getStart();
                int showTime = message.getTime();
                int fadeOutTime = message.getEnd();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    this.title(player, title, subTitle, fadeInTime, showTime, fadeOutTime);
                }
                break;
            }
            case NONE:
            default:
                break;
        }
    }

    @Override
    public String replaceMessage(String string) {

        if (string == null) return null;

        string = string.replace("%playerName%", this.currentPlayer != null ? this.currentPlayer.getName() : Config.noPlayer);
        string = string.replace("%teamName%", this.currentPlayer != null ? this.kothTeam.getTeamName(this.currentPlayer) : Config.noFaction);
        string = string.replace("%teamId%", this.currentPlayer != null ? this.kothTeam.getTeamId(this.currentPlayer) : Config.noFaction);
        string = string.replace("%teamLeader%", this.currentPlayer != null ? this.kothTeam.getTeamName(this.currentPlayer) : Config.noFaction);

        if (kothStatus == KothStatus.START) {
            int seconds = this.remainingSeconds == null ? this.captureSeconds : this.remainingSeconds.get();
            string = string.replace("%captureFormat%", TimerBuilder.getStringTime(seconds));
            string = string.replace("%captureSeconds%", String.valueOf(seconds));
            string = string.replace("%captureProgress%", getProgressBar(this.captureSeconds - seconds, this.captureSeconds, this.progressBar));
            string = string.replace("%capturePercent%", this.captureSeconds == 0 ? "0" : format(percent(this.captureSeconds - seconds, this.captureSeconds)));
        }

        return replaceKothInformations(string);
    }

    @Override
    public DiscordWebhookConfig getDiscordWebhookConfig() {
        return this.discordWebhookConfig;
    }

    @Override
    public List<ItemStack> getItemStacks() {
        return this.itemStacks;
    }

    @Override
    public void setItemStacks(List<ItemStack> itemStacks) {
        this.itemStacks = itemStacks;
    }

    @Override
    public KothLootType getLootType() {
        return this.kothLootType;
    }

    private String replaceKothInformations(String string) {

        Location centerLocation = getCenter();
        int seconds = this.remainingSeconds == null ? this.captureSeconds : this.remainingSeconds.get();
        string = string.replace("%spawnSeconds%", String.valueOf(seconds));
        string = string.replace("%spawnFormat%", TimerBuilder.getStringTime(seconds));

        string = string.replace("%name%", this.name);
        string = string.replace("%world%", centerLocation.getWorld().getName());
        string = string.replace("%minX%", String.valueOf(minLocation.getBlockX()));
        string = string.replace("%minY%", String.valueOf(minLocation.getBlockY()));
        string = string.replace("%minZ%", String.valueOf(minLocation.getBlockZ()));
        string = string.replace("%maxX%", String.valueOf(maxLocation.getBlockX()));
        string = string.replace("%maxY%", String.valueOf(maxLocation.getBlockY()));
        string = string.replace("%maxZ%", String.valueOf(maxLocation.getBlockZ()));
        string = string.replace("%centerX%", String.valueOf(centerLocation.getBlockX()));
        string = string.replace("%centerY%", String.valueOf(centerLocation.getBlockY()));
        string = string.replace("%centerZ%", String.valueOf(centerLocation.getBlockZ()));
        string = string.replace("%nearbyPlayers%", getFormattedPlayersNearbyList());
        string = string.replace("%countNearbyPlayers%", String.valueOf(countNearbyPlayers()));

        return string;
    }

    @Override
    public CollectionConsumer<Player> onScoreboard() {
        return player -> {
            ScoreboardConfiguration scoreboard = (this.kothStatus == KothStatus.COOLDOWN) ? this.cooldownScoreboard : this.startScoreboard;
            return scoreboard.getLines().stream().map(e -> color(papi(replaceMessage(e), player))).collect(Collectors.toList());
        };
    }

    @Override
    public HologramConfig getHologramConfig() {
        return this.hologramConfig;
    }


    @Override
    public List<ItemStack> getRandomItemStacks() {
        List<ItemStack> itemStacks = new ArrayList<>(this.itemStacks);
        if (this.randomItemStacks <= 0) {
            return itemStacks;
        }
        Collections.shuffle(itemStacks, new Random());
        return itemStacks.stream().limit(this.randomItemStacks).collect(Collectors.toList());
    }

    @Override
    public int getRandomItemStack() {
        return this.randomItemStacks;
    }

    @Override
    public int getRemainingSeconds() {
        return this.remainingSeconds == null ? this.captureSeconds : this.remainingSeconds.get();
    }

    @Override
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }
    
    @Override
    public List<Player> getAllPlayersNearby() {
        return getCenter().getNearbyPlayers(scoreboardRadius).stream().toList();
    }
    
    @Override
    public String getFormattedPlayersNearbyList() {
        return getAllPlayersNearby().stream()
            .map(Player::getName)
            .collect(Collectors.joining(", "));
    }
    
    @Override
    public int countNearbyPlayers() {
        return getAllPlayersNearby().size();
    }

    @Override
    public void updateDisplay() {
        this.plugin.getKothHologram().update(this);
        this.plugin.getScoreBoardManager().update();
    }

    @Override
    public int getScore(Player player) {
        return player == null ? 0 : this.playersValues.getOrDefault(player.getUniqueId(), 0);
    }

    @Override
    public PlayerResult getPlayer(int position) {

        this.sortScores();
        if (this.playerResults.isEmpty() || position > this.playerResults.size()) return Config.defaultPlayerResult;
        return this.playerResults.get(position - 1);
    }

    @Override
    public List<String> getBlacklistTeamId() {
        return this.blacklistTeamId;
    }

    private void sortScores() {

        // If the list is not empty, then there is already a cache.
        if (!this.playerResults.isEmpty()) return;

        this.playerResults = this.playersValues.entrySet().stream().map(entry -> {

            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(entry.getKey());
            int points = entry.getValue();
            String teamName = this.kothTeam.getTeamName(offlinePlayer);
            String teamLeader = this.kothTeam.getLeaderName(offlinePlayer);
            String teamId = this.kothTeam.getTeamId(offlinePlayer);

            return new PlayerResult(offlinePlayer.getName(), points, teamName, teamId, teamLeader);
        }).sorted(Comparator.comparingInt(PlayerResult::getPoints).reversed()).collect(Collectors.toList());
    }

    @Override
    public ProgressBar getProgressBar() {
        return this.progressBar;
    }

    @Override
    public List<RandomCommand> getRandomCommands() {
        return this.randomCommands;
    }

    @Override
    public int getMaxRandomCommands() {
        return this.maxRandomCommands;
    }

    @Override
    public boolean isEnableEverySecondsCooldownMessage() {
        return enableEverySecondsCooldownMessage;
    }
}
