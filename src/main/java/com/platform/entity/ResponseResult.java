package com.platform.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
//@AllArgsConstructor
//@NoArgsConstructor
@Data
public class ResponseResult<T> {

    private Integer code;
    private boolean flag;
    private String message;
    private T data;

    public ResponseResult() {
    }

    public ResponseResult(Integer code, boolean flag, String message) {
        this.code = code;
        this.flag = flag;
        this.message = message;
    }

    public ResponseResult(Integer code, boolean flag, String message, T data) {
        this.code = code;
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public ResponseResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseResult<T> success(Integer code,boolean flag,String message,T data){
        return new ResponseResult(code,true,message,data);
    }

    public  ResponseResult<T> fail(Integer code,String message,T data){
        return new ResponseResult(code,message,data);
    }

}
