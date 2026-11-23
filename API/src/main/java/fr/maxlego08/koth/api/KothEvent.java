package fr.maxlego08.koth.api;

/**
 * Lifecycle events of a KOTH, used to select which Discord webhook embed should be sent.
 */
public enum KothEvent {

    /**
     * The KOTH has started and can now be captured.
     */
    START,
    /**
     * The KOTH has been stopped.
     */
    STOP,
    /**
     * A player has started capturing the KOTH.
     */
    START_CAP,
    /**
     * A player has won the KOTH.
     */
    WIN,
    /**
     * The KOTH has spawned (its countdown has begun).
     */
    SPAWN,
    /**
     * The capturing player has lost control of the KOTH.
     */
    LOOSE,

}
