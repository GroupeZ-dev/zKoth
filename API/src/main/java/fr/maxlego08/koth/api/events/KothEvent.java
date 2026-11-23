package fr.maxlego08.koth.api.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Base class of every zKoth Bukkit event.
 */
public class KothEvent extends Event {

	private final static HandlerList handlers = new HandlerList();

	/**
	 * Creates a new synchronous zKoth event.
	 */
	public KothEvent() {
		super();
	}

	/**
	 * @return the handlers
	 */
	public HandlerList getHandlers() {
		return handlers;
	}

	/**
	 * Returns the handler list shared by every zKoth event, required by Bukkit.
	 *
	 * @return the handler list
	 */
	public static HandlerList getHandlerList() {
		return handlers;
	}

	/**
	 * Calls this event through the Bukkit plugin manager.
	 */
	public void call(){
		Bukkit.getPluginManager().callEvent(this);
	}

}
