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
	
package com.hyjf.mqreceiver.borrow.eve;

import com.hyjf.mybatis.model.auto.AleveLog;

import java.util.ArrayList;


/**
 * @author liubin
 */

public interface AleveFileService {

	void saveFile(ArrayList<AleveLog> list);

	//AleveLogMon selectaleveLogByTranno(String tranno);

}

	