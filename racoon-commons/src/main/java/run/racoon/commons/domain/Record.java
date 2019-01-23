package run.racoon.commons.domain;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Record {
    private final Set<Cell> cells;
    // TODO: Do we need offset there?
    private final Long offset;

    public Record(Set<Cell> cells, Long offset) {
        this.cells = cells;
        this.offset = offset;
    }

    public Optional<Cell> getByName(String name) {
        return cells.stream().filter(c -> c.getName().equals(name)).findFirst();
    }

    public Set<Cell> getCells() {
        return cells;
    }

    public Long getOffset() {
        return offset;
    }

    public static class Builder {
        private final Set<Cell> cells;
        private Long offset;

        public Builder() {
            this.cells = new HashSet<>();
            this.offset = 0L;
        }

        public Builder cell(String name, Object value) {
            cells.add(new Cell(name, value));
            return this;
        }

        public Builder offset(Long offset) {
            this.offset = offset;
            return this;
        }

        public Record build() {
            return new Record(cells, offset);
        }
    }
}
