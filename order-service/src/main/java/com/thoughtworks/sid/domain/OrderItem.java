package com.thoughtworks.sid.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "OrderItem")
@ApiModel("OrderItem model")
public class OrderItem {
    @ApiModelProperty("ID")
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ApiModelProperty("productPrice id")
    private Long productPriceId;

    @ApiModelProperty("unloading id")
    private Long unloadingId;

    @ApiModelProperty("amount of products")
    private Integer amount;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="OWNER_ID")
    private Order order;

    public OrderItem() {
    }

    public OrderItem(Long productPriceId, Long unloadingId, Integer amount) {
        this.productPriceId = productPriceId;
        this.unloadingId = unloadingId;
        this.amount = amount;
    }

    public OrderItem(Long id, Long productPriceId, Long unloadingId, Integer amount) {
        this.id = id;
        this.productPriceId = productPriceId;
        this.unloadingId = unloadingId;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Long getProductPriceId() {
        return productPriceId;
    }

    public Long getUnloadingId() {
        return unloadingId;
    }

    public Integer getAmount() {
        return amount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
