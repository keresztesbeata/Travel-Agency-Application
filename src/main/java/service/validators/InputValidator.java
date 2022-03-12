package business.validators;

import business.exceptions.InvalidInputException;

public interface InputValidator<T> {
    void validate(T entity) throws InvalidInputException;
}
