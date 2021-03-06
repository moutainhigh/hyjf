package com.hyjf.admin.manager.borrow.borrowcredit;

import com.hyjf.admin.BaseDefine;
import com.hyjf.common.util.ShiroConstants;
import com.hyjf.common.util.StringPool;

public class BorrowCreditDefine extends BaseDefine {

	/** 权限 CONTROLLOR @RequestMapping值 */
	public static final String REQUEST_MAPPING = "/manager/borrow/borrowcredit";

	/** 列表画面 路径 */
	public static final String LIST_PATH = "manager/borrow/borrowcredit/borrowcredit";

	/** 详细画面的路径 */
	public static final String INFO_PATH = "manager/borrow/borrowcredit/borrowcredit_info";

	/** 取消详细画面 路径 */
	public static final String CANCEL_PATH = "manager/borrow/borrowcredit/borrowcredit_cancel";

	/** 列表画面 @RequestMapping值 */
	public static final String INIT = "init";

	/** 列表画面 @RequestMapping值 */
	public static final String SEARCH_ACTION = "searchAction";

	/** 迁移到详细画面 @RequestMapping值 */
	public static final String INFO_ACTION = "infoAction";

	/** 取消 @RequestMapping值 */
	public static final String CANCEL_ACTION = "cancelAction";

	/** 取消详细画面 @RequestMapping值 */
	public static final String CANCEL_INFO_ACTION = "cancelInfoAction";

	/** 导出 @RequestMapping值 */
	public static final String EXPORT_ACTION = "exportAction";

	/** 二次提交后跳转的画面 */
	public static final String TOKEN_INIT_PATH = REQUEST_MAPPING + StringPool.FORWARD_SLASH + INIT;

	/** FROM */
	public static final String BORROW_FORM = "borrowcreditForm";

	/** 查看权限 */
	public static final String PERMISSIONS = "borrowcredit";

	/** 查看权限 */
	public static final String PERMISSIONS_VIEW = PERMISSIONS + StringPool.COLON + ShiroConstants.PERMISSION_VIEW;

	/** 查找权限 */
	public static final String PERMISSIONS_SEARCH = PERMISSIONS + StringPool.COLON + ShiroConstants.PERMISSION_SEARCH;

	/** 详细 */
	public static final String PERMISSIONS_INFO = PERMISSIONS + StringPool.COLON + ShiroConstants.PERMISSION_INFO;

	/** 取消 */
	public static final String PERMISSIONS_CANCEL = PERMISSIONS + StringPool.COLON + ShiroConstants.PERMISSION_CANCEL;

	/** 导出 */
	public static final String PERMISSIONS_EXPORT = PERMISSIONS + StringPool.COLON + ShiroConstants.PERMISSION_EXPORT;

}
