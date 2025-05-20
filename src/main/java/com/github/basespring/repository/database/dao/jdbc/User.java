package com.github.basespring.repository.database.dao.jdbc;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.basespring.application.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="users")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private Long id;

    private String userCode;

    private String fullName;

    private String password;

    private String email;

    private String phone;

    private String address;

    private String profilePicture;
}
