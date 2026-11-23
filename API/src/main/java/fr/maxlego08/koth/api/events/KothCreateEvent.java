package fr.maxlego08.koth.api.events;

import fr.maxlego08.koth.api.Koth;

/**
 * Called when a KOTH is created.
 */
public class KothCreateEvent extends CancelledKothEvent {

    private final Koth koth;

    /**
     * Creates a new {@code KothCreateEvent}.
     *
     * @param koth the created KOTH
     */
    public KothCreateEvent(Koth koth) {
        super();
        this.koth = koth;
    }

    /**
     * Returns the koth.
     *
     * @return the koth
     */
    public Koth getKoth() {
        return koth;
    }

}
