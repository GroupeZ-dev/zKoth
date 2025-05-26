package fr.maxlego08.koth.zcore.utils.loader;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public interface Loader<T> {

	/**
	 * Load object from yml
	 *
	 * @param configuration
	 * @param path
	 * @return element
	 */
	T load(YamlConfiguration configuration, String path, File file);
	
	/**
	 * Save object to yml
	 *
	 * @param object
	 * @param configuration
	 * @param path
	 */
	void save(T object, YamlConfiguration configuration, String path);
	
}
