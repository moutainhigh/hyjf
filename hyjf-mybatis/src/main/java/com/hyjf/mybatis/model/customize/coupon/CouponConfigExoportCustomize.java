/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2015年11月20日 下午5:24:10
 * Modification History:
 * Modified by : 
 */

package com.hyjf.mybatis.model.customize.coupon;

import java.io.Serializable;

/**
 * @author Administrator
 */

public class CouponConfigExoportCustomize implements Serializable {

	/**
	 * serialVersionUID:
	 */

	private static final long serialVersionUID = 1L;
    //优惠券名称
    private String couponName;
    //优惠券类型
    private String couponType;
    //优惠券编号
    private String couponCode;
    //优惠券面值
    private String couponQuota;
    //发行数量
    private String couponQuantity;
    //已发放数量
    private String issueNumber;
    //有效期
    private String expirationDate;
    //使用范围-操作平台
    private String couponSystem;
    //使用范围-项目类型
    private String projectType;
    //使用范围-项目期限
    private String tenderQuota;
    //使用范围-出借金额
    private String projectExpirationType;
    //状态
    private String status;
    //发行时间
    private String addTime;
    
    public String getCouponName() {
        return couponName;
    }
    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }
    public String getCouponType() {
        return couponType;
    }
    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }
    public String getCouponQuota() {
        return couponQuota;
    }
    public void setCouponQuota(String couponQuota) {
        this.couponQuota = couponQuota;
    }
    public String getCouponQuantity() {
        return couponQuantity;
    }
    public void setCouponQuantity(String couponQuantity) {
        this.couponQuantity = couponQuantity;
    }
    public String getIssueNumber() {
        return issueNumber;
    }
    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }
    public String getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
    public String getCouponSystem() {
        return couponSystem;
    }
    public void setCouponSystem(String couponSystem) {
        this.couponSystem = couponSystem;
    }
    public String getProjectType() {
        return projectType;
    }
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
    public String getTenderQuota() {
        return tenderQuota;
    }
    public void setTenderQuota(String tenderQuota) {
        this.tenderQuota = tenderQuota;
    }
    public String getProjectExpirationType() {
        return projectExpirationType;
    }
    public void setProjectExpirationType(String projectExpirationType) {
        this.projectExpirationType = projectExpirationType;
    }
    
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCouponCode() {
        return couponCode;
    }
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
    
	
}
