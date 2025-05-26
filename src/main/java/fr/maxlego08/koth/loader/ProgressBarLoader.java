package fr.maxlego08.koth.loader;

import fr.maxlego08.koth.zcore.utils.ProgressBar;
import fr.maxlego08.koth.zcore.utils.ZUtils;
import fr.maxlego08.koth.zcore.utils.loader.Loader;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ProgressBarLoader extends ZUtils implements Loader<ProgressBar> {

    @Override
    public ProgressBar load(YamlConfiguration configuration, String path, File file) {

        int length = configuration.getInt(path + "length", 10);
        char symbol = configuration.getString(path + "symbol", "-").charAt(0);
        String completedColor = configuration.getString(path + "completedColor", "&a");
        String notCompletedColor = configuration.getString(path + "notCompletedColor", "&7");

        return new ProgressBar(length, symbol, completedColor, notCompletedColor);
    }

    @Override
    public void save(ProgressBar progressBar, YamlConfiguration configuration, String path) {
        configuration.set(path + "length", progressBar.getLength());
        configuration.set(path + "symbol", progressBar.getSymbol());
        configuration.set(path + "completedColor", progressBar.getCompletedColor());
        configuration.set(path + "notCompletedColor", progressBar.getNotCompletedColor());
    }
}
