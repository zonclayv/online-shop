package by.haidash.shop.cart.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    private @Id @GeneratedValue Long id;
    private @Column(nullable = false)  Long user;
    private @Column(nullable = false)  CartStatus status = CartStatus.NEW;
    private @JsonFormat(pattern = "dd/MM/yyyy hh:mm") LocalDateTime creationDate;

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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public List<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
    }

    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }
}
