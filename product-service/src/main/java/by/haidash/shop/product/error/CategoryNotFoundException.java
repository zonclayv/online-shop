package by.haidash.shop.product.error;

public class CategoryNotFoundException extends EntityNotFoundException {

    public CategoryNotFoundException(Long id) {
        super("Category", id);
    }
}