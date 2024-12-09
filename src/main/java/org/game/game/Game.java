package org.game.game;

import lombok.Getter;
import lombok.Setter;
import org.game.Test;
import org.game.account.Account;
import org.game.entities.Character;
import org.game.entities.Enemy;
import org.game.entities.Entity;
import org.game.exceptions.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

@Getter @Setter
public class Game {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private ArrayList<Account> accounts;
    private Account currAccount;
    private Character currCharacter;
    private Grid map;
    private int gameLvl = 1;
    private Directions invalidDirection;
    private int length;
    private int width;
    private boolean gameOver;
    private boolean resetGame;

    public Game() {
        resetGame = false;
    }

    public void run(final ArrayList<Account> accounts, boolean comingFromTest) {
        if (!resetGame) {
            this.accounts = accounts;
            chooseLoginType();
        }

        setCharacter();
        setCharacterAttributes();

        if (!comingFromTest) {
            generateLimits();
            generateMap();
            map.setCurrentCell(map.getFirst().getFirst());
        } else {
            length = 5;
            width = 5;
            generateMap();
            Test.setDefaultMap(this);
        }

        String choice;
        while (true) {
            map.printMap();
            ArrayList<String> availableMoves = printAvailableOptions();
            System.out.println(availableMoves);
            choice = SCANNER.next();
            choice = choice.toLowerCase();
            System.out.println(choice);
            try {
                if (!availableMoves.contains(choice)) {
                    throw new InvalidMoveException("Invalid move, please choose from what is printed on screen");
                }
                if (choice.equals("q"))
                    System.exit(0);

                switch (choice) {
                    case "w" -> map.goNorth();
                    case "a" -> map.goWest();
                    case "d" -> map.goEast();
                    case "s" -> map.goSouth();
                    case "r" -> {
                        resetGame = true;
                        run(accounts, comingFromTest);
                    }
                }

                handleCellEvent(comingFromTest);
                if(gameOver)
                    break;
            } catch (InvalidMoveException e) {
                System.out.println(e.getMessage());
            }
        }

        resetGame = true;
        run(accounts, comingFromTest);
    }

