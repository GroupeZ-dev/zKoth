package fr.maxlego08.koth.hook;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.KothScoreboard;
import fr.maxlego08.koth.hook.scoreboard.FeatherBoardHook;
import fr.maxlego08.koth.hook.scoreboard.SternalBoardHook;
import fr.maxlego08.koth.hook.scoreboard.TabHook;
import fr.maxlego08.koth.hook.scoreboard.TitleManagerHook;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;

public enum ScoreboardPlugin {

    FEATHERBOARD("FeatherBoard", FeatherBoardHook.class),
    TAB("TAB", TabHook.class),
    STERNALBOARD("SternalBoard", SternalBoardHook.class),
    TITLEMANAGER("TitleManager", TitleManagerHook.class),

    ;

    private final String pluginName;
    private final Class<? extends KothScoreboard> scoreboardClass;

    ScoreboardPlugin(String pluginName, Class<? extends KothScoreboard> scoreboardClass) {
        this.pluginName = pluginName;
        this.scoreboardClass = scoreboardClass;
    }

    public String getPluginName() {
        return pluginName;
    }

    public boolean isEnable() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(this.pluginName);
        return plugin != null && plugin.isEnabled();
    }

    public KothScoreboard init(KothPlugin plugin) {
        try {
            return scoreboardClass.getConstructor(KothPlugin.class).newInstance(plugin);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
