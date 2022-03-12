package service.validators;

import service.exceptions.InvalidInputException;

public interface InputValidator<T> {
    void validate(T entity) throws InvalidInputException;
}
