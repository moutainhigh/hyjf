package com.hyjf.mybatis.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SpreadsLog implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer spreadsUserid;

    private String nid;

    private String type;

    private String spreadsType;

    private String accountType;

    private String scales;

    private String borrowNid;

    private Integer tenderId;

    private Integer repayId;

    private BigDecimal accountAll;

    private BigDecimal accountCapital;

    private BigDecimal accountInterest;

    private BigDecimal account;

    private String addtime;

    private String addip;

    private Integer payStatus;

    private Integer isValid;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSpreadsUserid() {
        return spreadsUserid;
    }

    public void setSpreadsUserid(Integer spreadsUserid) {
        this.spreadsUserid = spreadsUserid;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid == null ? null : nid.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSpreadsType() {
        return spreadsType;
    }

    public void setSpreadsType(String spreadsType) {
        this.spreadsType = spreadsType == null ? null : spreadsType.trim();
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }

    public String getScales() {
        return scales;
    }

    public void setScales(String scales) {
        this.scales = scales == null ? null : scales.trim();
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getTenderId() {
        return tenderId;
    }

    public void setTenderId(Integer tenderId) {
        this.tenderId = tenderId;
    }

    public Integer getRepayId() {
        return repayId;
    }

    public void setRepayId(Integer repayId) {
        this.repayId = repayId;
    }

    public BigDecimal getAccountAll() {
        return accountAll;
    }

    public void setAccountAll(BigDecimal accountAll) {
        this.accountAll = accountAll;
    }

    public BigDecimal getAccountCapital() {
        return accountCapital;
    }

    public void setAccountCapital(BigDecimal accountCapital) {
        this.accountCapital = accountCapital;
    }

    public BigDecimal getAccountInterest() {
        return accountInterest;
    }

    public void setAccountInterest(BigDecimal accountInterest) {
        this.accountInterest = accountInterest;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? null : addtime.trim();
    }

    public String getAddip() {
        return addip;
    }

    public void setAddip(String addip) {
        this.addip = addip == null ? null : addip.trim();
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}