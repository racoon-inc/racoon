package run.racoon.commons.exceptions;

public class ConfigurationException extends RuntimeException {
    public ConfigurationException(String message, Exception exception) {
        super(message, exception);
    }
}
