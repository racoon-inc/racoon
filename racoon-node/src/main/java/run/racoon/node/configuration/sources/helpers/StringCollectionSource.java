package run.racoon.node.configuration.sources.helpers;

import run.racoon.node.configuration.sources.ConfigurationSource;

import java.util.*;
import java.util.regex.Pattern;

public class StringCollectionSource implements ConfigurationSource {
    private Map<String, Object> configs = new HashMap<>();
    public StringCollectionSource(List<PropertyPair> args, CharSequence childSeparator) {

        for (PropertyPair entry : args) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.contains(childSeparator)) {
                var keyParts = Arrays.asList(key.split(Pattern.quote(childSeparator.toString())));
                if (keyParts.size() < 2) {
                    continue;
                }

                processNestedConfigs(keyParts, value);
            } else {
                if (configs.containsKey(key)) {
                    Object currentVal = configs.get(key);
                    if (currentVal instanceof List) {
                        ((List) currentVal).add(value);
                    } else {
                        var newValue = new ArrayList<>();
                        newValue.add(currentVal);
                        newValue.add(value);
                        configs.put(key, newValue);
                    }
                    continue;
                }
                configs.put(key, value);
            }
        }
    }


    private void processNestedConfigs(List<String> keyParts, Object value) {
        Iterator<String> iterator = keyParts.iterator();
        Map<String, Object> keyElement = configs;
        String subKey = null;

        while (iterator.hasNext()) {
            subKey = iterator.next();
            if (iterator.hasNext()) {
                var child = keyElement.get(subKey);
                if (!(child instanceof Map)) {
                    var newKeyElement = new HashMap<String, Object>();
                    keyElement.put(subKey, newKeyElement);
                    keyElement = newKeyElement;
                } else {
                    keyElement = (Map<String, Object>) child;
                }
            }
        }

        keyElement.put(subKey, value);
    }

    @Override
    public Object getProperty(String key) {
        return configs.get(key);
    }
}
