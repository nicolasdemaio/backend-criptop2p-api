package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Set;

public class ValidatableEntity {

    protected void validate() throws InvalidObjectException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        var validations = validator.validate(this);
        if (!validations.isEmpty()) throwExceptionReporting(validations);
    }

    private void throwExceptionReporting(Set<ConstraintViolation<ValidatableEntity>> validations) {
        var constraintViolations = new HashMap<String, String>();
        validations.stream().toList().forEach(constraintViolation -> {
            constraintViolations.put(
                    constraintViolation.getPropertyPath().toString(),
                    constraintViolation.getMessage()
            );
        });
        throw new InvalidObjectException(constraintViolations);
    }

}
