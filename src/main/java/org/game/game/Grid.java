package org.game.game;

import lombok.Getter;
import lombok.Setter;
import org.game.entities.Character;

import java.util.ArrayList;
import java.util.Random;

@Getter @Setter
public class Grid extends ArrayList<ArrayList<Cell>> {
    private static final int MAX_GRID_SIZE = 10;
    private Character currentCharacter;
    private int gridLength;
    private int gridWidth;
    private Cell currentCell;
    private CallEntityType oldType = CallEntityType.VOID;

    private Grid(int length, int width) {
        gridLength = length;
        gridWidth = width;
        for (int i = 0; i < length; i++) {
            this.add(new ArrayList<>());
            for (int j = 0; j < width; j++) {
                this.get(i).add(new Cell(i, j));
            }
        }
    }

    public static Grid createGrid(int length, int width) {
        if (length > MAX_GRID_SIZE || width > MAX_GRID_SIZE) {
            System.out.println("Limit is too large.");
            return null;
        }
        return new Grid(length, width);
    }

    public Grid generateMap() {
        Random rand = new Random();

        for (int i = 0; i < 2; i++) {
            setCellType(this, CallEntityType.SANCTUARY, rand);
        }

        for (int i = 0; i < 4; i++) {
            setCellType(this, CallEntityType.ENEMY, rand);
        }

        setCellType(this, CallEntityType.PORTAL, rand);

        return this;
    }

    public void setCellType(Grid grid, CallEntityType type, Random rand) {
        while(true) {
            int row = rand.nextInt(gridLength);
            int col = rand.nextInt(gridWidth);
            Cell cell = grid.get(row).get(col);

            if (cell.isEmpty()) {
                cell.setType(type);
                break;
            }
        }
    }

    public void setCellType(Grid grid, CallEntityType type, int row, int col) {
        Cell cell = grid.get(row).get(col);
        cell.setType(type);

        if (type == CallEntityType.PLAYER) {
            cell.setVisited(true);
        }
    }

    public void putPlayerOnCell() {
        oldType = get(currentCell.getRow()).get(currentCell.getCol()).getType();
        setCellType(this, CallEntityType.PLAYER, currentCell.getRow(), currentCell.getCol());
    }

    public void goNorth() {
        System.out.println("Went North");

        setCellType(this, CallEntityType.VOID, currentCell.getRow(), currentCell.getCol());
        currentCell.setRow(currentCell.getRow() - 1);

        putPlayerOnCell();
    }

    public void goSouth() {
        System.out.println("Went South");

        setCellType(this, CallEntityType.VOID, currentCell.getRow(), currentCell.getCol());
        currentCell.setRow(currentCell.getRow() + 1);

        putPlayerOnCell();
    }

    public void goEast() {
        System.out.println("Went East");

        setCellType(this, CallEntityType.VOID, currentCell.getRow(), currentCell.getCol());
        currentCell.setCol(currentCell.getCol() + 1);

        putPlayerOnCell();
    }

    public void goWest() {
        System.out.println("Went West");

        setCellType(this, CallEntityType.VOID, currentCell.getRow(), currentCell.getCol());
        currentCell.setCol(currentCell.getCol() - 1);

        putPlayerOnCell();
    }

    public void printMap() {
        System.out.println("------------------------------------------------");
        for (int i = 0; i < gridLength; i++) {
            for (int j = 0; j < gridWidth; j++) {
                if (!get(i).get(j).isVisited()) {
                    System.out.print("N ");
                } else if (get(i).get(j).getType() == CallEntityType.PLAYER) {
                        System.out.print("P ");
                } else if (get(i).get(j).getType() == CallEntityType.ENEMY) {
                    System.out.print("E ");
                } else if (get(i).get(j).getType() == CallEntityType.PORTAL) {
                    System.out.print("F ");
                } else if (get(i).get(j).getType() == CallEntityType.SANCTUARY) {
                    System.out.print("S ");
                } else if (get(i).get(j).getType() == CallEntityType.VOID) {
                    System.out.print("V ");
                }
            }
        }
        System.out.println("------------------------------------------------");
    }
}
