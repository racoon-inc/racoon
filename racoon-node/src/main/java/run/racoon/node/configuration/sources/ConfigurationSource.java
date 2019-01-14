package run.racoon.node.configuration.sources;

public interface ConfigurationSource {
    Object getProperty(String key);
}
