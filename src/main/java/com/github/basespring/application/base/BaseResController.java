package com.github.basespring.application.base;

import com.github.basespring.application.constant.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

public class BaseResController {

    @Autowired
    protected Validator validator;

    public BaseResController() {
    }

    ;

    protected <T> void validateValue(T request) {
        Set<ConstraintViolation<T>> oks = this.validator.validate(request);
        if (!oks.isEmpty()) {
            throw new ConstraintViolationException(oks);
        }
    }


    protected ResponseEntity<Response<?>> response() {
        Response<Object> response = new Response<>(AppConstants.Message.DEFAULT_SUCCESS, true);
        return ResponseEntity.ok(response);
    }

    protected <T> ResponseEntity<Response<T>> response(ServiceResolver<T> body) {
        Response<T> response = new Response<>(AppConstants.Message.DEFAULT_SUCCESS, true, body.getData());
        return ResponseEntity.ok(response);
    }

    protected ResponseEntity<Response> responseMessage(String msg) {
        Response<Object> response = new Response<>(msg, true);
        return ResponseEntity.ok(response);
    }

    protected <Dest, Src> ResponseEntity<Response<Dest>> response(ServiceResolver<Src> resolver, ResponseConverter<Src, Dest> converter){
        Dest convert = converter.convert(resolver.getData());
        Response<Dest> response = new Response<>(AppConstants.Message.DEFAULT_SUCCESS, true, convert);
        return ResponseEntity.ok(response);
    }

    protected <Dest, Src> ResponseEntity<Response<PageResponse<Dest>>> responsePage(ServiceResolver<Page<Src>> resolver, ResponseConverter<Page<Src>, PageResponse<Dest>> converter){
        PageResponse<Dest> convert = converter.convert(resolver.getData());
        Response<PageResponse<Dest>> response = new Response<>(AppConstants.Message.DEFAULT_SUCCESS, true, convert);
        return ResponseEntity.ok(response);
    }

}
