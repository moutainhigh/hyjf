package com.hyjf.mybatis.model.customize.admin;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author cwyang
 *add by 2017/4/7
 *银行对账DB返回结果类
 */
public class AdminBankAccountCheckCustomize  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5164360105505163224L;
	/**
	 * 操作员
	 */
	private String operator;
	private String startDate; // 创建时间 起始
	private String endDate; // 创建时间 结束
	private String tradeTypeSearch; // 交易类型
	private String typeSearch;
	/**
	 * 是否变更
	 */
	private int isUpdate;
	/**
	 * 备注
	 */
	private String remark;
	private int limitStart = -1;

	private int limitEnd = -1;
	/**
	 * account_list 主键
	 */
	private int id;
	/**
	 * 用户id
	 */
	private int userId;
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 订单号
	 */
	private String nId;
	/**
	 * 操作类型
	 */
	private String type;
	/**
	 * 交易类型
	 */
	private String tradeType;
	/**
	 * 操作金额
	 */
	private BigDecimal amount;
	/**
	 * 可用余额
	 */
	private BigDecimal balance;
	/**
	 * 交易时间
	 */
	private String createTime;
	/**
	 * 电子账号
	 */
	private String accountId;
	
	/**
	 * 交易流水号
	 */
	private int seqNo;
	/**
	 * 对账状态
	 */
	private String checkStatus;
	
	/**
	 * 交易状态
	 */
	private String tradeStatus;
	
	/**
	 * 日期
	 */
	private String createDate;
	/**
	 * 到帐金额
	 */
	private BigDecimal accountBalance;
	/**
	 * 到账时间
	 */
	private String acountTime;
	/**
	 * 对账时间
	 */
	private String checkDate;
	/**
	 * 银行流水号
	 */
	private String bankSeqNo;
	/**
	 * 对账状态字符
	 */
	private String accountCheckStr;
	/**
	 * 银行对账交易状态字符
	 */
	private String accountCheckTradeStr;
	/**
	 * 冲正资金流向
	 */
	private int typeId;
	
	private int baseUpdate;
	/**
	 * 交易日期
	 */
	private String txDate;
	/**
	 * 交易时间
	 */
	private String txTime;
	
	
	public String getTxDate() {
		return txDate;
	}
	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}
	public String getTxTime() {
		return txTime;
	}
	public void setTxTime(String txTime) {
		this.txTime = txTime;
	}
	public int getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(int isUpdate) {
		this.isUpdate = isUpdate;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public int getBaseUpdate() {
		return baseUpdate;
	}
	public void setBaseUpdate(int baseUpdate) {
		this.baseUpdate = baseUpdate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getnId() {
		return nId;
	}
	public void setnId(String nId) {
		this.nId = nId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getAcountTime() {
		return acountTime;
	}
	public void setAcountTime(String acountTime) {
		this.acountTime = acountTime;
	}
	public String getBankSeqNo() {
		return bankSeqNo;
	}
	public void setBankSeqNo(String bankSeqNo) {
		this.bankSeqNo = bankSeqNo;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTradeTypeSearch() {
		return tradeTypeSearch;
	}
	public void setTradeTypeSearch(String tradeTypeSearch) {
		this.tradeTypeSearch = tradeTypeSearch;
	}
	public String getTypeSearch() {
		return typeSearch;
	}
	public void setTypeSearch(String typeSearch) {
		this.typeSearch = typeSearch;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	
	
	
	public String getAccountCheckStr() {
		return accountCheckStr;
	}
	public void setAccountCheckStr(String accountCheckStr) {
		this.accountCheckStr = accountCheckStr;
	}
	public String getAccountCheckTradeStr() {
		return accountCheckTradeStr;
	}
	public void setAccountCheckTradeStr(String accountCheckTradeStr) {
		this.accountCheckTradeStr = accountCheckTradeStr;
	}
	
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}
	
}
