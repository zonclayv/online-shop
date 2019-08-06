package by.haidash.shop.cart.exception;

public class PermissionDeniedException extends RuntimeException {

    public PermissionDeniedException(String msg){
        super(msg);
    }
}
