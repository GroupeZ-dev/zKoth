package fr.maxlego08.koth.hook.teams;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.events.IslandDisbandEvent;
import com.bgsoftware.superiorskyblock.api.island.Island;
import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.KothTeam;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SuperiorSkyblock2Hook implements KothTeam {

	private final KothPlugin plugin;

    public SuperiorSkyblock2Hook(KothPlugin plugin) {
        this.plugin = plugin;
    }

    private Island getIsland(OfflinePlayer player) {
        return SuperiorSkyblockAPI.getPlayer(player.getUniqueId()).getIsland();
    }

    @Override
    public String getTeamName(OfflinePlayer player) {
        Island island = getIsland(player);
        return island == null ? player.getName() : getIsland(player).getName();
    }

    @Override
    public List<Player> getOnlinePlayer(OfflinePlayer player) {
        Island island = getIsland(player);
        if (island == null) return Collections.singletonList(player.getPlayer());

        return island.getIslandMembers(true).stream().map(p -> Bukkit.getOfflinePlayer(p.getUniqueId()))
                .filter(OfflinePlayer::isOnline).map(OfflinePlayer::getPlayer).collect(Collectors.toList());
    }

    @Override
    public String getLeaderName(OfflinePlayer player) {
        Island island = getIsland(player);
        return island != null ? island.getOwner().getName() : player.getName();
    }

    @Override
    public String getTeamId(OfflinePlayer player) {
        Island island = getIsland(player);
        return island != null ? island.getUniqueId().toString() : player.getUniqueId().toString();
    }

    @EventHandler
    public void onIslandDisband(IslandDisbandEvent event) {
        this.plugin.getStorageManager().onTeamDisband(event.getIsland().getUniqueId().toString());
    }

}
