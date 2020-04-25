package com.hyjf.wechat.controller.user.bankopenbg;

import com.hyjf.wechat.base.BaseDefine;

/**
 * Created by jijun on 2018/02/26
 */
public class OpenAccountDefinebg extends BaseDefine{
	/** 定义bean名称*/
	public static final String CONTROLLER_NAME = "BgOpenAccountController";
    /** @RequestMapping值 */
    public static final String REQUEST_MAPPING_BG = "/wx/user/bg";
    /** 初始化开户页面部分数据 update by jijun 2018/03/16 */
    public static final String BANKOPEN_INIT_OPEN_ACTION = "/initopen";

    public static final String BANKOPEN_OPEN_SUCESS_ACTION = "/openSuccess";

    public static final String BANKOPEN_OPEN_ACCOUNT_ACTION = "/open";
    
    /** @RequestMapping值 */
    public static final String BANKOPEN_SENDCODE_ACTION = "/open/smscode";

    /** 开户协议 */
    public static final String JX_BANK_SERVICE_ACTION = "/bankopen/jxBankService";
    public static final String JX_BANK_SERVICE_PATH = "/open/open_contract";

    /** 开户成功画面路径 */
    public static final String BANKOPEN_OPEN_SUCCESS_PATH = "/open/open_success";
    /** 开户失败画面路径*/
    public static final String BANKOPEN_OPEN_ERROR_PATH = "/open/open_error";

    public static final String STATUSDESC = "statusDesc";


    public static final String MOBILE = "phone";
    public static final String SIGN = "sign";
    public static final String TOKEN = "token";

    /** 开户成功跳转设置密码 */
    public static final String BANK_PASSWORD_URL = "/bank/user/transpassword/setPassword";
    
    /** 同步@RequestMapping值 */
    public static final String RETURL_SYN_ACTION = "/bankOpenReturn";
    /** 异步@RequestMapping值 */
    public static final String RETURL_ASY_ACTION = "/bankOpenBgreturn";
    // 跳转
	public static final String JUMP_HTML = "/jumpHTML";
}
