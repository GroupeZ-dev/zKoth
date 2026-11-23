package fr.maxlego08.koth.api.events;


import fr.maxlego08.koth.api.Koth;

/**
 * Called when a KOTH starts and becomes capturable.
 */
public class KothStartEvent extends CancelledKothEvent {

    private final Koth koth;

    /**
     * Creates a new {@code KothStartEvent}.
     *
     * @param koth the starting KOTH
     */
    public KothStartEvent(Koth koth) {
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
