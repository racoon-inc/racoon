package run.racoon.node.configuration

import org.junit.Rule
import org.junit.contrib.java.lang.system.EnvironmentVariables
import run.racoon.node.configuration.exceptions.ConfigValidationException
import run.racoon.node.configuration.sources.ArgsConfiguration
import run.racoon.node.configuration.sources.EnvironmentSource
import run.racoon.node.configuration.sources.YamlSource
import run.racoon.node.configuration.validation.ConfigValidator
import spock.lang.Specification
import spock.lang.Unroll

class ConfigurationReaderTest extends Specification {
    @Rule
    EnvironmentVariables environmentVariables = new EnvironmentVariables()

    private validator = new ConfigValidator()

    @Unroll
    def "Reads name #name from args #args"() {
        setup:
        def confReader = new ConfigurationReader([new ArgsConfiguration(args as String[])], validator)
        def conf = confReader.readConfiguration(Configuration.class)

        expect:
        conf.name == name
        conf.peers == peers
        conf.storage?.path == path

        where:
        args                                                   | name     | peers        | path
        ["--name", "racoon", "--storage.path", "/foo"]         | "racoon" | []           | "/foo"
        ["--name", "racoon", "0"]                              | "racoon" | []           | null
        ["--name", "racoon", "--peers", "c1", "--peers", "c2"] | "racoon" | ["c1", "c2"] | null
    }

    def "Reads configs from yaml with and overrides with args, when args primary"() {
        setup:
        def classLoader = getClass().getClassLoader()
        def file = new File(classLoader.getResource("config.yml").getFile())
        def confReader = new ConfigurationReader([
                new ArgsConfiguration(["--name", "coon"] as String[]),
                new YamlSource(file.getAbsolutePath())

        ], validator)

        when:
        def conf = confReader.readConfiguration(Configuration.class)

        then:
        conf.name == "coon"
        conf.peers == ["racoon1", "racoon2"]
        conf.storage.path == "/bar"
    }

    def "Overrides yaml with env variables, when arg not set"() {
        setup:
        environmentVariables.set("RACOON_NAME", "cooncoon")
        def classLoader = getClass().getClassLoader()
        def file = new File(classLoader.getResource("config.yml").getFile())
        def confReader = new ConfigurationReader([
                new ArgsConfiguration([] as String[]),
                new EnvironmentSource(),
                new YamlSource(file.getAbsolutePath())

        ], validator)
        when:
        def conf = confReader.readConfiguration(Configuration.class)

        then:
        conf.name == "cooncoon"
    }

    def "Fails on invalid configs"() {
        setup:
        def confReader = new ConfigurationReader([
                new ArgsConfiguration([] as String[])], validator)
        when:
        confReader.readConfiguration(Configuration.class)

        then:
        thrown ConfigValidationException
    }

}
