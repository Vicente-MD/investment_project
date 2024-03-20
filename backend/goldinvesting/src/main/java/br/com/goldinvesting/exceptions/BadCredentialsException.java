package br.com.goldinvesting.exceptions;


public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException(String msg){
        super(msg);
    }
}