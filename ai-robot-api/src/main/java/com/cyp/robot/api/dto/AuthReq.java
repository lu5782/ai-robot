package com.cyp.robot.api.dto;

/**
 * @description
 * @author lyj
 * @date 2019/11/26 16:05
 */
public class AuthReq {
    private String phoneNum;
    private String captchaVerifyCode;
    private String mobileVerifyCode;
    private Integer loginType;
    private String ownerId;
    private String userName;
    private String passWord;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCaptchaVerifyCode() {
        return captchaVerifyCode;
    }

    public void setCaptchaVerifyCode(String captchaVerifyCode) {
        this.captchaVerifyCode = captchaVerifyCode;
    }

    public String getMobileVerifyCode() {
        return mobileVerifyCode;
    }

    public void setMobileVerifyCode(String mobileVerifyCode) {
        this.mobileVerifyCode = mobileVerifyCode;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
