/**
 * Description:用户开户列表前端显示查询所用po
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by :
 */

package com.hyjf.mybatis.model.customize.web;

import java.io.Serializable;

/**
 * @author 王坤
 */

public class WebProjectListCustomize implements Serializable {

	/**
	 *序列化id
	 */
	private static final long serialVersionUID = 5748630051215873837L;
	// 项目id
	private String borrowNid;
	// 项目标题
	private String borrowName;
	// 项目标题（网站改版）
	private String projectName;
	// 项目还款方式
	private String borrowStyle;
	// 项目类型(汉字 尊享汇、新手汇)
	private String borrowType;
	// 项目类型(数字 4、13)
	private String projectType;
	// 项目年华收益率
	private String borrowApr;
	// 项目期限
	private String borrowPeriod;
	// 项目期限类型
	private String borrowPeriodType;
	// 项目金额
	private String borrowAccount;
	// 项目进度
	private String borrowSchedule;
	// 项目状态
	private String status;
	// 定时发标时间
	private String onTime;
	//定时发标时间戳
	private String time;
	 //是否可用体验金 ||是否可用加息券
    private String couponEnable;
    // 预约状态 0初始 1预约中 2预约结束
    private String bookingStatus;
    // 预约开始时间
    private String bookingBeginTime;
    //预约截止时间
    private String bookingEndTime;
    // 预约进度
    private String borrowAccountScaleAppoint;

	// 产品加息收益率（风险缓释金）
	private String borrowExtraYield;
    //融通宝资产编号
	private String borrowAssetNumber;
	//项目类别标识
	private String borrowClass;
	//20170520改版后为新标 1 其他为老标0
	private String isNew;

	// add by nxl 20180730 产品加息标志位(0:不加息,1:加息)
	private String increaseInterestFlag;
	// add by nxl 20180730 是否为产品加息
	private  String isIncrease;
	/**
	 * 构造方法
	 */

	public WebProjectListCustomize() {
		super();
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public String getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(String borrowType) {
		this.borrowType = borrowType;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOnTime() {
		return onTime;
	}

	public void setOnTime(String onTime) {
		this.onTime = onTime;
	}

    public String getBorrowSchedule() {
        return borrowSchedule;
    }

    public void setBorrowSchedule(String borrowSchedule) {
        this.borrowSchedule = borrowSchedule;
    }

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public String getBorrowAccount() {
		return borrowAccount;
	}

	public void setBorrowAccount(String borrowAccount) {
		this.borrowAccount = borrowAccount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

    public String getCouponEnable() {
        return couponEnable;
    }

    public void setCouponEnable(String couponEnable) {
        this.couponEnable = couponEnable;
    }

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public String getBookingBeginTime() {
		return bookingBeginTime;
	}

	public void setBookingBeginTime(String bookingBeginTime) {
		this.bookingBeginTime = bookingBeginTime;
	}

	public String getBookingEndTime() {
		return bookingEndTime;
	}

	public void setBookingEndTime(String bookingEndTime) {
		this.bookingEndTime = bookingEndTime;
	}

	public String getBorrowAccountScaleAppoint() {
		return borrowAccountScaleAppoint;
	}

	public void setBorrowAccountScaleAppoint(String borrowAccountScaleAppoint) {
		this.borrowAccountScaleAppoint = borrowAccountScaleAppoint;
	}

	public String getBorrowExtraYield() {
		return borrowExtraYield;
	}

	public void setBorrowExtraYield(String borrowExtraYield) {
		this.borrowExtraYield = borrowExtraYield;
	}

	public String getBorrowAssetNumber() {
		return borrowAssetNumber;
	}

	public void setBorrowAssetNumber(String borrowAssetNumber) {
		this.borrowAssetNumber = borrowAssetNumber;
	}

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBorrowClass() {
        return borrowClass;
    }

    public void setBorrowClass(String borrowClass) {
        this.borrowClass = borrowClass;
    }

	public String getBorrowPeriodType() {
		return borrowPeriodType;
	}

	public void setBorrowPeriodType(String borrowPeriodType) {
		this.borrowPeriodType = borrowPeriodType;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getIncreaseInterestFlag() {
		return increaseInterestFlag;
	}

	public void setIncreaseInterestFlag(String increaseInterestFlag) {
		this.increaseInterestFlag = increaseInterestFlag;
	}

	public String getIsIncrease() {
		return isIncrease;
	}

	public void setIsIncrease(String isIncrease) {
		this.isIncrease = isIncrease;
	}
}
