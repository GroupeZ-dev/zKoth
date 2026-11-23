package fr.maxlego08.koth.api.discord;

import fr.maxlego08.koth.api.Koth;
import fr.maxlego08.koth.api.KothEvent;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Class used to execute Discord Webhooks with low effort
 */
public class DiscordWebhook {

    private final String url;
    private transient final Pattern STRIP_EXTRAS_PATTERN = Pattern.compile("(?i)§[0-9A-FK-ORX]");
    private final List<EmbedObject> embeds = new ArrayList<>();
    private String content;
    private String username;
    private String avatarUrl;
    private boolean tts;

    /**
     * Constructs a new DiscordWebhook instance
     *
     * @param url The webhook URL obtained in Discord
     */
    public DiscordWebhook(String url) {
        this.url = url;
    }

    /**
     * Sets the plain text content of the message.
     *
     * @param content the message content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Overrides the webhook username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Overrides the webhook avatar.
     *
     * @param avatarUrl the avatar URL
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * Enables or disables text-to-speech.
     *
     * @param tts true to send the message as TTS
     */
    public void setTts(boolean tts) {
        this.tts = tts;
    }

    /**
     * Adds an embed to the message.
     *
     * @param embed the embed to add
     */
    public void addEmbed(EmbedObject embed) {
        this.embeds.add(embed);
    }

