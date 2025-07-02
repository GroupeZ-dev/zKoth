package fr.maxlego08.koth.api.discord;

import java.awt.Color;

import org.bukkit.configuration.file.YamlConfiguration;

public class DiscordColor {

	private final int r;
	private final int g;
	private final int b;

	public DiscordColor(int r, int g, int b) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public DiscordColor(YamlConfiguration configuration, String path) {
		this(configuration.getInt(path + "r"), configuration.getInt(path + "g"), configuration.getInt(path + "b"));
	}

	/**
	 * @return the r
	 */
	public int getR() {
		return r;
	}

	/**
	 * @return the g
	 */
	public int getG() {
		return g;
	}

	/**
	 * @return the b
	 */
	public int getB() {
		return b;
	}

	public Color getColor() {
		return new Color(this.r, this.g, this.b);
	}

}
