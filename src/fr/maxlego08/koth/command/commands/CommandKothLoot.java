package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;
import fr.maxlego08.koth.zcore.utils.commands.CommandType;

import java.util.Arrays;

public class CommandKothLoot extends VCommand {

    public CommandKothLoot(KothPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZKOTH_LOOT);
        this.addSubCommand("loot");
        this.setDescription(Message.DESCRIPTION_LOOT);
        this.addRequireArg("name", (a, b) -> plugin.getKothManager().getNameKoths());
        this.addOptionalArg("page", (a, b) -> Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
    }

    @Override
    protected CommandType perform(KothPlugin plugin) {

        String name = argAsString(0);
        int page = argAsInteger(1, 1);
        if (page < 1) return CommandType.SYNTAX_ERROR;
        this.manager.updateLoots(this.player, name, page);

        return CommandType.SUCCESS;
    }

}
