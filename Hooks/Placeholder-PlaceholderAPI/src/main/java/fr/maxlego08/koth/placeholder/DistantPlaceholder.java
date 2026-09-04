package fr.maxlego08.koth.placeholder;

import fr.maxlego08.koth.api.LocalPlaceholderApi;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class DistantPlaceholder extends PlaceholderExpansion {

    private final LocalPlaceholderApi placeholder;

    public DistantPlaceholder(LocalPlaceholderApi placeholder) {
        super();
        this.placeholder = placeholder;
    }

    @Override
    public String getAuthor() {
        return this.placeholder.getPlugin().getPluginMeta().getAuthors().stream().findFirst().orElse("Maxlego08");
    }

    @Override
    public String getIdentifier() {
        return this.placeholder.getPrefix();
    }

    @Override
    public String getVersion() {
        return this.placeholder.getPlugin().getPluginMeta().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        return this.placeholder.onRequest(player, params);
    }

}
