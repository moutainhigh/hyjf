package com.hyjf.api.aems.bankcard;

import com.hyjf.base.bean.BaseBean;

public class AemsThirdPartyBankCardRequestBean extends BaseBean{
    private String cardNo;

    private String mobile;
    
    private String code;
    
    private String lastSrvAuthCode;
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLastSrvAuthCode() {
        return lastSrvAuthCode;
    }

    public void setLastSrvAuthCode(String lastSrvAuthCode) {
        this.lastSrvAuthCode = lastSrvAuthCode;
    }
    
    
}
