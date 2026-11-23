package fr.maxlego08.koth.api.events;

import fr.maxlego08.koth.api.Koth;

/**
 * Called when a KOTH spawns.
 */
public class KothSpawnEvent extends CancelledKothEvent {

	private final Koth koth;

	/**
	 * Creates a new {@code KothSpawnEvent}.
	 *
	 * @param koth the spawning KOTH
	 */
	public KothSpawnEvent(Koth koth) {
		super();
		this.koth = koth;
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
