package fr.maxlego08.koth.zcore.utils;

public class ProgressBar {

    private final int length;
    private final char symbol;
    private final String completedColor;
    private final String notCompletedColor;

    public ProgressBar(int length, char symbol, String completedColor, String notCompletedColor) {
        super();
        this.length = length;
        this.symbol = symbol;
        this.completedColor = completedColor;
        this.notCompletedColor = notCompletedColor;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @return the symbol
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * @return the completedColor
     */
    public String getCompletedColor() {
        return completedColor;
    }

    /**
     * @return the notCompletedColor
     */
    public String getNotCompletedColor() {
        return notCompletedColor;
    }

}
