package fr.maxlego08.koth.hook.teams;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.KothTeam;
import net.brcdev.gangs.GangsPlugin;
import net.brcdev.gangs.gang.Gang;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GangsHook implements KothTeam {

    private final KothPlugin plugin;

    public GangsHook(KothPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getTeamName(OfflinePlayer player) {
        Gang gang = GangsPlugin.getInstance().getGangManager().getPlayersGang(player);
        return gang != null ? gang.getName() : player.getName();
    }

    @Override
    public List<Player> getOnlinePlayer(OfflinePlayer player) {
        Gang gang = GangsPlugin.getInstance().getGangManager().getPlayersGang(player);
        return gang != null ? new ArrayList<>(gang.getOnlineMembers()) : Collections.singletonList(player.getPlayer());
    }

    @Override
    public String getLeaderName(OfflinePlayer player) {
        Gang gang = GangsPlugin.getInstance().getGangManager().getPlayersGang(player);
        return gang != null ? gang.getOwnerName() : player.getName();
    }

    @Override
    public String getTeamId(OfflinePlayer player) {
        Gang gang = GangsPlugin.getInstance().getGangManager().getPlayersGang(player);
        return gang != null ? String.valueOf(gang.getId()) : player.getUniqueId().toString();
    }
}
