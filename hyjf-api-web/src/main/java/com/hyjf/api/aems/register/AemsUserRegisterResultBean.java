/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.api.aems.register;

import com.hyjf.base.bean.BaseResultBean;

/**
 * AEMS系统:用户注册返回Bean
 *
 * @author liuyang
 * @version AemsUserRegisterResultBean, v0.1 2018/9/4 18:55
 */
public class AemsUserRegisterResultBean extends BaseResultBean {

    // 用户ID
    private Integer userId;
    // 用户名
    private String userName;
    // 是否开户
    private String isOpenAccount;
    // 电子账号
    private String account;
    // 是否设置密码
    private String isSetPassword;
    // 自动投标授权
    private String autoInvesStatus;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIsOpenAccount() {
        return isOpenAccount;
    }

    public void setIsOpenAccount(String isOpenAccount) {
        this.isOpenAccount = isOpenAccount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIsSetPassword() {
        return isSetPassword;
    }

    public void setIsSetPassword(String isSetPassword) {
        this.isSetPassword = isSetPassword;
    }

    public String getAutoInvesStatus() {
        return autoInvesStatus;
    }

    public void setAutoInvesStatus(String autoInvesStatus) {
        this.autoInvesStatus = autoInvesStatus;
    }
}
