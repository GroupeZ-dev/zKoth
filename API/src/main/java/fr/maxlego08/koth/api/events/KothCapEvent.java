package fr.maxlego08.koth.api.events;

import fr.maxlego08.koth.api.Koth;
import org.bukkit.entity.Player;


/**
 * Called every second while a player is capturing a KOTH.
 */
public class KothCapEvent extends KothEvent {

	private final Koth koth;
	private final Player player;
	private final int timer;
	private final String teamName;

	/**
	 * Creates a new {@code KothCapEvent}.
	 *
	 * @param koth     the KOTH being captured
	 * @param player   the capturing player
	 * @param timer    the current capture timer, in seconds
	 * @param teamName the name of the capturing player's team
	 */
	public KothCapEvent(Koth koth, Player player, int timer, String teamName) {
		super();
		this.koth = koth;
		this.player = player;
		this.timer = timer;
		this.teamName = teamName;
	}

	/**
	 * Returns the koth.
	 *
	 * @return the koth
	 */
	public Koth getKoth() {
		return koth;
	}

	/**
	 * Returns the player.
	 *
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Returns the timer.
	 *
	 * @return the timer
	 */
	public int getTimer() {
		return timer;
	}

	/**
	 * Returns the team name.
	 *
	 * @return the team name
	 */
	public String getTeamName() {
		return teamName;
	}

}
