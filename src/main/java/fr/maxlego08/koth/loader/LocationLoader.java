package fr.maxlego08.koth.loader;

import fr.maxlego08.koth.zcore.utils.loader.Loader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LocationLoader implements Loader<Location> {
    @Override
    public Location load(YamlConfiguration configuration, String path, File file) {

        String worldName = configuration.getString(path + "world", "world");
        int x = configuration.getInt(path + "x");
        int y = configuration.getInt(path + "y");
        int z = configuration.getInt(path + "z");

        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }

    @Override
    public void save(Location location, YamlConfiguration configuration, String path) {

        configuration.set(path + "world", location.getWorld().getName());
        configuration.set(path + "x", location.getBlockX());
        configuration.set(path + "y", location.getBlockY());
        configuration.set(path + "z", location.getBlockZ());

    }
}
