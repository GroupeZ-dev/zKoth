package fr.maxlego08.koth.api.discord;

import fr.maxlego08.koth.api.Koth;
import fr.maxlego08.koth.api.KothEvent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Discord webhook configuration, loaded from the {@code webhook} section of a KOTH file.
 */
public class DiscordWebhookConfig {

    private final String url;
    private final boolean isEnable;
    private final List<DiscordWebhook.EmbedObject> embeds = new ArrayList<>();

    /**
     * Creates a new {@code DiscordWebhookConfig}.
     *
     * @param configuration the KOTH configuration to read from
     */
    public DiscordWebhookConfig(YamlConfiguration configuration) {
        super();
        this.url = configuration.getString("webhook.url", "https://discord.com/api/webhooks/xxxxx/xxxxxxxxxxxx");
        this.isEnable = configuration.getBoolean("webhook.enable", false);
        ConfigurationSection configurationSection = configuration.getConfigurationSection("webhook.events");
        if (configurationSection != null) {
            configurationSection.getKeys(false).forEach(key -> {
                String path = "webhook.events." + key + ".";
                this.embeds.add(new DiscordWebhook.EmbedObject(configuration, path));
            });
        }
    }

    /**
     * Returns the webhook URL.
     *
     * @return the webhook URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Returns whether the webhook is enabled.
     *
     * @return whether the webhook is enabled
     */
    public boolean isEnable() {
        return isEnable;
    }

    /**
     * Returns the configured embeds, one per {@link KothEvent}.
     *
     * @return the configured embeds, one per {@link KothEvent}
     */
    public List<DiscordWebhook.EmbedObject> getEmbeds() {
        return embeds;
    }

    /**
     * Sends the embed configured for the given event, if the webhook is enabled.
     *
     * @param plugin the plugin used to run the request
     * @param koth   the KOTH whose placeholders are applied to the embed
     * @param event  the event that triggered the message
     */
    public void send(Plugin plugin, Koth koth, KothEvent event) {

        if (this.isEnable) {

            DiscordWebhook discordWebhook = new DiscordWebhook(this.url);

            Optional<DiscordWebhook.EmbedObject> optional = this.embeds.stream().filter(e -> e.getEvent() == event).findFirst();
            if (optional.isEmpty()) return;

            discordWebhook.addEmbed(optional.get());

            try {
                discordWebhook.execute(plugin, koth);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

}
