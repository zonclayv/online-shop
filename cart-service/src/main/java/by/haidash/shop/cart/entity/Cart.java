package by.haidash.shop.cart.entity;

import by.haidash.shop.jpa.entity.CommonEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart extends CommonEntity {

    @Column(nullable = false)
    private Long user;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "cart_products")
    private List<OrderProduct> products = new ArrayList<>();

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public List<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        if (!super.equals(o)) return false;

        Cart cart = (Cart) o;

        if (!user.equals(cart.user)) return false;
        return products != null ? products.equals(cart.products) : cart.products == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }
}
