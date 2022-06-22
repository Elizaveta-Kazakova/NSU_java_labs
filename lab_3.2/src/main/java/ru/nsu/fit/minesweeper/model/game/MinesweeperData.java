package ru.nsu.fit.minesweeper.model.game;

import ru.nsu.fit.minesweeper.observerPattern.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MinesweeperData extends Observable {
    private static final int ORIGIN = 0;
    private static final int ONE_ELEMENT = 1;

    private String userName;
    private int numberOfMines;
    private int sizeOfField;
    private int widthOfField;
    private int lengthOfField;
    private List<CellData> cellsCondition = new ArrayList<>();
    private boolean isWin = false;
    private boolean isEndGame = false;

    private int convertNumberToIndex(int index) {
        return index - ONE_ELEMENT;
    }

    private int getCellIndexOfRow(int cellIndex) {
        return cellIndex / widthOfField;
    }

    private int getCellIndexInRow(int cellIndex) {
        return cellIndex % widthOfField;
    }

    private int calcStartAround(int cellIndex) {
        int startRow = Math.max(ORIGIN, getCellIndexOfRow(cellIndex) - 1);
        int startInRow = Math.max(ORIGIN, getCellIndexInRow(cellIndex) - 1);
        return widthOfField * startRow + startInRow;
    }

    private int calcEndAround(int cellIndex) {
        int endRow = Math.min(convertNumberToIndex(lengthOfField), getCellIndexOfRow(cellIndex) + 1);
        int endInRow = Math.min(convertNumberToIndex(widthOfField), getCellIndexInRow(cellIndex) + 1);
        return widthOfField * endRow + endInRow;
    }

    private int calcWidthOfAroundBlock(int cellIndex) {
        int startInRow = Math.max(ORIGIN, getCellIndexInRow(cellIndex) - 1);
        int endInRow = Math.min(convertNumberToIndex(widthOfField), getCellIndexInRow(cellIndex) + 1);
        return endInRow - startInRow + ONE_ELEMENT;
    }

    private void openAllMines() {
        for (int cellIndex = 0; cellIndex < sizeOfField; ++cellIndex) {
            if (cellsCondition.get(cellIndex).getCellFilling() == CellFilling.BOMB) {
                cellsCondition.get(cellIndex).openCell();
            }
        }
    }

    private void checkEndGame() {
        if (isWin) {
            isEndGame = true;
            return;
        }
        for (int cellIndex = 0; cellIndex < sizeOfField; ++cellIndex) {
            CellData cell = cellsCondition.get(cellIndex);
            if (cell.isOpened() && cell.getCellFilling() == CellFilling.BOMB) {
                isEndGame = true;
                break;
            }
        }
    }

    private void checkIsWin() {
        int numOfOpenedCells = 0;
        for (int cellIndex = 0; cellIndex < sizeOfField; ++cellIndex) {
            CellData cell = cellsCondition.get(cellIndex);
            if (cell.isOpened() && cell.getCellFilling() == CellFilling.BOMB) {
                isWin = false;
                break;
            }
            if (cell.isOpened()) {
                ++numOfOpenedCells;
            }
            if (numOfOpenedCells == sizeOfField - numberOfMines) {
                isWin = true;
            }
        }
    }

    private void handleOpenCell(int cellIndexInList) {
        if (cellsCondition.get(cellIndexInList).getCellFilling() == CellFilling.EMPTY) {
            openCellsAround(cellIndexInList);
        }
        if (cellsCondition.get(cellIndexInList).getCellFilling() == CellFilling.BOMB) {
            openAllMines();
        }
        checkIsWin();
        checkEndGame();
    }

    private void openCellsAround(int cellIndex) {

        int startAround = calcStartAround(cellIndex);
        int endAround = calcEndAround(cellIndex);

        int widthOfAroundBlock = calcWidthOfAroundBlock(cellIndex);
        int curNumOfCellInBlock = ORIGIN;
        int cellIndexInList = startAround;
        while (cellIndexInList <= endAround) {
            ++curNumOfCellInBlock;

            if (!cellsCondition.get(cellIndexInList).isOpened()) {

                cellsCondition.get(cellIndexInList).openCell();
                handleOpenCell(cellIndexInList);

            }

            if (curNumOfCellInBlock % widthOfAroundBlock == 0) {
                cellIndexInList += widthOfField + 1 - widthOfAroundBlock;
            } else {
                ++cellIndexInList;
            }

        }
    }

    private void increaseCellsAround(int mineIndexInList) {
        int startAround = calcStartAround(mineIndexInList);
        int endAround = calcEndAround(mineIndexInList);

        int widthOfAroundBlock = calcWidthOfAroundBlock(mineIndexInList);
        int curNumOfCellInBlock = ORIGIN;
        int cellIndexInList = startAround;
        while (cellIndexInList <= endAround) {
            ++curNumOfCellInBlock;

            CellFilling cellFilling = cellsCondition.get(cellIndexInList).getCellFilling();
            if (cellFilling != CellFilling.BOMB) {
                cellsCondition.get(cellIndexInList).increaseCellFilling();
            }

            if (curNumOfCellInBlock % widthOfAroundBlock == 0) {
                cellIndexInList += widthOfField + 1 - widthOfAroundBlock;
            } else {
                ++cellIndexInList;
            }

        }
    }

    private void setMines() {
        for (int indexOfMine = 0; indexOfMine < numberOfMines; ++indexOfMine) {
            int mineIndexInList = ThreadLocalRandom.current().nextInt(ORIGIN, sizeOfField);
            if (cellsCondition.get(mineIndexInList).getCellFilling() == CellFilling.BOMB) {
                --indexOfMine;
                continue;
            }
            cellsCondition.get(mineIndexInList).setCellFilling(CellFilling.BOMB);
            increaseCellsAround(mineIndexInList);
        }
        //debug
        /*
        for (int i = 0; i < lengthOfField; ++i) {
            for (int j = 0; j < widthOfField; ++j) {
                System.out.print(cellsCondition.get(i * widthOfField + j).getCellFilling().ordinal() + " ");

            }
            System.out.println("\n");
        }
        System.out.println("end of field\n");
         */
    }

    private void initCells () {
        for (int cellIndex = 0; cellIndex < sizeOfField; ++cellIndex) {
            CellData cell = new CellData();
            cellsCondition.add(cell);
        }
    }

    private void clearCells() {
        for (CellData cellData : cellsCondition) {
            cellData.setCellFilling(CellFilling.EMPTY);
            cellData.setCellUserNote(CellUserNote.UNKNOWN);
            cellData.closeCell();
        }
    }

    public MinesweeperData(String userName, int numberOfMines, int widthOfField, int lengthOfField) {
        this.userName = userName;
        this.numberOfMines = numberOfMines;
        this.widthOfField  = widthOfField;
        this.lengthOfField = lengthOfField;
        this.sizeOfField   = lengthOfField * widthOfField;
        initCells();
        setMines();
    }

   public void openCell(int cellIndex) {
       cellsCondition.get(cellIndex).openCell();
       handleOpenCell(cellIndex);
       notifyObserver();
   }

    public void changeUserNote(int cellIndex) {
        cellsCondition.get(cellIndex).setNextCellNote();
        notifyObserver();
    }

    public void restartGame() {
        clearCells();
        setMines();
        isWin = false;
        isEndGame = false;
        notifyObserver();
    }

    public int getSizeOfField() {
        return sizeOfField;
    }

    public CellData getCell(int index) {
        return cellsCondition.get(index);
    }

    public boolean isWin() {
        return isWin;
    }

    public boolean isEndGame() {
        return isEndGame;
    }
}
