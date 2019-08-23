package by.haidash.shop.core.exception;

public class InternalServerException extends BaseRuntimeException {

    public static final String INTERNAL_SERVER_EXCEPTION_CODE = "409-001";

    public InternalServerException(String msg, String code) {
        super(msg, code);
    }

    public InternalServerException(String msg, Exception cause) {
        super(msg, cause);
    }

    public InternalServerException(String msg) {
        super(msg, INTERNAL_SERVER_EXCEPTION_CODE);
    }
}
