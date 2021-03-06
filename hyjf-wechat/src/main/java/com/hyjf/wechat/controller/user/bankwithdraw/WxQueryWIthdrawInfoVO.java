package com.hyjf.wechat.controller.user.bankwithdraw;

import java.util.List;

import com.google.common.collect.Lists;
import com.hyjf.bank.service.user.bankwithdraw.BankCardBean;

/**
 * 微信客户端提现信息返回数据结构封装类
 * Created by cuigq on 2018/2/13.
 */
public class WxQueryWIthdrawInfoVO {

    // 是否为企业用户
    private Integer userType;
    // 绑定银行卡信息
    private List<BankCardBean> lstBankCard;
    // 是否设置交易密码
    private Integer isSetPassWord;
    // 手续费
    private String feeWithdraw;
    //可用余额
    private String bankBalance;
    //可用余额原始数据
    private String bankBalanceOriginal;
    //用户手机号
    private String mobile;
    //用户可提现金额
    private String balance;

    // 服务费授权状态
    private Integer paymentAuthStatus;

    // 服务费授权开关
    private Integer paymentAuthOn;

    // 银行名称
    private  String bankName;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public List<BankCardBean> getLstBankCard() {
        if (lstBankCard == null) {
            lstBankCard = Lists.newArrayList();
        }
        return lstBankCard;
    }

    public void setLstBankCard(List<BankCardBean> lstBankCard) {
        this.lstBankCard = lstBankCard;
    }

    public Integer getIsSetPassWord() {
        return isSetPassWord;
    }

    public void setIsSetPassWord(Integer isSetPassWord) {
        this.isSetPassWord = isSetPassWord;
    }

    public String getFeeWithdraw() {
        return feeWithdraw;
    }

    public void setFeeWithdraw(String feeWithdraw) {
        this.feeWithdraw = feeWithdraw;
    }

    public String getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(String bankBalance) {
        this.bankBalance = bankBalance;
    }

    public String getBankBalanceOriginal() {
        return bankBalanceOriginal;
    }

    public void setBankBalanceOriginal(String bankBalanceOriginal) {
        this.bankBalanceOriginal = bankBalanceOriginal;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getPaymentAuthStatus() {
        return paymentAuthStatus;
    }

    public void setPaymentAuthStatus(Integer paymentAuthStatus) {
        this.paymentAuthStatus = paymentAuthStatus;
    }

    public Integer getPaymentAuthOn() {
        return paymentAuthOn;
    }

    public void setPaymentAuthOn(Integer paymentAuthOn) {
        this.paymentAuthOn = paymentAuthOn;
    }


    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
