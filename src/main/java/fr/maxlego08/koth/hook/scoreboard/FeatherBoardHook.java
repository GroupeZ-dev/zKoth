package fr.maxlego08.koth.hook.scoreboard;

import be.maximvdw.featherboard.api.FeatherBoardAPI;
import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.KothScoreboard;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class FeatherBoardHook implements KothScoreboard {

    public FeatherBoardHook(KothPlugin plugin){

    }

    @Override
    public void toggle(Player player, Consumer<Player> after) {
        if (!FeatherBoardAPI.isToggled(player)) {
            FeatherBoardAPI.toggle(player, true);
        }
    }

    @Override
    public void hide(Player player, Consumer<Player> after) {
        if (FeatherBoardAPI.isToggled(player)) {
            FeatherBoardAPI.toggle(player, false);
        }
    }

}
