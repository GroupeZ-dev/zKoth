package fr.maxlego08.koth.hook.teams;

import dev.kitteh.factions.FPlayer;
import dev.kitteh.factions.FPlayers;
import dev.kitteh.factions.event.FactionDisbandEvent;
import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.KothTeam;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.List;

public class FactionsUUIDHook implements KothTeam {

    private final KothPlugin plugin;

    public FactionsUUIDHook(KothPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getTeamName(OfflinePlayer player) {
        return FPlayers.fPlayers().get(player).faction().tag();
    }

    @Override
    public List<Player> getOnlinePlayer(OfflinePlayer player) {
        return FPlayers.fPlayers().get(player).faction().membersOnlineAsPlayers();
    }

    @Override
    public String getLeaderName(OfflinePlayer player) {
        FPlayer fPlayer = FPlayers.fPlayers().get(player).faction().admin();
        return fPlayer == null ? player.getName() : fPlayer.name();
    }

    @Override
    public String getTeamId(OfflinePlayer player) {
        return String.valueOf(FPlayers.fPlayers().get(player).faction().id());
    }

    @EventHandler
    public void onDisband(FactionDisbandEvent event) {
        this.plugin.getStorageManager().onTeamDisband(String.valueOf(event.getFaction().id()));
    }
}
