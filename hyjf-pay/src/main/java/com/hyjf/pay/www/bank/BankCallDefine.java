/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2015年11月18日 下午12:34:08
 * Modification History:
 * Modified by :
 */

package com.hyjf.pay.www.bank;

import com.hyjf.pay.BaseDefine;

/**
 * @author Administrator
 */

public class BankCallDefine extends BaseDefine {

	/** CONTROLLOR @value值 */
	public static final String CONTROLLOR_CLASS_NAME = "BankCallController";

	/** CONTROLLOR @RequestMapping值 */
	public static final String CONTROLLOR_REQUEST_MAPPING = "/bankcall";

	/** 画面 @RequestMapping值 */
	public static final String CALL_API_PAGE = "callApiPage";
	/** 页面同步请求 @RequestMapping值 */
	public static final String CALL_PAGE_RETURN = "callPageReturn";
	/** 页面异步请求@RequestMapping值 */
	public static final String CALL_PAGE_BACK = "callPageBack";
	/** 忘记密码页面请求@RequestMapping值 */
	public static final String PAGE_FORGOTPWD = "pageForgotPWD";
	/** 接口 @RequestMapping值 */
	public static final String CALL_API_BG = "callApiBg";
	/** 接口 @RequestMapping值 */
	public static final String CALL_API_BG_FORQUERY = "callApiBgForQuery";
	/** 接口异步 @RequestMapping值 */
	public static final String CALL_API_RETURN = "callApiReturn";
	/** 画面 @RequestMapping值 */
	public static final String CALL_API_AJAX = "callApiAjax";
	/** bankForm值 */
	public static final String BANK_FORM = "bankForm";

	/** JSP 跳转到汇付天下画面 */
	public static final String JSP_BANK_SEND = "/bank/bank_send";
	/** JSP 汇付天下跳转画面 */
	public static final String JSP_BANK_RESULT = "/bank/bank_result";
	/** JSP 回调画面 */
	public static final String JSP_BANK_RECEIVE = "/bank/bank_receive";
	
	
	/** 接收即信返回的消息(2.2.1.线下充值回调)@RequestMapping值 */
	public static final String CALL_OFFLINERECHARGE_BACK = "callOfflineRechargeBack";

}
