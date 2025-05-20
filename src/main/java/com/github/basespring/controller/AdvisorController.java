package com.github.basespring.controller;

import com.github.basespring.application.base.Response;
import com.github.basespring.application.commons.StringUtils;
import com.github.basespring.application.exceptions.DataNotFoundException;
import com.github.basespring.application.exceptions.InvalidDataException;
import com.github.basespring.application.exceptions.UnautorizedExecption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@RestControllerAdvice
public class AdvisorController {

    private final Logger logger = LoggerFactory.getLogger(AdvisorController.class);

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity<Response> value(HttpClientErrorException ex) {
        logger.error("exception {}", ex.getMessage());
        Response response = new Response();
        response.setCode(ex.getRawStatusCode());
        response.setMessages(ex.getStatusText());
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<Response> value(DataNotFoundException ex) {
        logger.error("exception {}", ex.getMessage());
        Response response = new Response();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setMessages(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }

    @ExceptionHandler(value = InvalidDataException.class)
    public ResponseEntity<Response> value(InvalidDataException ex) {
        logger.error("exception {}", ex.getMessage());
        Response response = new Response();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setMessages(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }

    @ExceptionHandler(value = UnautorizedExecption.class)
    public ResponseEntity<Response> value(UnautorizedExecption ex) {
        logger.error("exception {}", ex.getMessage());
        Response response = new Response();
        response.setCode(HttpStatus.UNAUTHORIZED.value());
        response.setMessages(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response valueValidateException(MethodArgumentNotValidException ex, WebRequest request) {
        logger.error("exception MethodArgumentNotValid {}", ex.getMessage());
        List<FieldError> errorFields = ex.getBindingResult().getFieldErrors();
        List<FieldError> errors = new LinkedList<>(errorFields);

        List<ObjectError> allErrorFields = ex.getBindingResult().getAllErrors();
        List<ObjectError> allErrors = new LinkedList<>(allErrorFields);
        // force filter non null fields
        errors.sort(Comparator.comparing(FieldError::getField));
        Map<String, String> fields = new HashMap<>();

        Response<?> response = new Response<>();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        if (errors.isEmpty()) {
            for (ObjectError o : allErrors) {
                fields.put(o.getObjectName(), o.getDefaultMessage());
            }
        }
        for (FieldError o : errors) {
            String name = o.getField();
            String snakeCase = name.replaceAll("([A-Z])", "$1").replaceFirst("^", "").toLowerCase();
            fields.put(snakeCase, buildMessage(o));
        }
        if (!errors.isEmpty()) {
            response.setMessages(buildMessage(errors.get(0)));
        }
        if (!allErrors.isEmpty()) {
            response.setMessages(allErrors.get(0).getDefaultMessage());
        }
        response.setErrors(fields);
        return response;
    }

    private String buildMessage(FieldError o) {
        StringBuilder message = new StringBuilder();
        message.append(o.getDefaultMessage());
        return message.toString();
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response valueValidateException(ConstraintViolationException ex, WebRequest request) {
        logger.error("exception ConstraintViolation {}", ex.getMessage(), ex);
        Map<String, String> fields = new HashMap<>();
        for (ConstraintViolation c : ex.getConstraintViolations()) {
            String name = c.getPropertyPath().iterator().next().getName();
            String snakeCase = name.replaceAll("([A-Z])", "$1").replaceFirst("^", "").toLowerCase();
            fields.put(snakeCase, c.getMessage());
        }
        Response response = new Response();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setMessages(fields.entrySet().stream().findFirst().orElse(Map.entry("", "")).getValue());
        if (fields.isEmpty()) return response;
        response.setErrors(fields);
        return response;
    }


    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response valueValidateException(BindException ex) {
        logger.error("exception ConstraintViolation {}", ex.getMessage(), ex);

        FieldError fieldError = ex.getFieldError();
        String errMsg = "";
        if (Objects.nonNull(fieldError)) {
            String $1 = StringUtils.toCapitalizeWord(fieldError.getField());
            String $2 = fieldError.getDefaultMessage();
            errMsg = $1 + " " + $2;
        }


        Response response = new Response();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setMessages(errMsg);
        return response;
    }

}
