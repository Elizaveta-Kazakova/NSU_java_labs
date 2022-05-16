package ru.nsu.fit.minesweeper.model.game;

public enum CellUserNote {
    UNKNOWN, BOMB_FLAG, SUSPICIOUS;

    private static final int HOP_FOR_NEXT = 1;
    private static CellUserNote[] VALUES = values();

    public CellUserNote getNextNote() {
        return VALUES[(this.ordinal() + HOP_FOR_NEXT) % VALUES.length];
    }

}
