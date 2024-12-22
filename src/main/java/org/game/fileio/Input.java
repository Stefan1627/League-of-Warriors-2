package org.game.fileio;

import lombok.Getter;
import lombok.Setter;
import org.game.account.Account;

import java.util.ArrayList;

@Setter
@Getter
public class Input {
    private ArrayList<Account> accounts;

    public Input() {
    }

}
