package run.racoon.node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import run.racoon.node.configuration.Configuration;
import run.racoon.node.configuration.ConfigurationReader;
import run.racoon.node.configuration.sources.ArgsConfiguration;
import run.racoon.node.configuration.sources.ConfigurationSource;
import run.racoon.node.configuration.sources.EnvironmentSource;
import run.racoon.node.configuration.sources.YamlSource;
import run.racoon.node.handlers.EventHandler;
import run.racoon.node.handlers.SourceRegistrationHandler;
import run.racoon.node.handlers.StartPipelineHandler;
import run.racoon.storage.StorageFactory;
import run.racoon.storage.configuration.RacoonStorageConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RacoonNode {
    private static final Logger LOG = LoggerFactory.getLogger(RacoonNode.class);

    public static void main(String[] args) {
        var configurationSources = new ArrayList<ConfigurationSource>();
        var argConfiguration = new ArgsConfiguration(args);
        configurationSources.add(argConfiguration);
        configurationSources.add(new EnvironmentSource());
        Object configPath = argConfiguration.getProperty("config");
        if (configPath != null) {
            configurationSources.add(new YamlSource(configPath.toString()));
        }
        configurationSources.add(new YamlSource(configPath.toString()));
        var classLoader = RacoonNode.class.getClassLoader();
        var file = new File(classLoader.getResource("default.yml").getFile());
        configurationSources.add(new YamlSource(file.getAbsolutePath()));

        Configuration config = new ConfigurationReader(configurationSources).readConfiguration(Configuration.class);

        LOG.info("Using configuration: {}", config);
        var storageConfiguration = new RacoonStorageConfiguration(Collections.emptyList(), config.getName());
        var storage = StorageFactory.getStorage(storageConfiguration);
        // TODO: load dynamic handlers?
        List<EventHandler> handlers = List.of(
                new SourceRegistrationHandler(storage),
                new StartPipelineHandler(storage)
        );
        var listener = new Listener(config.getPort(), handlers);
        listener.start();

    }
}
