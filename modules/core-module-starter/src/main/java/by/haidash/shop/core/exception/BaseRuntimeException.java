package by.haidash.shop.core.exception;

import java.util.concurrent.ThreadLocalRandom;

public class BaseRuntimeException extends RuntimeException {

    private String code;

    public BaseRuntimeException(String msg, String code){
        super(msg);

        this.code = code;
    }

    public BaseRuntimeException(String msg, String code, Exception cause){
        super(msg, cause);

        this.code = code;
    }

    public BaseRuntimeException(String msg, Exception cause){
        this(msg, "000-" + String.valueOf(ThreadLocalRandom.current().nextInt()), cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
