package org.game.game;

import lombok.Getter;
import lombok.Setter;
import org.game.entities.Character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Getter @Setter
public class Grid extends ArrayList<ArrayList<Cell>> {
    private Character currentCharacter;
    private static int gridLength;
    private static int gridWidth;
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

    public static Grid generateMap(int length, int width) {
        Random rand = new Random();

        Grid grid = new Grid(length, width);

        for (int i = 0; i < 2; i++) {
            setCellType(grid, CallEntityType.SANCTUARY, rand);
        }

        for (int i = 0; i < 4; i++) {
            setCellType(grid, CallEntityType.ENEMY, rand);
        }

        setCellType(grid, CallEntityType.PORTAL, rand);

        int remainingCells = gridLength * gridWidth - 7;
        int randomCell = rand.nextInt(remainingCells);
        for (int i = 0; i < randomCell; i++) {
            setCellType(grid, getRandomCallEntityType(), rand);
        }

        return grid;
    }

    public static CallEntityType getRandomCallEntityType() {
        Random random = new Random();

        List<CallEntityType> filteredTypes = Arrays.stream(CallEntityType.values())
                .filter(type -> type != CallEntityType.PLAYER)
                .toList();

        return filteredTypes.get(random.nextInt(filteredTypes.size()));
    }

    public static void setCellType(Grid grid, CallEntityType type, Random rand) {
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

    public void selectStartingCell() {
        Random rand = new Random();

        while (true) {
            int row = rand.nextInt(gridLength);
            int col = rand.nextInt(gridWidth);
            if (this.get(row).get(col).getType() == CallEntityType.VOID) {
                currentCell = this.get(row).get(col);
                currentCell.setType(CallEntityType.PLAYER);
                break;
            }
        }
    }

    private void putPlayerOnCell() {
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
                /*if (!get(i).get(j).isVisited()) {
                    System.out.print("N ");
                } else */if (get(i).get(j).getType() == CallEntityType.PLAYER) {
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
            System.out.println();
        }
        System.out.println("------------------------------------------------");
    }
}
