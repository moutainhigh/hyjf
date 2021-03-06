package com.hyjf.mybatis.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BorrowUserStatistic implements Serializable {
    private Integer id;

    private Integer borrowuserCountTotal;

    private Integer borrowuserCountCurrent;

    private BigDecimal borrowuserMoneyTopten;

    private BigDecimal borrowuserMoneyTopone;

    private BigDecimal borrowuserMoneyTotal;

    private Integer tenderuserCountCurrent;

    private Integer addTime;

    private Integer updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBorrowuserCountTotal() {
        return borrowuserCountTotal;
    }

    public void setBorrowuserCountTotal(Integer borrowuserCountTotal) {
        this.borrowuserCountTotal = borrowuserCountTotal;
    }

    public Integer getBorrowuserCountCurrent() {
        return borrowuserCountCurrent;
    }

    public void setBorrowuserCountCurrent(Integer borrowuserCountCurrent) {
        this.borrowuserCountCurrent = borrowuserCountCurrent;
    }

    public BigDecimal getBorrowuserMoneyTopten() {
        return borrowuserMoneyTopten;
    }

    public void setBorrowuserMoneyTopten(BigDecimal borrowuserMoneyTopten) {
        this.borrowuserMoneyTopten = borrowuserMoneyTopten;
    }

    public BigDecimal getBorrowuserMoneyTopone() {
        return borrowuserMoneyTopone;
    }

    public void setBorrowuserMoneyTopone(BigDecimal borrowuserMoneyTopone) {
        this.borrowuserMoneyTopone = borrowuserMoneyTopone;
    }

    public BigDecimal getBorrowuserMoneyTotal() {
        return borrowuserMoneyTotal;
    }

    public void setBorrowuserMoneyTotal(BigDecimal borrowuserMoneyTotal) {
        this.borrowuserMoneyTotal = borrowuserMoneyTotal;
    }

    public Integer getTenderuserCountCurrent() {
        return tenderuserCountCurrent;
    }

    public void setTenderuserCountCurrent(Integer tenderuserCountCurrent) {
        this.tenderuserCountCurrent = tenderuserCountCurrent;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }
}