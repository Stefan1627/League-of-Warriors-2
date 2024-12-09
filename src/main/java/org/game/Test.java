package org.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.game.fileio.Input;
import org.game.game.CallEntityType;
import org.game.game.Game;

import java.io.File;
import java.io.IOException;

public class Test {
    private static final String filePath1 = "accounts.json";

    public static void setDefaultMap(Game game) {
        game.getMap().setCellType(game.getMap(), CallEntityType.PLAYER, 0, 0);
        game.getMap().setCurrentCell(game.getMap().getFirst().getFirst());
        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 1, 0);
        game.getMap().setCellType(game.getMap(), CallEntityType.SANCTUARY, 2, 0);
        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 3, 0);
        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 4, 0);

        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 0, 1);
        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 1, 1);
        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 2, 1);
        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 3, 1);
        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 4, 1);

        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 0, 2);
        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 1, 2);
        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 2, 2);
        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 3, 2);
        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 4, 2);

        game.getMap().setCellType(game.getMap(), CallEntityType.SANCTUARY, 0, 3);
        game.getMap().setCellType(game.getMap(), CallEntityType.SANCTUARY, 1, 3);
        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 2, 3);
        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 3, 3);
        game.getMap().setCellType(game.getMap(), CallEntityType.SANCTUARY, 4, 3);

        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 0, 4);
        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 1, 4);
        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 2, 4);
        game.getMap().setCellType(game.getMap(), CallEntityType.ENEMY, 3, 4);
        game.getMap().setCellType(game.getMap(), CallEntityType.PORTAL, 4, 4);
    }

    public static void main() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Input inputData = objectMapper.readValue(new File(filePath1), Input.class);

        Game game = new Game();

        game.run(inputData.getAccounts(), true);
    }
}