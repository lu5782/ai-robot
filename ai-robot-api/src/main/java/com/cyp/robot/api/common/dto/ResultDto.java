package com.cyp.robot.api.common.dto;

public class ResultDto<T> {

    public static final String SUCCESS_CODE = "0000";
    public static final String ERROR_CODE = "00001";

    public static final String SUCCESS_MESSAGE = "success";
    public static final String FAIL_MESSAGE = "fail";

    private String code;
    private String message;
    private T data;

    public ResultDto() {
        this(SUCCESS_CODE, SUCCESS_MESSAGE, null);
    }

    public ResultDto(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
