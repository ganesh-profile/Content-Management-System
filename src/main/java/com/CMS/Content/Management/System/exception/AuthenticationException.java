package com.CMS.Content.Management.System.exception;

public class AuthenticationException extends RuntimeException {

    public static final long serialVersionID = 1L;
    public AuthenticationException(String message){
        super(message);
    }
}
