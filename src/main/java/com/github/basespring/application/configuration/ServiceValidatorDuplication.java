package com.github.basespring.application.configuration;

import com.github.basespring.application.validation.duplicateentry.EnableDuplicateConstraint;
import com.github.basespring.application.validation.servicevalidator.EnableAllDuplication;
import com.github.basespring.application.validation.servicevalidator.RequestClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.annotation.PostConstruct;
import javax.persistence.Basic;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
@Slf4j
public class ServiceValidatorDuplication {

    @Autowired
    private BasicPackageNameFinder nameFinder;

    protected ApplicationContext applicationContext;

    private List<Class<?>> request = new ArrayList<>();

    @Autowired
    public ServiceValidatorDuplication(ApplicationContext applicationContext) {
        log.info("Initializing Service validator for all duplication");
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void scanAnnotatedRequest() throws ClassNotFoundException {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(EnableAllDuplication.class));
        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(nameFinder.basePackageName);

        for (BeanDefinition candidateComponent : candidateComponents) {
            Class<?> aClass = Class.forName(candidateComponent.getBeanClassName());

            EnableAllDuplication annotation = aClass.getAnnotation(EnableAllDuplication.class);
            if (annotation != null) {
                request.add(aClass);
            }
        }
    }

    @Bean
    public RequestClassLoader requestClassLoader() {
        RequestClassLoader.ClassBuilder builder = RequestClassLoader.builder();
        return builder.addRequest(request)
                .build();
    }
}
