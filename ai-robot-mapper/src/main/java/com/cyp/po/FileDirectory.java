package com.cyp.po;

import java.util.Date;
import javax.annotation.Generated;

public class FileDirectory {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String directoryName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String parentId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String fileNum;

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
    public String getDirectoryName() {
        return directoryName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName == null ? null : directoryName.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getParentId() {
        return parentId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getFileNum() {
        return fileNum;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setFileNum(String fileNum) {
        this.fileNum = fileNum == null ? null : fileNum.trim();
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