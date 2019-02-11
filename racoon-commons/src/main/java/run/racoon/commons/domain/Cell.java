package run.racoon.commons.domain;

public class Cell<T> {
    private final String name;
    private final T value;

    public Cell(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }
}
