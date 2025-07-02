package fr.maxlego08.koth.hook.teams;

import com.booksaw.betterTeams.PlayerRank;
import com.booksaw.betterTeams.Team;
import com.booksaw.betterTeams.customEvents.DisbandTeamEvent;
import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.KothTeam;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.Collections;
import java.util.List;

public class BetterTeamHook implements KothTeam {

    private final KothPlugin plugin;

    public BetterTeamHook(KothPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getTeamName(OfflinePlayer player) {
        Team team = Team.getTeam(player);
        return team == null ? player.getName() : team.getName();
    }

    @Override
    public List<Player> getOnlinePlayer(OfflinePlayer player) {
        Team team = Team.getTeam(player);
        return team == null ? Collections.singletonList(player.getPlayer()) : team.getOnlineMembers();
    }

    @Override
    public String getLeaderName(OfflinePlayer player) {
        Team team = Team.getTeam(player);
        return team == null ? player.getName() : team.getMembers().getRank(PlayerRank.OWNER).get(0).getPlayer().getName();
    }

    @Override
    public String getTeamId(OfflinePlayer player) {
        Team team = Team.getTeam(player);
        return team == null ? player.getUniqueId().toString() : team.getID().toString();
    }

    @EventHandler
    public void onDisband(DisbandTeamEvent event) {
        this.plugin.getStorageManager().onTeamDisband(event.getTeam().getID().toString());
    }

}
