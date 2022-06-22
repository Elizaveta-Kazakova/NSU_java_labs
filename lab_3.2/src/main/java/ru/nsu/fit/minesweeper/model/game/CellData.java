package ru.nsu.fit.minesweeper.model.game;

public class CellData {

    private CellFilling cellFilling;
    private CellUserNote cellUserNote;
    private boolean isOpened;

    CellData() {
        this.cellFilling = CellFilling.EMPTY;
        this.cellUserNote = CellUserNote.UNKNOWN;
        this.isOpened = false;
    }

    public void setCellFilling(CellFilling cellFilling) {
        this.cellFilling = cellFilling;
    }

    public void setCellUserNote(CellUserNote cellUserNotes) {
        this.cellUserNote = cellUserNotes;
    }

    public CellFilling getCellFilling() {
        return cellFilling;
    }

    public CellUserNote getCellUserNote() {
        return cellUserNote;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void increaseCellFilling() {
        cellFilling = cellFilling.increaseFilling();
    }

    public void setNextCellNote() {
        cellUserNote = cellUserNote.getNextNote();
    }

    public void openCell() {
        isOpened = true;
    }

    public void closeCell() {
        isOpened = false;
    }

}
