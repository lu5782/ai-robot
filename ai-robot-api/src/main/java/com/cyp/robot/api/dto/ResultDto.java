package com.cyp.robot.api.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResultDto<T> {

    public static final String SUCCESS_CODE = "200";
    public static final String FAIL_CODE = "-1";

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

    public static ResultDto<Object> successDto = new ResultDto<>();
    public static ResultDto<Object> failDto = new ResultDto<>();

    static {
        successDto.setCode(SUCCESS_CODE);
        successDto.setMessage(SUCCESS_MESSAGE);
        failDto.setCode(FAIL_CODE);
        failDto.setMessage(FAIL_MESSAGE);
    }

    public static ResultDto<Object> success() {
        return successDto;
    }

    public static ResultDto<Object> success(Object data) {
        ResultDto<Object> resultDto = new ResultDto<>();
        resultDto.setCode(SUCCESS_CODE);
        resultDto.setMessage(SUCCESS_MESSAGE);
        resultDto.setData(data);
        return resultDto;
    }

    public static ResultDto<Object> fail() {
        return failDto;
    }

    public static ResultDto<Object> fail(String message) {
        ResultDto<Object> resultDto = new ResultDto<>();
        resultDto.setCode(FAIL_CODE);
        resultDto.setMessage(message);
        return resultDto;
    }
}
