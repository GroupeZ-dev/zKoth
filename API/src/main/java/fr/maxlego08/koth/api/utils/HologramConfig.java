package fr.maxlego08.koth.api.utils;

import org.bukkit.Location;

import java.util.List;

/**
 * Hologram configuration of a KOTH.
 */
public class HologramConfig {

    private final boolean isEnable;
    private final List<String> lines;
    private final Location location;

    /**
     * Creates a new {@code HologramConfig}.
     *
     * @param isEnable whether the hologram is enabled
     * @param lines    the hologram lines
     * @param location the hologram location
     */
    public HologramConfig(boolean isEnable, List<String> lines, Location location) {
        this.isEnable = isEnable;
        this.lines = lines;
        this.location = location;
    }

    /**
     * Returns the location.
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns whether the hologram is enabled.
     *
     * @return whether the hologram is enabled
     */
    public boolean isEnable() {
        return isEnable;
    }

    /**
     * Returns the lines.
     *
     * @return the lines
     */
    public List<String> getLines() {
        return lines;
    }

}
