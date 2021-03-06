/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: lb
 * @version: 1.0
 * Created at: 2017年9月15日 上午9:43:49
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.web.api.aems.bind;

import com.hyjf.mybatis.model.auto.Users;
import com.hyjf.web.api.base.ApiBaseService;
import com.hyjf.web.user.login.LoginBean;

/**
 *
 * @author Zha Daojian
 * @date 2018/9/18 14:43
 * @param 
 * @return 
 **/

public interface AemsApiUserBindService extends ApiBaseService {

	Boolean bindThirdUser(Integer userId, String bindUniqueId, Integer bindPlatformId);
	Integer getUserIdByBind(Long bindUniqueId, int bindPlatformId);
	String getBindUniqueIdByUserId(int userId, int bindPlatformId);
	Users getUserInfoByLogin(LoginBean loginBean);
	/**
	 * 根据身份证号码查询登录注册手机号码
	 * @param idCard 身份证号码
	 * @return
	 */
	String getByIdCard(String idCard);
}

	