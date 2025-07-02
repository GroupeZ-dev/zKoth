package fr.maxlego08.koth.zcore.enums;

import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Message {

    PREFIX("§8(§6zKoth§8) "),

    TELEPORT_MOVE("§cYou must not move!"),
    TELEPORT_MESSAGE("§7Teleportation in §3%second% §7seconds!"),
    TELEPORT_ERROR("§cYou already have a teleportation in progress!"),
    TELEPORT_SUCCESS("§7Teleportation done!"),
    INVENTORY_CLONE_NULL("§cThe inventory clone is null!"),
    INVENTORY_OPEN_ERROR("§cAn error occurred with the opening of the inventory §6%id%§c."),
    TIME_DAY("%02d %day% %02d %hour% %02d %minute% %02d %second%"),
    TIME_HOUR("%02d %hour% %02d minute(s) %02d %second%"),
    TIME_MINUTE("%02d %minute% %02d %second%"),
    TIME_SECOND("%02d %second%"),

    FORMAT_SECOND("second"),
    FORMAT_SECONDS("seconds"),

    FORMAT_MINUTE("minute"),
    FORMAT_MINUTES("minutes"),

    FORMAT_HOUR("hour"),
    FORMAT_HOURS("hours"),

    FORMAT_DAY("d"),
    FORMAT_DAYS("days"),

    COMMAND_SYNTAX_ERROR("§cYou must execute the command like this§7: §a%syntax%"),
    COMMAND_NO_PERMISSION("§cYou do not have permission to run this command."),
    COMMAND_NO_CONSOLE("§cOnly one player can execute this command."),
    COMMAND_NO_ARG("§cImpossible to find the command with its arguments."),
    COMMAND_SYNTAX_HELP("§f%syntax% §7» §7%description%"),

    RELOAD("§aYou have just reloaded the configuration files."),

    DESCRIPTION_RELOAD("Reload configuration files"),
    DESCRIPTION_NOW("Spawn a koth without cooldown"),
    DESCRIPTION_SPAWN("Spawn a koth with cooldown"),
    DESCRIPTION_LOOT("Change loot for a koth"),
    DESCRIPTION_DELETE("Delete a koth"),
    DESCRIPTION_VERSION("Show plugin version"),
    DESCRIPTION_STOP("Stop a koth"),
    DESCRIPTION_MOVE("Move a koth"),
    DESCRIPTION_AXE("Getting the selection axe"),
    DESCRIPTION_CREATE("Create new koth"),
    DESCRIPTION_LIST("Get koth list"),
    DESCRIPTION_ADD_COMMAND("Add a command to a koth"),

    AXE_RECEIVE("§7You have just received the axe for zone selection."),
    AXE_NAME("§6✤ §7zKoth axe §6✤"),
    AXE_DESCRIPTION("§8§m-+------------------------------+-", "",
            "",
            "§f§l» §7Allows you to select a zone to create a koth",
            " §7§oYou must select an area with the right click",
            " §7§oand left then do the command §b/koth create §8<§aname§8>",
            "",
            "§8§m-+------------------------------+-"),

    AXE_POS1("§7You have just put the first position in §f%world%§7, §f%x%§7, §f%y%§7, §f%z%§7."),
    AXE_POS2("§7You have just put the second position in §f%world%§7, §f%x%§7, §f%y%§7, §f%z%§7."),
    AXE_ERROR("§cYour selection is invalid, you must have at least 2 blocks of height."),
    AXE_VALID("§aYour selection is valid, you can create a koth with the command §f/koth create <name>§a."),

    CREATE_SUCCESS("§7You just created the koth §f%name%§7."),
    CREATE_ERROR_SELECTION("§cYou must select a zone with the command §b/zkoth axe§c."),
    CREATE_ERROR_SIZE("§cYour selection is invalid, you must have at least 2 blocks of height."),

    ALREADY_EXIST("§cThe koth §f%name% §calready exists."),
    KOTH_SIZE("§cYour koth is too small, you can't create one that small. Then you will come on discord for support when the problem comes from you."),
    DOESNT_EXIST("§cThe koth §f%name% §cdoesnt exists."),
    COMMAND_CREATE("§7You have just added the command §8\"§f%command%§8\""),

    EVENT_START(MessageType.CENTER,
            "§8§m-+------------------------------+-",
            "",
            "§fThe koth §b%name% §fhas just appeared!",
            "§fCoordinate§8: §7%centerX%, %centerY%, %centerZ%.",
            "",
            "§8§m-+------------------------------+-"
    ),

    EVENT_WIN(MessageType.CENTER,
            "§8§m-+------------------------------+-",
            "",
            "§d%playerName% §fof faction §7%teamName% §fhas just captured",
            "§fthe koth, and §nwins§f the event!",
            "",
            "§8§m-+------------------------------+-"
    ),

    EVENT_COOLDOWN(MessageType.CENTER,
            "§8§m-+------------------------------+-",
            "",
            "§fThe koth §n%name%§f will appear in §d%spawnFormat%",
            "§fCoordinate§8: §7%centerX%, %centerY%, %centerZ%.",
            "",
            "§8§m-+------------------------------+-"
    ),
    EVENT_COOLDOWN_EVERYSECONDS(MessageType.ACTION, "§fThe koth §n%name%§f will appear in §d%spawnFormat% §8(§7%centerX%, %centerY%, %centerZ%§8)"),

    EVENT_STOP(MessageType.CENTER,
            "§8§m-+------------------------------+-",
            "",
            "§fkoth §n%name%§f has just been stopped.",
            "",
            "§8§m-+------------------------------+-"),

    SPAWN_ERROR("§cImpossible to spawn the koth, positions is wrong. You have to do §b/zkoth move §8<§aname§8>§c."),
    SPAWN_COOLDOWN("§cThe countdown to the appearance of the koth is already underway"),
    SPAWN_ALREADY("§cThe koth is running."),

    EVENT_FACION("No faction"),
    EVENT_PLAYER("Person"),
    EVENT_DISABLE("§cThe event is not enable."),

    EVENT_CATCH(MessageType.ACTION, "§d%playerName% §fjust started capturing the koth §n%name%§f. §8(§7%centerX%, %centerY%, %centerZ%§8)"),
    EVENT_LOOSE(MessageType.ACTION, "§d%playerName% §fjust loose koth §n%name%§f. §8(§7%centerX%, %centerY%, %centerZ%§8)"),
    EVENT_TIMER(MessageType.ACTION, "§fAnother §b%captureFormat% §fbefore §d%playerName% §fwins the koth §n%name%§e. §8(§7%centerX%, %centerY%, %centerZ%§8)"),
    EVENT_EVERYSECONDS(MessageType.ACTION, "§d%playerName% §7- §8[§f%captureProgress%§8] §7- §b%capturePercent%§f%"),

    MOVE_SUCCESS("§7You have just moved the koth §f%name%§7."),

    LIST_CONSOLE("§fKoths§8: §f%koth%"),
    LIST_PLAYER("§fKoths§8: §8(§7Use your mouse)"),
    LIST_EMPTY("§fKoths§8: §cempty"),

    DELETE_SUCCESS("§7You just deleted the koth §f%name%§7."),

    LOOT_CHANGE("§aYou have just modified the loots of the koth §2%name%§a."),

    ;


    private List<String> messages;
    private String message;
    private Map<String, Object> titles = new HashMap<>();
    private boolean use = true;
    private MessageType type = MessageType.TCHAT;

    private ItemStack itemStack;

    /**
     * @param message
     */
    private Message(String message) {
        this.message = message;
        this.use = true;
    }

    /**
     * @param title
     * @param subTitle
     * @param a
     * @param b
     * @param c
     */
    private Message(String title, String subTitle, int a, int b, int c) {
        this.use = true;
        this.titles.put("title", title);
        this.titles.put("subtitle", subTitle);
        this.titles.put("start", a);
        this.titles.put("time", b);
        this.titles.put("end", c);
        this.titles.put("isUse", true);
        this.type = MessageType.TITLE;
    }

    /**
     * @param message
     */
    private Message(String... message) {
        this.messages = Arrays.asList(message);
        this.use = true;
    }

    /**
     * @param message
     */
    private Message(MessageType type, String... message) {
        this.messages = Arrays.asList(message);
        this.use = true;
        this.type = type;
    }

    /**
     * @param message
     */
    private Message(MessageType type, String message) {
        this.message = message;
        this.use = true;
        this.type = type;
    }

    /**
     * @param message
     * @param use
     */
    private Message(String message, boolean use) {
        this.message = message;
        this.use = use;
    }

    public String getMessage() {
        return message;
    }

    public String toMsg() {
        return message;
    }

    public String msg() {
        return message;
    }

    public boolean isUse() {
        return use;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getMessages() {
        return messages == null ? Arrays.asList(message) : messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public boolean isMessage() {
        return messages != null && messages.size() > 1;
    }

    public String getTitle() {
        return (String) titles.get("title");
    }

    public Map<String, Object> getTitles() {
        return titles;
    }

    public void setTitles(Map<String, Object> titles) {
        this.titles = titles;
        this.type = MessageType.TITLE;
    }

    public String getSubTitle() {
        return (String) titles.get("subtitle");
    }

    public boolean isTitle() {
        return titles.containsKey("title");
    }

    public int getStart() {
        return ((Number) titles.get("start")).intValue();
    }

    public int getEnd() {
        return ((Number) titles.get("end")).intValue();
    }

    public int getTime() {
        return ((Number) titles.get("time")).intValue();
    }

    public boolean isUseTitle() {
        return (boolean) titles.getOrDefault("isUse", "true");
    }

    public String replace(String a, String b) {
        return message.replace(a, b);
    }

    public MessageType getType() {
        return type;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

}

