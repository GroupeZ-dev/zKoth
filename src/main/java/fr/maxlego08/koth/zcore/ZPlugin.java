package fr.maxlego08.koth.zcore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.maxlego08.koth.exceptions.ListenerNullException;
import fr.maxlego08.koth.inventory.ZInventoryManager;
import fr.maxlego08.koth.placeholder.LocalPlaceholder;
import fr.maxlego08.koth.placeholder.Placeholder;
import fr.maxlego08.koth.zcore.enums.EnumInventory;
import fr.maxlego08.koth.zcore.logger.Logger;
import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.command.CommandManager;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.inventory.VInventory;
import fr.maxlego08.koth.listener.AdapterListener;
import fr.maxlego08.koth.listener.ListenerAdapter;
import fr.maxlego08.koth.zcore.utils.gson.LocationAdapter;
import fr.maxlego08.koth.zcore.utils.gson.PotionEffectAdapter;
import fr.maxlego08.koth.zcore.utils.plugins.Plugins;
import fr.maxlego08.koth.zcore.utils.storage.NoReloadable;
import fr.maxlego08.koth.zcore.utils.storage.Persist;
import fr.maxlego08.koth.zcore.utils.storage.Savable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ZPlugin extends JavaPlugin {

    public static final ExecutorService service = Executors.newFixedThreadPool(5);
    private final Logger log = new Logger(this.getDescription().getFullName());
    private final List<Savable> savers = new ArrayList<>();
    private final List<ListenerAdapter> listenerAdapters = new ArrayList<>();

    private Gson gson;
    private Persist persist;
    private long enableTime;

    protected CommandManager commandManager;
    protected ZInventoryManager inventoryManager;

    protected void preEnable() {

        LocalPlaceholder.getInstance().setPlugin((KothPlugin) this);
        Placeholder.getPlaceholder();

        this.enableTime = System.currentTimeMillis();

        this.log.log("=== ENABLE START ===");
        this.log.log("Plugin Version V<&>c" + getDescription().getVersion(), Logger.LogType.INFO);

        this.getDataFolder().mkdirs();

        this.gson = getGsonBuilder().create();
        this.persist = new Persist(this);

        this.commandManager = new CommandManager((KothPlugin) this);
        this.inventoryManager = new ZInventoryManager((KothPlugin) this);

        /* Add Listener */
        this.addListener(new AdapterListener((KothPlugin) this));
        this.addListener(this.inventoryManager);
    }

    protected void postEnable() {

        if (this.inventoryManager != null) {
            this.inventoryManager.sendLog();
        }

        if (this.commandManager != null) {
            this.commandManager.validCommands();
        }

        this.log.log(
                "=== ENABLE DONE <&>7(<&>6" + Math.abs(enableTime - System.currentTimeMillis()) + "ms<&>7) <&>e===");

    }

    protected void preDisable() {
        this.enableTime = System.currentTimeMillis();
        this.log.log("=== DISABLE START ===");
    }

    protected void postDisable() {
        this.log.log(
                "=== DISABLE DONE <&>7(<&>6" + Math.abs(enableTime - System.currentTimeMillis()) + "ms<&>7) <&>e===");

    }

    /**
     * Build gson
     *
     * @return
     */
    public GsonBuilder getGsonBuilder() {
        return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().serializeNulls()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE)
                .registerTypeAdapter(PotionEffect.class, new PotionEffectAdapter(this))
                .registerTypeAdapter(Location.class, new LocationAdapter(this));
    }

    /**
     * Add a listener
     *
     * @param listener
     */
    public void addListener(Listener listener) {
        if (listener instanceof Savable)
            this.addSave((Savable) listener);
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    /**
     * Add a listener from ListenerAdapter
     *
     * @param adapter
     */
    public void addListener(ListenerAdapter adapter) {
        if (adapter == null)
            throw new ListenerNullException("Warning, your listener is null");
        if (adapter instanceof Savable)
            this.addSave((Savable) adapter);
        this.listenerAdapters.add(adapter);
    }

    /**
     * Add a Saveable
     *
     * @param saver
     */
    public void addSave(Savable saver) {
        this.savers.add(saver);
    }

    /**
     * Get logger
     *
     * @return loggers
     */
    public Logger getLog() {
        return this.log;
    }

    /**
     * Get gson
     *
     * @return {@link Gson}
     */
    public Gson getGson() {
        return gson;
    }

    public Persist getPersist() {
        return persist;
    }

    /**
     * Get all saveables
     *
     * @return savers
     */
    public List<Savable> getSavers() {
        return savers;
    }

    /**
     * @param classz
     * @return
     */
    public <T> T getProvider(Class<T> classz) {
        RegisteredServiceProvider<T> provider = getServer().getServicesManager().getRegistration(classz);
        if (provider == null) {
            log.log("Unable to retrieve the provider " + classz.toString(), Logger.LogType.WARNING);
            return null;
        }
        return provider.getProvider() != null ? (T) provider.getProvider() : null;
    }

    /**
     * @return listenerAdapters
     */
    public List<ListenerAdapter> getListenerAdapters() {
        return listenerAdapters;
    }

    /**
     * @return the commandManager
     */
    public CommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * @return the inventoryManager
     */
    public ZInventoryManager getInventoryManager() {
        return inventoryManager;
    }

    protected boolean isEnable(Plugins pl) {
        Plugin plugin = getPlugin(pl);
        return plugin == null ? false : plugin.isEnabled();
    }

    protected Plugin getPlugin(Plugins plugin) {
        return Bukkit.getPluginManager().getPlugin(plugin.getName());
    }

    /**
     * Register command
     *
     * @param command
     * @param vCommand
     * @param aliases
     */
    protected void registerCommand(String command, VCommand vCommand, String... aliases) {
        this.commandManager.registerCommand(this, command, vCommand, Arrays.asList(aliases));
    }

    /**
     * Register Inventory
     *
     * @param inventory
     * @param vInventory
     */
    protected void registerInventory(EnumInventory inventory, VInventory vInventory) {
        this.inventoryManager.registerInventory(inventory, vInventory);
    }

    /**
     * Load files
     */
    public void loadFiles() {
        this.savers.forEach(save -> save.load(this.persist));
    }

    /**
     * Save files
     */
    public void saveFiles() {
        this.savers.forEach(save -> save.save(this.persist));
    }

    /**
     * Reload files
     */
    public void reloadFiles() {
        this.savers.forEach(save -> {
            if (!(save instanceof NoReloadable)) {
                save.load(this.persist);
            }
        });
    }

}
