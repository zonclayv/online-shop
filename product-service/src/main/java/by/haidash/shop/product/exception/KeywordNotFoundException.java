package by.haidash.shop.product.exception;

public class KeywordNotFoundException extends EntityNotFoundException {

    public KeywordNotFoundException(Long id) {
        super("Keyword", id);
    }
}