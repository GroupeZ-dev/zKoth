package fr.maxlego08.koth.api.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomCommand {

    private final int percent;
    private final List<String> commands;

    public RandomCommand(int percent, List<String> commands) {
        this.percent = percent;
        this.commands = commands;
    }

    public int getPercent() {
        return percent;
    }

    public List<String> getCommands() {
        return commands;
    }

    public boolean canUse() {
        return percent >= ThreadLocalRandom.current().nextInt(0, 100);
    }
}
