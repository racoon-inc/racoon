package run.racoon.node.configuration.exceptions;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ConfigValidationException extends Exception {

    public ConfigValidationException(Set<ConstraintViolation<Object>> validationResults) {
        super(validationResults.toString());
    }
}
