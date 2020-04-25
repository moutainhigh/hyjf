/**
 * Description:用户出借 已回款vO
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年12月4日 下午2:32:36
 * Modification History:
 * Modified by :
 */
package com.hyjf.app.user.project;

import java.io.Serializable;

import com.hyjf.mybatis.model.customize.app.AppProjectContractDetailCustomize;

public class ProjectContractDetailBean extends AppProjectContractDetailCustomize implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 3278149257478770256L;

	/**用户id*/
	private String userId;

	private String orderId;
	

	public ProjectContractDetailBean() {
		super();
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
