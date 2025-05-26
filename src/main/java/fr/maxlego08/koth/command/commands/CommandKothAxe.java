package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;
import fr.maxlego08.koth.zcore.utils.commands.CommandType;
import org.bukkit.inventory.ItemStack;

public class CommandKothAxe extends VCommand {

	public CommandKothAxe(KothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_AXE);
		this.addSubCommand("axe");
		this.setDescription(Message.DESCRIPTION_AXE);
		this.onlyPlayers();
	}

	@Override
	protected CommandType perform(KothPlugin plugin) {

		ItemStack itemStack = this.manager.getKothAxe();
		this.player.getInventory().addItem(itemStack);
		message(this.sender, Message.AXE_RECEIVE);
		
		return CommandType.SUCCESS;
	}

}