    /**
     * Sends the message asynchronously, replacing the KOTH placeholders.
     *
     * @param plugin the plugin used to schedule the asynchronous request
     * @param koth   the KOTH whose placeholders are applied
     * @throws IOException if the message cannot be built
     * @throws IllegalArgumentException if neither content nor embed is set
     */
    public void execute(Plugin plugin, Koth koth) throws IOException {
        if (this.content == null && this.embeds.isEmpty()) {
            throw new IllegalArgumentException("Set content or add at least one EmbedObject");
        }

        JSONObject json = new JSONObject();

        if (this.content != null) json.put("content", koth.replaceMessage(this.content));
        if (this.username != null) json.put("username", koth.replaceMessage(this.username));
        if (this.avatarUrl != null) json.put("avatar_url", this.avatarUrl);
        if (this.tts) json.put("tts", true);

        if (!this.embeds.isEmpty()) {
            List<JSONObject> embedObjects = new ArrayList<>();

            for (EmbedObject embed : this.embeds) {
                JSONObject jsonEmbed = new JSONObject();

                jsonEmbed.put("title", koth.replaceMessage(embed.getTitle()));
                jsonEmbed.put("description", koth.replaceMessage(embed.getDescription()));
                jsonEmbed.put("url", koth.replaceMessage(embed.getUrl()));

                if (embed.getColor() != null) {
                    Color color = embed.getColor().getColor();
                    int rgb = color.getRed();
                    rgb = (rgb << 8) + color.getGreen();
                    rgb = (rgb << 8) + color.getBlue();

                    jsonEmbed.put("color", rgb);
                }

                EmbedObject.Footer footer = embed.getFooter();
                String image = embed.getImage();
                String thumbnail = embed.getThumbnail();
                EmbedObject.Author author = embed.getAuthor();
                List<EmbedObject.Field> fields = embed.getFields();

                if (footer != null) {
                    JSONObject jsonFooter = new JSONObject();
                    if (footer.getText() != null) {
                        jsonFooter.put("text", koth.replaceMessage(footer.getText()));
                    }
                    if (footer.getIconUrl() != null) {
                        jsonFooter.put("icon_url", koth.replaceMessage(footer.getIconUrl()));
                    }
                    if (footer.getIconUrl() != null || footer.getText() != null) {
                        jsonEmbed.put("footer", jsonFooter);
                    }
                }

                if (image != null) {
                    JSONObject jsonImage = new JSONObject();
                    jsonImage.put("url", koth.replaceMessage(image));
                    jsonEmbed.put("image", jsonImage);
                }

                if (thumbnail != null) {
                    JSONObject jsonThumbnail = new JSONObject();
                    jsonThumbnail.put("url", koth.replaceMessage(thumbnail));
                    jsonEmbed.put("thumbnail", jsonThumbnail);
                }

                if (author != null) {

                    if (author.getName() != null && author.getUrl() != null && author.getIconUrl() != null) {
                        JSONObject jsonAuthor = new JSONObject();

                        jsonAuthor.put("url", koth.replaceMessage(author.getUrl()));
                        jsonAuthor.put("name", koth.replaceMessage(author.getName()));
                        jsonAuthor.put("icon_url", koth.replaceMessage(author.getIconUrl()));

                        jsonEmbed.put("author", jsonAuthor);
                    }
                }

                List<JSONObject> jsonFields = new ArrayList<>();
                for (EmbedObject.Field field : fields) {
                    JSONObject jsonField = new JSONObject();

                    jsonField.put("name", koth.replaceMessage(field.getName()));
                    jsonField.put("value", koth.replaceMessage(field.getValue()));
                    jsonField.put("inline", field.isInline());

                    jsonFields.add(jsonField);
                }

                jsonEmbed.put("fields", jsonFields.toArray());
                embedObjects.add(jsonEmbed);
            }

            json.put("embeds", embedObjects.toArray());
        }

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URL url = new URI(this.url).toURL();
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.addRequestProperty("Content-Type", "application/json");
                connection.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_");
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                OutputStream stream = connection.getOutputStream();
                stream.write(json.toString().getBytes(StandardCharsets.UTF_8));
                stream.flush();
                stream.close();

                connection.getInputStream().close();
                connection.disconnect();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    /**
     * Discord embed bound to a {@link KothEvent}.
     */
    public static class EmbedObject {

        private final KothEvent event;
        private final List<Field> fields;
        private String title;
        private String description;
        private String url;
        private DiscordColor color;
        private Footer footer;
        private String thumbnail;
        private String image;
        private Author author;

        /**
         * Loads an embed from the configuration.
         *
         * @param configuration the configuration to read from
         * @param path          the path prefix of the embed, ending with a dot
         */
        public EmbedObject(YamlConfiguration configuration, String path) {

            KothEvent event;
            try {
                event = KothEvent.valueOf(configuration.getString(path + "event", "START").toUpperCase());
            } catch (IllegalArgumentException ignored) {
                event = KothEvent.START;
            }
            this.event = event;

            this.title = configuration.getString(path + "title", null);
            this.description = configuration.getString(path + "description", null);
            this.url = configuration.getString(path + "url", null);
            this.color = new DiscordColor(configuration, path + "color.");
            this.footer = new Footer(configuration, path);
            this.thumbnail = configuration.getString(path + "thumbnail", null);
            this.image = configuration.getString(path + "image", null);
            this.author = new Author(configuration, path);
            List<?> list = configuration.getList(path + "fields", new ArrayList<>());
            this.fields = ((List<Map<String, Object>>) list).stream().map(Field::new).collect(Collectors.toList());
        }

        /**
         * Returns the event that triggers this embed.
         *
         * @return the event that triggers this embed
         */
        public KothEvent getEvent() {
            return event;
        }

        /**
         * Returns the title.
         *
         * @return the title
         */
        public String getTitle() {
            return title;
        }

        /**
         * Returns this embed.
         *
         * @param title the title
         * @return this embed
         */
        public EmbedObject setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Returns the description.
         *
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * Returns this embed.
         *
         * @param description the description
         * @return this embed
         */
        public EmbedObject setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Returns the URL of the title.
         *
         * @return the URL of the title
         */
        public String getUrl() {
            return url;
        }

        /**
         * Returns this embed.
         *
         * @param url the URL of the title
         * @return this embed
         */
        public EmbedObject setUrl(String url) {
            this.url = url;
            return this;
        }

        /**
         * Returns the color.
         *
         * @return the color
         */
        public DiscordColor getColor() {
            return color;
        }

        /**
         * Returns this embed.
         *
         * @param color the color
         * @return this embed
         */
        public EmbedObject setColor(DiscordColor color) {
            this.color = color;
            return this;
        }

        /**
         * Returns the footer.
         *
         * @return the footer
         */
        public Footer getFooter() {
            return footer;
        }

        /**
         * Returns the thumbnail URL.
         *
         * @return the thumbnail URL
         */
        public String getThumbnail() {
            return thumbnail;
        }

        /**
         * Returns this embed.
         *
         * @param url the thumbnail URL
         * @return this embed
         */
        public EmbedObject setThumbnail(String url) {
            this.thumbnail = url;
            return this;
        }

        /**
         * Returns the image URL.
         *
         * @return the image URL
         */
        public String getImage() {
            return image;
        }

        /**
         * Returns this embed.
         *
         * @param url the image URL
         * @return this embed
         */
        public EmbedObject setImage(String url) {
            this.image = url;
            return this;
        }

        /**
         * Returns the author.
         *
         * @return the author
         */
        public Author getAuthor() {
            return author;
        }

        /**
         * Returns the fields.
         *
         * @return the fields
         */
        public List<Field> getFields() {
            return fields;
        }

        /**
         * Returns this embed.
         *
         * @param text the footer text
         * @param icon the footer icon URL
         * @return this embed
         */
        public EmbedObject setFooter(String text, String icon) {
            this.footer = new Footer(text, icon);
            return this;
        }

        /**
         * Returns this embed.
         *
         * @param name the author name
         * @param url  the author URL
         * @param icon the author icon URL
         * @return this embed
         */
        public EmbedObject setAuthor(String name, String url, String icon) {
            this.author = new Author(name, url, icon);
            return this;
        }

        /**
         * Returns this embed.
         *
         * @param name   the field name
         * @param value  the field value
         * @param inline whether the field is displayed inline
         * @return this embed
         */
        public EmbedObject addField(String name, String value, boolean inline) {
            this.fields.add(new Field(name, value, inline));
            return this;
        }

        @Override
        public String toString() {
            return "EmbedObject{" + "event=" + event + ", title='" + title + '\'' + ", description='" + description + '\'' + ", url='" + url + '\'' + ", color=" + color + ", footer=" + footer + ", thumbnail='" + thumbnail + '\'' + ", image='" + image + '\'' + ", author=" + author + ", fields=" + fields + '}';
        }

        private class Footer {
            private final String text;
            private final String iconUrl;

            private Footer(String text, String iconUrl) {
                this.text = text;
                this.iconUrl = iconUrl;
            }

            public Footer(YamlConfiguration configuration, String path) {
                this(configuration.getString(path + "text"), configuration.getString(path + "iconUrl"));
            }

            private String getText() {
                return text;
            }

            private String getIconUrl() {
                return iconUrl;
            }
        }

        private class Author {
            private final String name;
            private final String url;
            private final String iconUrl;

            private Author(String name, String url, String iconUrl) {
                this.name = name;
                this.url = url;
                this.iconUrl = iconUrl;
            }

            public Author(YamlConfiguration configuration, String path) {
                this(configuration.getString(path + "name"), configuration.getString(path + "url"), configuration.getString(path + "iconUrl"));
            }

            private String getName() {
                return name;
            }

            private String getUrl() {
                return url;
            }

            private String getIconUrl() {
                return iconUrl;
            }
        }

        private class Field {
            private final String name;
            private final String value;
            private final boolean inline;

            private Field(String name, String value, boolean inline) {
                this.name = name;
                this.value = value;
                this.inline = inline;
            }

            private Field(Map<String, Object> map) {
                this((String) map.get("name"), (String) map.get("value"), (boolean) map.get("inline"));
            }

            private String getName() {
                return name;
            }

            private String getValue() {
                return value;
            }

            private boolean isInline() {
                return inline;
            }
        }
    }

    private class JSONObject {

        private final HashMap<String, Object> map = new HashMap<>();

        void put(String key, Object value) {
            if (value != null) {
                map.put(key, value);
            }
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            Set<Map.Entry<String, Object>> entrySet = map.entrySet();
            builder.append("{");

            int i = 0;
            for (Map.Entry<String, Object> entry : entrySet) {
                Object val = entry.getValue();
                builder.append(quote(entry.getKey())).append(":");

                if (val instanceof String) {
                    builder.append(quote(String.valueOf(val)));
                } else if (val instanceof Integer) {
                    builder.append(Integer.valueOf(String.valueOf(val)));
                } else if (val instanceof Boolean) {
                    builder.append(val);
                } else if (val instanceof JSONObject) {
                    builder.append(val);
                } else if (val.getClass().isArray()) {
                    builder.append("[");
                    int len = Array.getLength(val);
                    for (int j = 0; j < len; j++) {
                        builder.append(Array.get(val, j).toString()).append(j != len - 1 ? "," : "");
                    }
                    builder.append("]");
                }

                builder.append(++i == entrySet.size() ? "}" : ",");
            }

            return builder.toString();
        }

        private String quote(String string) {
            return "\"" + string + "\"";
        }
    }

}