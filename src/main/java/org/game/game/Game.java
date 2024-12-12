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
    private static final int MIN_NUM_OF_CELLS = 8;
    private static final int MAX_MAP_SIZE = 10;
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private ArrayList<Account> accounts;
    private Grid map;
    private Account currAccount;
    private Character currCharacter;
    private int gameLvl = 1;
    private Directions invalidDirection;
    private int length;
    private int width;
    private boolean gameOver;
    private boolean resetGame;

    public Game() {
        resetGame = false;
        gameOver = false;
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
            map = Grid.generateMap(length, width);
            map.setCurrentCharacter(currCharacter);
            map.selectStartingCell();
        } else {
            length = 5;
            width = 5;
            map = Grid.generateMap(length, width);
            map.setCurrentCharacter(currCharacter);
            Test.setDefaultMap(this);
        }

        String choice;
        System.out.println("Current Level: " + gameLvl);
        while (true) {
            map.printMap();
            ArrayList<String> availableMoves = printAvailableOptions();

            choice = SCANNER.next();
            choice = choice.toLowerCase();

            try {
                if (!availableMoves.contains(choice)) {
                    throw new InvalidMoveException();
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
        gameOver = false;
        run(accounts, comingFromTest);
    }

    private void chooseLoginType() {
        System.out.println("Please select the login type.");
        System.out.println("1. Enter Credentials(email + password) manually;");
        System.out.println("2. Choose from a list of accounts");
        System.out.println("3. Exit");
        while (true) {
            String choice = SCANNER.next();
            try {
                if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
                    throw new InvalidChooseOption(3);
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
                if (choiceInt > i || choiceInt < 1) {
                    throw new InvalidChooseOption(i);
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
                if (choiceInt > i || choiceInt < 1) {
                    throw new InvalidChooseOption(i);
                }
                currCharacter = chooseCharacter(choiceInt - 1);
                currCharacter.setMaxMana(currCharacter.getMana());
                if (currCharacter.getHealth() > 0) {
                    break;
                } else {
                    System.out.println("Oops... Your character is dead, you can`t play with it anymore"
                                        + ", choose another one.");
                }
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
        do {
            length = RANDOM.nextInt(MAX_MAP_SIZE + 1);
            width = RANDOM.nextInt(MAX_MAP_SIZE + 1);
        } while (length * width < MIN_NUM_OF_CELLS);
    }

    private void handleEnemyMeeting() {
        EnemyFight enemyFight = new EnemyFight();
        Enemy enemy = new Enemy();

        gameOver = enemyFight.fight(currCharacter, enemy);

        if (!gameOver) {
            currCharacter.wonFight(RANDOM.nextInt(40,101));
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

    private void handleCellEvent(boolean comingFromTest) {
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
                System.out.println("Going through sanctuary, your mana and health regenerated");
                currCharacter.regenerateMana(currCharacter.getMaxMana());
                currCharacter.setHealth(Entity.MAX_HEALTH);
            }
        }
    }

    private void setCharacterAttributes() {
        currCharacter.setStrength(RANDOM.nextInt(1,11) * currCharacter.getCurrLvl());
        currCharacter.setDexterity(RANDOM.nextInt(1,11) * currCharacter.getCurrLvl());
        currCharacter.setCharisma(RANDOM.nextInt(1,11) * currCharacter.getCurrLvl());
    }

    private ArrayList<String> printAvailableOptions() {
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
