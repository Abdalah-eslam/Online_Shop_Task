package org.Task.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String Massage) {
        super(Massage);
    }
}
