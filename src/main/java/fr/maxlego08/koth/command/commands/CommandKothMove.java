package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.Selection;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;
import fr.maxlego08.koth.zcore.utils.commands.CommandType;
import org.bukkit.Location;

import java.util.Optional;

public class CommandKothMove extends VCommand {

	public CommandKothMove(KothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_MOVE);
		this.addSubCommand("move");
		this.setDescription(Message.DESCRIPTION_MOVE);
		this.addRequireArg("name", (a,b) -> plugin.getKothManager().getNameKoths());
	}

	@Override
	protected CommandType perform(KothPlugin plugin) {

		String name = argAsString(0);

		Optional<Selection> optional = this.manager.getSelection(this.player.getUniqueId());

		if (!optional.isPresent()) {
			message(this.sender, Message.CREATE_ERROR_SELECTION);
			return CommandType.DEFAULT;
		}

		Selection selection = optional.get();

		if (!selection.isValid()) {
			message(this.sender, Message.CREATE_ERROR_SELECTION);
			return CommandType.DEFAULT;
		}

		if (!selection.isCorrect()) {
			message(this.sender, Message.CREATE_ERROR_SIZE);
			return CommandType.DEFAULT;
		}

		Location minLocation = selection.getRightLocation();
		Location maxLocation = selection.getLeftLocation();
		this.manager.moveKoth(this.player, maxLocation, minLocation, name);
		
		return CommandType.SUCCESS;
	}

}
