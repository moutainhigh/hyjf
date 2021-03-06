/**
 * Description:出借常量类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 *           Created at: 2015年12月4日 下午2:33:39
 *           Modification History:
 *           Modified by :
 */

package com.hyjf.user.synbalance;

import com.hyjf.base.bean.BaseDefine;

public class SynBalanceDefine extends BaseDefine {

    /** 用户出借 @RequestMapping值 */
    public static final String REQUEST_MAPPING = "/synbalance";
    /** 获取用户优惠券列表 @RequestMapping值*/
    public static final String SYNBALANCE_ACTION = "/synbalance";
    /** 同步余额 @RequestMapping值*/
    public static final String SYNBALANCE_BANK_ACTION = "/synbankbalance";
    
    /** 同步用户线下充值交易明细及异常处理对账（后台admin用） @RequestMapping值*/
    public static final String SYNBALANCE_EXCEPTION_ACTION = "/synexception";
    
    /** 错误页面 @Path值 */
    public static final String ERROR_PTAH = "error";
}
