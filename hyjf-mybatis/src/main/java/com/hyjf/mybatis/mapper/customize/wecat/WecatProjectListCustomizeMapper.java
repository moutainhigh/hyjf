/**
 * Description:会员用户开户记录初始化列表查询
 * Copyright: (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 上午11:01:57
 * Modification History:
 * Modified by : 
 * */

package com.hyjf.mybatis.mapper.customize.wecat;

import java.util.List;
import java.util.Map;

import com.hyjf.mybatis.model.customize.wecat.WecatProjectListCustomize;

public interface WecatProjectListCustomizeMapper {

	List<WecatProjectListCustomize> selectProjectList(Map<String, Object> params);

	int countProjectListRecordTotal(Map<String, Object> params);
}
