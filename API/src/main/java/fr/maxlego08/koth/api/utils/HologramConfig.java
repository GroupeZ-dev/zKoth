package fr.maxlego08.koth.api.utils;

import org.bukkit.Location;

import java.util.List;

public class HologramConfig {

    private final boolean isEnable;
    private final List<String> lines;
    private final Location location;

    public HologramConfig(boolean isEnable, List<String> lines, Location location) {
        this.isEnable = isEnable;
        this.lines = lines;
        this.location = location;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return the isEnable
     */
    public boolean isEnable() {
        return isEnable;
    }

    /**
     * @return the linge
     */
    public List<String> getLines() {
        return lines;
    }

}
