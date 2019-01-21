package run.racoon.node.configuration.validation;

import run.racoon.node.configuration.exceptions.ConfigValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ConfigValidator {
    private final Validator validator;

    public ConfigValidator() {
        var factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public void validate(Object object) throws ConfigValidationException {
        Set<ConstraintViolation<Object>> validationResults = validator.validate(object);
        if (!validationResults.isEmpty()) {
            throw new ConfigValidationException(validationResults);
        }
    }
}
