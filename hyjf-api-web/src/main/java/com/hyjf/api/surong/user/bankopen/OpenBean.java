package com.hyjf.api.surong.user.bankopen;

public class OpenBean {
	
	/**用户userId*/
	private String userId;
	/** 用户真实姓名 */
	private String trueName;
	/** 用户身份证号码 */
	private String idNo;
	/** 用户银行卡号 */
	private String cardNo;
	/** 用户手机号 */
	private String mobile;
	/**用户的验证码*/
	private String smsCode;

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
