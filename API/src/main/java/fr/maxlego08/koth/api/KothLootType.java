package fr.maxlego08.koth.api;

/**
 * Defines how the loot of a KOTH is given to the winner.
 */
public enum KothLootType {

	/**
	 * Items are dropped on the ground.
	 */
	DROP,
	/**
	 * Items are placed in a chest.
	 */
	CHEST,
	/**
	 * Items are added directly to the winner's inventory.
	 */
	INVENTORY,
	/**
	 * No item loot is given.
	 */
	NONE,
	
}
