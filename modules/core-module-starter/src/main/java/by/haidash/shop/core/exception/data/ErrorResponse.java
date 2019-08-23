package by.haidash.shop.core.exception.data;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class ErrorResponse implements Serializable{

    private String timestamp;
    private int status;
    private String error;
    private String code;
    private String message;
    private String path;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static class Builder {

        private String code;
        private String message;
        private String timestamp;
        private int status;
        private String error;
        private String path;

        public Builder withCode(String code){
            this.code = code;
            return this;
        }
        public Builder withMessage(String message){
            this.message = message;
            return this;
        }
        public Builder withTimestamp(String timestamp){
            this.timestamp = timestamp;
            return this;
        }
        public Builder withCurrentTimestamp(){
            this.timestamp = ZonedDateTime.now().toString();
            return this;
        }

        public Builder withStatus(int status){
            this.status = status;
            return this;
        }

        public Builder withPath(String path){
            this.path = path;
            return this;
        }

        public Builder withError(String error){
            this.error = error;
            return this;
        }

        public ErrorResponse build(){
            ErrorResponse response = new ErrorResponse();
            response.code = this.code;
            response.message = this.message;
            response.timestamp = this.timestamp;
            response.status = this.status;
            response.path = this.path;
            response.error = this.error;
            return response;
        }
    }
}
