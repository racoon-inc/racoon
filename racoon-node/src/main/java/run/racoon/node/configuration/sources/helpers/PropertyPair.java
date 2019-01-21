package run.racoon.node.configuration.sources.helpers;

public class PropertyPair {
    private final String key;
    private final String value;

    public static PropertyPair of(String key, String value) {
        return new PropertyPair(key, value);
    }

    private PropertyPair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
