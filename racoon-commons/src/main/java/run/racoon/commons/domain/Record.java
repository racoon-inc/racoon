package run.racoon.commons.domain;

import java.util.HashMap;
import java.util.Map;

public class Record {
    private final Map<String, Object> cells;

    private Record(Map<String, Object> cells) {
        this.cells = cells;
    }

    public <T> T getByName(String name) {
        return (T) cells.get(name);
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
}
