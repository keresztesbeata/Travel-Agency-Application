package service.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidOperationException extends Exception{
    public InvalidOperationException(String message) {
        super(message);
    }
}
