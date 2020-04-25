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

package com.hyjf.mybatis.model.customize;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 还款列表
 *
 * @author 孙亮
 * @since 2015年12月19日 上午9:29:09
 */
public class RepayExceptionCustomize implements Serializable {
	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 检索条件 limitStart
	 */
	private int limitStart = -1;
	/**
	 * 检索条件 limitEnd
	 */
	private int limitEnd = -1;
	/**
	 * 应还日期 检索条件
	 */
	private String repayLastTimeStartSrch;
	/**
	 * 应还日期 检索条件
	 */
	private String repayLastTimeEndSrch;

	// ========================参数=============================
	private String borrowNid;// 借款编号
	private String userId;// 借款人ID
	private String borrowUserName;// 借款人用户名
	private String borrowName;// 借款标题
	private String projectType;// 项目类型id
	private String projectTypeName;// 项目类型名称
	private String borrowPeriod;// 借款期限
	private String borrowApr;// 年化收益
	private String borrowAccount;// 借款金额
	private String borrowAccountYes;// 借到金额
	private String repayType;// 还款方式中文名
	private String repayAccountCapital;// 应还本金
	private String repayAccountInterest;// 应还利息
	private String repayAccountAll;// 应还本息
	private String borrowManager;// 没用到
	private String repayFee;// 管理费
	private String repayAccountCapitalYes;// 已还本金
	private String repayAccountInterestYes;// 已还利息
	private String repayAccountYes;// 已还本息
	private String repayAccountCapitalWait;// 未还本金
	private String repayAccountInterestWait;// 未还利息
	private String repayAccountWait;// 未还本息
	private String status;// 还款状态
	private String repayStatus;// 还款状态(JOB)
	private String repayLastTime;// 最后还款日
	private String repayNextTime;// 下次还款日
	private String borrowStyle;// 还款方式
	private String periodNow; // 当前期数
	private String data; // 失败原因
	private String tenderUserId; // 出借人用户ID
	private String tenderUserName; // 出借人用户名
	private BigDecimal recoverAccount; // 应还本息
	private BigDecimal recoverAccountYes; // 实还本息
	private BigDecimal recoverFee; // 应还管理费
	private Integer recoverStatus; // 状态
	private String recoverYestime;// 还款日
	private String recoverPeriod; // 期数
	private Integer monthType; // 状态 0：分期 1：单期
	private String borrowUserAccount;// 借款人电子账号

	// ========================查询用=============================
	private String borrowNidSrch; // 借款编号
	private String borrowUserNameSrch; // 借款人用户名
	private String borrowNameSrch; // 借款标题
	private String statusSrch; // 状态
	private String recoverYestimeStartSrch;// 还款日
	private String recoverYestimeEndSrch;// 还款日
	private String recoverStatusSrch;// 还款日
	private String tenderUserNameSrch;// 出借人
	private String borrowPeriodSrch;// 当前期数
	private String repayTypeSrch;// 还款方式中文名

	/**
	 * borrowStyle
	 *
	 * @return the borrowStyle
	 */

	public String getBorrowStyle() {
		return borrowStyle;
	}

	/**
	 * @param borrowStyle
	 *            the borrowStyle to set
	 */

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
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

	public String getRepayLastTimeStartSrch() {
		return repayLastTimeStartSrch;
	}

	public void setRepayLastTimeStartSrch(String repayLastTimeStartSrch) {
		this.repayLastTimeStartSrch = repayLastTimeStartSrch;
	}

	public String getRepayLastTimeEndSrch() {
		return repayLastTimeEndSrch;
	}

