package by.bsuir.lab3.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class NoAccessException extends ExceptionWithHttpStatus{
    public NoAccessException() {
        super(FORBIDDEN, "You don't have access");
    }
}
