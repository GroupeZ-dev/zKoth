package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;
import fr.maxlego08.koth.zcore.utils.commands.CommandType;

public class CommandKothDelete extends VCommand {

	public CommandKothDelete(KothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_DELETE);
		this.addSubCommand("spawn");
		this.setDescription(Message.DESCRIPTION_DELETE);
		this.addRequireArg("name", (a,b) -> plugin.getKothManager().getNameKoths());
	}

	@Override
	protected CommandType perform(KothPlugin plugin) {

		String name = argAsString(0);
		this.manager.deleteKoth(sender, name);
		
		return CommandType.SUCCESS;
	}

}
