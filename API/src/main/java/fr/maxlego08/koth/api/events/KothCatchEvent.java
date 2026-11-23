package fr.maxlego08.koth.api.events;

import fr.maxlego08.koth.api.Koth;
import org.bukkit.entity.Player;

/**
 * Called when a player starts capturing a KOTH.
 */
public class KothCatchEvent extends CancelledKothEvent {

    private final Koth koth;
    private final Player player;
    private int captureSeconds;

    /**
     * Creates a new {@code KothCatchEvent}.
     *
     * @param koth           the KOTH being captured
     * @param player         the player starting the capture
     * @param captureSeconds the number of seconds required to capture the KOTH
     */
    public KothCatchEvent(Koth koth, Player player, int captureSeconds) {
        super();
        this.koth = koth;
        this.player = player;
        this.captureSeconds = captureSeconds;
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
     * Returns the player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the captureSeconds.
     *
     * @return the captureSeconds
     */
    public int getCaptureSeconds() {
        return captureSeconds;
    }

    /**
     * Sets the captureSeconds.
     *
     * @param captureSeconds the captureSeconds to set
     */
    public void setCaptureSeconds(int captureSeconds) {
        this.captureSeconds = captureSeconds;
    }

}