    public void chooseLoginType() {
        System.out.println("Please select the login type.");
        System.out.println("1. Enter Credentials(email + password) manually;");
        System.out.println("2. Choose from a list of accounts");
        System.out.println("3. Exit");
        while (true) {
            String choice = SCANNER.next();
            try {
                if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
                    throw new InvalidChooseOption("Invalid input. Please enter 1, 2, or 3.");
                }

                switch (choice) {
                    case "1" -> {
                        handleLoginInput();
                        return;
                    }
                    case "2" -> {
                        handleChoosingAccount();
                        return;
                    }
                    case "3" -> {
                        System.out.println("Exiting...");
                        System.exit(0);
                    }
                }
            } catch (InvalidChooseOption e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void handleChoosingAccount() {
        int i = 1;
        System.out.println("Please choose an account by entering the index of the account.");
        for (Account account : accounts) {
            System.out.println(i + ". " + account.getInfo());
            i++;
        }
        i--;

        while (true) {
            String choice = SCANNER.next();
            try {
                int choiceInt = Integer.parseInt(choice);
                if (choiceInt > i || choiceInt < 0) {
                    throw new InvalidChooseOption("Invalid input. Please enter a number between 1 and " + i + ".");
                }
                currAccount = chooseAccount(choiceInt - 1);
                break;
            } catch (InvalidChooseOption e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
    }

    private void handleLoginInput() {
        int numOfTries = 3;
        while (numOfTries > 0) {
            System.out.println("Please enter your email.");
            String email = SCANNER.next();
            System.out.println("Please enter your password.");
            String password = SCANNER.next();

            for (Account account : accounts) {
                if(account.accountExists(email, password)) {
                    currAccount = account;
                }
            }

            if (currAccount == null) {
                System.out.print("Invalid email or password. ");
            } else {
                break;
            }
            numOfTries--;
            System.out.println("You have " + numOfTries + " more tries.");
        }
        if (numOfTries == 0) {
            System.out.println("No more tries available, returning to choosing login type.");
            chooseLoginType();
        }
    }

    private void setCharacter() {
        int i = 1;

        System.out.println("Please choose a character to play with");
        for (Character character : currAccount.getCharacters()) {
            System.out.println(i + ". " + character);
            i++;
        }
        i--;

        while (true) {
            String choice = SCANNER.next();
            try {
                int choiceInt = Integer.parseInt(choice);
                if (choiceInt > i || choiceInt < 0) {
                    throw new InvalidChooseOption("Invalid input. Please enter a number between 1 and " + i + ".");
                }
                currCharacter = chooseCharacter(choiceInt - 1);
                currCharacter.setMaxMana(currCharacter.getMana());
                break;
            } catch (InvalidChooseOption e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
    }

    private Account chooseAccount(int index) {
        return accounts.get(index);
    }

    private Character chooseCharacter(int index) {
        return currAccount.getCharacters().get(index);
    }

    private void generateLimits() {
        length = RANDOM.nextInt(3, 10);
        width = RANDOM.nextInt(3, 10);
    }

    public void generateMap() {
        map = Grid.createGrid(length, width);
        assert map != null;
        map = map.generateMap();
        map.setCurrentCharacter(currCharacter);
    }

    private void handleEnemyMeeting() {
        EnemyFight enemyFight = new EnemyFight();
        Enemy enemy = new Enemy();

        gameOver = enemyFight.startFight(currCharacter, enemy);

        if (!gameOver) {
            currCharacter.wonFight(RANDOM.nextInt(5,21));
        }
    }

    private void resetGame(boolean comingFromTest) {
        resetGame = true;

        if (comingFromTest) {
            System.out.println("Exiting...");
            System.exit(0);
        }

        run(accounts, false);
    }

    public void handleCellEvent(boolean comingFromTest) {
        switch (map.getOldType()) {
            case CallEntityType.PORTAL -> {
                System.out.println("Going through portal");
                currCharacter.incrementExp(gameLvl * 5);
                gameLvl++;
                currAccount.setGamesPlayed(currAccount.getGamesPlayed() + 1);
                currCharacter.evolve();
                resetGame(comingFromTest);
            }
            case CallEntityType.ENEMY -> handleEnemyMeeting();
            case CallEntityType.SANCTUARY -> {
                currCharacter.regenerateMana();
                currCharacter.setHealth(Entity.MAX_HEALTH);
            }
        }
    }

    public void setCharacterAttributes() {
        currCharacter.setStrength(RANDOM.nextInt(1,11) * currCharacter.getCurrLvl());
        currCharacter.setDexterity(RANDOM.nextInt(1,11) * currCharacter.getCurrLvl());
        currCharacter.setCharisma(RANDOM.nextInt(1,11) * currCharacter.getCurrLvl());
    }

    public ArrayList<String> printAvailableOptions() {
        ArrayList<String> res = new ArrayList<>();
        int currX = map.getCurrentCell().getRow();
        int currY = map.getCurrentCell().getCol();

        System.out.println("Available options, choose one:");
        for (Directions direction : Directions.values()) {
            if (invalidDirection == direction)
                continue;

            switch (direction) {
                case North -> {
                    if (currX == 0) break;
                    System.out.println("w. Go " + direction);
                    res.add("w");
                }
                case West -> {
                    if (currY == 0) break;
                    System.out.println("a. Go " + direction);
                    res.add("a");
                }
                case East -> {
                    if (currY == width - 1) break;
                    System.out.println("d. Go " + direction);
                    res.add("d");
                }
                case South -> {
                    if (currX == length - 1) break;
                    System.out.println("s. Go " + direction);
                    res.add("s");
                }
            }
        }

        System.out.println("Special options:");
        System.out.println("press 'q' to exit.");
        System.out.println("press 'r' to choose another character");
        res.add("r");
        res.add("q");
        return res;
    }
}
