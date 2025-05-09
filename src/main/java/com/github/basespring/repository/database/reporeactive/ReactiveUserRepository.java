package com.github.basespring.repository.database.reporeactive;

import com.github.basespring.repository.database.daoreactive.UserReactive;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReactiveUserRepository extends ReactiveCrudRepository<UserReactive, Long> {
}
