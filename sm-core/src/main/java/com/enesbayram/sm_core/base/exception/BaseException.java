package com.enesbayram.sm_core.base.exception;

public class BaseException extends  RuntimeException{

    public BaseException(ErrorMessage errorMessage){
        super(errorMessage.prepareErrorMessage());
    }
}
