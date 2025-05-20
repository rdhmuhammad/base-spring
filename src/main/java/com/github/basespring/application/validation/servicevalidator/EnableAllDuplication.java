package com.github.basespring.application.validation.servicevalidator;

import com.github.basespring.application.base.BaseEntity;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableAllDuplication {
    Class<? extends BaseEntity> entity();
}
