package fr.maxlego08.koth.loader;

import fr.maxlego08.koth.api.utils.HologramConfig;
import fr.maxlego08.koth.zcore.utils.loader.Loader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class HologramLoader implements Loader<HologramConfig> {

    @Override
    public HologramConfig load(YamlConfiguration configuration, String path, File file) {

        String worldName = configuration.getString(path + "world", "world");
        int x = configuration.getInt(path + "x");
        int y = configuration.getInt(path + "y");
        int z = configuration.getInt(path + "z");
        boolean isEnable = configuration.getBoolean(path + "enable");
        List<String> lines = configuration.getStringList(path + "lines");

        Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
        return new HologramConfig(isEnable, lines, location);
    }

    @Override
    public void save(HologramConfig hologramConfig, YamlConfiguration configuration, String path) {

        Location location = hologramConfig.getLocation();
        configuration.set(path + "enable", hologramConfig.isEnable());
        configuration.set(path + "lines", hologramConfig.getLines());
        configuration.set(path + "world", location.getWorld().getName());
        configuration.set(path + "x", location.getBlockX());
        configuration.set(path + "y", location.getBlockY());
        configuration.set(path + "z", location.getBlockZ());

    }
}
