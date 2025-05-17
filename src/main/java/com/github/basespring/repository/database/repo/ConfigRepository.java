package com.github.basespring.repository.database.repo;

import com.github.basespring.application.base.BaseRepository;
import com.github.basespring.repository.database.dao.Config;
import com.github.basespring.repository.database.dao.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfigRepository  extends BaseRepository<Config, Long> {
    Optional<Config> findFirstByKey(String key);
}
