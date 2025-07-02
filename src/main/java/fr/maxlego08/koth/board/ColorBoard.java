package fr.maxlego08.koth.board;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ColorBoard implements Board {

    private final Team redTeam;
    private final Team greenTeam;

    public ColorBoard() {

        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        this.greenTeam = board.getTeams().stream().filter(e -> e.getName().equals("zkothgreenteam")).findFirst().orElseGet(() -> board.registerNewTeam("zkothgreenteam"));
        this.greenTeam.setColor(ChatColor.GREEN);

        this.redTeam = board.getTeams().stream().filter(e -> e.getName().equals("zkothredteam")).findFirst().orElseGet(() -> board.registerNewTeam("zkothredteam"));
        this.redTeam.setColor(ChatColor.RED);

    }

    @Override
    public void addEntity(boolean isGreen, LivingEntity entity) {

        if (entity == null) {
            return;
        }

        String uuid = entity.getUniqueId().toString();

        this.redTeam.removeEntry(uuid);
        this.greenTeam.removeEntry(uuid);

        if (isGreen) {
            this.greenTeam.addEntry(uuid);
        } else {
            this.redTeam.addEntry(uuid);
        }
    }

}
