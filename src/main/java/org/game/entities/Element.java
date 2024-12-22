package org.game.entities;

import org.game.spells.Visitor;

public interface Element<T extends Entity> {
    void accept(Visitor<T> visitor);
}
