package com.github.basespring.repository.database.repo.jdbc;

import com.github.basespring.application.base.BaseRepository;
import com.github.basespring.repository.database.dao.jdbc.Config;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigRepository  extends BaseRepository<Config, Long> {
    Optional<Config> findFirstByKey(String key);
}
