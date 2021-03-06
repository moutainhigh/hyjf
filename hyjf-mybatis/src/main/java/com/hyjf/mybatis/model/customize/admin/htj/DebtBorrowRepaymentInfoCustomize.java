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

package com.hyjf.mybatis.model.customize.admin.htj;

import java.io.Serializable;

/**
 * 还款明细
 * 
 * @author 孙亮
 * @since 2015年12月19日 上午9:29:09
 */
public class DebtBorrowRepaymentInfoCustomize implements Serializable {
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
	private String recoverTimeStartSrch;
	/**
	 * 应还日期 检索条件
	 */
	private String recoverTimeEndSrch;

	// ========================参数=============================
	private String nid;// 出借nid
	private String borrowNid;// 借款编号
	private String userId;// 借款人ID
	private String borrowUserName;// 借款人用户名
	private String borrowStyle;// 类型
	private String borrowName;// 借款标题
	private String projectType;// 项目类型id
	private String projectTypeName;// 项目类型名称
	private String borrowPeriod;// 借款期限
	private String borrowApr;// 年化收益
	private String borrowAccount;// 借款金额
	private String borrowAccountYes;// 借到金额
	private String repayType;// 还款方式
	private String recoverUserId;// 出借人ID
	private String recoverUserName;// 出借人用户名
	private String recoverUserAttribute;// 出借人用户属性（当前）
	private String recoverRegionName;// 出借人所属一级分部（当前）
	private String recoverBranchName;// 出借人所属二级分部（当前）
	private String recoverDepartmentName;// 出借人所属团队（当前）
	private String referrerName;// 推荐人（当前）
	private String referrerUserId;// 推荐人ID（当前）
	private String referrerTrueName;// 推荐人姓名（当前）
	private String referrerRegionName;// 推荐人所属一级分部（当前）
	private String referrerBranchName;//推荐人所属二级分部（当前）
	private String referrerDepartmentName; //推荐人所属团队（当前）
	private String recoverPeriod;// 出借期限
	private String recoverTotal;// 出借金额
	private String recoverCapital;// 应还本金
	private String recoverInterest;// 应还利息
	private String recoverAccount;// 应还本息
	private String recoverFee;// 管理费
	private String recoverCapitalYes;// 已还本金
	private String recoverInterestYes;// 已还利息
	private String recoverAccountYes;// 已换本息
	private String recoverCapitalWait;// 未还本金
	private String recoverInterestWait;// 未还利息
	private String recoverAccountWait;// 未还本息
	private String status;// 还款状态
	private String recoverLastTime;// 最后还款日

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

	public String getRecoverTimeStartSrch() {
		return recoverTimeStartSrch;
	}

	public void setRecoverTimeStartSrch(String recoverTimeStartSrch) {
		this.recoverTimeStartSrch = recoverTimeStartSrch;
	}

	public String getRecoverTimeEndSrch() {
		return recoverTimeEndSrch;
	}

