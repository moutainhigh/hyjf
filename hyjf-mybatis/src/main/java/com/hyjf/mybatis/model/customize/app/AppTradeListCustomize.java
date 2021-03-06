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

package com.hyjf.mybatis.model.customize.app;

import java.io.Serializable;

/**
 * @author 王坤
 */

public class AppTradeListCustomize implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1457644265103686234L;
	// 类型：充值，提现，提现，出借成功，出借冻结等
	private String tradeType;
	// 时间
	private String tradeTime;
	// 出借时间年
    private String tradeYear;
    // 出借时间月
    private String tradeMonth;
	// 金额
	private String account;
	// 是否是银行交易明细(0:汇付,1:江西银行)
	private String isBank;
	//是否是月份 0（月份）1（交易明细数据）
	private String isMonth="1";
	//月份标题
	private String month;
	//可用余额
	private String bankBalance;
	/**
	 * 构造方法
	 */

	public AppTradeListCustomize() {
		super();
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getIsBank() {
		return isBank;
	}

	public void setIsBank(String isBank) {
		this.isBank = isBank;
	}

    public String getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(String isMonth) {
        this.isMonth = isMonth;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTradeYear() {
        return tradeYear;
    }

    public void setTradeYear(String tradeYear) {
        this.tradeYear = tradeYear;
    }

    public String getTradeMonth() {
        return tradeMonth;
    }

    public void setTradeMonth(String tradeMonth) {
        this.tradeMonth = tradeMonth;
    }

    public String getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(String bankBalance) {
        this.bankBalance = bankBalance;
    }

}
