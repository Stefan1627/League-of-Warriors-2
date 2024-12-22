package org.game.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.game.entities.Character;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter @Setter
public class Account {
    @JsonUnwrapped
    private Information info;

    private ArrayList<Character> characters;

    @JsonProperty("maps_completed")
    private int gamesPlayed;

    public Account() {
    }

    public Account(String email, String password, List<String> favoriteGames, String name,
                   String country, ArrayList<Character> characters, int gamesPlayed) {
        this.info = new Information.Builder()
                .setCredentials(new Credentials(email, password))
                .setName(name)
                .setCountry(country)
                .setFavoriteGames(favoriteGames)
                .build();
        this.characters = characters;
        this.gamesPlayed = gamesPlayed;
    }

    /**
     * Method getInfo
     * @return a list with the essential information for choosing an account
     */
    public List<String> getInfo() {
        List<String> res = new ArrayList<>();
        res.add(info.name);
        res.add(info.country);
        res.add("" + gamesPlayed);
        return res;
    }

    /**
     * Verifying if the email and password are correct for an account
     * @param email the user input email
     * @param password the user input password
     * @return true or false
     */
    public boolean accountExists(String email, String password) {
        return info.credentials.getEmail().equals(email)
                && info.credentials.getPassword().equals(password);
    }

    @Getter @Setter
    public static class Information {
        private Credentials credentials;
        private String name;
        private String country;

        @JsonProperty("favorite_games")
        private List<String> favoriteGames;

        public Information() {
        }

        public Information(Credentials credentials, List<String> favoriteGames, String name, String country) {
            this.credentials = credentials;
            this.favoriteGames = new ArrayList<>(favoriteGames);
            this.name = name;
            this.country = country;
        }

        public void setFavoriteGames(List<String> favoriteGames) {
            this.favoriteGames = new ArrayList<>(favoriteGames);
            Collections.sort(this.favoriteGames);
        }

        public static class Builder {
            private Credentials credentials;
            private String name;
            private String country;
            private List<String> favoriteGames = new ArrayList<>();

            public Builder setCredentials(Credentials credentials) {
                this.credentials = credentials;
                return this;
            }

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Builder setCountry(String country) {
                this.country = country;
                return this;
            }

            public Builder setFavoriteGames(List<String> favoriteGames) {
                this.favoriteGames = new ArrayList<>(favoriteGames);
                Collections.sort(this.favoriteGames); // Sortare automată
                return this;
            }

            public Information build() {
                return new Information(credentials, favoriteGames, name, country);
            }
        }
    }
}
