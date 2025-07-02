package fr.maxlego08.koth.hook.teams;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.KothTeam;
import fr.maxlego08.koth.zcore.utils.ColorTransformer;
import me.ulrich.clans.Clans;
import me.ulrich.clans.data.ClanData;
import me.ulrich.clans.events.ClanDeleteEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UltimateClan implements KothTeam {

    private final KothPlugin plugin;
    private final Clans clans;

    public UltimateClan(KothPlugin plugin) {
        this.plugin = plugin;
        this.clans = (Clans) Bukkit.getPluginManager().getPlugin("UltimateClans");
    }

    @Override
    public String getTeamName(OfflinePlayer player) {
        Optional<ClanData> optional = this.clans.getPlayerAPI().getPlayerClan(player.getUniqueId());
        return optional.isPresent() ? ColorTransformer.transformColors(optional.get().getTag()) : player.getName();
    }

    @Override
    public List<Player> getOnlinePlayer(OfflinePlayer player) {
        Optional<ClanData> optional = this.clans.getPlayerAPI().getPlayerClan(player.getUniqueId());
        return optional.map(clanData -> clanData.getOnlineMembers().stream().map(Bukkit::getPlayer).collect(Collectors.toList())).orElseGet(() -> Collections.singletonList(player.getPlayer()));
    }

    @Override
    public String getLeaderName(OfflinePlayer player) {
        Optional<ClanData> optional = this.clans.getPlayerAPI().getPlayerClan(player.getUniqueId());
        return optional.isPresent() ? Bukkit.getOfflinePlayer(optional.get().getLeader()).getName() : player.getName();
    }

    @Override
    public String getTeamId(OfflinePlayer player) {
        Optional<ClanData> optional = this.clans.getPlayerAPI().getPlayerClan(player.getUniqueId());
        return optional.isPresent() ? optional.get().getId().toString() : player.getUniqueId().toString();
    }

    @EventHandler
    public void onDelete(ClanDeleteEvent event) {
        this.plugin.getStorageManager().onTeamDisband(String.valueOf(event.getClanID().toString()));
    }
}
