/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */
package com.hyjf.web.bank.web.user.recharge;

import com.hyjf.web.BaseVo;

/**
 * 充值表单提交数据对象
 * @author renxingchen
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年3月28日
 * @see 上午10:27:59
 */
public class RechargeVo extends BaseVo {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = -203594478055115887L;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType;
    }

    private String money;// 充值金额

    private String bankCode;// 银行编号

    private String rechargeType;// 充值类型

}
