/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.mongo.hgdatareport.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author PC-LIUSHOUYI
 * @version UserInfoSHA256Entity, v0.1 2018/12/7 15:03
 */
@Document(collection = "ht_user_info_sha256")
public class UserInfoSHA256Entity {
    /**
     * 平台用户编号
     */
    private Integer userId;
    /**
     * 真实姓名
     */
    private String trueName;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 密文
     */
    private String sha256;
    /**
     * 是否上送开户信息
     */
    private String isOpenUp;
    /**
     * 是否上送借款为0的状态
     */
    private String isLenderZeroUp;
    /**
     * 是否上送借款不为0的状态
     */
    private String isLenderOneUp;
    /**
     * 插入时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public String getIsOpenUp() {
        return isOpenUp;
    }

    public void setIsOpenUp(String isOpenUp) {
        this.isOpenUp = isOpenUp;
    }

    public String getIsLenderZeroUp() {
        return isLenderZeroUp;
    }

    public void setIsLenderZeroUp(String isLenderZeroUp) {
        this.isLenderZeroUp = isLenderZeroUp;
    }

    public String getIsLenderOneUp() {
        return isLenderOneUp;
    }

    public void setIsLenderOneUp(String isLenderOneUp) {
        this.isLenderOneUp = isLenderOneUp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
