package fr.maxlego08.koth.zcore.enums;

public enum Permission {

	ZKOTH_USE,
	ZKOTH_RELOAD,
	ZKOTH_NOW,
	ZKOTH_AXE,
	ZKOTH_LIST,
	ZKOTH_CREATE, ZKOTH_SPAWN, ZKOTH_STOP, ZKOTH_MOVE, ZKOTH_DELETE, ZKOTH_LOOT, ZKOTH_COMMAND_ADD;

	private String permission;

	private Permission() {
		this.permission = this.name().toLowerCase().replace("_", ".");
	}

	public String getPermission() {
		return permission;
	}

}
