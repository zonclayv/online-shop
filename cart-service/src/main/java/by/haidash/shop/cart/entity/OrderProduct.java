package by.haidash.shop.cart.entity;

import by.haidash.shop.jpa.entity.CommonEntity;

import javax.persistence.*;

@Entity
public class OrderProduct extends CommonEntity {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderProduct)) return false;
        if (!super.equals(o)) return false;

        OrderProduct that = (OrderProduct) o;

        if (!product.equals(that.product)) return false;
        return quantity != null ? quantity.equals(that.quantity) : that.quantity == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + product.hashCode();
        return result;
    }
}
