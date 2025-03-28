package com.github.basespring.application.validation;

import com.github.basespring.application.base.BaseEntity;
import com.github.basespring.application.base.BaseRepository;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableDuplicateConstraint {

    Class<? extends BaseEntity> entity();

    Class<? extends BaseRepository<?, ?>> repo();

}
