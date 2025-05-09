package com.github.basespring.application.validation;

import com.github.basespring.application.base.BaseEntity;
import com.github.basespring.application.base.BaseRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DuplicateEntryModule {

    private List<Item> items = new ArrayList<>();

    @Data
    @AllArgsConstructor
    public static class Item {
        private Class<? extends BaseEntity> entity;
        private BaseRepository<?, ?> repo;
        private Class<?> dtoClass;


    }

    public DuplicateEntryModule() {
    }

    public void addModule(Class<? extends BaseEntity> entity, BaseRepository<?, ?> repository, Class<?> key) {
        this.items.add(new Item(entity, repository, key));
    }

    public List<Item> getItems() {
        return items;
    }
}