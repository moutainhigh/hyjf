/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */
package com.hyjf.app.bank.user.recharge;

import com.hyjf.app.BaseResultBean;

/**
 * 此处为类说明
 * 
 * @author renxingchen
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年2月19日
 * @see 下午2:47:59
 */
public class AppRechargeInfoResultVo extends BaseResultBean {

	public AppRechargeInfoResultVo(String request) {
		super(request);
	}

	public AppRechargeInfoResultVo(String request, String rechargeRuleUrl, String otherUrl) {
		super(request);
		this.rechargeRuleUrl = rechargeRuleUrl;
		this.otherUrl = otherUrl;
	}

	/** 充值手续费 */
	private String fee = "";
	/** 实际到账 */
	private String balance = "";
	/** 快捷卡发卡行logo的url */
	private String logo = "";
	/** 快捷卡发卡行的名称 */
	private String bank = "";
	/** 卡号 */
	private String cardNo = "";
	/** 银行编号 */
	private String code = "";
	/** 充值信息 */
	private String moneyInfo = "";
	/** 充值规则地址 */
	private String rechargeRuleUrl = "";
	/** 其他充值方式url(是否成功，都要给出url) */
	private String otherUrl = "";
    
    private String smsSeq;
    
    private String rechargeUrl;
    
    private String isDefault = "0";// 0普通提现卡1默认提现卡2快捷支付卡
	/** 卡号绑定手机号码 */
	private String mobile = "";

	//add by xiashuqing 20171204 begin app改版2.1新增查询账户余额
	/** 可用金额 */
	private String availableAmount = "";

	/** 收款方户名 */
	private String rcvAccountName = "";
	/** 收款方账号 */
	private String rcvAccount = "";
	/** 收款方开户行 */
	private String rcvOpenBankName = "";
	/** 友情提示内容 */
	private String kindlyReminder = "";

	private String buttonWord = "";

	//add by xiashuqing 20171204 begin app改版2.1新增查询账户余额
	
    // 合规改造 需要银行卡统一脱敏显示
	private String cardNo_info = "";
	// 重要提示
	private String hints = "";
	//add by cwyang 2018-06-29 APP3.0.9新增参数转账指南
	/**app转账指南*/
	private String transferGuideURL = "";
	/**
	 * 此处为属性说明
	 */
	private static final long serialVersionUID = -3095160356825014456L;

	public String getTransferGuideURL() {
		return transferGuideURL;
	}

	public void setTransferGuideURL(String transferGuideURL) {
		this.transferGuideURL = transferGuideURL;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMoneyInfo() {
		return moneyInfo;
	}

	public void setMoneyInfo(String moneyInfo) {
		this.moneyInfo = moneyInfo;
	}

	public String getRechargeRuleUrl() {
		return rechargeRuleUrl;
	}

	public void setRechargeRuleUrl(String rechargeRuleUrl) {
		this.rechargeRuleUrl = rechargeRuleUrl;
	}

	public String getOtherUrl() {
		return otherUrl;
	}

	public void setOtherUrl(String otherUrl) {
		this.otherUrl = otherUrl;
	}

	public String getSmsSeq() {
		return smsSeq;
	}

	public void setSmsSeq(String smsSeq) {
		this.smsSeq = smsSeq;
	}

	public String getRechargeUrl() {
		return rechargeUrl;
	}

	public void setRechargeUrl(String rechargeUrl) {
		this.rechargeUrl = rechargeUrl;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(String availableAmount) {
		this.availableAmount = availableAmount;
	}

	public String getRcvAccountName() {
		return rcvAccountName;
	}

	public void setRcvAccountName(String rcvAccountName) {
		this.rcvAccountName = rcvAccountName;
	}

	public String getRcvAccount() {
		return rcvAccount;
	}

	public void setRcvAccount(String rcvAccount) {
		this.rcvAccount = rcvAccount;
	}

	public String getRcvOpenBankName() {
		return rcvOpenBankName;
	}

	public void setRcvOpenBankName(String rcvOpenBankName) {
		this.rcvOpenBankName = rcvOpenBankName;
	}

	public String getKindlyReminder() {
		return kindlyReminder;
	}

	public void setKindlyReminder(String kindlyReminder) {
		this.kindlyReminder = kindlyReminder;
	}

	public String getButtonWord() {
		return buttonWord;
	}

	public void setButtonWord(String buttonWord) {
		this.buttonWord = buttonWord;
	}

    public String getCardNo_info() {
        return cardNo_info;
    }

    public void setCardNo_info(String cardNo_info) {
        this.cardNo_info = cardNo_info;
    }

	public String getHints() {
		return hints;
	}

	public void setHints(String hints) {
		this.hints = hints;
	}
}
