package com.github.basespring.service;

import com.github.basespring.application.app.AppService;
import com.github.basespring.application.base.ServiceResolver;
import com.github.basespring.application.commons.BeanCopy;
import com.github.basespring.application.commons.StorageUtils;
import com.github.basespring.repository.api.request.RegisterRequest;
import com.github.basespring.repository.database.dao.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AppService implements UserService {
    @Override
    public ServiceResolver<User> register(RegisterRequest request) {
        User user = BeanCopy.copy(request, User.class);
        StorageUtils.upload(request.getProfilePicture());
        user.setProfilePicture(request.getProfilePicture().getOriginalFilename());
        userRepository.save(user);
        return success(user);
    }


}
