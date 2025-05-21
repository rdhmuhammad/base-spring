package com.github.basespring.repository.api.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.basespring.application.validation.duplicateentry.DuplicateEntry;
import com.github.basespring.application.validation.duplicateentry.EnableDuplicateConstraint;
import com.github.basespring.application.validation.servicevalidator.DuplicationInDB;
import com.github.basespring.application.validation.servicevalidator.EnableAllDuplication;
import com.github.basespring.repository.database.dao.jdbc.User;
import com.github.basespring.repository.database.repo.jdbc.UserRepository;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@EnableAllDuplication()
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RegisterRequest {
    private String fullName;

    private String password;

    @DuplicationInDB
    private String email;

    @DuplicationInDB
    private String phone;

    private String address;

    @NotNull(message = "File cannot be null")
    private MultipartFile profilePicture;
}
