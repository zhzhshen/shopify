package com.thoughtworks.sid.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Product_Price")
@ApiModel("Product Price model")
public class ProductPrice {
    @ApiModelProperty("ID")
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty("Product Id")
    @Basic(optional = false)
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @ApiModelProperty("Price Value")
    @Basic(optional = false)
    @Column(name = "PRICE")
    private Double price;

    @ApiModelProperty("Created Date")
    @Basic(optional = false)
    @Column(name = "CREATED_AT", insertable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    @ApiModelProperty("Effected Date")
    @Column(name = "EFFECTED_AT")
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
