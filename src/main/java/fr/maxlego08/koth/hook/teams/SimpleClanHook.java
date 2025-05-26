package fr.maxlego08.koth.hook.teams;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.KothTeam;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import net.sacredlabyrinth.phaed.simpleclans.events.DisbandClanEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimpleClanHook implements KothTeam {

    private final KothPlugin plugin;
    private final SimpleClans clan;

    public SimpleClanHook(KothPlugin plugin) {
        this.plugin = plugin;
        this.clan = (SimpleClans) Bukkit.getServer().getPluginManager().getPlugin("SimpleClans");
    }

    private Optional<Clan> getClan(OfflinePlayer player) {
        ClanPlayer clanPlayer = this.clan.getClanManager().getClanPlayer(player);
        if (clanPlayer == null) return Optional.empty();
        return Optional.ofNullable(clanPlayer.getClan());
    }

    @Override
    public String getTeamName(OfflinePlayer player) {
        Optional<Clan> optional = getClan(player);
        return optional.isPresent() ? optional.get().getName() : player.getName();
    }

    @Override
    public List<Player> getOnlinePlayer(OfflinePlayer player) {
        Optional<Clan> optional = getClan(player);
        return optional.map(value -> value.getOnlineMembers().stream().map(e -> Bukkit.getOfflinePlayer(e.getUniqueId()))
                .filter(OfflinePlayer::isOnline).map(OfflinePlayer::getPlayer).collect(Collectors.toList())).orElseGet(() -> Collections.singletonList(player.getPlayer()));
    }

    @Override
    public String getLeaderName(OfflinePlayer player) {
        Optional<Clan> optional = getClan(player);
        if (!optional.isPresent()) return player.getName();
        Clan clan = optional.get();
        return clan.getMembers().stream().filter(e -> clan.isLeader(e.getUniqueId())).findFirst().map(ClanPlayer::getName).orElseGet(player::getName);
    }

    @Override
    public String getTeamId(OfflinePlayer player) {
        Optional<Clan> optional = getClan(player);
        return optional.isPresent() ? optional.get().getName() : player.getUniqueId().toString();
    }

    @EventHandler
    public void onClan(DisbandClanEvent event) {
        this.plugin.getStorageManager().onTeamDisband(event.getClan().getName());
    }
}
