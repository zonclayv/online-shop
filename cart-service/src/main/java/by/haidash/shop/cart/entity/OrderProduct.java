package by.haidash.shop.cart.entity;

import by.haidash.shop.jpa.entity.BaseEntity;

import javax.persistence.*;

@Entity
public class OrderProduct extends BaseEntity<Long> {

    @Column(nullable = false)
    private Long product;

    @Column(nullable = false)
    private Integer quantity;

    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
