package by.bsuir.lab3.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ResourceNotFoundException extends ExceptionWithHttpStatus{
    public ResourceNotFoundException(String message) {
        super(BAD_REQUEST, message);
    }
}
