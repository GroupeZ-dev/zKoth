package fr.maxlego08.koth.api.events;

import fr.maxlego08.koth.api.Koth;

/**
 * Called when a KOTH is stopped.
 */
public class KothStopEvent extends CancelledKothEvent {

	private final Koth koth;

	/**
	 * Creates a new {@code KothStopEvent}.
	 *
	 * @param koth the stopped KOTH
	 */
	public KothStopEvent(Koth koth) {
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
