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
}
