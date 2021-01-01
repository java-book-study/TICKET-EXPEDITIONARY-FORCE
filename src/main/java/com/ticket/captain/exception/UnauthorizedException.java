package com.ticket.captain.exception;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException() { super("AuthenticationFailed");}

}
