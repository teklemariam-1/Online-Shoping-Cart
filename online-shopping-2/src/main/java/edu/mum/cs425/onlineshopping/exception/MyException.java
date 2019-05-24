package edu.mum.cs425.onlineshopping.exception;

import edu.mum.cs425.onlineshopping.enums.ResultEnum;


public class MyException extends RuntimeException{

    private Integer code;

    public MyException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public MyException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
