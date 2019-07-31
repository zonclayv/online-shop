package by.haidash.shop.product.exception;

public class ProductNotFoundException extends EntityNotFoundException {

    public ProductNotFoundException(Long id) {
        super("Product", id);
    }
}