package com.github.basespring.repository.database.dao;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.basespring.application.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "configs")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class Config extends BaseEntity {

    @Id
    private Long id;

    private String key;

    private String value;

    public Config(){
        this.key = "";
        this.value = "";
    }
}
