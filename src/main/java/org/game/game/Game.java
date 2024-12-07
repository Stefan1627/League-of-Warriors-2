package org.game.game;

import lombok.Getter;
import lombok.Setter;
import org.game.account.Account;
import org.game.entities.Character;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

@Getter @Setter
public class Game {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static final String filePath1 = "accounts.json";
    private static final String filePath2 = "output.out";
    private ArrayList<Account> accounts;
    private Account currAccount;
    private Character currCharacter;
    private Grid map;
    private int currLvl;
    private Directions invalidDirection;
    private int length;
    private int width;

    public Game() {
    }

    public void run(final ArrayList<Account> accounts) {
        this.accounts = accounts;
        setAccountAndCharacter();
        setCharacterAttributes();

        //generateLimits();
        length = 5;
        width = 5;
        generateMap();
    }

    private void setAccountAndCharacter() {
        int i = 1;
        System.out.println("Please choose an account by entering the index of the account.");
        for (Account account : accounts) {
            System.out.println(i + ". " + account.getInfo());
            i++;
        }
        i = SCANNER.nextInt();
        currAccount = chooseAccount(i - 1);

        System.out.println("Please choose a character to play with");
        i = 1;
        for (Character character : currAccount.getCharacters()) {
            System.out.println(i + ". " + character);
            i++;
        }
        i = SCANNER.nextInt();
        currCharacter = chooseCharacter(i - 1);
        currCharacter.setMaxMana(currCharacter.getMana());
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

    private void generateMap() {
        map = Grid.createGrid(length, width);
        assert map != null;
        map = map.generateMap();
        map.setCurrentCharacter(currCharacter);
    }

    private void handleEnemyMeeting() {
        EnemyFight enemyFight = new EnemyFight();
        System.out.println(currCharacter);
        enemyFight.startFight(currCharacter, map.getCurrentCell().getEnemy());
    }

    public void handleCellEvent() {
        switch (map.getOldType()) {
            case CallEntityType.VOID -> {

            }
            case CallEntityType.PORTAL -> {
                currCharacter.setCurrExp(currCharacter.getCurrExp() + currLvl * 5);
                currLvl++;
                currAccount.setGamesPlayed(currAccount.getGamesPlayed() + 1);
                currCharacter.setCurrLvl(currCharacter.getCurrLvl() + 1);
                currCharacter.evolve();
                //resetGame();
            }
            case CallEntityType.ENEMY -> handleEnemyMeeting();
            case CallEntityType.SANCTUARY -> {
                currCharacter.regenerateMana();
                currCharacter.setHealth(100);
            }
        }
    }

    public void setCharacterAttributes() {
        currCharacter.setStrength(RANDOM.nextInt(1,11));
        currCharacter.setDexterity(RANDOM.nextInt(1,11));
        currCharacter.setCharisma(RANDOM.nextInt(1,11));
    }
}
