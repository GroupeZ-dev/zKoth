package fr.maxlego08.koth.api.events;

import fr.maxlego08.koth.api.Koth;

public class KothStopEvent extends CancelledKothEvent {

	private final Koth koth;

	public KothStopEvent(Koth koth) {
		super();
		this.koth = koth;
	}

	/**
	 * @return the koth
	 */
	public Koth getKoth() {
		return koth;
	}

}
