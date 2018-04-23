package com.thoughtworks.sid.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "ProductLoading")
@ApiModel("Product Loading model")
public class ProductLoading {
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

    public ProductLoading() {
    }

    public ProductLoading(Long storeId, Long productId, Integer stock) {
        this.storeId = storeId;
        this.productId = productId;
        this.stock = stock;
    }

    public ProductLoading(Long id, Long storeId, Long productId, Integer stock) {
        this.id = id;
        this.storeId = storeId;
        this.productId = productId;
        this.stock = stock;
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
