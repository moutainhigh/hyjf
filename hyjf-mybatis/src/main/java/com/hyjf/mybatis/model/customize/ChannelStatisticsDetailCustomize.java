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
 * @author Administrator
 */

public class ChannelStatisticsDetailCustomize implements Serializable {

	/**
	 * serialVersionUID:
	 */

	private static final long serialVersionUID = 1L;
	/** 主键id */
	private Integer id;
	/** 推广id */
	private Integer utmId;
	/** 关键词 */
	private String keyName;
	/** 渠道id */
	private Integer sourceId;
	/** 渠道名称 */
	private String sourceName;
	/** 用户id */
	private Integer userId;
	/** 用户名 */
	private String userName;
	/** 真实姓名 */
	private String trueName;
	/** 手机号 */
	private String mobile;
	/** 注册时间 */
	private String registerTime;
	/** 开户时间 */
	private String openAccountTime;
	/** 首次出借时间 */
	private String firstInvestTime;
	/** 累计出借金额 */
	private BigDecimal cumulativeInvest;
	/** 性别 导出用 **/
	private String sex;

	/**
	 * 渠道id
	 */
	private String utmIds;

	/**
	 * 渠道id
	 */
	private String[] utmIdsSrch;
	/**
	 * 汇添金出借金额
	 */
	private BigDecimal htjInvest;
	/**
	 * 汇转让出借金额
	 */
	private BigDecimal hzrInvest;
	/**
	 * 首次投标的出借金额
	 */
	private BigDecimal investAmount;
	/**
	 * 首次出借标的的项目类型
	 */
	private String investProjectType;
	/**
	 * 首次出借标的的项目期限
	 */
	private String investProjectPeriod;

	/**
	 * 检索条件 limitStart
	 */
	private int limitStart = -1;

	/**
	 * 检索条件 limitEnd
	 */
	private int limitEnd = -1;

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getOpenAccountTime() {
		return openAccountTime;
	}

	public void setOpenAccountTime(String openAccountTime) {
		this.openAccountTime = openAccountTime;
	}

	public String getFirstInvestTime() {
		return firstInvestTime;
	}

	public void setFirstInvestTime(String firstInvestTime) {
		this.firstInvestTime = firstInvestTime;
	}

	public BigDecimal getCumulativeInvest() {
		return cumulativeInvest;
	}

	public void setCumulativeInvest(BigDecimal cumulativeInvest) {
		this.cumulativeInvest = cumulativeInvest;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUtmId() {
		return utmId;
	}

	public void setUtmId(Integer utmId) {
		this.utmId = utmId;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getHtjInvest() {
		return htjInvest;
	}

	public void setHtjInvest(BigDecimal htjInvest) {
		this.htjInvest = htjInvest;
	}

	public BigDecimal getHzrInvest() {
		return hzrInvest;
	}

	public void setHzrInvest(BigDecimal hzrInvest) {
		this.hzrInvest = hzrInvest;
	}

	public BigDecimal getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}

	public String getInvestProjectType() {
		return investProjectType;
	}

	public void setInvestProjectType(String investProjectType) {
		this.investProjectType = investProjectType;
	}

	public String getInvestProjectPeriod() {
		return investProjectPeriod;
	}

	public void setInvestProjectPeriod(String investProjectPeriod) {
		this.investProjectPeriod = investProjectPeriod;
	}

	public String getUtmIds() {
		return utmIds;
	}

	public void setUtmIds(String utmIds) {
		this.utmIds = utmIds;
	}

	public String[] getUtmIdsSrch() {
		return utmIdsSrch;
	}

	public void setUtmIdsSrch(String[] utmIdsSrch) {
		this.utmIdsSrch = utmIdsSrch;
	}

}
