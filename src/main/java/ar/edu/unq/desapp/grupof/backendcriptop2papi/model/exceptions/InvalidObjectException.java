package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.ValidatableEntity;
import lombok.Getter;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InvalidObjectException extends RuntimeException {
    @Getter
    private final Map<String, String> brokenConstraints;

    public InvalidObjectException(Set<ConstraintViolation<ValidatableEntity>> constraintViolations) {
        var constraints = new HashMap<String, String>();
        constraintViolations.stream().toList().forEach(constraintViolation -> addConstraintTo(constraints, constraintViolation));
        this.brokenConstraints = constraints;
    }

    public InvalidObjectException(String fieldName, String errorMessage) {
        this.brokenConstraints = Map.ofEntries(
                Map.entry(fieldName, errorMessage)
        );
    }

    private void addConstraintTo(HashMap<String, String> brokenConstraints, ConstraintViolation<ValidatableEntity> constraintViolation) {
        brokenConstraints.put(
                constraintViolation.getPropertyPath().toString(),
                constraintViolation.getMessage()
        );
    }

    public String getMessage() {
        return getBrokenConstraints().toString();
    }
}
