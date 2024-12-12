package org.game.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Credentials {
    private String email;
    private String password;

    public Credentials() {}

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
