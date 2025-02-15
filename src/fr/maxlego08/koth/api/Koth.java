package fr.maxlego08.koth.api;

import fr.maxlego08.koth.api.discord.DiscordWebhookConfig;
import fr.maxlego08.koth.api.utils.HologramConfig;
import fr.maxlego08.koth.api.utils.PlayerResult;
import fr.maxlego08.koth.api.utils.RandomCommand;
import fr.maxlego08.koth.api.utils.ScoreboardConfiguration;
import fr.maxlego08.koth.zcore.utils.Cuboid;
import fr.maxlego08.koth.zcore.utils.ProgressBar;
import fr.maxlego08.koth.zcore.utils.interfaces.CollectionConsumer;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;

/**
 * The Koth interface represents all the essential information and functionalities of a King of the Hill (KOTH) game.
 * It includes methods for managing game settings, locations, statuses, and interactions. This interface is central to
 * the KOTH plugin, providing a comprehensive set of operations for configuring and controlling KOTH games.
 * <p>
 * Implementing this interface allows for detailed customization and control over the KOTH games, including game dynamics,
 * area definitions, scoreboard configurations, and more. It's designed to facilitate seamless integration with other game
 * management components, such as teams, scoreboards, and holograms, enhancing the overall gameplay experience.
 */
public interface Koth {

    /**
     * Gets the file name associated with this KOTH game's configuration.
     *
     * @return A {@code String} representing the configuration file name.
     */
    String getFileName();

    /**
     * Retrieves the type of the KOTH game. The {@code KothType} enum may define various game types, such as CLASSIC, TIMED, etc.
     *
     * @return The {@code KothType} representing the type of the KOTH game.
     */
    KothType getKothType();

    /**
     * Gets the name of this KOTH game.
     *
     * @return A {@code String} representing the name of the KOTH game.
     */
    String getName();

    /**
     * Sets the name of this KOTH game.
     *
     * @param name The new name for the KOTH game.
     */
    void setName(String name);

    /**
     * Gets the minimum corner location of the KOTH game area.
     *
     * @return A {@code Location} representing the minimum corner of the KOTH area.
     */
    Location getMinLocation();

    /**
     * Gets the maximum corner location of the KOTH game area.
     *
     * @return A {@code Location} representing the maximum corner of the KOTH area.
     */
    Location getMaxLocation();

    /**
     * Retrieves the cuboid defining the KOTH game area.
     *
     * @return A {@code Cuboid} representing the KOTH game area.
     */
    Cuboid getCuboid();

    /**
     * Gets the center location of the KOTH game area.
     *
     * @return A {@code Location} representing the center of the KOTH area.
     */
    Location getCenter();

    /**
     * Retrieves a list of commands to be executed at the start of the KOTH game.
     *
     * @return A list of {@code String} commands to execute at the start.
     */
    List<String> getStartCommands();

    /**
     * Retrieves a list of commands to be executed at the end of the KOTH game.
     *
     * @return A list of {@code String} commands to execute at the end.
     */
    List<String> getEndCommands();
    
    /**
     * Retrieves a collection of all nearby players according to the koth's scoreboard radius
     *
     * @return A collection of {@link Player}s currently within the koth's scoreboard radius
     */
    Collection<Player> getAllPlayersNearby();
    
    String getFormattedPlayersNearbyList();
    /**
     * @return Counts the number of nearby players from {@link Koth#getAllPlayersNearby()}
     */
    int countNearbyPlayers();

    /**
     * Moves the KOTH game area to a new location defined by minimum and maximum corner locations.
     *
     * @param minLocation The new minimum corner location.
     * @param maxLocation The new maximum corner location.
     */
    void move(Location minLocation, Location maxLocation);

    /**
     * Gets the number of seconds required for capturing the KOTH.
     *
     * @return An {@code int} representing the capture seconds.
     */
    int getCaptureSeconds();

    /**
     * Sets the number of seconds required for capturing the KOTH.
     *
     * @param captureSeconds The new capture time in seconds.
     */
    void setCaptureSeconds(int captureSeconds);

    /**
     * Retrieves the scoreboard configuration used during the cooldown period of the KOTH game.
     *
     * @return A {@code ScoreboardConfiguration} for the cooldown period.
     */
    ScoreboardConfiguration getCooldownScoreboard();

    /**
     * Retrieves the scoreboard configuration used at the start of the KOTH game.
     *
     * @return A {@code ScoreboardConfiguration} for the start of the game.
     */
    ScoreboardConfiguration getStartScoreboard();

    /**
     * Gets the current status of the KOTH game.
     *
     * @return A {@code KothStatus} representing the current game status.
     */
    KothStatus getStatus();

    /**
     * Spawns the KOTH game immediately or schedules it for later, based on the {@code now} parameter. Optionally,
     * a {@code CommandSender} can be specified to receive feedback about the operation.
     *
     * @param sender The command sender to notify about the operation's outcome. Can be {@code null}.
     * @param now    A boolean indicating whether to spawn the KOTH game immediately ({@code true}) or later ({@code false}).
     */
    void spawn(CommandSender sender, boolean now);

