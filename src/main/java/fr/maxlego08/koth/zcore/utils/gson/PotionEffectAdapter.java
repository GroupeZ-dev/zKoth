package fr.maxlego08.koth.zcore.utils.gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import fr.maxlego08.koth.zcore.ZPlugin;
import org.bukkit.potion.PotionEffect;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class PotionEffectAdapter extends TypeAdapter<PotionEffect> {

	private final ZPlugin plugin;
	private static Type seriType = new TypeToken<Map<String, Object>>() {
	}.getType();

	private static final String HIDDEN_EFFECT = "hidden_effect";

	/**
	 * @param plugin
	 */
	public PotionEffectAdapter(ZPlugin plugin) {
		super();
		this.plugin = plugin;
	}

	@Override
	public void write(JsonWriter jsonWriter, PotionEffect potionEffect) throws IOException {
		if (potionEffect == null) {
			jsonWriter.nullValue();
			return;
		}
		jsonWriter.value(getRaw(potionEffect));
	}

	@Override
	public PotionEffect read(JsonReader jsonReader) throws IOException {
		if (jsonReader.peek() == JsonToken.NULL) {
			jsonReader.nextNull();
			return null;
		}
		return fromRaw(jsonReader.nextString());
	}

	private String getRaw(PotionEffect potion) {
		Map<String, Object> serial = potion.serialize();
		return this.plugin.getGson().toJson(serial);
	}

	private PotionEffect fromRaw(String raw) {
		Map<String, Object> keys = this.plugin.getGson().fromJson(raw, seriType);
		return new PotionEffect(toBukkitMap(keys));
	}

	/**
	 * Gson lit tous les nombres en Double alors que {@link PotionEffect#PotionEffect(Map)} attend des
	 * Integer. Le type d'effet peut être une clé ({@code minecraft:speed}) ou un ancien id numérique.
	 *
	 * @param keys les valeurs lues par Gson
	 * @return les valeurs au format attendu par Bukkit
	 */
	private Map<String, Object> toBukkitMap(Map<?, ?> keys) {
		Map<String, Object> result = new HashMap<>();
		keys.forEach((key, value) -> {
			String name = String.valueOf(key);
			if (value instanceof Number number) {
				result.put(name, number.intValue());
			} else if (name.equals(HIDDEN_EFFECT) && value instanceof Map<?, ?> hidden) {
				result.put(name, new PotionEffect(toBukkitMap(hidden)));
			} else if (name.equals(HIDDEN_EFFECT) && value instanceof String hidden) {
				// L'effet caché est écrit par ce même adapter, donc sous forme de texte JSON.
				result.put(name, fromRaw(hidden));
			} else {
				result.put(name, value);
			}
		});
		return result;
	}

}
