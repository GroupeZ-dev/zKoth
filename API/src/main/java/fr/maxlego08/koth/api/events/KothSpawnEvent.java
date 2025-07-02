package fr.maxlego08.koth.api.events;

import fr.maxlego08.koth.api.Koth;

public class KothSpawnEvent extends CancelledKothEvent {

	private final Koth koth;

	public KothSpawnEvent(Koth koth) {
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
