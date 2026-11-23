package fr.maxlego08.koth.api.events;

import fr.maxlego08.koth.api.Koth;
import org.bukkit.Location;

/**
 * Called when the zone of a KOTH is moved.
 */
public class KothMoveEvent extends CancelledKothEvent {

    private final Koth koth;
    private Location newMaxLocation;
    private Location newMinLocation;

    /**
     * Creates a new {@code KothMoveEvent}.
     *
     * @param koth           the moved KOTH
     * @param newMaxLocation the new maximum corner of the zone
     * @param newMinLocation the new minimum corner of the zone
     */
    public KothMoveEvent(Koth koth, Location newMaxLocation, Location newMinLocation) {
        super();
        this.koth = koth;
        this.newMaxLocation = newMaxLocation;
        this.newMinLocation = newMinLocation;
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
     * Returns the newMaxLocation.
     *
     * @return the newMaxLocation
     */
    public Location getNewMaxLocation() {
        return newMaxLocation;
    }

    /**
     * Sets the newMaxLocation.
     *
     * @param newMaxLocation the newMaxLocation to set
     */
    public void setNewMaxLocation(Location newMaxLocation) {
        this.newMaxLocation = newMaxLocation;
    }

    /**
     * Returns the newMinLocation.
     *
     * @return the newMinLocation
     */
    public Location getNewMinLocation() {
        return newMinLocation;
    }

    /**
     * Sets the newMinLocation.
     *
     * @param newMinLocation the newMinLocation to set
     */
    public void setNewMinLocation(Location newMinLocation) {
        this.newMinLocation = newMinLocation;
    }

}
