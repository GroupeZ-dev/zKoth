package fr.maxlego08.koth;

import fr.maxlego08.koth.api.Koth;
import fr.maxlego08.koth.api.KothHologram;
import fr.maxlego08.koth.api.KothScoreboard;
import fr.maxlego08.koth.command.commands.CommandKoth;
import fr.maxlego08.koth.hologram.EmptyHologram;
import fr.maxlego08.koth.hook.ScoreboardPlugin;
import fr.maxlego08.koth.hook.scoreboard.DefaultHook;
import fr.maxlego08.koth.placeholder.LocalPlaceholder;
import fr.maxlego08.koth.save.Config;
import fr.maxlego08.koth.save.MessageLoader;
import fr.maxlego08.koth.scoreboard.ScoreBoardManager;
import fr.maxlego08.koth.storage.StorageManager;
import fr.maxlego08.koth.zcore.ZPlugin;
import fr.maxlego08.koth.zcore.logger.Logger;
import fr.maxlego08.koth.zcore.utils.plugins.Plugins;
import dev.faststats.Metrics;
import dev.faststats.bukkit.BukkitContext;

import java.util.List;
import java.util.Optional;

/**
 * System to create your plugins very simply Projet:
 * <a href="https://github.com/Maxlego08/TemplatePlugin">https://github.com/Maxlego08/TemplatePlugin</a>
 *
 * @author Maxlego08
 */
public class KothPlugin extends ZPlugin implements fr.maxlego08.koth.api.KothPlugin {

    private final ScoreBoardManager scoreBoardManager = new ScoreBoardManager(this);
    private KothManager kothManager;
    private StorageManager storageManager;
    private KothScoreboard kothScoreboard = new DefaultHook();
    private KothHologram kothHologram = new EmptyHologram();
    private final BukkitContext context = new BukkitContext.Factory(this, "63d50ebba8e966319012d0b33bf74df3")
            .metrics(Metrics.Factory::create)
            .create();

    @Override
    public void onEnable() {

        LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
        placeholder.setPrefix("zkoth");

        this.preEnable();

        this.saveResource("koth-example.yml", true);

        this.storageManager = new StorageManager(this);
        this.kothManager = new KothManager(this);

        this.registerCommand("zkoth", new CommandKoth(this), "fr/maxlego08/koth");

        this.saveDefaultConfig();
        // this.addSave(Config.getInstance());
        this.addSave(new MessageLoader(this));
        this.addSave(this.kothManager);

        this.addListener(new KothListener(this, this.kothManager));

        Config.getInstance().load(this);
        this.loadFiles();

        for (ScoreboardPlugin value : ScoreboardPlugin.values()) {
            if (value.isEnable()) {
                kothScoreboard = value.init(this);
                Logger.info("Register " + value.getPluginName() + " scoreboard implementation.", Logger.LogType.INFO);
                break;
            }
        }
        this.scoreBoardManager.setScoreboard(this.kothScoreboard);

        if (this.isEnable(Plugins.ZSCHEDULERS)) {
            Logger.info("Register zScheduler implementation", Logger.LogType.INFO);
            try {
                Class<?> clazz = Class.forName("fr.maxlego08.koth.scheduler.ZkothImplementation");
                Object implementation = clazz.getConstructor(fr.maxlego08.koth.api.KothPlugin.class).newInstance(this);
                clazz.getMethod("register").invoke(implementation);
            } catch (Exception exception) {
                Logger.info("Failed to register zScheduler: " + exception.getMessage(), Logger.LogType.ERROR);
            }
        }

        if (this.isEnable(Plugins.DH)) {
            Logger.info("Register DecentHologram implementation", Logger.LogType.INFO);
            try {
                Class<?> clazz = Class.forName("fr.maxlego08.koth.hologram.DecentHologram");
                this.kothHologram = (KothHologram) clazz.getConstructor().newInstance();
            } catch (Exception exception) {
                Logger.info("Failed to register DecentHologram: " + exception.getMessage(), Logger.LogType.ERROR);
            }
        }

        if (this.isEnable(Plugins.FH)) {
            Logger.info("Register FancyHologram implementation", Logger.LogType.INFO);
            try {
                Class<?> clazz = Class.forName("fr.maxlego08.koth.hologram.FancyHologram");
                this.kothHologram = (KothHologram) clazz.getConstructor().newInstance();
            } catch (Exception exception) {
                Logger.info("Failed to register FancyHologram: " + exception.getMessage(), Logger.LogType.ERROR);
            }
        }

        KothPlaceholder kothPlaceholder = new KothPlaceholder(this.kothManager);
        kothPlaceholder.register();

        this.context.ready();

        this.postEnable();
    }

    @Override
    public void onDisable() {

        this.preDisable();

        this.kothHologram.onDisable();
        this.scoreBoardManager.setRunning(false);
        this.saveFiles();

        this.context.shutdown();

        this.postDisable();
    }

    public KothManager getKothManager() {
        return this.kothManager;
    }

    public KothScoreboard getKothScoreboard() {
        return this.kothScoreboard;
    }

    public StorageManager getStorageManager() {
        return this.storageManager;
    }

    public ScoreBoardManager getScoreBoardManager() {
        return scoreBoardManager;
    }

    public KothHologram getKothHologram() {
        return kothHologram;
    }

    @Override
    public List<Koth> getKoths() {
        return this.kothManager.getKoths();
    }

    @Override
    public Optional<Koth> getKoth(String name) {
        return this.kothManager.getKoth(name);
    }

    @Override
    public void onTeamDisband(String teamId) {
        this.storageManager.onTeamDisband(teamId);
    }
}
