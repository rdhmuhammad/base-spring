package com.github.basespring.application.configuration;

import com.github.basespring.application.base.BaseEntity;
import com.github.basespring.application.base.BaseRepository;
import com.github.basespring.application.validation.DuplicateEntryConstraint;
import com.github.basespring.application.validation.EnableDuplicateConstraint;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DuplicateEntryConfiguration {

    private final Map<Class<?>, EntityRepositoryPair> entityRepoMap = new HashMap<>();
    private final ApplicationContext applicationContext;

    @Getter
    private static final class EntityRepositoryPair {
        private final Class<? extends BaseEntity> entity;

        private final Class<? extends BaseRepository<?, ?>> repository;
        public EntityRepositoryPair(Class<? extends BaseEntity> entity, Class<? extends BaseRepository<?, ?>> repository) {
            this.entity = entity;
            this.repository = repository;
        }
    }

    @Autowired
    public DuplicateEntryConfiguration(ApplicationContext context){
        this.applicationContext = context;
        Map<String, Object> beans = context.getBeansWithAnnotation(EnableDuplicateConstraint.class);

        for (Object bean : beans.values()) {
            EnableDuplicateConstraint annotation = bean.getClass().getAnnotation(EnableDuplicateConstraint.class);

            if (annotation != null) {
                entityRepoMap.put(bean.getClass(),
                        new EntityRepositoryPair(annotation.entity(), annotation.repo()));
            }
        }
    }


    @Bean
    public DuplicateEntryConstraint duplicateEntryConstraint() {
        DuplicateEntryConstraint entryConstraint = new DuplicateEntryConstraint();

        for (Map.Entry<Class<?>, EntityRepositoryPair> entry : entityRepoMap.entrySet()) {
            BaseRepository<?, ?> repository = applicationContext.getBean(entry.getValue().getRepository());
            entryConstraint.addModule(entry.getValue().getEntity(), repository, entry.getKey());
        }

        return entryConstraint;
    }
}
