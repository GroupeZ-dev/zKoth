package fr.maxlego08.koth.scoreboard;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.KothScoreboard;
import fr.maxlego08.koth.save.Config;
import fr.maxlego08.koth.zcore.utils.ZUtils;
import fr.maxlego08.koth.api.utils.interfaces.CollectionConsumer;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

public class ScoreBoardManager extends ZUtils {

    private final ConcurrentMap<Player, FastBoard> boards = new ConcurrentHashMap<>();
    private final KothPlugin plugin;
    private boolean isRunning = false;
    private CollectionConsumer<Player> lines;
    private KothScoreboard scoreboard;

    public ScoreBoardManager(KothPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Start scheduler
     */
    public void schedule() {

        if (Config.schedulerMillisecond == 1000) return;

        if (isRunning)
            return;

        isRunning = true;

        scheduleFix(plugin, Config.schedulerMillisecond, (task, canRun) -> {

            // If the task cannot continue then we do not update the scoreboard
            if (!canRun)
                return;

            if (!isRunning) {
                task.cancel();
                return;
            }

            // if the addition of the lines is null then we stop the task
            if (lines == null) {
                task.cancel();
                return;
            }

            Iterator<FastBoard> iterator = this.boards.values().iterator();
            while (iterator.hasNext()) {
                try {
                    FastBoard b = iterator.next();
                    if (b.isDeleted() || !b.getPlayer().isOnline()) {
                        this.boards.remove(b.getPlayer());
                    }
                } catch (Exception ignored) {
                }
            }

            this.boards.forEach((player, board) -> {
                board.updateLines(this.lines.accept(player));
            });

        });
    }

    public void update() {

        if (this.lines == null) return;

        this.boards.forEach((player, board) -> {
            board.updateLines(this.lines.accept(player));
        });
    }

    public FastBoard createBoard(Player player, String title) {

        if (this.hasBoard(player)) {
            return this.getBoard(player);
        }

        FastBoard board = new FastBoard(player);
        board.updateTitle(title);

        if (this.lines != null) {
            board.updateLines(this.lines.accept(player));
        }

        this.boards.put(player, board);
        this.scoreboard.hide(player, create(board, player, title));

        return board;

    }

    private Consumer<Player> create(FastBoard current, Player player, String title) {
        return p -> {

            if (current != null) {
                if (!current.isDeleted()) {
                    current.delete();
                }
            }

            this.boards.remove(player);

            FastBoard board = new FastBoard(player);
            board.updateTitle(papi(title, player));

            if (this.lines != null) {
                board.updateLines(this.lines.accept(player));
            }

            this.boards.put(player, board);
        };
    }

    public boolean delete(Player player) {

        if (!this.hasBoard(player)) {
            return false;
        }

        FastBoard board = getBoard(player);
        if (!board.isDeleted()) {
            board.delete();
        }

        Bukkit.getScheduler().runTaskLater(this.plugin, () -> this.scoreboard.toggle(player, p -> {
        }), 10);
        return true;
    }

    /**
     * Update board title
     *
     * @param player
     * @param title
     * @return
     */
    public boolean updateTitle(Player player, String title) {
        if (!this.hasBoard(player)) {
            return false;
        }
        FastBoard board = getBoard(player);
        board.updateTitle(title);
        return true;
    }

    public void clearBoard() {
        this.isRunning = false;
        this.boards.keySet().forEach(this::delete);
        this.boards.clear();
    }

    public boolean updateLine(Player player, int line, String string) {
        if (!this.hasBoard(player)) {
            return false;
        }
        FastBoard board = getBoard(player);
        board.updateLine(line, string);
        return true;
    }

    /**
     * @return {@link Boolean}
     */
    public boolean hasBoard(Player player) {
        return this.boards.containsKey(player);
    }

    /**
     * @return {@link FastBoard}
     */
    public FastBoard getBoard(Player player) {
        return this.boards.getOrDefault(player, null);
    }

    /**
     * @return the boards
     */
    public Map<Player, FastBoard> getBoards() {
        return this.boards;
    }

    /**
     * @return the isRunning
     */
    public boolean isRunning() {
        return this.isRunning;
    }

    /**
     * @param isRunning the isRunning to set
     */
    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    /**
     * @return the lines
     */
    public CollectionConsumer<Player> getLines() {
        return this.lines;
    }

    /**
     * @param lines the lines to set
     */
    public void setLines(CollectionConsumer<Player> lines) {
        this.lines = lines;
    }

    /**
     * @param lines the lines to set
     */
    public void setLinesAndSchedule(CollectionConsumer<Player> lines) {
        this.lines = lines;
        this.schedule();
    }

    public KothScoreboard getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(KothScoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

}
