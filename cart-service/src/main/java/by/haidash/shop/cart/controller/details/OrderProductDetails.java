package by.haidash.shop.cart.controller.details;

import by.haidash.shop.core.controller.details.BaseDetails;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderProductDetails implements BaseDetails {


    private Long id;

    @NotNull
    private Long product;

    @Min(1)
    @NotNull
    private Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
