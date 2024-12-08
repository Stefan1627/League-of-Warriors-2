package org.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.game.fileio.Input;
import org.game.game.CallEntityType;
import org.game.game.Game;

import java.io.File;
import java.io.IOException;

public class Main {
    private static final String filePath1 = "accounts.json";

//    public static void setDefaultMap(Game game) {
//        game.getMap().setCellType(game.getMap(), CallEntityType.PLAYER, 0, 0);
//        game.getMap().setCurrentCell(game.getMap().getFirst().getFirst());
//        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 1, 0);
//        game.getMap().setCellType(game.getMap(), CallEntityType.SANCTUARY, 2, 0);
//        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 3, 0);
//        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 4, 0);
//
//        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 0, 1);
//        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 1, 1);
//        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 2, 1);
//        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 3, 1);
//        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 4, 1);
//
//        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 0, 2);
//        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 1, 2);
//        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 2, 2);
//        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 3, 2);
//        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 4, 2);
//
//        game.getMap().setCellType(game.getMap(), CallEntityType.SANCTUARY, 0, 3);
//        game.getMap().setCellType(game.getMap(), CallEntityType.SANCTUARY, 1, 3);
//        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 2, 3);
//        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 3, 3);
//        game.getMap().setCellType(game.getMap(), CallEntityType.SANCTUARY, 4, 3);
//
//        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 0, 4);
//        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 1, 4);
//        game.getMap().setCellType(game.getMap(), CallEntityType.VOID, 2, 4);
//        game.getMap().setCellType(game.getMap(), CallEntityType.ENEMY, 3, 4);
//        game.getMap().setCellType(game.getMap(), CallEntityType.PORTAL, 4, 4);
//    }

    public static void main() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Input inputData = objectMapper.readValue(new File(filePath1), Input.class);
        //output = test(inputData.getAccounts());

        Game game = new Game();
        // 1.
        game.run(inputData.getAccounts());

//        setDefaultMap(game);
//        game.getMap().printMap();
//
//        // 3.
//        for(int i = 0; i < 3; i++) {
//            game.getMap().goEast();
//        }
//
//        // 4.
//        System.out.println(game.getCurrCharacter().getMana());
//        game.getCurrCharacter().setMana(20);
//        System.out.println(game.getCurrCharacter().getMana());
//        game.handleCellEvent();
//        System.out.println(game.getCurrCharacter().getMana());
//
//        // 5.
//        game.getMap().goEast();
//        for(int i = 0; i < 3; i++) {
//            game.getMap().goSouth();
//        }
//
//        game.getMap().getCurrentCell().setEnemy();
//        game.getMap().getCurrentCell().getEnemy().setHealth(1);
//
//        game.handleCellEvent();
//
//        System.out.println(game.getCurrCharacter().getCurrExp());

//        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
//        objectWriter.writeValue(new File(filePath2), output);
    }

//    public static ArrayNode test(ArrayList<Account> accounts) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        ArrayNode output = objectMapper.createArrayNode();
//        ObjectNode actionNode = objectMapper.createObjectNode();
//        actionNode.put("gamesPlayed", accounts.getFirst().getGamesPlayed());
//        actionNode.put("name", accounts.getFirst().getInfo().getFirst());
//        actionNode.put("country", accounts.getFirst().getInfo().get(1));
//        actionNode.put("favoriteGame1", accounts.getFirst().getInfo().get(2));
//        actionNode.put("favoriteGame2", accounts.getFirst().getInfo().get(0));
//        actionNode.put("Character1", accounts.getFirst().getCharacters().getFirst().getName());
//        actionNode.put("Character level", accounts.getFirst().getCharacters().getFirst().getClass() + " ");
//        actionNode.put("Character2", accounts.getFirst().getCharacters().get(1).getName());
//        output.add(actionNode);
//        return output;
//    }
}