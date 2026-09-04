package fr.maxlego08.koth.zcore.utils;

import java.time.Duration;
import java.util.List;

import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.utils.players.ActionBar;

/**
 * Allows you to manage messages sent to players and the console
 * 
 * @author Maxence
 *
 */
public abstract class MessageUtils extends LocationUtils {

	/**
	 * 
	 * @param player
	 * @param message
	 * @param args
	 */
	protected void messageWO(CommandSender player, Message message, Object... args) {
		player.sendMessage(getMessage(message, args));
	}

	/**
	 * 
	 * @param player
	 * @param message
	 * @param args
	 */
	protected void messageWO(CommandSender player, String message, Object... args) {
		player.sendMessage(getMessage(message, args));
	}

	/**
	 * 
	 * @param sender
	 * @param message
	 * @param args
	 */
	protected void message(CommandSender sender, String message, Object... args) {
		sender.sendMessage(Message.PREFIX.msg() + getMessage(message, args));
	}

	/**
	 * Allows you to send a message to a command sender
	 * 
	 * @param sender
	 *            User who sent the command
	 * @param message
	 *            The message - Using the Message enum for simplified message
	 *            management
	 * @param args
	 *            The arguments - The arguments work in pairs, you must put for
	 *            example %test% and then the value
	 */
	protected void message(CommandSender sender, Message message, Object... args) {

		if (sender instanceof ConsoleCommandSender) {
			if (message.getMessages().size() > 0) {
				message.getMessages().forEach(msg -> sender.sendMessage(Message.PREFIX.msg() + getMessage(msg, args)));
			} else {
				sender.sendMessage(Message.PREFIX.msg() + getMessage(message, args));
			}
		} else {

			Player player = (Player) sender;
			switch (message.getType()) {
			case CENTER:
				if (message.getMessages().size() > 0) {
					message.getMessages()
							.forEach(msg -> sender.sendMessage(this.getCenteredMessage(this.papi(getMessage(msg, args), player))));
				} else {
					sender.sendMessage(this.getCenteredMessage(this.papi(getMessage(message, args), player)));
				}

				break;
			case ACTION:
				this.actionMessage(player, message, args);
				break;
			case TCHAT:
				if (message.getMessages().size() > 0) {
					message.getMessages()
							.forEach(msg -> sender.sendMessage(this.papi(Message.PREFIX.msg() + getMessage(msg, args), player)));
				} else {
					sender.sendMessage(this.papi(Message.PREFIX.msg() + getMessage(message, args), player));
				}
				break;
			case TITLE:
				// title message management
				String title = message.getTitle();
				String subTitle = message.getSubTitle();
				int fadeInTime = message.getStart();
				int showTime = message.getTime();
				int fadeOutTime = message.getEnd();
				this.title(player, this.papi(this.getMessage(title, args), player), this.papi(this.getMessage(subTitle, args), player), fadeInTime, showTime,
						fadeOutTime);
				break;
			default:
				break;

			}

		}
	}

	protected void broadcast(Message message, Object... args) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			message(player, message, args);
		}
		message(Bukkit.getConsoleSender(), message, args);
	}

	protected void broadcast(String message) {
		Bukkit.broadcast(LegacyText.component(Message.PREFIX.msg() + message));
	}

	protected void actionMessage(Player player, Message message, Object... args) {
		ActionBar.sendActionBar(player, this.papi(getMessage(message, args), player));
	}

	protected String getMessage(Message message, Object... args) {
		return getMessage(message.getMessage(), args);
	}

	protected String getMessage(String message, Object... args) {
		if (args.length % 2 != 0) {
			System.err.println("Impossible to apply the method for messages.");
		} else {
			for (int a = 0; a < args.length; a += 2) {
				String replace = args[a].toString();
				String to = args[a + 1].toString();
				message = message.replace(replace, to);
			}
		}
		return message;
	}

	/**
	 * Send title to player
	 * 
	 * @param player
	 * @param title
	 * @param subtitle
	 * @param fadeInTime
	 * @param showTime
	 * @param fadeOutTime
	 */
	protected void title(Player player, String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime) {
		player.showTitle(Title.title(LegacyText.component(title), LegacyText.component(subtitle),
				Title.Times.times(ticks(fadeInTime), ticks(showTime), ticks(fadeOutTime))));
	}

	private Duration ticks(int ticks) {
		return Duration.ofMillis(Math.max(0, ticks) * 50L);
	}

	private final transient static int CENTER_PX = 154;

	/**
	 * 
	 * @param message
	 * @return message
	 */
	protected String getCenteredMessage(String message) {
		if (message == null || message.equals(""))
			return "";
		message = LegacyText.colorize(message);

		int messagePxSize = 0;
		boolean previousCode = false;
		boolean isBold = false;

		for (char c : message.toCharArray()) {
			if (c == '§') {
				previousCode = true;
			} else if (previousCode) {
				previousCode = false;
				if (c == 'l' || c == 'L') {
					isBold = true;
				} else
					isBold = false;
			} else {
				DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
				messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
				messagePxSize++;
			}
		}

		int halvedMessageSize = messagePxSize / 2;
		int toCompensate = CENTER_PX - halvedMessageSize;
		int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
		int compensated = 0;
		StringBuilder sb = new StringBuilder();
		while (compensated < toCompensate) {
			sb.append(" ");
			compensated += spaceLength;
		}
		return sb.toString() + message;
	}

	protected void broadcastCenterMessage(List<String> messages) {
		messages.stream().map(e -> e = getCenteredMessage(e)).forEach(e -> {
			for (Player player : Bukkit.getOnlinePlayers()) {
				messageWO(player, e);
			}
		});
	}
	
	protected void broadcastAction(String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			ActionBar.sendActionBar(player, papi(message, player));
		}
	}
	
}
