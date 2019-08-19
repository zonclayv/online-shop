package by.haidash.shop.cart.controller.details;

import by.haidash.shop.cart.entity.OrderProduct;
import by.haidash.shop.core.controller.details.BaseDetails;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CartDetails implements BaseDetails {

    private Long id;

    @NotNull
    private Long user;

    private List<OrderProductDetails> products;

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

    public List<OrderProductDetails> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProductDetails> products) {
        this.products = products;
    }
}
