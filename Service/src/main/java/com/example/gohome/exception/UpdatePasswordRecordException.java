package com.example.gohome.exception;

public class UpdatePasswordRecordException extends Exception {

    @Override
    public String getMessage() {
        return "更新密码记录出现错误，请稍后再试";
    }
}
