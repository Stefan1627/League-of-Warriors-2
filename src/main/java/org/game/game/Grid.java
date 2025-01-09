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

    /**
     * Method generateMap
     * Generates a Grid of Cells and give a specific type for each Cell
     * @param length the length of the grid
     * @param width the width of the grid
     * @return the generate map
     */
    public static Grid generateMap(int length, int width) {
        Random rand = new Random();

        Grid map = new Grid(length, width);

        for (int i = 0; i < 2; i++) {
            setCellType(map, CallEntityType.SANCTUARY, rand);
        }

        for (int i = 0; i < 4; i++) {
            setCellType(map, CallEntityType.ENEMY, rand);
        }

        setCellType(map, CallEntityType.PORTAL, rand);

        int remainingCells = gridLength * gridWidth - 7;
        int randomCell = rand.nextInt(remainingCells);
        for (int i = 0; i < randomCell; i++) {
            setCellType(map, getRandomCallEntityType(), rand);
        }

        return map;
    }

    /**
     * Method getRandomCallEntityType
     * @return a random type from the enum to put on a Cell
     */
    private static CallEntityType getRandomCallEntityType() {
        Random random = new Random();

        List<CallEntityType> filteredTypes = Arrays.stream(CallEntityType.values())
                .filter(type -> type != CallEntityType.PLAYER
                        && type != CallEntityType.PORTAL)
                .toList();

        return filteredTypes.get(random.nextInt(filteredTypes.size()));
    }

    /**
     * Method setCellType
     * Giving type to a random Cell from the grid
     * @param type the type to be added
     * @param rand the random object for generation the coordinates
     */
    private static void setCellType(Grid map, CallEntityType type, Random rand) {
        while(true) {
            int row = rand.nextInt(gridLength);
            int col = rand.nextInt(gridWidth);
            Cell cell = map.get(row).get(col);

            if (cell.isEmpty()) {
                cell.setType(type);
                break;
            }
        }
    }

    /**
     * Method setCellType
     * Giving type to an already chosen Cell
     * @param type the type to be added
     * @param row the row of the chosen one
     * @param col the col of the chosen one
     */
    public void setCellType(Grid map, CallEntityType type, int row, int col) {
        Cell cell = map.get(row).get(col);
        cell.setType(type);

        if (type == CallEntityType.PLAYER) {
            cell.setVisited(true);
        }
    }

    /**
     * Method selectStartingCell
     * Randomly generates some coords to put the player on at the start of the game
     */
    public void selectStartingCell() {
        Random rand = new Random();

        while (true) {
            int row = rand.nextInt(gridLength);
            int col = rand.nextInt(gridWidth);
            if (this.get(row).get(col).getType() == CallEntityType.VOID) {
                currentCell = this.get(row).get(col);
                currentCell.setType(CallEntityType.PLAYER);
                this.get(row).get(col).setVisited(true);
                break;
            }
        }
    }

    /**
     * Method putPlayerOnCell
     * After a move the Cell type needs to be updated for printing the correct map
     */
    private void putPlayerOnCell() {
        oldType = get(currentCell.getRow()).get(currentCell.getCol()).getType();
        setCellType(this, CallEntityType.PLAYER, currentCell.getRow(), currentCell.getCol());
    }

    /**
     * Method goNorth
     * Moves the player to the first Cell in north direction
     */
    public void goNorth() {
        System.out.println("Went North");

        setCellType(this, CallEntityType.VOID, currentCell.getRow(), currentCell.getCol());
        currentCell.setRow(currentCell.getRow() - 1);

        putPlayerOnCell();
    }

    /**
     * Method goNorth
     * Moves the player to the first Cell in south direction
     */
    public void goSouth() {
        System.out.println("Went South");

        setCellType(this, CallEntityType.VOID, currentCell.getRow(), currentCell.getCol());
        currentCell.setRow(currentCell.getRow() + 1);

        putPlayerOnCell();
    }

    /**
     * Method goNorth
     * Moves the player to the first Cell in east direction
     */
    public void goEast() {
        System.out.println("Went East");

        setCellType(this, CallEntityType.VOID, currentCell.getRow(), currentCell.getCol());
        currentCell.setCol(currentCell.getCol() + 1);

        putPlayerOnCell();
    }

    /**
     * Method goNorth
     * Moves the player to the first Cell in west direction
     */
    public void goWest() {
        System.out.println("Went West");

        setCellType(this, CallEntityType.VOID, currentCell.getRow(), currentCell.getCol());
        currentCell.setCol(currentCell.getCol() - 1);

        putPlayerOnCell();
    }

    /**
     * Method printMap
     * Printing the map as the player should see it
     */
    public void printMap() {
        System.out.println("----------------------");
        for (int i = 0; i < gridLength; i++) {
            for (int j = 0; j < gridWidth; j++) {
                if (get(i).get(j).getType() == CallEntityType.PLAYER) {
                    System.out.print("P ");
                } else if (!get(i).get(j).isVisited()) {
                    System.out.print("N ");
                } else if (get(i).get(j).getType() == CallEntityType.VOID) {
                    System.out.print("V ");
                }
            }
            System.out.println();
        }
        System.out.println("----------------------");
    }
}