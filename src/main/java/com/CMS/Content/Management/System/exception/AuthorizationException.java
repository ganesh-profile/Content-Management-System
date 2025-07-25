package com.CMS.Content.Management.System.exception;

public class AuthorizationException extends RuntimeException{
    public static final long serialVersionID = 1L;
    public AuthorizationException(String message){
        super(message);
    }
}
