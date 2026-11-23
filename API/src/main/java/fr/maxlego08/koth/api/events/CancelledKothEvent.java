package fr.maxlego08.koth.api.events;

import org.bukkit.event.Cancellable;

/**
 * Base class of the zKoth events that can be cancelled.
 */
public class CancelledKothEvent extends KothEvent implements Cancellable {

	private boolean cancelled;

	/**
	 * Creates a new cancellable zKoth event.
	 */
	public CancelledKothEvent() {
		super();
	}

	/**
	 * @return the cancelled
	 */
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * @param cancelled
	 *            the cancelled to set
	 */
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}
