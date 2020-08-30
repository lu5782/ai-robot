package com.cyp.po;

import java.util.Date;
import javax.annotation.Generated;

public class UserInfo {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String username;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String password;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer phoneNum;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String email;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Boolean sex;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer isDeleted;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String createdBy;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date createdDate;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String updatedBy;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date updatedDate;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getId() {
        return id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getUsername() {
        return username;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getPassword() {
        return password;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getPhoneNum() {
        return phoneNum;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setPhoneNum(Integer phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getEmail() {
        return email;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Boolean getSex() {
        return sex;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getIsDeleted() {
        return isDeleted;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getCreatedBy() {
        return createdBy;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getCreatedDate() {
        return createdDate;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getUpdatedBy() {
        return updatedBy;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getUpdatedDate() {
        return updatedDate;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}