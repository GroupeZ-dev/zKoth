package fr.maxlego08.koth.zcore.utils.players;

import fr.maxlego08.koth.zcore.utils.LegacyText;
import org.bukkit.entity.Player;

public class ActionBar {
    public static void sendActionBar(Player player, String message) {
        if (player.isOnline()) player.sendActionBar(LegacyText.component(message));
    }
}
