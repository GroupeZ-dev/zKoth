package fr.maxlego08.koth.api.discord;

import java.awt.Color;

import org.bukkit.configuration.file.YamlConfiguration;

/**
 * RGB color of a Discord embed.
 */
public class DiscordColor {

	private final int r;
	private final int g;
	private final int b;

	/**
	 * Creates a new {@code DiscordColor}.
	 *
	 * @param r the red component (0-255)
	 * @param g the green component (0-255)
	 * @param b the blue component (0-255)
	 */
	public DiscordColor(int r, int g, int b) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
	}

	/**
	 * Loads a color from the {@code r}, {@code g} and {@code b} keys under the given path.
	 *
	 * @param configuration the configuration to read from
	 * @param path          the path prefix, ending with a dot
	 */
	public DiscordColor(YamlConfiguration configuration, String path) {
		this(configuration.getInt(path + "r"), configuration.getInt(path + "g"), configuration.getInt(path + "b"));
	}

	/**
	 * Returns the red component.
	 *
	 * @return the red component
	 */
	public int getR() {
		return r;
	}

	/**
	 * Returns the green component.
	 *
	 * @return the green component
	 */
	public int getG() {
		return g;
	}

	/**
	 * Returns the blue component.
	 *
	 * @return the blue component
	 */
	public int getB() {
		return b;
	}

	/**
	 * Returns this color as an AWT color.
	 *
	 * @return this color as an AWT color
	 */
	public Color getColor() {
		return new Color(this.r, this.g, this.b);
	}

}
