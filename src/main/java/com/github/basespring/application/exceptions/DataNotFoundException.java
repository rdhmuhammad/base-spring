package com.github.basespring.application.exceptions;

public class DataNotFoundException extends RuntimeException{

    public DataNotFoundException(){
        super();
    }

    public DataNotFoundException(String msg){
        super(msg);
    }
}
