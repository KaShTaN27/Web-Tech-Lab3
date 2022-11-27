package by.bsuir.lab3.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ResourceAlreadyExistsException extends ExceptionWithHttpStatus{
    public ResourceAlreadyExistsException(String message) {
        super(BAD_REQUEST, message);
    }
}
