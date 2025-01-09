package org.game.entities;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class CharacterDeserializer extends JsonDeserializer<Character> {

    @Override
    public Character deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        String profession = node.get("profession").asText();
        String name = node.get("name").asText();
        int currExp = node.get("experience").asInt();
        int currLvl = node.get("level").asInt();

        return CharacterFactory.createCharacter(profession, name, currExp, currLvl);
    }
}
