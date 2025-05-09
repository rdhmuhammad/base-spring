package com.github.basespring.repository.database.repo;

import com.github.basespring.application.base.BaseRepository;
import com.github.basespring.repository.database.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
}
