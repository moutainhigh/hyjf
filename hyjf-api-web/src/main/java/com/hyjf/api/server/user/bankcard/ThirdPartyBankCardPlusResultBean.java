package com.hyjf.api.server.user.bankcard;

import com.hyjf.base.bean.BaseResultBean;

public class ThirdPartyBankCardPlusResultBean extends BaseResultBean {
	
	/**
     * 此处为属性说明
     */
    private static final long serialVersionUID = 3709370958884607483L;
    private String srvAuthCode;

    public String getSrvAuthCode() {
        return srvAuthCode;
    }

    public void setSrvAuthCode(String srvAuthCode) {
        this.srvAuthCode = srvAuthCode;
    }
	
	
}
