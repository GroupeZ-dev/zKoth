package fr.maxlego08.koth.hook.scoreboard;

import com.xism4.sternalboard.SternalBoardPlugin;
import com.xism4.sternalboard.managers.ScoreboardManager;
import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.KothScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class SternalBoardHook implements KothScoreboard {

    private final ScoreboardManager manager;

    public SternalBoardHook(KothPlugin plugin) {
        super();
        this.manager = ((SternalBoardPlugin) Bukkit.getPluginManager().getPlugin("SternalBoard")).getScoreboardManager();
    }

    @Override
    public void toggle(Player player, Consumer<Player> after) {
        this.manager.setScoreboard(player);
    }

    @Override
    public void hide(Player player, Consumer<Player> after) {
        this.manager.removeScoreboard(player);
    }

}
