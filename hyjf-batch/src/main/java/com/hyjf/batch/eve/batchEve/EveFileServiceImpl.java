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
	
package com.hyjf.batch.eve.batchEve;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.hyjf.common.util.GetDate;
import com.hyjf.mybatis.customize.CustomizeMapper;
import com.hyjf.mybatis.model.auto.EveLog;
import com.hyjf.mybatis.model.auto.EveLogExample;


/**
 * @author liubin
 */

@Service("EveService")
public class EveFileServiceImpl extends CustomizeMapper implements EveFileService {

	@Override
	public void saveFile(ArrayList<EveLog> list) {
	    for (EveLog eve : list) {
	        int nowTime = GetDate.getNowTime10();
	        eve.setCreateTime(nowTime);
	        eve.setCreateUserId(1028);
	        eve.setDelFlg(0);
	        eveLogMapper.insert(eve);
        }
	    
	}
	@Override
    public int countByExample(String beforeDay){
        EveLogExample example = new EveLogExample();
        EveLogExample.Criteria criteria = example.createCriteria();
        criteria.andCreateDayEqualTo(beforeDay);
        return eveLogMapper.countByExample(example);
    }
}
	