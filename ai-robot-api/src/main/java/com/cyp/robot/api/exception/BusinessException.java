package com.cyp.robot.api.exception;

import com.cyp.robot.api.dto.ResultDto;
import org.apache.log4j.Logger;


public class BusinessException extends RuntimeException {

    private static Logger logger = Logger.getLogger(BusinessException.class);

    private ErrorCodes errorCodes;

    public BusinessException(ErrorCodes errorCode) {
        this.errorCodes = errorCode;
    }

    public BusinessException(ResultDto respResult) {
        String code = Integer.toString(this.errorCodes.getErrorCode());
        respResult.setCode(code);
        respResult.setMessage(this.errorCodes.getErrorMsg());
    }

    public BusinessException(ResultDto respResult, ErrorCodes errorCode) {
        String code = Integer.toString(errorCode.getErrorCode());
        respResult.setCode(code);
        respResult.setMessage(errorCode.getErrorMsg());
    }




}
