package fr.maxlego08.koth.api.utils;

/**
 * Score of a player at the end of a SCORE KOTH.
 */
public class PlayerResult {

    private final String playerName;
    private final int points;
    private final String teamName;
    private final String teamId;
    private final String teamLeader;

    /**
     * Creates a new {@code PlayerResult}.
     *
     * @param playerName the player name
     * @param points     the points earned by the player
     * @param teamName   the name of the player's team
     * @param teamId     the identifier of the player's team
     * @param teamLeader the leader of the player's team
     */
    public PlayerResult(String playerName, int points, String teamName, String teamId, String teamLeader) {
        this.playerName = playerName;
        this.points = points;
        this.teamName = teamName;
        this.teamId = teamId;
        this.teamLeader = teamLeader;
    }

    /**
     * Returns the player name.
     *
     * @return the player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Returns the points earned by the player.
     *
     * @return the points earned by the player
     */
    public int getPoints() {
        return points;
    }

    /**
     * Returns the name of the player's team.
     *
     * @return the name of the player's team
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Returns the identifier of the player's team.
     *
     * @return the identifier of the player's team
     */
    public String getTeamId() {
        return teamId;
    }

    /**
     * Returns the leader of the player's team.
     *
     * @return the leader of the player's team
     */
    public String getTeamLeader() {
        return teamLeader;
    }

    @Override
    public String toString() {
        return "PlayerResult{" +
                "playerName='" + playerName + '\'' +
                ", points=" + points +
                ", teamName='" + teamName + '\'' +
                ", teamId='" + teamId + '\'' +
                ", teamLeader='" + teamLeader + '\'' +
                '}';
    }
}
