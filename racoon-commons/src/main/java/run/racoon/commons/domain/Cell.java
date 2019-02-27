package run.racoon.commons.domain;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class Cell {
    private final String name;
    private final Object value;

    public Cell(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return Pojomatic.equals(o, this);
    }

    @Override
    public int hashCode() {
        return Pojomatic.hashCode(this);
    }
}
