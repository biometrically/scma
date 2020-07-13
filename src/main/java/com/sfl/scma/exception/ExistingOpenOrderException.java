package com.sfl.scma.exception;

public class ExistingOpenOrderException extends RuntimeException {
    public ExistingOpenOrderException() {
        super("The table has an existing open order");
    }
}
