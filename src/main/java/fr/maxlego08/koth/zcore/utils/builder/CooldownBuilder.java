package fr.maxlego08.koth.zcore.utils.builder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import fr.maxlego08.koth.zcore.utils.storage.Persist;
import fr.maxlego08.koth.zcore.utils.storage.Savable;
import org.bukkit.entity.Player;

public class CooldownBuilder implements Savable {

	public static Map<String, Map<UUID, Long>> cooldowns = new HashMap<>();

	public static Map<UUID, Long> getCooldownMap(String key) {
		return cooldowns.getOrDefault(key, null);
	}

	public static void clear() {
		cooldowns.clear();
	}

	public static void createCooldown(String key) {
		cooldowns.putIfAbsent(key, new HashMap<>());
	}

	public static void removeCooldown(String key, UUID uuid) {

		createCooldown(key);

		getCooldownMap(key).remove(uuid);
	}

	public static void removeCooldown(String key, Player player) {
		removeCooldown(key, player.getUniqueId());
	}

	public static void addCooldown(String key, UUID uuid, int seconds) {

		createCooldown(key);

		long next = System.currentTimeMillis() + seconds * 1000L;
		getCooldownMap(key).put(uuid, Long.valueOf(next));
	}

	public static void addCooldown(String key, Player player, int seconds) {
		addCooldown(key, player.getUniqueId(), seconds);
	}

	public static boolean isCooldown(String key, UUID uuid) {

		createCooldown(key);
		Map<UUID, Long> map = cooldowns.get(key);

		return (map.containsKey(uuid)) && (System.currentTimeMillis() <= ((Long) map.get(uuid)).longValue());
	}

	public static boolean isCooldown(String key, Player player) {
		return isCooldown(key, player.getUniqueId());
	}

	public static long getCooldown(String key, UUID uuid) {

		createCooldown(key);
		Map<UUID, Long> map = cooldowns.get(key);

		return ((Long) map.getOrDefault(uuid, 0l)).longValue() - System.currentTimeMillis();
	}

	public static long getCooldownPlayer(String key, Player player) {
		return getCooldown(key, player.getUniqueId());
	}

	public static String getCooldownAsString(String key, UUID player) {
		return TimerBuilder.getStringTime(getCooldown(key, player) / 1000);
	}

	private static transient CooldownBuilder i = new CooldownBuilder();

	@Override
	public void save(Persist persist) {
		persist.save(i, "cooldowns");
	}

	@Override
	public void load(Persist persist) {
		persist.loadOrSaveDefault(i, CooldownBuilder.class, "cooldowns");
	}
}
