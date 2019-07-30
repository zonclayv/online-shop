package by.haidash.shop.product.error;

public class KeywordNotFoundException extends EntityNotFoundException {

    public KeywordNotFoundException(Long id) {
        super("Keyword", id);
    }
}