package com.github.basespring.application.validation.servicevalidator;

import com.github.basespring.application.base.BaseEntity;
import com.github.basespring.application.base.BaseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class AllColDuplicateEntryValidation<T, E extends BaseEntity> extends ValidationStep<T> {

    private Class<E> entity;

    private EntityManager entityManager;

    private RequestClassLoader requestClassLoader;

    public AllColDuplicateEntryValidation(Class<E> entity, EntityManager entityManager, RequestClassLoader requestClassLoader) {
        this.entity = entity;
        this.entityManager = entityManager;
        this.requestClassLoader = requestClassLoader;
    }

    @Override
    public ValidationResult validate(T input) {
        List<String> byClass = requestClassLoader.findByClass(input.getClass());
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(input);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createTupleQuery();
        Root<E> rootMain = query.from(entity);


        List<Selection<Boolean>> subSelection = new ArrayList<>();
        for (String fieldName : byClass) {
            Subquery<Boolean> subquery = query.subquery(Boolean.class);
            Root<E> root = subquery.from(entity);
            Expression<Long> count = cb.count(root.get("id"));
            subquery.where(cb.equal(root.get(fieldName), beanWrapper.getPropertyValue(fieldName)));
            Expression<Boolean> isExist = cb.selectCase()
                    .when(cb.gt(count, 0), true)
                    .otherwise(false).as(Boolean.class);
            subquery.select(isExist);
            subSelection.add(subquery.alias("exist" + StringUtils.capitalize(fieldName)));
        }

        query.multiselect(subSelection.toArray(new Selection[0]));

        Object singleResult = entityManager.createQuery(query).getResultList();
        log.info("singleResult: {}", singleResult);

        return ValidationResult.valid();
    }

}
