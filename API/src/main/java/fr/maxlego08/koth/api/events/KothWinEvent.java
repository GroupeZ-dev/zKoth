package fr.maxlego08.koth.api.events;

import fr.maxlego08.koth.api.Koth;
import org.bukkit.entity.Player;


/**
 * Called when a player wins a KOTH.
 */
public class KothWinEvent extends CancelledKothEvent {

	private final Koth koth;
	private final Player player;

	/**
	 * Creates a new {@code KothWinEvent}.
	 *
	 * @param koth   the won KOTH
	 * @param player the winner
	 */
	public KothWinEvent(Koth koth, Player player) {
		super();
		this.koth = koth;
		this.player = player;
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

}
