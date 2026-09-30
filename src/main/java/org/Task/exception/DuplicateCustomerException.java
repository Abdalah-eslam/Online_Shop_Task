package org.Task.exception;

public class DuplicateCustomerException extends RuntimeException {
    public DuplicateCustomerException(String massage) {
        super(massage);
    }
}
