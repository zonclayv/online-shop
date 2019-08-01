package by.haidash.shop.cart.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Cart {

    private @Id @GeneratedValue Long id;
    private @Column(nullable = false)  Long user;
    private @JsonFormat(pattern = "dd/MM/yyyy hh:mm") LocalDateTime creationDate;
    private @ManyToMany @JoinTable(name = "cart_products") List<OrderProduct> products;

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
}
