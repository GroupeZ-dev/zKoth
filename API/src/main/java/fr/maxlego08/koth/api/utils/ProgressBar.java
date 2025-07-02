package fr.maxlego08.koth.api.utils;

public record ProgressBar(int length, char symbol, String completedColor, String notCompletedColor) {

    /**
     * @return the length
     */
    @Override
    public int length() {
        return length;
    }

    /**
     * @return the symbol
     */
    @Override
    public char symbol() {
        return symbol;
    }

    /**
     * @return the completedColor
     */
    @Override
    public String completedColor() {
        return completedColor;
    }

    /**
     * @return the notCompletedColor
     */
    @Override
    public String notCompletedColor() {
        return notCompletedColor;
    }

}
