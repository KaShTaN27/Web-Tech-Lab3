package by.bsuir.lab3.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleExceptionWithHttpStatus(ExceptionWithHttpStatus exception) {
        return ResponseEntity.status(exception.getHttpStatus()).body(exception.getMessage());
    }
}