	public void setRecoverTimeEndSrch(String recoverTimeEndSrch) {
		this.recoverTimeEndSrch = recoverTimeEndSrch;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
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

	public String getRecoverUserId() {
		return recoverUserId;
	}

	public void setRecoverUserId(String recoverUserId) {
		this.recoverUserId = recoverUserId;
	}

	public String getRecoverUserName() {
		return recoverUserName;
	}

	public void setRecoverUserName(String recoverUserName) {
		this.recoverUserName = recoverUserName;
	}

	public String getRecoverPeriod() {
		return recoverPeriod;
	}

	public void setRecoverPeriod(String recoverPeriod) {
		this.recoverPeriod = recoverPeriod;
	}

	public String getRecoverTotal() {
		return recoverTotal;
	}

	public void setRecoverTotal(String recoverTotal) {
		this.recoverTotal = recoverTotal;
	}

	public String getRecoverCapital() {
		return recoverCapital;
	}

	public void setRecoverCapital(String recoverCapital) {
		this.recoverCapital = recoverCapital;
	}

	public String getRecoverInterest() {
		return recoverInterest;
	}

	public void setRecoverInterest(String recoverInterest) {
		this.recoverInterest = recoverInterest;
	}

	public String getRecoverAccount() {
		return recoverAccount;
	}

	public void setRecoverAccount(String recoverAccount) {
		this.recoverAccount = recoverAccount;
	}

	public String getRecoverFee() {
		return recoverFee;
	}

	public void setRecoverFee(String recoverFee) {
		this.recoverFee = recoverFee;
	}

	public String getRecoverCapitalYes() {
		return recoverCapitalYes;
	}

	public void setRecoverCapitalYes(String recoverCapitalYes) {
		this.recoverCapitalYes = recoverCapitalYes;
	}

	public String getRecoverInterestYes() {
		return recoverInterestYes;
	}

	public void setRecoverInterestYes(String recoverInterestYes) {
		this.recoverInterestYes = recoverInterestYes;
	}

	public String getRecoverAccountYes() {
		return recoverAccountYes;
	}

	public void setRecoverAccountYes(String recoverAccountYes) {
		this.recoverAccountYes = recoverAccountYes;
	}

	public String getRecoverCapitalWait() {
		return recoverCapitalWait;
	}

	public void setRecoverCapitalWait(String recoverCapitalWait) {
		this.recoverCapitalWait = recoverCapitalWait;
	}

	public String getRecoverInterestWait() {
		return recoverInterestWait;
	}

	public void setRecoverInterestWait(String recoverInterestWait) {
		this.recoverInterestWait = recoverInterestWait;
	}

	public String getRecoverAccountWait() {
		return recoverAccountWait;
	}

	public void setRecoverAccountWait(String recoverAccountWait) {
		this.recoverAccountWait = recoverAccountWait;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRecoverLastTime() {
		return recoverLastTime;
	}

	public void setRecoverLastTime(String recoverLastTime) {
		this.recoverLastTime = recoverLastTime;
	}

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public String getRecoverUserAttribute() {
		return recoverUserAttribute;
	}

	public void setRecoverUserAttribute(String recoverUserAttribute) {
		this.recoverUserAttribute = recoverUserAttribute;
	}

	public String getRecoverRegionName() {
		return recoverRegionName;
	}

	public void setRecoverRegionName(String recoverRegionName) {
		this.recoverRegionName = recoverRegionName;
	}

	public String getRecoverBranchName() {
		return recoverBranchName;
	}

	public void setRecoverBranchName(String recoverBranchName) {
		this.recoverBranchName = recoverBranchName;
	}

	public String getRecoverDepartmentName() {
		return recoverDepartmentName;
	}

	public void setRecoverDepartmentName(String recoverDepartmentName) {
		this.recoverDepartmentName = recoverDepartmentName;
	}

	public String getReferrerName() {
		return referrerName;
	}

	public void setReferrerName(String referrerName) {
		this.referrerName = referrerName;
	}

	public String getReferrerUserId() {
		return referrerUserId;
	}

	public void setReferrerUserId(String referrerUserId) {
		this.referrerUserId = referrerUserId;
	}

	public String getReferrerTrueName() {
		return referrerTrueName;
	}

	public void setReferrerTrueName(String referrerTrueName) {
		this.referrerTrueName = referrerTrueName;
	}

	public String getReferrerRegionName() {
		return referrerRegionName;
	}

	public void setReferrerRegionName(String referrerRegionName) {
		this.referrerRegionName = referrerRegionName;
	}

	public String getReferrerBranchName() {
		return referrerBranchName;
	}

	public void setReferrerBranchName(String referrerBranchName) {
		this.referrerBranchName = referrerBranchName;
	}

	public String getReferrerDepartmentName() {
		return referrerDepartmentName;
	}

	public void setReferrerDepartmentName(String referrerDepartmentName) {
		this.referrerDepartmentName = referrerDepartmentName;
	}

}
