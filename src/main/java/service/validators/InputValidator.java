package service.validators;

import service.exceptions.InvalidInputException;

public interface InputValidator {
    void validate() throws InvalidInputException;
}
