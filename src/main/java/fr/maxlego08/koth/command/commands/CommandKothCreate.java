package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.Selection;
import fr.maxlego08.koth.api.KothType;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;
import fr.maxlego08.koth.zcore.utils.commands.CommandType;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommandKothCreate extends VCommand {

    public CommandKothCreate(KothPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZKOTH_CREATE);
        this.addSubCommand("create");
        this.setDescription(Message.DESCRIPTION_CREATE);
        this.setConsoleCanUse(false);
        this.addRequireArg("name");
        this.addOptionalArg("type", (a, b) -> Arrays.stream(KothType.values()).map(e -> e.name().toLowerCase()).collect(Collectors.toList()));
        this.addOptionalArg("capture/score", (a, b) -> Arrays.asList("10", "20", "30", "40", "50", "60"));
        this.onlyPlayers();
    }

    @Override
    protected CommandType perform(KothPlugin plugin) {

        String name = argAsString(0);
        KothType kothType = KothType.valueOf(argAsString(1, KothType.CAPTURE.name()).toUpperCase());
        int capture = argAsInteger(2, 30);

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

        if (name == null) {
            return CommandType.SYNTAX_ERROR;
        }

        Location minLocation = selection.getRightLocation();
        Location maxLocation = selection.getLeftLocation();
        this.manager.createKoth(this.player, name, minLocation, maxLocation, capture, kothType);

        return CommandType.SUCCESS;
    }

}
