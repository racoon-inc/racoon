package run.racoon.node.configuration.sources;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class YamlSource implements ConfigurationSource {
    private Map<String, Object> config;

    public YamlSource(String path) {
        Yaml yaml = new Yaml();
        try (InputStream in = Files.newInputStream(Paths.get(path) ) ) {
            this.config = yaml.load(in);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid configuration ", e);
        }
    }

    public Object getProperty(String key) {
        return this.config.get(key);
    }
}
