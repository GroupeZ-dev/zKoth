package fr.maxlego08.koth.hook.scoreboard;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.KothScoreboard;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class TabHook implements KothScoreboard {
    private final KothPlugin plugin;

    public TabHook(KothPlugin plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    public void toggle(Player player, Consumer<Player> after) {
        TabAPI api = TabAPI.getInstance();
        TabPlayer tabPlayer = api.getPlayer(player.getUniqueId());
        ScoreboardManager manager = TabAPI.getInstance().getScoreboardManager();
        // System.out.println(tabPlayer + " - " + manager);
        if (tabPlayer != null && manager != null) {
            manager.toggleScoreboard(tabPlayer, true);
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> after.accept(player), 10);
        }
    }

    @Override
    public void hide(Player player, Consumer<Player> after) {
        this.toggle(player, after);
    }
}
