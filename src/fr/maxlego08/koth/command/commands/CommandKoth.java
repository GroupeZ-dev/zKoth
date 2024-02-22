package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Permission;
import fr.maxlego08.koth.zcore.utils.commands.CommandType;

public class CommandKoth extends VCommand {

    public CommandKoth(KothPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZKOTH_USE);
        this.addSubCommand(new CommandKothReload(plugin));
        this.addSubCommand(new CommandKothAxe(plugin));
        this.addSubCommand(new CommandKothCreate(plugin));
        this.addSubCommand(new CommandKothNow(plugin));
        this.addSubCommand(new CommandKothSpawn(plugin));
        this.addSubCommand(new CommandKothStop(plugin));
    }

    @Override
    protected CommandType perform(KothPlugin plugin) {
        syntaxMessage();
        return CommandType.SUCCESS;
    }

}
