package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;
import fr.maxlego08.koth.zcore.utils.commands.CommandType;

public class CommandKothList extends VCommand {

    public CommandKothList(KothPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZKOTH_LIST);
        this.addSubCommand("list");
        this.setDescription(Message.DESCRIPTION_LIST);
    }

    @Override
    protected CommandType perform(KothPlugin plugin) {
        this.manager.sendKothList(this.sender);
        return CommandType.SUCCESS;
    }

}
