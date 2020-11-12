package com.company.bch_vp.entity.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.company.bch_vp.entity.ExceptionHandler.entityNotFound.DetailInfoNotFoundException;
import com.company.bch_vp.entity.ExceptionHandler.entityNotFound.DetailNotFoundException;
import com.company.bch_vp.entity.ExceptionHandler.entityNotFound.EntityNotFoundException;
import com.company.bch_vp.entity.ExceptionHandler.entityNotFound.ProjectNotFoundException;
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
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        return new ResponseEntity<Object>(new ApiError(status, "No handler found", ex), HttpStatus.NOT_FOUND);
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

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundEx(EntityNotFoundException ex,
                                                            WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Entity not found", ex);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DetailNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundEx(DetailNotFoundException ex,
                                                            WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Detail not found", ex);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundEx(ProjectNotFoundException ex,
                                                            WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Project not found", ex);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DetailInfoNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundEx(DetailInfoNotFoundException ex,
                                                            WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "DetailInfo not found", ex);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex,
                                                         WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown exception", ex);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}