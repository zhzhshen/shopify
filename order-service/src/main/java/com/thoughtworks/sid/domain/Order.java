package com.thoughtworks.sid.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Order")
@ApiModel("Order model")
public class Order {
    @ApiModelProperty("ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty("customer")
    private String customer;

    @ApiModelProperty("总金额")
    private Double amount;

    @ApiModelProperty("order items")
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItemList;

    public Order() {
    }

    public Order(String customer, Double amount, List<OrderItem> orderItemList) {
        this.customer = customer;
        this.amount = amount;
        this.orderItemList = orderItemList;
    }

    public Order(Long id, String customer, Double amount, List<OrderItem> orderItemList) {
        this.id = id;
        this.customer = customer;
        this.amount = amount;
        this.orderItemList = orderItemList;
    }

    public Long getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public Double getAmount() {
        return amount;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
