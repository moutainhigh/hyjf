package com.hyjf.admin.manager.user.regist;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hyjf.mybatis.model.auto.UtmPlat;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hyjf.admin.BaseController;
import com.hyjf.common.log.LogUtil;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.AsteriskProcessUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.ExportExcel;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.mybatis.model.auto.ParamName;
import com.hyjf.mybatis.model.customize.admin.AdminRegistListCustomize;

/**
 * @package com.hyjf.admin.maintenance.AdminPermissions
 * @author GOGTZ-Z
 * @date 2015/07/09 17:00
 * @version V1.0  
 */
@Controller
@RequestMapping(value = RegistDefine.REQUEST_MAPPING)
public class RegistController extends BaseController {

	@Autowired
	private RegistService registService;

	/**
	 * 权限维护画面初始化
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(RegistDefine.REGIST_LIST_ACTION)
	@RequiresPermissions(RegistDefine.PERMISSIONS_VIEW)
	public ModelAndView init(HttpServletRequest request, RedirectAttributes attr, @ModelAttribute(RegistDefine.REGIST_LIST_FORM) RegistListCustomizeBean form) {
		LogUtil.startLog(RegistController.class.toString(), RegistDefine.REGIST_LIST_ACTION);
		ModelAndView modelAndView = new ModelAndView(RegistDefine.REGIST_LIST_PATH);
		// 创建分页
		this.createPage(request, modelAndView, form);
		LogUtil.endLog(RegistController.class.toString(), RegistDefine.REGIST_LIST_ACTION);
		return modelAndView;
	}

	/**
	 * 创建权限维护分页机能
	 * 
	 * @param request
	 * @param modelAndView
	 * @param form
	 */
	private void createPage(HttpServletRequest request, ModelAndView modelAndView, RegistListCustomizeBean form) {

		// 注册渠道
//		List<UtmPlat> utmPlat = this.registService.getUtmPlagList();
//		modelAndView.addObject("utmPlat", utmPlat);
		// 注册平台
		List<ParamName> registPlat = this.registService.getParamNameList("CLIENT");
		modelAndView.addObject("registPlat", registPlat);

		Map<String, Object> registUser = new HashMap<String, Object>();
		registUser.put("userName", form.getUserName());
		registUser.put("mobile", form.getMobile());
		registUser.put("recommendName", form.getRecommendName());
		registUser.put("registPlat", form.getRegistPlat());
		registUser.put("sourceId", form.getSourceId());
		registUser.put("regTimeStart", StringUtils.isNotBlank(form.getRegTimeStart())?form.getRegTimeStart():null);
		registUser.put("regTimeEnd", StringUtils.isNotBlank(form.getRegTimeEnd())?form.getRegTimeEnd():null);
		int recordTotal = this.registService.countRecordTotal(registUser);
		if (recordTotal > 0) {
			Paginator paginator = new Paginator(form.getPaginatorPage(), recordTotal);
			List<AdminRegistListCustomize> recordList = this.registService.getRecordList(registUser, paginator.getOffset(), paginator.getLimit());
			form.setPaginator(paginator);
			form.setRecordList(recordList);
			modelAndView.addObject(RegistDefine.REGIST_LIST_FORM, form);
		}
	}

	/**
	 * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
	 * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
	 * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(RegistDefine.EXPORT_REGIST_ACTION)
	@RequiresPermissions(RegistDefine.PERMISSIONS_EXPORT)
	public void exportExcel(@ModelAttribute RegistListCustomizeBean form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		LogUtil.startLog(RegistDefine.THIS_CLASS, RegistDefine.EXPORT_REGIST_ACTION);
		// 表格sheet名称
		String sheetName = "注册记录";
		// 文件名称
		String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

		// 需要输出的结果列表
		Map<String, Object> registUser = new HashMap<String, Object>();
		registUser.put("userName", form.getUserName());
		registUser.put("mobile", form.getMobile());
		registUser.put("recommendName", form.getRecommendName());
		registUser.put("registPlat", form.getRegistPlat());
		registUser.put("sourceId", form.getSourceId());
		registUser.put("regTimeStart", StringUtils.isNotBlank(form.getRegTimeStart())?form.getRegTimeStart():null);
		registUser.put("regTimeEnd", StringUtils.isNotBlank(form.getRegTimeEnd())?form.getRegTimeEnd():null);

		List<AdminRegistListCustomize> recordList = this.registService.getRecordList(registUser, -1, -1);
		String[] titles = new String[] { "序号", "用户名", "手机号", "推荐人", "注册渠道", "注册平台", "注册IP", "注册时间" };
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

		if (recordList != null && recordList.size() > 0) {

			int sheetCount = 1;
			int rowNum = 0;

			for (int i = 0; i < recordList.size(); i++) {
				rowNum++;
				if (i != 0 && i % 60000 == 0) {
					sheetCount++;
					sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
					rowNum = 1;
				}

				// 新建一行
				Row row = sheet.createRow(rowNum);
				// 循环数据
				for (int celLength = 0; celLength < titles.length; celLength++) {
					AdminRegistListCustomize user = recordList.get(i);
					// 创建相应的单元格
					Cell cell = row.createCell(celLength);
					if (celLength == 0) {// 序号
						cell.setCellValue(i + 1);
					} else if (celLength == 1) {// 用户名
						cell.setCellValue(user.getUserName());
					} else if (celLength == 2) {// 手机号
						cell.setCellValue(AsteriskProcessUtil.getAsteriskedValue(user.getMobile(), RegistDefine.PERMISSION_HIDE_SHOW));
					} else if (celLength == 3) {// 推荐人
						cell.setCellValue(user.getRecommendName());
					} else if (celLength == 4) {// 注册渠道
						cell.setCellValue(user.getSourceName());
					/* }else if (celLength == 5) {// 注册平台
						cell.setCellValue(user.getRegistPlat());
					} else if (celLength == 6) {// 注册时间
						cell.setCellValue(user.getRegTime());
					}*/
					} else if (celLength == 5) {// 注册平台
						cell.setCellValue(user.getRegistPlat());
					} else if (celLength == 6) {// 注册IP
						cell.setCellValue(user.getRegIP());
					} else if (celLength == 7) {// 注册时间
                        cell.setCellValue(user.getRegTime());
                    }
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
		LogUtil.endLog(RegistDefine.THIS_CLASS, RegistDefine.EXPORT_REGIST_ACTION);
	}
}
