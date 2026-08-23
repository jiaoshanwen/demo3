package com.example.demo.util;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 失败响应（用于异常）
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    // 成功响应（可选）
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }
}