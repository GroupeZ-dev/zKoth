package fr.maxlego08.koth.hook.teams;

import fr.maxlego08.koth.api.KothPlugin;
import fr.maxlego08.koth.api.KothTeam;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
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

    private static final LegacyComponentSerializer AMPERSAND = LegacyComponentSerializer.legacyAmpersand();
    private static final LegacyComponentSerializer SECTION = LegacyComponentSerializer.legacySection();

    private final KothPlugin plugin;
    private final Clans clans;

    public UltimateClan(KothPlugin plugin) {
        this.plugin = plugin;
        this.clans = (Clans) Bukkit.getPluginManager().getPlugin("UltimateClans");
    }

    @Override
    public String getTeamName(OfflinePlayer player) {
        Optional<ClanData> optional = this.clans.getPlayerAPI().getPlayerClan(player.getUniqueId());
        return optional.isPresent() ? SECTION.serialize(AMPERSAND.deserialize(optional.get().getTag())) : player.getName();
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
        this.plugin.onTeamDisband(String.valueOf(event.getClanID().toString()));
    }
}
