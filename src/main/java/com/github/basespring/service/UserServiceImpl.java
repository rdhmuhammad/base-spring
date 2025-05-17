package com.github.basespring.service;

import com.github.basespring.application.app.AppService;
import com.github.basespring.application.base.ServiceResolver;
import com.github.basespring.application.commons.BeanCopy;
import com.github.basespring.application.commons.FileUploadUtil;
import com.github.basespring.application.commons.StorageUtils;
import com.github.basespring.application.constant.AppConstants;
import com.github.basespring.repository.api.request.RegisterRequest;
import com.github.basespring.repository.database.dao.User;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserServiceImpl extends AppService implements UserService {
    @Override
    public ServiceResolver<User> register(RegisterRequest request) throws IOException {
        User user = BeanCopy.copy(request, User.class);

        FileUploadUtil.saveFile(config(AppConstants.UPLOAD_DIR), request.getProfilePicture().getOriginalFilename(), request.getProfilePicture().getInputStream());

        user.setProfilePicture(request.getProfilePicture().getOriginalFilename());
        userRepository.save(user);
        return success(user);
    }

}
