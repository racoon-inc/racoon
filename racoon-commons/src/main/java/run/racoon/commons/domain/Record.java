package run.racoon.commons.domain;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AutoProperty
public class Record {
    private final Map<String, Object> cells;

    private Record(Map<String, Object> cells) {
        this.cells = cells;
    }

    public <T> T getByName(String name) {
        return (T) cells.get(name);
    }

    public Set<Cell> getCells() {
        return cells.entrySet().stream()
                .map(pair -> new Cell(pair.getKey(), pair.getValue())).collect(Collectors.toSet());
    }

    public static class Builder {
        private Map<String, Object> cells;

        public static Builder newBuilder() {
            return new Builder(new HashMap<>());
        }

        public static Builder newBuilder(Record record) {
            return new Builder(new HashMap<>(record.cells));
        }

        private Builder(Map<String, Object> cells) {
            this.cells = cells;
        }

        public Builder cell(String name, Object value) {
            cells.put(name, value);
            return this;
        }

        public Record build() {
            return new Record(cells);
        }
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }
}
