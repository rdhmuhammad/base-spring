package com.github.basespring.application.configuration;

import com.github.basespring.application.base.BaseEntity;
import com.github.basespring.application.base.BaseRepository;
import com.github.basespring.application.validation.DuplicateEntryModule;
import com.github.basespring.application.validation.EnableDuplicateConstraint;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    @PostConstruct
    public void scanAnnotatedRequests() throws ClassNotFoundException {
        String basePackage = "com.asliri.rnd.repository.api.request";

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(EnableDuplicateConstraint.class));

        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);

        for (BeanDefinition candidateComponent : candidateComponents) {
            Class<?> aClass = Class.forName(candidateComponent.getBeanClassName());

            EnableDuplicateConstraint annotation = aClass.getAnnotation(EnableDuplicateConstraint.class);
            if (annotation != null) {
                entityRepoMap.put(aClass,
                        new EntityRepositoryPair(annotation.entity(), annotation.repo()));
            }
        }
    }


    @Autowired
    public DuplicateEntryConfiguration(ApplicationContext context){
        this.applicationContext = context;
    }


    @Bean
    public DuplicateEntryModule duplicateEntryConstraint() {
        DuplicateEntryModule entryConstraint = new DuplicateEntryModule();

        for (Map.Entry<Class<?>, EntityRepositoryPair> entry : entityRepoMap.entrySet()) {
            BaseRepository<?, ?> repository = applicationContext.getBean(entry.getValue().getRepository());
            entryConstraint.addModule(entry.getValue().getEntity(), repository, entry.getKey());
        }

        return entryConstraint;
    }
}
