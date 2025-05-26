package fr.maxlego08.koth.api.events;

import fr.maxlego08.koth.api.Koth;

public class KothCreateEvent extends CancelledKothEvent {

    private final Koth koth;

    /**
     * @param koth
     */
    public KothCreateEvent(Koth koth) {
        super();
        this.koth = koth;
    }

    /**
     * @return the koth
     */
    public Koth getKoth() {
        return koth;
    }

}
