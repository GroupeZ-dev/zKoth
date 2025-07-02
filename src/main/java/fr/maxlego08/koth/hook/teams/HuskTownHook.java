package fr.maxlego08.koth.hook.teams;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.KothTeam;
import me.angeschossen.lands.api.events.LandDeleteEvent;
import net.william278.husktowns.api.HuskTownsAPI;
import net.william278.husktowns.town.Member;
import net.william278.husktowns.town.Town;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class HuskTownHook implements KothTeam {

    private final KothPlugin plugin;
    private final Map<UUID, Town> towns = new HashMap<>();

    public HuskTownHook(KothPlugin plugin) {
        this.plugin = plugin;
    }

    private Optional<Town> getTown(OfflinePlayer player) {
        return HuskTownsAPI.getInstance().getTowns().stream().filter(e -> e.getMembers().containsKey(player.getUniqueId())).findFirst();
    }

    @Override
    public String getTeamName(OfflinePlayer player) {
        Optional<Town> optional = getTown(player);
        if (optional.isPresent()) return optional.get().getName();
        return player.getName();
    }

    @Override
    public List<Player> getOnlinePlayer(OfflinePlayer player) {
        Optional<Town> optional = getTown(player);
        return optional.map(town -> town.getMembers().keySet().stream().map(Bukkit::getOfflinePlayer)
                .filter(OfflinePlayer::isOnline).map(OfflinePlayer::getPlayer).collect(Collectors.toList())).orElseGet(() -> Collections.singletonList(player.getPlayer()));
    }

    @Override
    public String getLeaderName(OfflinePlayer player) {
        Optional<Town> optional = getTown(player);
        if (optional.isPresent()) return Bukkit.getOfflinePlayer(optional.get().getMayor()).getName();
        return player.getName();
    }

    @Override
    public String getTeamId(OfflinePlayer player) {
        Optional<Town> optional = getTown(player);
        return optional.map(town -> String.valueOf(town.getId())).orElseGet(player::getName);
    }

    @EventHandler
    public void onDelete(LandDeleteEvent event) {
        this.plugin.getStorageManager().onTeamDisband(String.valueOf(event.getLand().getId()));
    }

}
