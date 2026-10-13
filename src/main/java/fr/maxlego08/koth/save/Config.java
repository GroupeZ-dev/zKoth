package fr.maxlego08.koth.save;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.utils.PlayerResult;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Arrays;
import java.util.List;

public class Config {

	public static boolean enableDebug = true;
	public static boolean enableDebugTime = false;
	public static long playerMoveEventCooldown = 50;
	public static long schedulerMillisecond = 1000;
	public static List<Integer> displayMessageCooldown = Arrays.asList(300, 120, 60, 30, 10, 5, 4, 3, 2, 1);
	public static List<Integer> displayMessageKothCap = Arrays.asList(300, 120, 60, 30, 10, 5, 4, 3, 2, 1);
    public static String noPlayer = "X";
    public static String noFaction = "X";
    public static String noKoth = "X";
	public static int removeChestSec = 120;
	public static boolean enableCapturePermission = false;
	public static String capturePermission = "zkoth.capture";
	public static boolean enableTeamCaptureTransfer = true;
	public static List<String> teamCaptureTransferIgnoredIds = Arrays.asList("0", "-1", "-2");

	public static PlayerResult defaultPlayerResult = new PlayerResult("X", 0, "X", "X", "X");

    /**
	 * static Singleton instance.
	 */
	private static volatile Config instance;


	/**
	 * Private constructor for singleton.
	 */
	private Config() {
	}

	/**
	 * Return a singleton instance of Config.
	 */
	public static Config getInstance() {
		// Double lock for thread safety.
		if (instance == null) {
			synchronized (Config.class) {
				if (instance == null) {
					instance = new Config();
				}
			}
		}
		return instance;
	}

	public void load(KothPlugin plugin) {

		YamlConfiguration configuration = (YamlConfiguration) plugin.getConfig();

		enableDebug = configuration.getBoolean("enableDebug", false);
		enableDebugTime = configuration.getBoolean("enableDebugTime", false);
		playerMoveEventCooldown = configuration.getInt("playerMoveEventCooldown", 50);

		displayMessageCooldown = configuration.getIntegerList("displayMessageCooldown");
		displayMessageKothCap = configuration.getIntegerList("displayMessageKothCap");
		noPlayer = configuration.getString("noPlayer", "X");
		noFaction = configuration.getString("noFaction", "X");
		noKoth = configuration.getString("noKoth", "X");
		schedulerMillisecond = configuration.getLong("schedulerMillisecond", 1000);
		removeChestSec = configuration.getInt("removeChestSec", 120);
		enableCapturePermission = configuration.getBoolean("enableCapturePermission", false);
		capturePermission = configuration.getString("capturePermission", "zkoth.capture");
		enableTeamCaptureTransfer = configuration.getBoolean("teamCaptureTransfer.enable", true);
		teamCaptureTransferIgnoredIds = configuration.contains("teamCaptureTransfer.ignoredTeamIds")
				? configuration.getStringList("teamCaptureTransfer.ignoredTeamIds")
				: Arrays.asList("0", "-1", "-2");

		defaultPlayerResult = new PlayerResult(
				configuration.getString("defaultPlayerResult.playerName", "X"),
				configuration.getInt("defaultPlayerResult.points", 0),
				configuration.getString("defaultPlayerResult.teamName", "X"),
				configuration.getString("defaultPlayerResult.teamId", "X"),
				configuration.getString("defaultPlayerResult.teamLeader", "X")
		);
	}

}
