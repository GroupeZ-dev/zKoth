package fr.maxlego08.koth.api;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;

/**
 * The KothTeam interface provides methods for interacting with team-related data within a King of the Hill (KOTH) game mode.
 * This interface serves as a bridge between the KOTH plugin and other team plugins, facilitating the retrieval of team information,
 * online players within a team, the team leader's name, and the unique team identifier.
 *
 * Implementing this interface allows for seamless integration and manipulation of team data, enhancing the KOTH gaming experience by
 * providing essential team-related functionalities. It's designed to be implemented by classes that manage team data in the context
 * of KOTH game modes.
 *
 * <p>Note: This interface requires the implementation class to be a listener to game events, as indicated by the extension of the {@code Listener} interface.</p>
 */
public interface KothTeam extends Listener {

    /**
     * Retrieves the name of the team to which a given player belongs.
     *
     * @param player The player for whom the team name is to be retrieved. This parameter cannot be null.
     * @return A {@code String} representing the name of the team. Returns {@code null} if the player is not part of any team.
     */
    String getTeamName(OfflinePlayer player);

    /**
     * Retrieves a list of players who are currently online and belong to the same team as the given player.
     *
     * @param player The player used as a reference for the team query. This parameter cannot be null.
     * @return A list of {@code Player} objects representing the online team members. Returns an empty list if no online players are found in the team.
     */
    List<Player> getOnlinePlayer(OfflinePlayer player);

    /**
     * Retrieves the name of the leader of the team to which a given player belongs.
     *
     * @param player The player for whom the team leader's name is to be retrieved. This parameter cannot be null.
     * @return A {@code String} representing the name of the team leader. Returns {@code null} if the player is not part of any team or the team does not have a designated leader.
     */
    String getLeaderName(OfflinePlayer player);

    /**
     * Retrieves the unique identifier (ID) of the team to which a given player belongs.
     *
     * @param player The player for whom the team ID is to be retrieved. This parameter cannot be null.
     * @return A {@code String} representing the unique ID of the team. Returns {@code null} if the player is not part of any team.
     */
    String getTeamId(OfflinePlayer player);
}
