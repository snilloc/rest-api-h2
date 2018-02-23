package com.snilloc.exceptions;

public class JsonConversionException extends Exception {

    public JsonConversionException(String msg) {
        super(msg);
    }

    public JsonConversionException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
