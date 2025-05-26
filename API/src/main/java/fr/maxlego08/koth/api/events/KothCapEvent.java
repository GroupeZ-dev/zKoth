package fr.maxlego08.koth.api.events;

import fr.maxlego08.koth.api.Koth;
import org.bukkit.entity.Player;


public class KothCapEvent extends KothEvent {

	private final Koth koth;
	private final Player player;
	private final int timer;
	private final String teamName;

	public KothCapEvent(Koth koth, Player player, int timer, String teamName) {
		super();
		this.koth = koth;
		this.player = player;
		this.timer = timer;
		this.teamName = teamName;
	}

	/**
	 * @return the koth
	 */
	public Koth getKoth() {
		return koth;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the timer
	 */
	public int getTimer() {
		return timer;
	}

	/**
	 * @return the factionName
	 */
	public String getTeamName() {
		return teamName;
	}

}
