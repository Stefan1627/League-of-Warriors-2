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
    private static Game game;
    private static final int MIN_NUM_OF_CELLS = 8;
    private static final int MAX_MAP_SIZE = 10;
    private static final Scanner SCANNER = new Scanner(System.in);
    public static final Random RANDOM = new Random();
    public ArrayList<Account> accounts;
    private Grid map;
    private Account currAccount;
    private Character currCharacter;
    private int gameLvl = 1;
    private Directions invalidDirection;
    private int length;
    private int width;
    private boolean gameOver;
    private boolean resetGame;

    private Game() {
        resetGame = false;
        gameOver = false;
    }

    private Game(final ArrayList<Account> accounts) {
        resetGame = false;
        gameOver = false;
        this.accounts = accounts;
    }

    public static Game createGame() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }

    public static Game createGame(final ArrayList<Account> accounts) {
        if (game == null) {
            game = new Game(accounts);
        }
        return game;
    }

    /**
     * Method run handles the game logic overall
     * @param accounts the list of the accounts loaded from json file
     * @param comingFromTest it represents if the method is called from the main game
     *                       or from the test class
     */
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
            // if comingFromTest is true the limits are already set
            length = 5;
            width = 5;
            map = Grid.generateMap(length, width);
            map.setCurrentCharacter(currCharacter);
            // setting up the test map configuration
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

        // if the program reaches this point that means that the player`s
        // character died and the player should choose again a character to play with
        System.out.println("Game Over");
        resetGame = true;
        gameOver = false;
        run(accounts, comingFromTest);
    }

    /**
     * Mehtod chooseLoginType handles the player`s input for a choosing a login type
     * 1. Enter the email and password
     * 2. Choosing from a list of accounts(easier to debug)
     */
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

    /**
     * Method handleChoosingAccount handles the case in which the player`s choice is to
     * choose from the list of existing accounts
     */
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

    /**
     * Method handleLoginInput handles the case in which the player`s choice is to
     * enter the email and password for the account he wants to play with
     */
    public boolean handleLoginInput(String email, String password) {
        currAccount = null;
        for (Account account : accounts) {
            if(account.accountExists(email, password)) {
                currAccount = account;
            }
        }

        if (currAccount == null) {
            System.out.println("Invalid email or password. ");
        } else {
            return true;
        }
        return false;
    }

    /**
     * Method handleLoginInput handles the case in which the player`s choice is to
     * enter the email and password for the account he wants to play with
     */
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

    /**
     * Method setCharacter gives the player a list of existing characters on the respective
     * account and lets him choose one of them
     */
    private void setCharacter() {
        int i = 1;

        System.out.println("Please choose a character to play with");
        for (Character character : currAccount.getCharacters()) {
            System.out.println(i + ". " + character);
            i++;
        }
        i--;

        // clearing the buffer
        SCANNER.nextLine();

        while (true) {
            String choice = SCANNER.nextLine();
            try {
                int choiceInt = Integer.parseInt(choice);
                if (choiceInt > i || choiceInt < 1) {
                    throw new InvalidChooseOption(i);
                }
                currCharacter = chooseCharacter(choiceInt - 1);
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

    /**
     * @param index the index of the account in the list of accounts
     * @return the specified account
     */
    private Account chooseAccount(int index) {
        return accounts.get(index);
    }

    /**
     * @param index the index of the character in the list of characters
     * @return the specified character
     */
    private Character chooseCharacter(int index) {
        return currAccount.getCharacters().get(index);
    }

    /**
     * Method setCharacterAttributes is setting up the character for the game
     */
    public void setCharacterAttributes() {
        currCharacter.setStrength(RANDOM.nextInt(1,11) * currCharacter.getCurrLvl());
        currCharacter.setDexterity(RANDOM.nextInt(1,11) * currCharacter.getCurrLvl());
        currCharacter.setCharisma(RANDOM.nextInt(1,11) * currCharacter.getCurrLvl());
    }

    /**
     * Method generateLimits randomly generates the length and width of the map
     */
    public void generateLimits() {
        do {
            length = RANDOM.nextInt(MAX_MAP_SIZE + 1);
            width = RANDOM.nextInt(MAX_MAP_SIZE + 1);
        } while (length * width < MIN_NUM_OF_CELLS);
    }

    /**
     * Method handleEnemyMeeting handles the case of stepping on an Enemy Cell
     * It generates a fight and setting the gameOver in function of the player`s character health
     */
    private void handleEnemyMeeting() {
        EnemyFight enemyFight = new EnemyFight();
        Enemy enemy = new Enemy();

        gameOver = enemyFight.fight(currCharacter, enemy);

        if (!gameOver) {
            currCharacter.wonFight(RANDOM.nextInt(40,101));
        }
    }

    /**
     * Method resetGame starts a new game
     * @param comingFromTest in case of this argument is true the program stops
     */
    private void resetGame(boolean comingFromTest) {
        resetGame = true;

        if (comingFromTest) {
            System.out.println("Exiting...");
            System.exit(0);
        }

        run(accounts, false);
    }

    /**
     * Methos handleCellEvent handles the Cell event after every move
     * @param comingFromTest passed further to resetGame
     */
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
                System.out.println("Going through sanctuary, your mana and health regenerated");
                currCharacter.regenerateMana();
                currCharacter.setHealth(Entity.MAX_HEALTH);
            }
        }
    }

    public int handleCellEventUI() {
        switch (map.getOldType()) {
            case CallEntityType.PORTAL -> {
                System.out.println("Going through portal");
                currCharacter.incrementExp(gameLvl * 5);
                gameLvl++;
                currAccount.setGamesPlayed(currAccount.getGamesPlayed() + 1);
                currCharacter.evolve();
                return 1;
            }
            case CallEntityType.ENEMY -> {
                System.out.println("Enemy fight");
                return 2;
            }
            case CallEntityType.SANCTUARY -> {
                System.out.println("Going through sanctuary, your mana and health regenerated");
                currCharacter.regenerateMana();
                currCharacter.setHealth(Entity.MAX_HEALTH);
            }
        }
        return 0;
    }

    /**
     * Method printAvailableOptions verifies all options and print the available ones
     * @return a list of available options
     */
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

    public boolean canMove(String direction) {
        int currX = map.getCurrentCell().getRow();
        int currY = map.getCurrentCell().getCol();
        // Example logic for movement
        return switch (direction) {
            case "North" -> currX > 0;
            case "South" -> currX < length - 1;
            case "West" -> currY > 0;
            case "East" -> currY < width - 1;
            default -> false;
        };
    }

    public void generateMapUI(boolean comingFromTest) {
        if (!comingFromTest) {
            generateLimits();
            map = Grid.generateMap(length, width);
            map.setCurrentCharacter(currCharacter);
            map.selectStartingCell();
        } else {
            // if comingFromTest is true the limits are already set
            length = 5;
            width = 5;
            map = Grid.generateMap(length, width);
            map.setCurrentCharacter(currCharacter);
            // setting up the test map configuration
            Test.setDefaultMap(this);
        }
    }
}