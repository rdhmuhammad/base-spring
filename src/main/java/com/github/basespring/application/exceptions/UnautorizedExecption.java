
package com.github.basespring.application.exceptions;

public class UnautorizedExecption extends RuntimeException{

    public UnautorizedExecption(){
        super();
    }

    public UnautorizedExecption(String msg){
        super(msg);
    }
}
