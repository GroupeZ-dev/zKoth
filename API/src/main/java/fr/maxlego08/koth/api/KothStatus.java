package fr.maxlego08.koth.api;

/**
 * Current state of a KOTH.
 */
public enum KothStatus {

    /**
     * The KOTH has spawned and is waiting for its countdown to end before starting.
     */
    COOLDOWN,
    /**
     * The KOTH is running and can be captured.
     */
    START,
    /**
     * The KOTH is not running.
     */
    STOP,

}
