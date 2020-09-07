package org.kolesnikov.exception;

public class DbException extends RuntimeException {
    public DbException(String message, Throwable cause) {
        super(message, cause);
    }
}
