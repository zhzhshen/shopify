package com.thoughtworks.sid.productService.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@ApiModel("Product model")
public class Product {
    @ApiModelProperty("ID")
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("内容")
    private String description;

    public Product() {
    }

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Product(Long id, String name, String description) {
        this.id = id;
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

}
