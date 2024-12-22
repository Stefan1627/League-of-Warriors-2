package org.game.spells;

import org.game.entities.Entity;

public interface Visitor<T extends Entity> {
    void visit(T entity);
}
