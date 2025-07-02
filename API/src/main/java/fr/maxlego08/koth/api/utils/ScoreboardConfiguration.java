package fr.maxlego08.koth.api.utils;

import java.util.Arrays;
import java.util.List;

public class ScoreboardConfiguration {

    private final boolean isEnable;
    private final String title;
    private final List<String> lines;

    public ScoreboardConfiguration(boolean isEnable, String title, List<String> lines) {
        this.isEnable = isEnable;
        this.title = title;
        this.lines = lines;
    }

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

    public boolean isEnable() {
        return isEnable;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getLines() {
        return lines;
    }
}
