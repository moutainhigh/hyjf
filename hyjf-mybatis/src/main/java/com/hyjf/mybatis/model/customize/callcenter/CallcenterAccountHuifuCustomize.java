/**
 * Description:按照用户名/手机号查询汇付绑卡关系Customize类
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: 刘彬
 * @version: 1.0
 *           Created at: 2017年7月15日 下午1:50:02
 *           Modification History:
 *           Modified by :
 */

package com.hyjf.mybatis.model.customize.callcenter;

/**
 * 同步另外实体类AdminBankcardListCustomize
 * @author 刘彬
 */

public class CallcenterAccountHuifuCustomize {

	// 用戶id
	public String userId;
	// 用戶名
	public String userName;

    //用户手机号
    private String mobile;
    /** 用户真实姓名 */
    private String realName;
    /** 身份证号 */
    private String idcard;
    
    
	// 银行账户
	public String account;
	/**专门添加为了调用汇付接口,从而核对银行卡信息*/
	//汇付客户号
	public String customerAccount;
	// 银行
	public String bank;
	// 银行卡是否是默认提现卡
	public String cardType;
	// 银行卡是否是快捷支付卡
	public String cardProperty;
	// 添加时间
	public String addTime;

	/**
	 * 构造方法不带参数
	 */

	public CallcenterAccountHuifuCustomize() {
		super();
	}

	/**
	 * userId
	 * 
	 * @return the userId
	 */

	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * userName
	 * 
	 * @return the userName
	 */

	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * account
	 * 
	 * @return the account
	 */

	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * bank
	 * 
	 * @return the bank
	 */

	public String getBank() {
		return bank;
	}

	/**
	 * @param bank
	 *            the bank to set
	 */

	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * addTime
	 * 
	 * @return the addTime
	 */

	public String getAddTime() {
		return addTime;
	}

	/**
	 * @param addTime
	 *            the addTime to set
	 */

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	/**
	 * cardProperty
	 * @return the cardProperty
	 */
	
	public String getCardProperty() {
		return cardProperty;
	}

	/**
	 * @param cardProperty the cardProperty to set
	 */
	
	public void setCardProperty(String cardProperty) {
		this.cardProperty = cardProperty;
	}

	/**
	 * customerAccount
	 * @return the customerAccount
	 */
	
	public String getCustomerAccount() {
		return customerAccount;
	}

	/**
	 * @param customerAccount the customerAccount to set
	 */
	
	public void setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

}
