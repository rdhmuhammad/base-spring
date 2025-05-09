package com.github.basespring.repository.database.daoreactive;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name="users")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class UserReactive {
    @Id
    private Long id;

    private String userCode;

    private String fullName;

    private String password;

    private String email;

    private String phone;

    private String address;

    private String profilePicture;
}
