package fr.maxlego08.koth.hook.teams;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.event.FactionDisbandEvent;
import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.KothTeam;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.List;

public class SaberFactionHook implements KothTeam {

    private final KothPlugin plugin;

    public SaberFactionHook(KothPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getTeamName(OfflinePlayer player) {
        return FPlayers.getInstance().getByOfflinePlayer(player).getFaction().getTag();
    }

    @Override
    public List<Player> getOnlinePlayer(OfflinePlayer player) {
        return FPlayers.getInstance().getByOfflinePlayer(player).getFaction().getOnlinePlayers();
    }

    @Override
    public String getLeaderName(OfflinePlayer player) {
        FPlayer fPlayer = FPlayers.getInstance().getByOfflinePlayer(player).getFaction().getFPlayerAdmin();
        return fPlayer == null ? player.getName() : fPlayer.getName();
    }

    @Override
    public String getTeamId(OfflinePlayer player) {
        return FPlayers.getInstance().getByOfflinePlayer(player).getFactionId();
    }

    @EventHandler
    public void onDisband(FactionDisbandEvent event) {
        this.plugin.getStorageManager().onTeamDisband(event.getFaction().getId());
    }
}