    /**
     * Spawns the KOTH game immediately or schedules it for later, based on the {@code now} parameter.
     *
     * @param now A boolean indicating whether to spawn the KOTH game immediately ({@code true}) or later ({@code false}).
     */
    void spawn(boolean now);

    /**
     * Stops the KOTH game. Optionally, a {@code CommandSender} can be specified to receive feedback about the operation.
     *
     * @param sender The command sender to notify about the operation's outcome. Can be {@code null}.
     */
    void stop(CommandSender sender);

    /**
     * Stops the KOTH game.
     */
    void stop();

    /**
     * Handles player movement within the KOTH game area, potentially affecting the game's state based on the player's actions and team.
     *
     * @param player   The player who moved.
     * @param kothTeam The team to which the player belongs.
     */
    void playerMove(Player player, KothTeam kothTeam);

    /**
     * Gets the cooldown start time in seconds.
     *
     * @return An {@code int} representing the cooldown start time.
     */
    int getCooldownStart();

    /**
     * Gets the configured time in seconds after which the KOTH game should automatically stop.
     *
     * @return An {@code int} representing the auto-stop time.
     */
    int getStopAfterSeconds();

    /**
     * Checks if a start capture message is enabled.
     *
     * @return {@code true} if the start capture message is enabled, {@code false} otherwise.
     */
    boolean isEnableStartCapMessage();

    /**
     * Checks if a loose capture message is enabled.
     *
     * @return {@code true} if the loose capture message is enabled, {@code false} otherwise.
     */
    boolean isEnableLooseCapMessage();

    /**
     * Checks if a message should be displayed every second during capture.
     *
     * @return {@code true} if the every-second capture message is enabled, {@code false} otherwise.
     */
    boolean isEnableEverySecondsCapMessage();

    /**
     * Checks if a message should be displayed every second during the game.
     *
     * @return {@code true} if the every-second game message is enabled, {@code false} otherwise.
     */
    boolean isEnableEverySecondsCooldownMessage();


    /**
     * Defines a consumer to be called for scoreboards updates. This allows for dynamic scoreboard content based on the game state.
     *
     * @return A {@code CollectionConsumer<Player>} for scoreboard updates.
     */
    CollectionConsumer<Player> onScoreboard();

    /**
     * Retrieves the hologram configuration for the KOTH game.
     *
     * @return A {@code HologramConfig} representing the hologram settings.
     */
    HologramConfig getHologramConfig();

    /**
     * Replaces placeholders in a message string with dynamic values related to the KOTH game.
     *
     * @param string The message string with placeholders.
     * @return A {@code String} with placeholders replaced by dynamic values.
     */
    String replaceMessage(String string);

    /**
     * Retrieves the Discord webhook configuration for the KOTH game.
     *
     * @return A {@code DiscordWebhookConfig} representing the Discord integration settings.
     */
    DiscordWebhookConfig getDiscordWebhookConfig();

    /**
     * Retrieves the list of ItemStacks associated with the KOTH game.
     *
     * @return A list of {@code ItemStack} objects.
     */
    List<ItemStack> getItemStacks();

    /**
     * Sets the list of ItemStacks associated with the KOTH game.
     *
     * @param itemStacks The new list of {@code ItemStack} objects.
     */
    void setItemStacks(List<ItemStack> itemStacks);

    /**
     * Gets the loot type for the KOTH game, indicating how loot is distributed or acquired.
     *
     * @return A {@code KothLootType} representing the loot distribution method.
     */
    KothLootType getLootType();

    /**
     * Retrieves a list of random ItemStacks based on the game's configuration.
     *
     * @return A list of randomly selected {@code ItemStack} objects.
     */
    List<ItemStack> getRandomItemStacks();

    /**
     * Gets a random item stack index based on the game's loot configuration.
     *
     * @return An {@code int} representing the index of a randomly selected item stack.
     */
    int getRandomItemStack();

    /**
     * Retrieves an {@code AtomicInteger} representing the remaining seconds until the KOTH game ends.
     *
     * @return An {@code AtomicInteger} with the remaining seconds.
     */
    int getRemainingSeconds();

    /**
     * Gets the current player who is capturing the KOTH.
     *
     * @return A {@code Player} who is currently capturing the KOTH, or {@code null} if no player is capturing.
     */
    Player getCurrentPlayer();

    /**
     * Updates the display elements associated with the KOTH game, such as scoreboards or holograms, based on the current game state.
     */
    void updateDisplay();

    int getScore(Player player);

    PlayerResult getPlayer(int position);

    List<String> getBlacklistTeamId();

    ProgressBar getProgressBar();

    List<RandomCommand> getRandomCommands();

    int getMaxRandomCommands();
}