	public void setRepayLastTimeEndSrch(String repayLastTimeEndSrch) {
		this.repayLastTimeEndSrch = repayLastTimeEndSrch;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBorrowUserName() {
		return borrowUserName;
	}

	public void setBorrowUserName(String borrowUserName) {
		this.borrowUserName = borrowUserName;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectTypeName() {
		return projectTypeName;
	}

	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public String getBorrowApr() {
		return borrowApr;
	}

	public void setBorrowApr(String borrowApr) {
		this.borrowApr = borrowApr;
	}

	public String getBorrowAccount() {
		return borrowAccount;
	}

	public void setBorrowAccount(String borrowAccount) {
		this.borrowAccount = borrowAccount;
	}

	public String getBorrowAccountYes() {
		return borrowAccountYes;
	}

	public void setBorrowAccountYes(String borrowAccountYes) {
		this.borrowAccountYes = borrowAccountYes;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	public String getRepayAccountCapital() {
		return repayAccountCapital;
	}

	public void setRepayAccountCapital(String repayAccountCapital) {
		this.repayAccountCapital = repayAccountCapital;
	}

	public String getRepayAccountInterest() {
		return repayAccountInterest;
	}

	public void setRepayAccountInterest(String repayAccountInterest) {
		this.repayAccountInterest = repayAccountInterest;
	}

	public String getRepayAccountAll() {
		return repayAccountAll;
	}

	public void setRepayAccountAll(String repayAccountAll) {
		this.repayAccountAll = repayAccountAll;
	}

	public String getBorrowManager() {
		return borrowManager;
	}

	public void setBorrowManager(String borrowManager) {
		this.borrowManager = borrowManager;
	}

	public String getRepayAccountCapitalYes() {
		return repayAccountCapitalYes;
	}

	public void setRepayAccountCapitalYes(String repayAccountCapitalYes) {
		this.repayAccountCapitalYes = repayAccountCapitalYes;
	}

	public String getRepayAccountInterestYes() {
		return repayAccountInterestYes;
	}

	public void setRepayAccountInterestYes(String repayAccountInterestYes) {
		this.repayAccountInterestYes = repayAccountInterestYes;
	}

	public String getRepayAccountYes() {
		return repayAccountYes;
	}

	public void setRepayAccountYes(String repayAccountYes) {
		this.repayAccountYes = repayAccountYes;
	}

	public String getRepayAccountCapitalWait() {
		return repayAccountCapitalWait;
	}

	public void setRepayAccountCapitalWait(String repayAccountCapitalWait) {
		this.repayAccountCapitalWait = repayAccountCapitalWait;
	}

	public String getRepayAccountInterestWait() {
		return repayAccountInterestWait;
	}

	public void setRepayAccountInterestWait(String repayAccountInterestWait) {
		this.repayAccountInterestWait = repayAccountInterestWait;
	}

	public String getRepayAccountWait() {
		return repayAccountWait;
	}

	public void setRepayAccountWait(String repayAccountWait) {
		this.repayAccountWait = repayAccountWait;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRepayLastTime() {
		return repayLastTime;
	}

	public void setRepayLastTime(String repayLastTime) {
		this.repayLastTime = repayLastTime;
	}

	public String getRepayStatus() {
		return repayStatus;
	}

	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}

	public String getStatusSrch() {
		return statusSrch;
	}

	public void setStatusSrch(String statusSrch) {
		this.statusSrch = statusSrch;
	}

	public String getRepayNextTime() {
		return repayNextTime;
	}

	public void setRepayNextTime(String repayNextTime) {
		this.repayNextTime = repayNextTime;
	}

	public String getRepayFee() {
		return repayFee;
	}

	public void setRepayFee(String repayFee) {
		this.repayFee = repayFee;
	}

	public String getPeriodNow() {
		return periodNow;
	}

	public void setPeriodNow(String periodNow) {
		this.periodNow = periodNow;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getBorrowNidSrch() {
		return borrowNidSrch;
	}

	public void setBorrowNidSrch(String borrowNidSrch) {
		this.borrowNidSrch = borrowNidSrch;
	}

	public String getBorrowUserNameSrch() {
		return borrowUserNameSrch;
	}

	public void setBorrowUserNameSrch(String borrowUserNameSrch) {
		this.borrowUserNameSrch = borrowUserNameSrch;
	}

	public String getBorrowNameSrch() {
		return borrowNameSrch;
	}

	public void setBorrowNameSrch(String borrowNameSrch) {
		this.borrowNameSrch = borrowNameSrch;
	}

	public String getTenderUserId() {
		return tenderUserId;
	}

	public void setTenderUserId(String tenderUserId) {
		this.tenderUserId = tenderUserId;
	}

	public String getTenderUserName() {
		return tenderUserName;
	}

	public void setTenderUserName(String tenderUserName) {
		this.tenderUserName = tenderUserName;
	}

	public BigDecimal getRecoverAccount() {
		return recoverAccount;
	}

	public void setRecoverAccount(BigDecimal recoverAccount) {
		this.recoverAccount = recoverAccount;
	}

	public BigDecimal getRecoverAccountYes() {
		return recoverAccountYes;
	}

	public void setRecoverAccountYes(BigDecimal recoverAccountYes) {
		this.recoverAccountYes = recoverAccountYes;
	}

	public BigDecimal getRecoverFee() {
		return recoverFee;
	}

	public void setRecoverFee(BigDecimal recoverFee) {
		this.recoverFee = recoverFee;
	}

	public Integer getRecoverStatus() {
		return recoverStatus;
	}

	public void setRecoverStatus(Integer recoverStatus) {
		this.recoverStatus = recoverStatus;
	}

	public String getRecoverYestime() {
		return recoverYestime;
	}

	public void setRecoverYestime(String recoverYestime) {
		this.recoverYestime = recoverYestime;
	}

	public String getRecoverYestimeStartSrch() {
		return recoverYestimeStartSrch;
	}

	public void setRecoverYestimeStartSrch(String recoverYestimeStartSrch) {
		this.recoverYestimeStartSrch = recoverYestimeStartSrch;
	}

	public String getRecoverYestimeEndSrch() {
		return recoverYestimeEndSrch;
	}

	public void setRecoverYestimeEndSrch(String recoverYestimeEndSrch) {
		this.recoverYestimeEndSrch = recoverYestimeEndSrch;
	}

	public String getRecoverStatusSrch() {
		return recoverStatusSrch;
	}

	public void setRecoverStatusSrch(String recoverStatusSrch) {
		this.recoverStatusSrch = recoverStatusSrch;
	}

	public String getTenderUserNameSrch() {
		return tenderUserNameSrch;
	}

	public void setTenderUserNameSrch(String tenderUserNameSrch) {
		this.tenderUserNameSrch = tenderUserNameSrch;
	}

	public String getBorrowPeriodSrch() {
		return borrowPeriodSrch;
	}

	public void setBorrowPeriodSrch(String borrowPeriodSrch) {
		this.borrowPeriodSrch = borrowPeriodSrch;
	}

	public Integer getMonthType() {
		return monthType;
	}

	public void setMonthType(Integer monthType) {
		this.monthType = monthType;
	}

	public String getRecoverPeriod() {
		return recoverPeriod;
	}

	public void setRecoverPeriod(String recoverPeriod) {
		this.recoverPeriod = recoverPeriod;
	}

	public String getRepayTypeSrch() {
		return repayTypeSrch;
	}

	public void setRepayTypeSrch(String repayTypeSrch) {
		this.repayTypeSrch = repayTypeSrch;
	}

	public String getBorrowUserAccount() {
		return borrowUserAccount;
	}

	public void setBorrowUserAccount(String borrowUserAccount) {
		this.borrowUserAccount = borrowUserAccount;
	}

}
