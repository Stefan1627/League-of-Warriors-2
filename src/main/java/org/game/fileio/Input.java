package org.game.fileio;

import org.game.account.Account;

import java.util.ArrayList;

public class Input {
    private ArrayList<Account> accounts;

    public Input() {
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
}
