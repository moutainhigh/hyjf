/**
 * Description:用户详情显示类PO
 * Copyright: Copyright (c)2015
 * Company: 
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月12日 下午4:10:37
 * Modification History:
 * Modified by : 
 */

package com.hyjf.mybatis.model.customize.admin;

/**
 * @author 王坤
 */

public class AdminUserBankOpenAccountCustomize {
    //用户银行账户
    private String account;
    //用户开户平台
    private String openAccountPlat;
    //用户开户时间
    private String openAccountTime;
    //用户开户类型
    private String userType;
    //用户预留手机号
    private String mobile;
    // add by nxl 合规四期,用户详情添加以下三个字段显示
    private String bankNo;
    private String payAllianceCode;
    private String bankName;

    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getOpenAccountPlat() {
        return openAccountPlat;
    }
    public void setOpenAccountPlat(String openAccountPlat) {
        this.openAccountPlat = openAccountPlat;
    }
    public String getOpenAccountTime() {
        return openAccountTime;
    }
    public void setOpenAccountTime(String openAccountTime) {
        this.openAccountTime = openAccountTime;
    }
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getPayAllianceCode() {
        return payAllianceCode;
    }

    public void setPayAllianceCode(String payAllianceCode) {
        this.payAllianceCode = payAllianceCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
