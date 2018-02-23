package com.snilloc.exceptions;

public class DaoDataException extends Exception {

    public DaoDataException(String msg) {
        super(msg);
    }

    public DaoDataException(String msg, Throwable ex) {
        super(msg, ex);
    }

    public DaoDataException(Throwable ex) {
        super(ex);
    }
}
