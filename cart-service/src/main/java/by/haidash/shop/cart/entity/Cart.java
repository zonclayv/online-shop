package by.haidash.shop.cart.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    private @Id @GeneratedValue Long id;
    private @Column(nullable = false)  Long user;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "cart_products")
    private List<OrderProduct> products = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
