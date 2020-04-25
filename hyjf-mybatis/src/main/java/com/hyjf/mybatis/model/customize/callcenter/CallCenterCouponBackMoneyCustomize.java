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

package com.hyjf.mybatis.model.customize.callcenter;

import java.io.Serializable;

/**
 * @author Administrator
 */

public class CallCenterCouponBackMoneyCustomize implements Serializable {

	/**
	 * serialVersionUID:
	 */

	private static final long serialVersionUID = 1L;
	
	//用户优惠券id
	private Integer id;
	//项目编号
    private String borrowNid;
    //订单号
    private String nid;
	//优惠券用户编号
	private String couponUserCode;
	//优惠券类型
    private String couponType;
    //项目年化收益
    private String borrowApr;
    //项目期限
    private String borrowPeriod;
    //出借金额
    private String account;
    //回款期数
    private String recoverPeriod;
    //优惠券面值
    private String couponQuota;
    //优惠券收益（元）
    private String recoverInterest;
    //转账订单号
    private String transferId;
    //状态
    private String receivedFlg;
    //优惠券使用时间
    private String addTime;
    // 应回款日期
    private String recoverTime;
    //实际回款时间
    private String recoverYestime;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getBorrowNid() {
        return borrowNid;
    }
    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
    public String getNid() {
        return nid;
    }
    public void setNid(String nid) {
        this.nid = nid;
    }
    public String getCouponUserCode() {
        return couponUserCode;
    }
    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode;
    }
    public String getCouponType() {
        return couponType;
    }
    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }
    public String getBorrowApr() {
        return borrowApr;
    }
    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }
    public String getBorrowPeriod() {
        return borrowPeriod;
    }
    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getRecoverPeriod() {
        return recoverPeriod;
    }
    public void setRecoverPeriod(String recoverPeriod) {
        this.recoverPeriod = recoverPeriod;
    }
    public String getCouponQuota() {
        return couponQuota;
    }
    public void setCouponQuota(String couponQuota) {
        this.couponQuota = couponQuota;
    }
    public String getRecoverInterest() {
        return recoverInterest;
    }
    public void setRecoverInterest(String recoverInterest) {
        this.recoverInterest = recoverInterest;
    }
    public String getTransferId() {
        return transferId;
    }
    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }
    public String getReceivedFlg() {
        return receivedFlg;
    }
    public void setReceivedFlg(String receivedFlg) {
        this.receivedFlg = receivedFlg;
    }
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getRecoverTime() {
        return recoverTime;
    }
    public void setRecoverTime(String recoverTime) {
        this.recoverTime = recoverTime;
    }
    public String getRecoverYestime() {
        return recoverYestime;
    }
    public void setRecoverYestime(String recoverYestime) {
        this.recoverYestime = recoverYestime;
    }
    
}
