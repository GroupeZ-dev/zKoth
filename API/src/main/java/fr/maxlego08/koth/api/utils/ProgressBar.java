package fr.maxlego08.koth.api.utils;

/**
 * Configuration of a text progress bar.
 *
 * @param length            the number of symbols in the bar
 * @param symbol            the symbol used to draw the bar
 * @param completedColor    the color of the completed part
 * @param notCompletedColor the color of the remaining part
 */
public record ProgressBar(int length, char symbol, String completedColor, String notCompletedColor) {

    /**
     * Returns the length.
     *
     * @return the length
     */
    @Override
    public int length() {
        return length;
    }

    /**
     * Returns the symbol.
     *
     * @return the symbol
     */
    @Override
    public char symbol() {
        return symbol;
    }

    /**
     * Returns the completedColor.
     *
     * @return the completedColor
     */
    @Override
    public String completedColor() {
        return completedColor;
    }

    /**
     * Returns the notCompletedColor.
     *
     * @return the notCompletedColor
     */
    @Override
    public String notCompletedColor() {
        return notCompletedColor;
    }

}
