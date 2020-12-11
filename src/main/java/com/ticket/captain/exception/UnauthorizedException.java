package com.ticket.captain.exception;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException() { super("인증이 실패하였습니다");}

}
