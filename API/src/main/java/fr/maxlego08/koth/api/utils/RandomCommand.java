package fr.maxlego08.koth.api.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Group of reward commands executed with a given probability.
 */
public class RandomCommand {

    private final int percent;
    private final List<String> commands;

    /**
     * Creates a new {@code RandomCommand}.
     *
     * @param percent  the chance, between 0 and 100, for the commands to be executed
     * @param commands the commands to execute
     */
    public RandomCommand(int percent, List<String> commands) {
        this.percent = percent;
        this.commands = commands;
    }

    /**
     * Returns the chance, between 0 and 100, for the commands to be executed.
     *
     * @return the chance, between 0 and 100, for the commands to be executed
     */
    public int getPercent() {
        return percent;
    }

    /**
     * Returns the commands to execute.
     *
     * @return the commands to execute
     */
    public List<String> getCommands() {
        return commands;
    }

    /**
     * Rolls the chance of this group.
     *
     * @return true if the commands should be executed
     */
    public boolean canUse() {
        return percent >= ThreadLocalRandom.current().nextInt(0, 100);
    }
}
