package by.bsuir.lab3.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class UndefinedRequestedAccessException extends ExceptionWithHttpStatus {
    public UndefinedRequestedAccessException() {
        super(BAD_REQUEST, "Undefined requested access");
    }
}
