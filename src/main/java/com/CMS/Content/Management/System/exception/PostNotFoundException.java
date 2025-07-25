package com.CMS.Content.Management.System.exception;

public class PostNotFoundException extends RuntimeException {

    public static final long serialVersionID = 1L;

    public PostNotFoundException(String message){
        super(message);
    }
}
