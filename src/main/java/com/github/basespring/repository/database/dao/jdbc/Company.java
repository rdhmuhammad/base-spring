package com.github.basespring.repository.database.dao.jdbc;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="companies")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class Company {
    @Id
    private Long id;

    private String name;

    private String companyId;

    private String companyName;


}
