package com.ticket.captain.exception;

public class EmailExistedException extends RuntimeException {

    public EmailExistedException(String email) {
        super("Email is already registered: " + email);
    }

}
