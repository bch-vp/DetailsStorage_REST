package org.bch_vp.details_storage.exception_handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.bch_vp.details_storage.exception_handler.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ApiError apiError = new ApiError(status, "Malformed JSON request", ex);
        return new ResponseEntity(apiError, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ApiError apiError = new ApiError(status, "Method argument not valid", ex);
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiError.setDebugMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        return new ResponseEntity<Object>(new ApiError(status, "No handler found", ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundEx(EntityNotFoundException ex,
                                                            WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getClassName() + " not found", ex);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DetailInfoNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundEx(DetailInfoNotFoundException ex,
                                                            WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Detail and project didn't join", ex);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<Object> handleEntityNotFoundEx(IOException ex,
                                                            WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "API can't parse json", ex);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyHasRelationsException.class)
    protected ResponseEntity<Object> alreadyHasRelationsException(AlreadyHasRelationsException ex,
                                                                  WebRequest request) {
        ApiError apiError = new ApiError(BAD_REQUEST, "Entities already have relations", ex);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(IdNotValidException.class)
    protected ResponseEntity<Object> handleEntityNotFoundEx(IdNotValidException ex,
                                                            WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Id mustn't be presented for POST", ex);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QuantityOfDetailsException.class)
    protected ResponseEntity<Object> handleEntityNotFoundEx(QuantityOfDetailsException ex,
                                                            WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getQuantityMessage(), ex);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberOfQuantityException.class)
    protected ResponseEntity<Object> handleEntityNotFoundEx(NumberOfQuantityException ex,
                                                            WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getQuantityMessage(), ex);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex,
                                                         WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown exception", ex);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}