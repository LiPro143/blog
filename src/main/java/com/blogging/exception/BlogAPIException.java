package com.blogging.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException{

    public BlogAPIException(HttpStatus badRequest, String msg){
        super(msg);
    }
}
