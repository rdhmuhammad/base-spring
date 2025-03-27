package com.github.basespring.application.validation;

import org.springframework.data.jpa.domain.Specification;

public class DuplicateEntrySpec<T> {

    public Specification<T> filterDuplicate(String keys, String columns) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(columns), keys);
    }
}
