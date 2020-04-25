package com.hyjf.admin.manager.borrow.borrowinvest;

import java.io.Serializable;

import com.hyjf.common.paginator.Paginator;

/**
 * @package com.hyjf.admin.maintenance.AlllBorrowCustomize;
 * @author GOGTZ-Z
 * @date 2015/07/09 17:00
 * @version V1.0  
 */
public class BorrowInvestBean implements Serializable {

	/**
	 * serialVersionUID:
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * 项目编号 检索条件
	 */
	private String borrowNidSrch;
	/**
	 * 项目名称 检索条件
	 */
	private String borrowNameSrch;
	/**
	 * 用户名 检索条件
	 */
	private String usernameSrch;
	/**
	 * 推荐人 检索条件
	 */
	private String referrerNameSrch;
	/**
	 * 检索条件 计划编号
	 */
	private String planNidSrch;
	/**
	 * 还款方式 检索条件
	 */
	private String borrowStyleSrch;
	/**
	 * 操作平台 检索条件
	 */
	private String clientSrch;
	/**
	 * 渠道 检索条件
	 */
	private String utmIdSrch;
	/**
	 * 出借时间 检索条件
	 */
	private String timeStartSrch;
	/**
	 * 出借时间 检索条件
	 */
	private String timeEndSrch;
	/**
	 * 项目期限
	 */
	private String borrowPeriod;
	/**
	 * 出借类型
	 */
	private String investType;
	
	/**
	 * 检索条件 limitStart
	 */
	private int limitStart = -1;
	/**
	 * 检索条件 limitEnd
	 */
	private int limitEnd = -1;

	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;

	/**
	 * 列表画面自定义标签上的用翻页对象：paginator
	 */
	private Paginator paginator;

	// 是从原始标的点击链接跳转标志（1：是）
	private String isOptFlag;

	/**
	 * 计划编号，传参用
	 */
	private  String planNidTemp;

	private String isSearch;

	/**
	 * 是否复投 1：是  0： 否
	 */
	private String tenderType;

	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	/**
	 * borrowNidSrch
	 * 
	 * @return the borrowNidSrch
	 */

	public String getBorrowNidSrch() {
		return borrowNidSrch;
	}

	/**
	 * @param borrowNidSrch
	 *            the borrowNidSrch to set
	 */

	public void setBorrowNidSrch(String borrowNidSrch) {
		this.borrowNidSrch = borrowNidSrch;
	}

	/**
	 * borrowNameSrch
	 * 
	 * @return the borrowNameSrch
	 */

	public String getBorrowNameSrch() {
		return borrowNameSrch;
	}

	/**
	 * @param borrowNameSrch
	 *            the borrowNameSrch to set
	 */

	public void setBorrowNameSrch(String borrowNameSrch) {
		this.borrowNameSrch = borrowNameSrch;
	}

	/**
	 * usernameSrch
	 * 
	 * @return the usernameSrch
	 */

	public String getUsernameSrch() {
		return usernameSrch;
	}

	/**
	 * @param usernameSrch
	 *            the usernameSrch to set
	 */

	public void setUsernameSrch(String usernameSrch) {
		this.usernameSrch = usernameSrch;
	}

	/**
	 * referrerNameSrch
	 * 
	 * @return the referrerNameSrch
	 */

	public String getReferrerNameSrch() {
		return referrerNameSrch;
	}

	/**
	 * @param referrerNameSrch
	 *            the referrerNameSrch to set
	 */

	public void setReferrerNameSrch(String referrerNameSrch) {
		this.referrerNameSrch = referrerNameSrch;
	}

	/**
	 * borrowStyleSrch
	 * 
	 * @return the borrowStyleSrch
	 */

	public String getBorrowStyleSrch() {
		return borrowStyleSrch;
	}

	/**
	 * @param borrowStyleSrch
	 *            the borrowStyleSrch to set
	 */

	public void setBorrowStyleSrch(String borrowStyleSrch) {
		this.borrowStyleSrch = borrowStyleSrch;
	}

	/**
	 * clientSrch
	 * 
	 * @return the clientSrch
	 */

	public String getClientSrch() {
		return clientSrch;
	}

	/**
	 * @param clientSrch
	 *            the clientSrch to set
	 */

	public void setClientSrch(String clientSrch) {
		this.clientSrch = clientSrch;
	}

	/**
	 * utmIdSrch
	 * 
	 * @return the utmIdSrch
	 */

	public String getUtmIdSrch() {
		return utmIdSrch;
	}

	/**
	 * @param utmIdSrch
	 *            the utmIdSrch to set
	 */

	public void setUtmIdSrch(String utmIdSrch) {
		this.utmIdSrch = utmIdSrch;
	}

	/**
	 * timeStartSrch
	 * 
	 * @return the timeStartSrch
	 */

	public String getTimeStartSrch() {
		return timeStartSrch;
	}

	/**
	 * @param timeStartSrch
	 *            the timeStartSrch to set
	 */

	public void setTimeStartSrch(String timeStartSrch) {
		this.timeStartSrch = timeStartSrch;
	}

	/**
	 * timeEndSrch
	 * 
	 * @return the timeEndSrch
	 */

	public String getTimeEndSrch() {
		return timeEndSrch;
	}

	/**
	 * @param timeEndSrch
	 *            the timeEndSrch to set
	 */

	public void setTimeEndSrch(String timeEndSrch) {
		this.timeEndSrch = timeEndSrch;
	}

	/**
	 * limitStart
	 * 
	 * @return the limitStart
	 */

	public int getLimitStart() {
		return limitStart;
	}

	/**
	 * @param limitStart
	 *            the limitStart to set
	 */

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	/**
	 * limitEnd
	 * 
	 * @return the limitEnd
	 */

	public int getLimitEnd() {
		return limitEnd;
	}

	/**
	 * @param limitEnd
	 *            the limitEnd to set
	 */

	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public String getInvestType() {
		return investType;
	}

	public void setInvestType(String investType) {
		this.investType = investType;
	}

	/**
	 * planNidSrch
	 * @return the planNidSrch
	 */
	
	public String getPlanNidSrch() {
		return planNidSrch;
	}

	/**
	 * @param planNidSrch the planNidSrch to set
	 */
	
	public void setPlanNidSrch(String planNidSrch) {
		this.planNidSrch = planNidSrch;
	}

	public String getTenderType() {
		return tenderType;
	}

	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}

	public String getIsOptFlag() {
		return isOptFlag;
	}

	public void setIsOptFlag(String isOptFlag) {
		this.isOptFlag = isOptFlag;
	}

	public String getPlanNidTemp() {
		return planNidTemp;
	}

	public void setPlanNidTemp(String planNidTemp) {
		this.planNidTemp = planNidTemp;
	}

	public String getIsSearch() {
		return isSearch;
	}

	public void setIsSearch(String isSearch) {
		this.isSearch = isSearch;
	}
}
