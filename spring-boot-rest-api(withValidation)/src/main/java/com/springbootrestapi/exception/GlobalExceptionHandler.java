package com.springbootrestapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {                        //ResponseEntityExceptionHandler is extended to customize the validation error message
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException,
                                                                        WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                resourceNotFoundException.getMessage(),
                webRequest.getDescription(false),
                "USER_NOT_FOUND"
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> handleEmailAlreadyExistsException(EmailAlreadyExistException resourceNotFoundException,
                                                                        WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                resourceNotFoundException.getMessage(),
                webRequest.getDescription(false),
                "USER_EMAIL_ALREADY_EXISTS"
        );
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception resourceNotFoundException,
                                                                        WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                resourceNotFoundException.getMessage(),
                webRequest.getDescription(false),
                "INTERNAL SERVER ERROR"
        );
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,                   //This method is used to customize the validation error message and send that response back to the client
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        // To store multiple error message in an object we use map and send that obj to client
        Map<String, String> errors = new HashMap<>();
//        List<String> errors = new ArrayList<>();

        // From MethodArgumentNotValidException we can get all the validation error message
        List<ObjectError> objectErrorList = ex.getBindingResult().getAllErrors();

        objectErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();                 //Here if we dont cast ((FieldError) error) we wont get the getField() mwthod
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
            /*errors.add(fieldName);
            errors.add(message);*/
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
