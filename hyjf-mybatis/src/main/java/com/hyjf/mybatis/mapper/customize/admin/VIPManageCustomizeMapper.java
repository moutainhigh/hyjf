/**
 * Description:会员管理初始化列表查询
 * Copyright: (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 上午11:01:57
 * Modification History:
 * Modified by : 
 * */

package com.hyjf.mybatis.mapper.customize.admin;

import java.util.List;
import java.util.Map;

import com.hyjf.mybatis.model.customize.admin.VIPManageListCustomize;

public interface VIPManageCustomizeMapper {
	/**
	 * 查询VIP用户列表
	 * 
	 * @param id
	 * @return
	 */
	List<VIPManageListCustomize> selectUserList(Map<String, Object> user);


	/**
	 * @param user
	 * @return
	 */

	int countRecordTotal(Map<String, Object> user);


}
