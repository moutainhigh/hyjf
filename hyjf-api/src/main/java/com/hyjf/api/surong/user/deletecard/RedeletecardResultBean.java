package com.hyjf.api.surong.user.deletecard;

import com.hyjf.base.bean.BaseMapBean;

public class RedeletecardResultBean extends BaseMapBean {
	
	
	/**
	 * 异常编号
	 */
	private String errorCode;
	
	
	/**
	 * 返回调用方的url
	 */
	private String callBackAction;
	/** 返回数据 **/
    public Object data;
    public String status;
	public String statusDesc;
	

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }


    public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getCallBackAction() {
		return callBackAction;
	}

	public void setCallBackAction(String callBackAction) {
		this.callBackAction = callBackAction;
	}	
}
