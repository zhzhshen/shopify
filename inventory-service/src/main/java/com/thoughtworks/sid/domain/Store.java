package com.thoughtworks.sid.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "Store")
@ApiModel("Store model")
public class Store {
    @ApiModelProperty("ID")
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ApiModelProperty("所有者")
    private String owner;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("内容")
    private String description;

    public Store() {
    }

    public Store(String owner, String name, String description) {
        this.owner = owner;
        this.name = name;
        this.description = description;
    }

    public Store(Long id, String owner, String name, String description) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
