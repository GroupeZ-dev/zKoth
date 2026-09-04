package fr.maxlego08.koth.hook.teams;

import fr.maxlego08.koth.api.KothPlugin;
import fr.maxlego08.koth.api.KothTeam;
import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.events.LandDeleteEvent;
import me.angeschossen.lands.api.land.Land;
import me.angeschossen.lands.api.land.enums.LandType;
import me.angeschossen.lands.api.player.LandPlayer;
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
    private final LandsIntegration lands;

    public LandHook(KothPlugin plugin) {
        this(plugin, LandsIntegration.of(plugin));
    }

    LandHook(KothPlugin plugin, LandsIntegration lands) {
        this.plugin = plugin;
        this.lands = lands;
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

        Player onlinePlayer = player.getPlayer();
        return onlinePlayer == null ? Collections.emptyList() : Collections.singletonList(onlinePlayer);
    }

    private Optional<? extends Land> getLandByPlayer(OfflinePlayer player) {
        LandPlayer landPlayer = this.lands.getLandPlayer(player.getUniqueId());
        if (landPlayer == null) return Optional.empty();

        Land ownedLand = landPlayer.getOwningLand();
        if (isUsableLand(ownedLand)) return Optional.of(ownedLand);

        return landPlayer.getLands().stream()
                .filter(this::isUsableLand)
                .sorted((left, right) -> left.getULID().toString().compareTo(right.getULID().toString()))
                .findFirst();
    }

    private boolean isUsableLand(Land land) {
        return land != null && land.exists() && land.getLandType() == LandType.LAND && land.getULID() != null;
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
        return optional.map(land -> land.getULID().toString()).orElseGet(() -> player.getUniqueId().toString());
    }

    @EventHandler
    public void onDisband(LandDeleteEvent event) {
        Land land = event.getLand();
        if (land.getULID() != null) this.plugin.onTeamDisband(land.getULID().toString());
    }

}
