package fr.maxlego08.koth.api.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Scoreboard configuration of a KOTH.
 */
public class ScoreboardConfiguration {

    private final boolean isEnable;
    private final String title;
    private final List<String> lines;

    /**
     * Creates a new {@code ScoreboardConfiguration}.
     *
     * @param isEnable whether the scoreboard is enabled
     * @param title    the scoreboard title
     * @param lines    the scoreboard lines
     */
    public ScoreboardConfiguration(boolean isEnable, String title, List<String> lines) {
        this.isEnable = isEnable;
        this.title = title;
        this.lines = lines;
    }

    /**
     * Creates the default scoreboard displayed while a KOTH is in cooldown.
     *
     * @return the default cooldown scoreboard
     */
    public static ScoreboardConfiguration cooldown() {
        return new ScoreboardConfiguration(true, "&f&l  &7&oZkoth &f&l ", Arrays.asList(
                "&r",
                "#16db47Location:",
                " #636363 #ffffffX: %centerX% Y: %centerY% Z: %centerZ%",
                "&r",
                "#16db47Starts in:",
                " #636363 #ffffff%spawnFormat%",
                "&r"
        ));
    }

    /**
     * Creates the default scoreboard displayed while a KOTH is running.
     *
     * @return the default start scoreboard
     */
    public static ScoreboardConfiguration start() {
        return new ScoreboardConfiguration(true, "&f&l  &7&oZkoth &f&l ", Arrays.asList(
                "&r",
                "#16db47Location",
                " #636363 #ffffffX: %centerX% Y: %centerY% Z: %centerZ%",
                "&r",
                "#16db47Player:",
                " #636363 #ffffff%playerName%",
                "&r",
                "#16db47Time left:",
                " #636363 #ffffff%captureFormat%",
                "&r"
        ));
    }

    /**
     * Returns whether the scoreboard is enabled.
     *
     * @return whether the scoreboard is enabled
     */
    public boolean isEnable() {
        return isEnable;
    }

    /**
     * Returns the scoreboard title.
     *
     * @return the scoreboard title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the scoreboard lines.
     *
     * @return the scoreboard lines
     */
    public List<String> getLines() {
        return lines;
    }
}
