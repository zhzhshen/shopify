package com.thoughtworks.sid.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Payment")
@ApiModel("Payment model")
public class Payment {
    @ApiModelProperty("ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty("order id")
    private Long orderId;

    @ApiModelProperty("总金额")
    private Double amount;

    @ApiModelProperty("支付方式")
    private String method;

    public Payment() {
    }

    public Payment(Long orderId, Double amount, String method) {
        this.orderId = orderId;
        this.amount = amount;
        this.method = method;
    }

    public Payment(Long id, Long orderId, Double amount, String method) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.method = method;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
