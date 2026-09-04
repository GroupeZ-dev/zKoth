package fr.maxlego08.koth.zcore.utils.inventory;

import java.util.Arrays;
import java.util.List;

import fr.maxlego08.koth.zcore.utils.ZUtils;
import org.bukkit.Material;

public class Button extends ZUtils {

	private final int slot;
	private final String name;
	private final Material item;
	private final int data;
	private final List<String> lore;

	/**
	 * 
	 * @param slot
	 * @param name
	 * @param material
	 * @param data
	 * @param lore
	 */
	public Button(int slot, String name, Material material, int data, List<String> lore) {
		super();
		this.slot = slot;
		this.name = name;
		this.item = material;
		this.data = data;
		this.lore = lore;
	}

	/**
	 * 
	 * @param slot
	 * @param name
	 * @param item
	 */
	public Button(int slot, String name, Material item) {
		this(slot, name, item, 0);
	}

	/**
	 * 
	 * @param slot
	 * @param name
	 * @param item
	 * @param lore
	 */
	public Button(int slot, String name, Material item, String... lore) {
		this(slot, name, item, 0, Arrays.asList(lore));
	}

	/**
	 * 
	 * @param slot
	 * @param name
	 * @param item
	 * @param data
	 * @param lore
	 */
	public Button(int slot, String name, Material item, int data, String... lore) {
		this(slot, name, item, data, Arrays.asList(lore));
	}

	/**
	 * @return the slot
	 */
	public int getSlot() {
		return slot;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the item
	 */
	public Material getItem() {
		return item;
	}

	public int getData() {
		return data;
	}

	/**
	 * @return the lore
	 */
	public List<String> getLore() {
		return lore;
	}

}
