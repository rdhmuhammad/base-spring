package com.github.basespring.repository.api.request;

import com.github.basespring.application.validation.DuplicateEntry;
import com.github.basespring.application.validation.EnableDuplicateConstraint;
import com.github.basespring.repository.database.dao.User;
import com.github.basespring.repository.database.repo.UserRepository;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@EnableDuplicateConstraint(
        entity = User.class,
        repo = UserRepository.class
)
public class RegisterRequest {
    private String fullName;

    private String password;

    @DuplicateEntry(message = "email telah digunakan", columnName = "email", constraint = RegisterRequest.class)
    private String email;

    @DuplicateEntry(message = "telephone telah digunakan", columnName = "phone", constraint = RegisterRequest.class)
    private String phone;

    private String address;

    private MultipartFile profilePicture;
}
