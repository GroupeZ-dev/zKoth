package fr.maxlego08.koth.api;

import org.bukkit.entity.Player;

import java.util.function.Consumer;


/**
 * The KothScoreboard interface defines methods for managing the visibility of the scoreboard for players in a King of the Hill (KOTH) game mode.
 * It acts as an intermediary between the KOTH plugin and other scoreboard plugins, enabling the dynamic display of game-related information
 * on the player's scoreboard.
 *
 * Implementing this interface allows scoreboard plugins to easily integrate with KOTH game modes, providing a seamless experience for
 * players by showing or hiding scoreboards as necessary. This interface is crucial for plugins that wish to offer customizable scoreboard
 * features within the context of KOTH matches.
 *
 * <p>Note: Implementations of this interface should ensure thread-safety if scoreboards are toggled or hidden asynchronously.</p>
 */
public interface KothScoreboard {

	/**
	 * Toggles the visibility of the scoreboard for the given player. If the scoreboard is currently visible, it will be hidden, and vice versa.
	 * The {@code after} consumer is called after the toggle operation is completed, allowing for further actions to be taken.
	 *
	 * @param player The player for whom the scoreboard visibility will be toggled. This parameter cannot be null.
	 * @param after A {@code Consumer<Player>} that is executed after the scoreboard's visibility has been toggled, receiving the player as its parameter.
	 *              This allows for additional operations to be performed after the toggle action. Can be {@code null} if no action is required afterwards.
	 */
	void toggle(Player player, Consumer<Player> after);

	/**
	 * Hides the scoreboard for the given player, ensuring that it is not visible. The {@code after} consumer is called after the hide operation is completed,
	 * allowing for further actions to be taken.
	 *
	 * @param player The player for whom the scoreboard will be hidden. This parameter cannot be null.
	 * @param after A {@code Consumer<Player>} that is executed after the scoreboard has been hidden, receiving the player as its parameter.
	 *              This allows for additional operations to be performed after hiding the scoreboard. Can be {@code null} if no action is required afterwards.
	 */
	void hide(Player player, Consumer<Player> after);
}

