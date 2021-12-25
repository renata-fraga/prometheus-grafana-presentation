package edu.poc.prometheus.application.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ObjectValidator<T> {

    private final LocalValidatorFactoryBean validator;

    public void validateFields(final T object) {

        var violations = validator.validate(object);

        if (!violations.isEmpty()) {
            throw new RuntimeException(getViolations(violations));
        }
    }

    private String getViolations(final Set<ConstraintViolation<T>> constraintViolationSet) {
        return constraintViolationSet
            .stream()
            .collect(Collectors.toMap(setList -> setList.getPropertyPath().toString(), ConstraintViolation::getMessage))
            .toString();
    }

}
