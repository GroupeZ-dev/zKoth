package fr.maxlego08.koth.zcore.utils;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.maxlego08.koth.placeholder.Placeholder;
import net.kyori.adventure.text.Component;

public class PapiUtils extends TranslationHelper {

	/**
	 * 
	 * @param itemStack
	 * @param player
	 * @return itemstack
	 */
	protected ItemStack papi(ItemStack itemStack, Player player) {

		if (itemStack == null) {
			return itemStack;
		}

		ItemMeta itemMeta = itemStack.getItemMeta();

		if (itemMeta.hasDisplayName()) {
			String displayName = LegacyText.serialize(itemMeta.displayName());
			itemMeta.displayName(LegacyText.component(Placeholder.getPlaceholder().setPlaceholders(player, displayName)));
		}

		if (itemMeta.hasLore()) {
			List<Component> lore = itemMeta.lore();
			if (lore != null) {
				List<String> legacyLore = lore.stream().map(LegacyText::serialize).toList();
				itemMeta.lore(Placeholder.getPlaceholder().setPlaceholders(player, legacyLore).stream()
						.map(LegacyText::component).toList());
			}
		}

		itemStack.setItemMeta(itemMeta);
		return itemStack;

	}

	/**
	 * 
	 * @param placeHolder
	 * @param player
	 * @return string
	 */
	public String papi(String placeHolder, Player player) {
		return Placeholder.getPlaceholder().setPlaceholders(player, placeHolder);
	}

	/**
	 * Transforms a list into a list with placeholder API
	 * 
	 * @param placeHolder
	 * @param player
	 * @return placeholders
	 */
	public List<String> papi(List<String> placeHolder, Player player) {
		return Placeholder.getPlaceholder().setPlaceholders(player, placeHolder);
	}

}
