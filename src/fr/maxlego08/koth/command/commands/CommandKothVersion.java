package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.utils.commands.CommandType;

public class CommandKothVersion extends VCommand {

    public CommandKothVersion(KothPlugin plugin) {
        super(plugin);
        this.addSubCommand("version", "?", "v");
        this.setDescription(Message.DESCRIPTION_VERSION);
    }

    @Override
    protected CommandType perform(KothPlugin plugin) {

        message(this.sender, "§aVersion du plugin§7: §2" + plugin.getDescription().getVersion());
        message(this.sender, "§aAuteur§7: §2Maxlego08");
        message(this.sender, "§aDiscord§7: §2http://discord.groupez.dev/");
        message(this.sender, "§aBuy it for §d8€§7: §2https://groupez.dev/resources/zKoth.9");
        message(this.sender, "§aSponsor§7: §chttps://serveur-minecraft-vote.fr/?ref=345");

        return CommandType.SUCCESS;
    }

}
