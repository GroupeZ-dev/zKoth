package fr.maxlego08.koth.hook.scoreboard;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.KothScoreboard;
import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class TitleManagerHook implements KothScoreboard {

    private TitleManagerAPI api;

    public TitleManagerHook(KothPlugin plugin){

    }

    @Override
    public void toggle(Player player, Consumer<Player> after) {
        if (api == null)
            api = (TitleManagerAPI) Bukkit.getServer().getPluginManager().getPlugin("TitleManager");
        api.giveDefaultScoreboard(player);
    }

    @Override
    public void hide(Player player, Consumer<Player> after) {
        if (api == null)
            api = (TitleManagerAPI) Bukkit.getServer().getPluginManager().getPlugin("TitleManager");
        api.removeScoreboard(player);
    }

}
