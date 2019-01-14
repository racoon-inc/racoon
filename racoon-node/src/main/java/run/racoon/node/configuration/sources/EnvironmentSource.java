package run.racoon.node.configuration.sources;

import run.racoon.node.configuration.sources.helpers.PropertyPair;
import run.racoon.node.configuration.sources.helpers.StringCollectionSource;

import java.util.List;
import java.util.stream.Collectors;

public class EnvironmentSource implements ConfigurationSource {
    private static final String PROPERTY_PREFIX = "RACOON_";
    private final StringCollectionSource store;

    public EnvironmentSource() {
        List<PropertyPair> pairs = System.getenv().entrySet().stream()
                .filter(item -> item.getKey().startsWith(PROPERTY_PREFIX))
                .map(item -> PropertyPair.of(
                        item.getKey().replace(PROPERTY_PREFIX, "").toLowerCase(),
                        item.getValue())
                )
                .collect(Collectors.toList());

        this.store = new StringCollectionSource(pairs, "__");

    }

    @Override
    public Object getProperty(String key) {
        return store.getProperty(key);
    }
}
