package org.game.game;

import lombok.Getter;
import lombok.Setter;
import org.game.entities.Enemy;

@Getter @Setter
public class Cell {
    private int row;
    private int col;
    private CallEntityType type;
    private boolean visited;
    private Enemy enemy;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        visited = false;
        type = CallEntityType.VOID;
    }

    public boolean isEmpty() {
        return type == CallEntityType.VOID;
    }
}
