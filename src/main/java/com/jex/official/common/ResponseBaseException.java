package com.jex.official.common;

public class ResponseBaseException extends Exception {

    private int code;

    public ResponseBaseException(int code, String message){
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
