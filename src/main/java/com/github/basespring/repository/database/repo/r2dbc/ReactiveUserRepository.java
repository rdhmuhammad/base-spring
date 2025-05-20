package com.github.basespring.repository.database.repo.r2dbc;

import com.github.basespring.application.base.BaseR2dbcRepository;
import com.github.basespring.repository.database.dao.jdbc.User;
import com.github.basespring.repository.database.dao.r2dbc.UserReactive;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactiveUserRepository extends BaseR2dbcRepository<UserReactive, Long> {
}
