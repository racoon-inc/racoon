package run.racoon.node;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import run.racoon.node.configuration.CliConfiguration;
import run.racoon.node.handlers.EventHandler;
import run.racoon.node.handlers.SourceRegistrationHandler;
import run.racoon.node.handlers.StartPipelineHandler;
import run.racoon.storage.StorageFactory;
import run.racoon.storage.configuration.RacoonStorageConfiguration;

import java.util.Collections;
import java.util.List;

public class RacoonNode {
    private static final Logger LOG = LoggerFactory.getLogger(RacoonNode.class);

    public static void main(String[] args) {
        var config = new CliConfiguration();
        try {
            JCommander.newBuilder()
                    .addObject(config)
                    .build()
                    .parse(args);
        } catch (ParameterException e) {
            LOG.error(e.getMessage());
            System.exit(1);
        }

        LOG.info("Using configuration: {}", config);
        var storageConfiguration = new RacoonStorageConfiguration(Collections.emptyList()); // TODO
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
