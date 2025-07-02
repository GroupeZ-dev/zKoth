package fr.maxlego08.koth.zcore.utils.yaml;

import java.io.File;

import fr.maxlego08.koth.zcore.logger.Logger;
import fr.maxlego08.koth.zcore.utils.ZUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class YamlUtils extends ZUtils {

	protected transient final JavaPlugin plugin;

	/**
	 * @param plugin
	 */
	public YamlUtils(JavaPlugin plugin) {
		super();
		this.plugin = plugin;
	}

	/**
	 * 
	 * @return file confirguration
	 */
	protected FileConfiguration getConfig() {
		return plugin.getConfig();
	}

	protected YamlConfiguration getConfig(File file) {
		if (file == null)
			return null;
		return YamlConfiguration.loadConfiguration(file);
	}

	protected YamlConfiguration getConfig(String path) {
		File file = new File(plugin.getDataFolder() + "/" + path);
		if (!file.exists())
			return null;
		return getConfig(file);
	}

	/**
	 * Send info to console
	 * 
	 * @param message
	 */
	protected void info(String message) {
		Logger.info(message);
	}

	/**
	 * Send success to console
	 * 
	 * @param message
	 */
	protected void success(String message) {
		Logger.info(message, Logger.LogType.SUCCESS);
	}

	/**
	 * Send error to console
	 * 
	 * @param message
	 */
	protected void error(String message) {
		Logger.info(message, Logger.LogType.ERROR);
	}

	/**
	 * Send warn to console
	 * 
	 * @param message
	 */
	protected void warn(String message) {
		Logger.info(message, Logger.LogType.WARNING);
	}

}
