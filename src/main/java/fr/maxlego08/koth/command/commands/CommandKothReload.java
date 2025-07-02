package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.save.Config;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;
import fr.maxlego08.koth.zcore.utils.commands.CommandType;

public class CommandKothReload extends VCommand {

	public CommandKothReload(KothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_RELOAD);
		this.addSubCommand("reload", "rl");
		this.setDescription(Message.DESCRIPTION_RELOAD);
	}

	@Override
	protected CommandType perform(KothPlugin plugin) {

		plugin.reloadConfig();
		Config.getInstance().load(plugin);
		plugin.reloadFiles();
		message(sender, Message.RELOAD);
		
		return CommandType.SUCCESS;
	}

}
