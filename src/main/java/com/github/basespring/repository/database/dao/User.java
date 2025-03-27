package com.github.basespring.repository.database.dao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.basespring.application.base.BaseEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class User extends BaseEntity {
    @Id
    private Long id;

    private String fullName;

    private String password;

    private String email;

    private String phone;

    private String address;

    private String profilePicture;
}
