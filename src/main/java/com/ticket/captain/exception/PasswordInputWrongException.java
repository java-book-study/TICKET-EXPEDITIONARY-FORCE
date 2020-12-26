package com.ticket.captain.exception;

public class PasswordInputWrongException extends RuntimeException{

    public PasswordInputWrongException() {super("password length must be between 4 and 13 characters.");}
}
