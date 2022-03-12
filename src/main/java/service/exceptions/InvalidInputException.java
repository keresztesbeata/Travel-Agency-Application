package service.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidInputException extends Exception{
    public InvalidInputException(String message) {
        super(message);
    }
}
