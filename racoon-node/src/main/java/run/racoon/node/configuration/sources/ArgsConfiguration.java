package run.racoon.node.configuration.sources;

import run.racoon.node.configuration.sources.helpers.PropertyPair;
import run.racoon.node.configuration.sources.helpers.StringCollectionSource;

import java.util.*;

public class ArgsConfiguration implements ConfigurationSource {
    private final StringCollectionSource store;

    public ArgsConfiguration(String[] args) {
        List<PropertyPair> pairs = new ArrayList<>();

        int i = 0;
        while (i < args.length) {
            String key = args[i];
            if (key.startsWith("--")) {
                key = key.replace("--", "");
            } else {
                i++;
                continue;
            }

            if (++i >= args.length) {
                continue;
            }

            String value = args[i];
            pairs.add(PropertyPair.of(key, value));

            i++;
        }

        this.store = new StringCollectionSource(pairs, ".");
    }

    @Override
    public Object getProperty(String key) {
        return store.getProperty(key);
    }

}

