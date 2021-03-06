package com.hyjf.admin.exception.withdrawexception;

import com.hyjf.admin.BaseDefine;
import com.hyjf.common.util.ShiroConstants;
import com.hyjf.common.util.StringPool;

public class WithdrawExceptionDefine extends BaseDefine {

	/** 权限 CONTROLLOR @RequestMapping值 */
	public static final String REQUEST_MAPPING = "/exception/withdrawexception";

	/** 提现列表画面 路径 */
	public static final String WITHDRAW_LIST_PATH = "/exception/withdrawexception/withdrawexception";

    /** 提现列表画面 @RequestMapping值 */
    public static final String INIT = "init";

    /** 查询提现列表数据 @RequestMapping值 */
    public static final String SEARCH_WITHDRAW_ACTION = "searchWithdrawAction";

    /** 提现确认 @RequestMapping值 */
    public static final String CONFIRM_WITHDRAW_ACTION = "confirmWithdrawAction";

    /** 导出提现列表 @RequestMapping值 */
    public static final String EXPORT_WITHDRAW_ACTION = "exportWithdrawExcelAction";

	/** 二次提交后跳转的画面 */
	public static final String TOKEN_INIT_PATH = REQUEST_MAPPING + StringPool.FORWARD_SLASH + INIT;

    /** 提现平台列表 */
    public static final String CLIENT_LIST = "clientlist";

    /** 提现状态列表 */
    public static final String STATUS_LIST = "statuslist";

	/** FORM */
	public static final String WITHDRAW_FORM = "withdrawForm";

	/** 查看权限 */
	public static final String PERMISSIONS_WITHDRAW = "withdrawexception";

	/** 查看权限 */
	public static final String PERMISSIONS_WITHDRAW_VIEW = PERMISSIONS_WITHDRAW + StringPool.COLON + ShiroConstants.PERMISSION_VIEW;

    /** 文件导出权限 */
    public static final String PERMISSIONS_WITHDRAW_EXPORT = PERMISSIONS_WITHDRAW + StringPool.COLON + ShiroConstants.PERMISSION_EXPORT;

    /** 提现确认权限 */
    public static final String PERMISSIONS_WITHDRAW_CONFIRM = PERMISSIONS_WITHDRAW + StringPool.COLON + ShiroConstants.PERMISSION_WITHDRAW_CONFIRM;
}
