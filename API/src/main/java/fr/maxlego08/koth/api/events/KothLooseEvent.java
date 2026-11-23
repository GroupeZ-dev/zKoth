package fr.maxlego08.koth.api.events;

import fr.maxlego08.koth.api.Koth;
import org.bukkit.entity.Player;


/**
 * Called when the capturing player loses control of a KOTH.
 */
public class KothLooseEvent extends CancelledKothEvent {

	private final Player player;
	private final Koth koth;

	/**
	 * Creates a new {@code KothLooseEvent}.
	 *
	 * @param player the player who lost the KOTH
	 * @param koth   the KOTH
	 */
	public KothLooseEvent(Player player, Koth koth) {
		super();
		this.player = player;
		this.koth = koth;
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
	 * Returns the koth.
	 *
	 * @return the koth
	 */
	public Koth getKoth() {
		return koth;
	}

}
