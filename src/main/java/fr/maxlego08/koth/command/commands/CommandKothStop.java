package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;
import fr.maxlego08.koth.zcore.utils.commands.CommandType;

public class CommandKothStop extends VCommand {

    public CommandKothStop(KothPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZKOTH_STOP);
        this.addSubCommand("stop");
        this.setDescription(Message.DESCRIPTION_SPAWN);
        this.addRequireArg("name", (a, b) -> plugin.getKothManager().getActiveNameKoths());
    }

    @Override
    protected CommandType perform(KothPlugin plugin) {

        String name = argAsString(0);
        this.manager.stopKoth(sender, name);

        return CommandType.SUCCESS;
    }

}
