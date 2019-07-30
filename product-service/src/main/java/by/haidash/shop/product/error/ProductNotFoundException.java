package by.haidash.shop.product.error;

public class ProductNotFoundException extends EntityNotFoundException {

    public ProductNotFoundException(Long id) {
        super("Product", id);
    }
}