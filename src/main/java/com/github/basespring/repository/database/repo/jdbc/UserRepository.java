package com.github.basespring.repository.database.repo.jdbc;

import com.github.basespring.application.base.BaseRepository;
import com.github.basespring.repository.database.dao.jdbc.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
}
