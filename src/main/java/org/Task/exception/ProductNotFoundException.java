package org.Task.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String massage) {
        super(massage);
    }
}
