package ru.nsu.fit.minesweeper.model.game;

public enum CellFilling {
    EMPTY, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, BOMB;

    private static final int HOP_FOR_NEXT = 1;
    private static CellFilling[] VALUES = values();

    public CellFilling increaseFilling() {
        if (this == BOMB) {
            // throw exception
        }
        return VALUES[(this.ordinal() + HOP_FOR_NEXT) % VALUES.length];
    }

}
