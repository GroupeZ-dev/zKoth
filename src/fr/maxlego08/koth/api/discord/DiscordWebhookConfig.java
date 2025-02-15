package fr.maxlego08.koth.api.discord;

import fr.maxlego08.koth.api.Koth;
import fr.maxlego08.koth.api.KothEvent;
import fr.maxlego08.koth.zcore.utils.ZUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DiscordWebhookConfig extends ZUtils {

    private final String url;
    private final boolean isEnable;
    private final List<DiscordWebhook.EmbedObject> embeds = new ArrayList<>();

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

    public String getUrl() {
        return url;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public List<DiscordWebhook.EmbedObject> getEmbeds() {
        return embeds;
    }

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
