package com.github.basespring.application.validation;


import com.github.basespring.repository.database.dao.User;
import com.github.basespring.repository.database.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DuplicateEntryUserValidator extends BaseDuplicateEntryConstraint<User, DuplicateEntryUserConstraint, UserRepository> {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init(){
        build(userRepository);
    }
}
