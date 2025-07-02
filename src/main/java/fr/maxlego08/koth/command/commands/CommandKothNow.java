package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;
import fr.maxlego08.koth.zcore.utils.commands.CommandType;

public class CommandKothNow extends VCommand {

	public CommandKothNow(KothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_NOW);
		this.addSubCommand("now");
		this.setDescription(Message.DESCRIPTION_NOW);
		this.addRequireArg("name", (a,b) -> plugin.getKothManager().getNameKoths());
	}

	@Override
	protected CommandType perform(KothPlugin plugin) {

		String name = argAsString(0);
		this.manager.spawnKoth(sender, name, true);
		
		return CommandType.SUCCESS;
	}

}
