package com.thoughtworks.sid.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "Inventory")
@ApiModel("Inventory model")
public class Inventory {
    @ApiModelProperty("ID")
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ApiModelProperty("store id")
    private Long storeId;

    @ApiModelProperty("product id")
    private Long productId;

    @ApiModelProperty("库存")
    private Integer stock;

    @ApiModelProperty("库存变化原因")
    private String causeBy;

    @ApiModelProperty("库存变化原因ID")
    private Long causeById;

    public Inventory() {
    }

    public Inventory(Integer stock) {
        this.stock = stock;
    }

    public Inventory(Long id, Integer stock) {
        this.id = id;
        this.stock = stock;
    }

    public Inventory(Long storeId, Long productId, Integer stock, String causeBy, Long causeById) {
        this.storeId = storeId;
        this.productId = productId;
        this.stock = stock;
        this.causeBy = causeBy;
        this.causeById = causeById;
    }

    public Long getId() {
        return id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getStock() {
        return stock;
    }
}
