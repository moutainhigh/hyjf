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

package com.hyjf.mybatis.model.customize.datacenter;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * @author Administrator
 */

public class DataCenterCouponCustomize implements Serializable {

	/**
	 * serialVersionUID:
	 */

	private static final long serialVersionUID = 1L;
	
    //来源
    private String title;
    //已发放数量
    private String grantNum;
    //已使用数量
    private String usedNum;
    //已失效数量
    private String expireNum;
    //使用率
    private String utilizationRate;
    //失效率
    private String failureRate;
    //总收益
    private String recoverInterest;
    //已领取收益
    private String recivedMoney;
    //待领取收益
    private String norecivedMoney;
    //待回款收益
    private String waitReciveMoney;
    //已过期收益
    private String expireReciveMoney;
    //累计真实出借金额
    private String realTenderMoney;
	/**
	 * 检索条件 时间开始
	 */
	private String timeStartSrch;

	/**
	 * 检索条件 时间结束
	 */
	private String timeEndSrch;

	/**
	 * 检索条件 limitStart
	 */
	private int limitStart = -1;

	/**
	 * 检索条件 limitEnd
	 */
	private int limitEnd = -1;

   
    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGrantNum() {
        return grantNum;
    }

    public void setGrantNum(String grantNum) {
        this.grantNum = grantNum;
    }

    public String getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(String usedNum) {
        this.usedNum = usedNum;
    }

    public String getExpireNum() {
        return expireNum;
    }

    public void setExpireNum(String expireNum) {
        this.expireNum = expireNum;
    }

    public String getRecoverInterest() {
        return recoverInterest;
    }

    public void setRecoverInterest(String recoverInterest) {
        this.recoverInterest = recoverInterest;
    }

    public String getRecivedMoney() {
        return recivedMoney;
    }

    public void setRecivedMoney(String recivedMoney) {
        this.recivedMoney = recivedMoney;
    }

    public String getNorecivedMoney() {
        return norecivedMoney;
    }

    public void setNorecivedMoney(String norecivedMoney) {
        this.norecivedMoney = norecivedMoney;
    }

    public String getWaitReciveMoney() {
        return waitReciveMoney;
    }

    public void setWaitReciveMoney(String waitReciveMoney) {
        this.waitReciveMoney = waitReciveMoney;
    }

    public String getExpireReciveMoney() {
        return expireReciveMoney;
    }

    public void setExpireReciveMoney(String expireReciveMoney) {
        this.expireReciveMoney = expireReciveMoney;
    }

    public String getRealTenderMoney() {
        return realTenderMoney;
    }

    public void setRealTenderMoney(String realTenderMoney) {
        this.realTenderMoney = realTenderMoney;
    }

    public String getUtilizationRate() {
        DecimalFormat    df   = new DecimalFormat("######0.00"); 
        
        return df.format(Double.parseDouble(usedNum)/Double.parseDouble(grantNum)*100)+"%";
    }

    public void setUtilizationRate(String utilizationRate) {
        this.utilizationRate = utilizationRate;
    }

    public String getFailureRate() {
        DecimalFormat    df   = new DecimalFormat("######0.00"); 
        return df.format(Double.parseDouble(expireNum)/Double.parseDouble(grantNum)*100)+"%";
    }

    public void setFailureRate(String failureRate) {
        this.failureRate = failureRate;
    }

    
    
    
}
