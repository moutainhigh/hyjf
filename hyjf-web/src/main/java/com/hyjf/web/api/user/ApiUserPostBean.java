/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: lb
 * @version: 1.0
 * Created at: 2017年9月15日 上午9:23:07
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.web.api.user;

import com.hyjf.web.api.base.ApiBaseBean;

/**
 * @author liubin
 */

public class ApiUserPostBean extends ApiBaseBean {
	/** 加密的汇晶社用户ID */
	private String bindUniqueIdScy;
	
	/** 返回Url */
    private String retUrl;
    
	/** 平台id */
    private Integer pid;
    
    /** 手机号码*/
    private String mobile;
    
    /** 身份证号码*/
    private String idCard;
    
    /** 姓名*/
    private String name;
    
	/**
	 * bindUniqueIdScy
	 * @return the bindUniqueIdScy
	 */
	public String getBindUniqueIdScy() {
		return bindUniqueIdScy;
	}
	
	public void setBindUniqueIdScy(String bindUniqueIdScy) {
		this.bindUniqueIdScy = bindUniqueIdScy;
	}

	/**
	 * retUrl
	 * @return the retUrl
	 */
	
	public String getRetUrl() {
		return retUrl;
	}

	/**
	 * @param retUrl the retUrl to set
	 */
	
	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}

	/**
	 * pid
	 * @return the pid
	 */
	
	public Integer getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	
	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 执行前每个方法前需要添加BusinessDesc描述
	 * @return
	 * @author lb
	 */
		
	@Override
	public String toString() {
		return "ApiUserPostBean [bindUniqueIdScy=" + bindUniqueIdScy + ", retUrl=" + retUrl + ", pid=" + pid + ", timestamp=" + super.getTimestamp() +", chkValue=" + super.getChkValue() + "]";
	}
}

	