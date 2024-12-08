package org.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.game.fileio.Input;
import org.game.game.Game;

import java.io.File;
import java.io.IOException;

public class Main {
    private static final String filePath1 = "accounts.json";

    public static void main() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Input inputData = objectMapper.readValue(new File(filePath1), Input.class);

        Game game = new Game();

        game.run(inputData.getAccounts(), false);
    }
}