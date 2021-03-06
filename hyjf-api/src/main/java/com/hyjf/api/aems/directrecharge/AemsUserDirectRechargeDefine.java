package com.hyjf.api.aems.directrecharge;

import com.hyjf.base.bean.BaseDefine;

/**
 * 外部服务接口:3.3.充值页面
 * 
 *
 */
public class AemsUserDirectRechargeDefine extends BaseDefine {

	/** 用户充值 @RequestMapping */
	public static final String REQUEST_MAPPING = "/aems/directRechargePage";

	/** 充值 @ReqestMapping */
	public static final String RECHARGE_ACTION = "/recharge";
	
	/** 同步回调  */
    public static final String RETURL_SYN_ACTION = "/directRechargePageReturn";
    /** 异步回调  */
    public static final String RETURL_ASY_ACTION = "/directRechargePageBgreturn";



}
