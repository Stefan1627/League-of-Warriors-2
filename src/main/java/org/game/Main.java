package org.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.game.fileio.Input;
import org.game.game.Game;
import org.game.ui.LoWUI;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final String filePath1 = "accounts.json";

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Input inputData = objectMapper.readValue(new File(filePath1), Input.class);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose one option:");
        System.out.println("1. Terminal game");
        System.out.println("2. GUI version");
        System.out.println("3. Exit");

        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.next());
                if (choice == 1 || choice == 2 || choice == 3) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number");
            }
        }

        switch (choice) {
            case 1 -> {
                Game game = Game.createGame();
                game.run(inputData.getAccounts(), false);
            }
            case 2 -> {
                Game game = Game.createGame(inputData.getAccounts());
                LoWUI loWUI = new LoWUI();
                loWUI.startApplication(game, false);
            }
            case 3 -> System.exit(0);
        }
    }
}