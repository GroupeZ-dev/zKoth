package fr.maxlego08.koth.hook.teams;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.KothTeam;
import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.events.LandDeleteEvent;
import me.angeschossen.lands.api.land.Land;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class LandHook implements KothTeam {

    private final KothPlugin plugin;

    public LandHook(KothPlugin plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    public String getTeamName(OfflinePlayer player) {
        Optional<? extends Land> optional = getLandByPlayer(player);
        return optional.map(Land::getName).orElseGet(player::getName);
    }

    @Override
    public List<Player> getOnlinePlayer(OfflinePlayer player) {

        Optional<? extends Land> optional = getLandByPlayer(player);
        if (optional.isPresent()) {
            return new ArrayList<>(optional.get().getOnlinePlayers());
        }

        return Collections.singletonList(player.getPlayer());
    }

    private Optional<? extends Land> getLandByPlayer(OfflinePlayer player) {
        LandsIntegration api = LandsIntegration.of(this.plugin);
        return api.getLandPlayer(player.getUniqueId()).getLands().stream().findFirst();
    }

    @Override
    public String getLeaderName(OfflinePlayer player) {
        Optional<? extends Land> optional = getLandByPlayer(player);
        if (optional.isPresent()) return Bukkit.getOfflinePlayer(optional.get().getOwnerUID()).getName();
        return player.getName();
    }

    @Override
    public String getTeamId(OfflinePlayer player) {
        Optional<? extends Land> optional = getLandByPlayer(player);
        return optional.map(land -> String.valueOf(land.getId())).orElseGet(() -> player.getUniqueId().toString());
    }

    @EventHandler
    public void onDisband(LandDeleteEvent event) {
        this.plugin.getStorageManager().onTeamDisband(String.valueOf(event.getLand().getId()));
    }

}
