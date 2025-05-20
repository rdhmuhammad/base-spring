package com.github.basespring.application.base;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Mono;

@NoRepositoryBean
public interface BaseR2dbcRepository<T, ID>{

    Mono<Boolean> exists(Query query);

    Mono<Page<T>> findAll(Query query, Pageable pageable);
}
