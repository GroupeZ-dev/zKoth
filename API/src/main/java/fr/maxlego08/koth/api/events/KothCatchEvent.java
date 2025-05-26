package fr.maxlego08.koth.api.events;

import fr.maxlego08.koth.api.Koth;
import org.bukkit.entity.Player;

public class KothCatchEvent extends CancelledKothEvent {

    private final Koth koth;
    private final Player player;
    private int captureSeconds;

    public KothCatchEvent(Koth koth, Player player, int captureSeconds) {
        super();
        this.koth = koth;
        this.player = player;
        this.captureSeconds = captureSeconds;
    }

    /**
     * @return the koth
     */
    public Koth getKoth() {
        return koth;
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return the captureSeconds
     */
    public int getCaptureSeconds() {
        return captureSeconds;
    }

    /**
     * @param captureSeconds the captureSeconds to set
     */
    public void setCaptureSeconds(int captureSeconds) {
        this.captureSeconds = captureSeconds;
    }

}
