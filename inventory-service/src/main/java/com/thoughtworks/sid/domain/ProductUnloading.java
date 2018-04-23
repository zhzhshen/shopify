package com.thoughtworks.sid.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "ProductUnloading")
@ApiModel("Product Unloading model")
public class ProductUnloading {
    @ApiModelProperty("ID")
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ApiModelProperty("store id")
    private Long storeId;

    @ApiModelProperty("product id")
    private Long productId;

    @ApiModelProperty("order item id")
    private Long orderItemId;

    @ApiModelProperty("库存")
    private Integer stock;

    @ApiModelProperty("出库状态")
    private String state;

    public ProductUnloading() {
    }

    public ProductUnloading(Long storeId, Integer stock) {
        this.storeId = storeId;
        this.stock = stock;
    }

    public ProductUnloading(Long id, Long orderItemId, Integer stock) {
        this.id = id;
        this.orderItemId = orderItemId;
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

    public void cancel() {
        this.state = "CANCELLED";
    }

    public boolean isCancelled() {
        return "CANCELLED".equals(this.state);
    }

    public void confirm() {
        this.state = "CONFIRMED";
    }

    public boolean isConfirmed() {
        return "CONFIRMED".equals(this.state);
    }
}
