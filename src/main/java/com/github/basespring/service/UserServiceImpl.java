package com.github.basespring.service;

import com.github.basespring.application.app.AppService;
import com.github.basespring.application.base.ServiceResolver;
import com.github.basespring.application.commons.BeanCopy;
import com.github.basespring.application.commons.FileUploadUtil;
import com.github.basespring.application.constant.AppConstants;
import com.github.basespring.application.exceptions.InvalidDataException;
import com.github.basespring.application.validation.servicevalidator.ValidationResult;
import com.github.basespring.repository.api.request.RegisterRequest;
import com.github.basespring.repository.database.dao.jdbc.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class UserServiceImpl extends AppService implements UserService {

    @Override
    public ServiceResolver<User> register(RegisterRequest request) throws IOException {
        ValidationResult validationResult = validateDuplication(User.class)
                .getResult(request);
        if (!validationResult.getIsValid()) {
            throw new InvalidDataException(validationResult.getMessage());
        }
        User user = BeanCopy.copy(request, User.class);

        MultipartFile profilePicture = request.getProfilePicture();
        FileUploadUtil.saveFile(config(AppConstants.UPLOAD_DIR), profilePicture.getOriginalFilename(), profilePicture);

        user.setProfilePicture(profilePicture.getOriginalFilename());
        userRepository.save(user);
        return success(user);
    }

}
