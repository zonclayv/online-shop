package by.haidash.shop.product.controller.details;

import by.haidash.shop.core.controller.details.BaseDetails;

import javax.validation.constraints.NotEmpty;

public class KeywordDetails implements BaseDetails {

    private Long id;

    @NotEmpty
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
