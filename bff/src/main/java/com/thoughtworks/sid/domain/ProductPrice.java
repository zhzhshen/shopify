package com.thoughtworks.sid.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("Product Price model")
public class ProductPrice {
    private Long id;

    @ApiModelProperty("Product Id")
    private Long productId;

    @ApiModelProperty("Price Value")
    private Double price;

    @ApiModelProperty("Created Date")
    private Date createdAt;

    @ApiModelProperty("Effected Date")
    private Date effectedAt;

    public ProductPrice() {
    }

    public ProductPrice(Long id, Long productId, Double price, Date createdAt, Date effectedAt) {
        this.id = id;
        this.productId = productId;
        this.price = price;
        this.createdAt = createdAt;
        this.effectedAt = effectedAt;
    }

    public ProductPrice(Long productId, Double price, Date createdAt, Date effectedAt) {
        this.productId = productId;
        this.price = price;
        this.createdAt = createdAt;
        this.effectedAt = effectedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Double getPrice() {
        return price;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getEffectedAt() {
        return effectedAt;
    }
}
