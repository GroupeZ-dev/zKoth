package fr.maxlego08.koth.zcore.utils;

import java.util.List;

import net.kyori.adventure.text.Component;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.maxlego08.koth.placeholder.Placeholder;

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
			itemMeta.displayName(applyPlaceholders(player, itemMeta.displayName()));
		}

		List<Component> lore = itemMeta.lore();
		if (lore != null) {
			itemMeta.lore(lore.stream().map(line -> applyPlaceholders(player, line)).toList());
		}

		itemStack.setItemMeta(itemMeta);
		return itemStack;

	}

	/**
	 * Applique les placeholders à un composant. Le composant d'origine est conservé tel quel s'il ne
	 * contient aucun placeholder, pour ne pas perdre ce que le format legacy ne sait pas représenter.
	 *
	 * @param player le joueur
	 * @param component le composant
	 * @return le composant avec les placeholders remplacés
	 */
	private Component applyPlaceholders(Player player, Component component) {
		String legacy = LegacyText.serialize(component);
		String replaced = Placeholder.getPlaceholder().setPlaceholders(player, legacy);
		return replaced.equals(legacy) ? component : LegacyText.itemComponent(replaced);
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