package fr.maxlego08.koth.api.events;

import fr.maxlego08.koth.api.Koth;
import org.bukkit.Location;

public class KothMoveEvent extends CancelledKothEvent {

    private final Koth koth;
    private Location newMaxLocation;
    private Location newMinLocation;

    /**
     * @param koth
     * @param newMaxLocation
     * @param newMinLocation
     */
    public KothMoveEvent(Koth koth, Location newMaxLocation, Location newMinLocation) {
        super();
        this.koth = koth;
        this.newMaxLocation = newMaxLocation;
        this.newMinLocation = newMinLocation;
    }

    /**
     * @return the koth
     */
    public Koth getKoth() {
        return koth;
    }

    /**
     * @return the newMaxLocation
     */
    public Location getNewMaxLocation() {
        return newMaxLocation;
    }

    /**
     * @param newMaxLocation the newMaxLocation to set
     */
    public void setNewMaxLocation(Location newMaxLocation) {
        this.newMaxLocation = newMaxLocation;
    }

    /**
     * @return the newMinLocation
     */
    public Location getNewMinLocation() {
        return newMinLocation;
    }

    /**
     * @param newMinLocation the newMinLocation to set
     */
    public void setNewMinLocation(Location newMinLocation) {
        this.newMinLocation = newMinLocation;
    }

}
