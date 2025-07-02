package fr.maxlego08.koth.api;

/**
 * The KothHologram interface specifies methods for managing holograms within a King of the Hill (KOTH) game mode.
 * It serves as a conduit between the KOTH plugin and hologram plugins, facilitating the creation, update, and removal
 * of holograms that display game-related information or visuals in the KOTH context.
 *
 * Implementing this interface allows hologram plugins to seamlessly integrate with KOTH game modes, providing a dynamic
 * and engaging player experience by visually representing game status, scores, or other relevant information through holograms.
 * This interface is crucial for plugins that aim to enhance the KOTH gameplay experience with interactive and informative holographic displays.
 *
 * <p>Note: Implementations should handle hologram lifecycle events carefully to ensure holograms are created, updated, and removed appropriately, avoiding any potential memory leaks or performance issues.</p>
 */
public interface KothHologram {

    /**
     * Initiates the display of a hologram for a specific KOTH game. This method is called when a KOTH game starts,
     * and it should handle the creation and initial display of the hologram associated with the given KOTH instance.
     *
     * @param koth The KOTH instance for which the hologram is to be displayed. This parameter cannot be null.
     */
    void start(Koth koth);

    /**
     * Ends the display of a hologram for a specific KOTH game. This method is called when a KOTH game ends,
     * and it should handle the removal or hiding of the hologram associated with the given KOTH instance.
     *
     * @param koth The KOTH instance for which the hologram display is to be ended. This parameter cannot be null.
     */
    void end(Koth koth);

    /**
     * Updates the hologram for a specific KOTH game. This method is called periodically or when there is a significant
     * event that requires the hologram display to be updated (e.g., score changes, time updates). Implementations should
     * refresh the hologram's content based on the current state of the given KOTH instance.
     *
     * @param koth The KOTH instance for which the hologram needs to be updated. This parameter cannot be null.
     */
    void update(Koth koth);

    /**
     * Handles the disabling of the hologram feature. This method is called when the plugin is disabled (e.g., server shutdown,
     * plugin reload) and should ensure that all active holograms are properly removed or cleaned up to prevent memory leaks
     * or lingering holograms in the game world.
     */
    void onDisable();
}

