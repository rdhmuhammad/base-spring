package com.github.basespring.application.validation;

import com.github.basespring.application.base.BaseEntity;
import com.github.basespring.application.base.BaseRepository;
import lombok.Getter;

@Getter
public class Module {
    private Class<? extends BaseEntity> entity;
    private Class<?> dtoClass;
    private BaseRepository<?, ?> repo;

    public Module(Class<? extends BaseEntity> entity,BaseRepository<?, ?> repo, Class<?> dtoClass) {
        this.entity = entity;
        this.repo = repo;
        this.dtoClass = dtoClass;
    }


}