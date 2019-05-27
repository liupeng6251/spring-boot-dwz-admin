package org.qvit.hybrid.base.vo;

public class CustomErrorType {

    private int status;
    private String message;
    
    public CustomErrorType(int value, String message) {
        this.status=value;
        this.message=message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
