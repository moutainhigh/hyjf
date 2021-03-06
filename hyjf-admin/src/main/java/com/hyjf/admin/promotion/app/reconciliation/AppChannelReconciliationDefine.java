package com.hyjf.admin.promotion.app.reconciliation;

import com.hyjf.admin.BaseDefine;
import com.hyjf.common.util.ShiroConstants;
import com.hyjf.common.util.StringPool;

public class AppChannelReconciliationDefine extends BaseDefine {

	/** 权限 CONTROLLOR @RequestMapping值 */
	public static final String REQUEST_MAPPING = "/promotion/app/channelreconciliation";

	/** 列表画面 路径 */
	public static final String LIST_PATH = "promotion/app/reconciliation/channelreconciliation";

	/** 汇计划列表画面 路径 */
	public static final String HJH_LIST_PATH = "promotion/app/reconciliation/channelreconciliationhjh";

	/** 列表画面 @RequestMapping值 */
	public static final String INIT = "init";

	/** 汇计划列表画面 @RequestMapping值 */
	public static final String HJH_INIT = "hjhInit";

	/** 列表画面 @RequestMapping值 */
	public static final String SEARCH_ACTION = "searchAction";

	/** 汇计划列表画面 @RequestMapping值 */
	public static final String HJH_SEARCH_ACTION = "searchHjhAction";

	/** 导出数据的 @RequestMapping值 */
	public static final String EXPORT_ACTION = "exportAction";

	/** 汇计划导出数据的 @RequestMapping值 */
	public static final String EXPORT_HJH_ACTION = "hjhExportAction";

	/** 二次提交后跳转的画面 */
	public static final String TOKEN_INIT_PATH = REQUEST_MAPPING + StringPool.FORWARD_SLASH + INIT;

	/** FROM */
	public static final String FORM = "channelreconciliationForm";


	/** FROM */
	public static final String HJH_FORM = "channelreconciliationHjhForm";

	/** 查看权限 */
	public static final String PERMISSIONS = "appchannelrecon";

	/** 查看权限 */
	public static final String PERMISSIONS_VIEW = PERMISSIONS + StringPool.COLON + ShiroConstants.PERMISSION_VIEW;

	/** 检索权限 */
	public static final String PERMISSIONS_SEARCH = PERMISSIONS + StringPool.COLON + ShiroConstants.PERMISSION_SEARCH;

	/** 导出权限 */
	public static final String PERMISSIONS_EXPORT = PERMISSIONS + StringPool.COLON + ShiroConstants.PERMISSION_EXPORT;
}
