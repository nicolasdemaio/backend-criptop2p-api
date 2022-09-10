package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public abstract class ValidatableEntity {

    protected void validate() throws InvalidObjectException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        var constraintValidations = validator.validate(this);
        if (isInvalidObjectConsidering(constraintValidations)) throw new InvalidObjectException(constraintValidations);
    }

    private boolean isInvalidObjectConsidering(Set<ConstraintViolation<ValidatableEntity>> validations) {
        return !validations.isEmpty();
    }

}
