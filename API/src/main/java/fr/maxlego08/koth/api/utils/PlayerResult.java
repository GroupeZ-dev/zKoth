package fr.maxlego08.koth.api.utils;

public class PlayerResult {

    private final String playerName;
    private final int points;
    private final String teamName;
    private final String teamId;
    private final String teamLeader;

    public PlayerResult(String playerName, int points, String teamName, String teamId, String teamLeader) {
        this.playerName = playerName;
        this.points = points;
        this.teamName = teamName;
        this.teamId = teamId;
        this.teamLeader = teamLeader;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPoints() {
        return points;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamId() {
        return teamId;
    }

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
