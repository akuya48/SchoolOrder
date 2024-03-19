package com.sky.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(){}
    public CategoryNotFoundException(String msg){super(msg);}
}
