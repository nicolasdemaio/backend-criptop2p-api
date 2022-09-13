package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import lombok.Getter;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InvalidObjectException extends RuntimeException {
    @Getter
    private final Map<String, String> brokenConstraints;

    public InvalidObjectException(Set<ConstraintViolation<ValidatableEntity>> constraintViolations) {
        var brokenConstraints = new HashMap<String, String>();
        constraintViolations.stream().toList().forEach(constraintViolation -> addConstraintTo(brokenConstraints, constraintViolation));
        this.brokenConstraints = brokenConstraints;
    }

    private void addConstraintTo(HashMap<String, String> brokenConstraints, ConstraintViolation<ValidatableEntity> constraintViolation) {
        brokenConstraints.put(
                constraintViolation.getPropertyPath().toString(),
                constraintViolation.getMessage()
        );
    }
}
