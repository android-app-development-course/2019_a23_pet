package com.example.gohome.exception;

public class UpdatePasswordHandleException extends Exception {
    @Override
    public String getMessage() {
        return "记录密码操作出错，请稍后重试";
    }
}
