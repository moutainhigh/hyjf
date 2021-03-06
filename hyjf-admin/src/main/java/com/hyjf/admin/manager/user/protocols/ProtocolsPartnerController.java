package com.hyjf.admin.manager.user.protocols;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.admin.BaseController;
import com.hyjf.admin.manager.borrow.borrowcommon.BorrowCommonService;
import com.hyjf.admin.manager.content.links.ContentLinksBean;
import com.hyjf.admin.manager.content.links.ContentLinksService;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.log.LogUtil;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.PropUtils;
import com.hyjf.common.validator.ValidatorFieldCheckUtil;
import com.hyjf.mybatis.model.auto.Category;
import com.hyjf.mybatis.model.auto.Links;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 合作伙伴type为2
 * 
 * @author qingbing
 *
 */
@Controller
@RequestMapping(value = ProtocolsPartnerDefine.REQUEST_MAPPING)
public class ProtocolsPartnerController extends BaseController {

	@Autowired
	private ContentLinksService contentLinksService;
	@Autowired
	private BorrowCommonService borrowCommonService;

	/**
	 * 合作伙伴维护画面初始化
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(ProtocolsPartnerDefine.INIT)
	@RequiresPermissions(ProtocolsPartnerDefine.PERMISSIONS_VIEW)
	public ModelAndView init(HttpServletRequest request, RedirectAttributes attr,
			@ModelAttribute(ProtocolsPartnerDefine.PROTOCOLSPARTNER_FORM) ContentLinksBean form) {
		LogUtil.startLog(ProtocolsPartnerController.class.toString(), ProtocolsPartnerDefine.INIT);
		ModelAndView modelAndView = new ModelAndView(ProtocolsPartnerDefine.LIST_PATH);
		if (form.getMystatus() != null) {
			form.setStatus(form.getMystatus().shortValue());
		}
		// 创建分页
		this.createPage(request, modelAndView, form);
		LogUtil.endLog(ProtocolsPartnerController.class.toString(), ProtocolsPartnerDefine.INIT);
		return modelAndView;
	}

	/**
	 * 合作伙伴维护分页机能 页面初始化
	 *
	 * @param request
	 * @param modelAndView
	 * @param form
	 */
	private void createPage(HttpServletRequest request, ModelAndView modelAndView, ContentLinksBean form) {
		// 合作伙伴为2
		form.setType(2);
		List<Links> recordList = this.contentLinksService.getRecordList(form, -1, -1);
		if (recordList != null) {
			Paginator paginator = new Paginator(form.getPaginatorPage(), recordList.size());
			recordList = contentLinksService.getRecordList(form, paginator.getOffset(), paginator.getLimit());
			form.setPaginator(paginator);
			form.setRecordList(recordList);
			modelAndView.addObject(ProtocolsPartnerDefine.PROTOCOLSPARTNER_FORM, form);
			String fileDomainUrl = UploadFileUtils.getDoPath(PropUtils.getSystem("file.domain.url"));
			modelAndView.addObject("fileDomainUrl",fileDomainUrl);
		}
		putCategory(modelAndView);
	}

	/**
	 * 画面迁移(含有id更新，不含有id添加)
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(ProtocolsPartnerDefine.INFO_ACTION)
	@RequiresPermissions(value = { ProtocolsPartnerDefine.PERMISSIONS_INFO, ProtocolsPartnerDefine.PERMISSIONS_ADD,
			ProtocolsPartnerDefine.PERMISSIONS_MODIFY }, logical = Logical.OR)
	public ModelAndView info(HttpServletRequest request, RedirectAttributes attr,
			@ModelAttribute(ProtocolsPartnerDefine.PROTOCOLSPARTNER_FORM) ContentLinksBean form) {
		LogUtil.startLog(ProtocolsPartnerController.class.toString(), ProtocolsPartnerDefine.INIT);
		ModelAndView modelAndView = new ModelAndView(ProtocolsPartnerDefine.INFO_PATH);

		if (StringUtils.isNotEmpty(form.getIds())) {
			Integer id = Integer.valueOf(form.getIds());
			Links record = this.contentLinksService.getRecord(id.shortValue());
			modelAndView.addObject(ProtocolsPartnerDefine.PROTOCOLSPARTNER_FORM, record);
			String fileDomainUrl = UploadFileUtils.getDoPath(PropUtils.getSystem("file.domain.url"));
			modelAndView.addObject("fileDomainUrl",fileDomainUrl);
		}
		putCategory(modelAndView);
		LogUtil.endLog(ProtocolsPartnerController.class.toString(), ProtocolsPartnerDefine.INIT);
		return modelAndView;
	}

	/**
	 * 设置Category
	 *
	 * @param modelAndView
	 */
	private void putCategory(ModelAndView modelAndView) {
		List<Category> categoryList = new ArrayList<Category>();
		{
			Category category = new Category();
			category.setId(11);
			category.setTitle("法律支持");
			categoryList.add(category);
		}
		{
			Category category = new Category();
			category.setId(7);
			category.setTitle("金融机构");
			categoryList.add(category);
		}
		{
			Category category = new Category();
			category.setId(12);
			category.setTitle("其他");
			categoryList.add(category);
		}
		{
			Category category = new Category();
			category.setId(10);
			category.setTitle("服务支持");
			categoryList.add(category);
		}
		{
			Category category = new Category();
			category.setId(8);
			category.setTitle("联系我们");
			categoryList.add(category);
		}
		modelAndView.addObject("categoryList", categoryList);
	}

	/**
	 * 合作伙伴维护条件查询
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(ProtocolsPartnerDefine.SEARCH_ACTION)
	@RequiresPermissions(ProtocolsPartnerDefine.PERMISSIONS_SEARCH)
	public ModelAndView select(HttpServletRequest request, RedirectAttributes attr,
			@ModelAttribute(ProtocolsPartnerDefine.PROTOCOLSPARTNER_FORM) ContentLinksBean form) {
		LogUtil.startLog(ProtocolsPartnerController.class.toString(), ProtocolsPartnerDefine.SEARCH_ACTION);
		ModelAndView modelAndView = new ModelAndView(ProtocolsPartnerDefine.LIST_PATH);
		// 创建分页
		if (form.getMystatus() != null) {
			form.setStatus(form.getMystatus().shortValue());
		}
		this.createPage(request, modelAndView, form);
		LogUtil.endLog(ProtocolsPartnerController.class.toString(), ProtocolsPartnerDefine.SEARCH_ACTION);
		return modelAndView;
	}

	/**
	 * 添加活动信息
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = ProtocolsPartnerDefine.INSERT_ACTION, method = RequestMethod.POST)
	@RequiresPermissions(ProtocolsPartnerDefine.PERMISSIONS_ADD)
	public ModelAndView insertAction(HttpServletRequest request, Links form) {
		LogUtil.startLog(ProtocolsPartnerController.class.toString(), ProtocolsPartnerDefine.INSERT_ACTION);
		ModelAndView modelAndView = new ModelAndView(ProtocolsPartnerDefine.INFO_PATH);

		// 调用校验
		if (validatorFieldCheck(modelAndView, form) != null) {
			// 失败返回
			return validatorFieldCheck(modelAndView, form);
		}
		// 数据插入(合作伙伴为2)
		form.setType(2);
		this.contentLinksService.insertRecord(form);
		// 跳转页面用（info里面有）
		modelAndView.addObject(ProtocolsPartnerDefine.SUCCESS, ProtocolsPartnerDefine.SUCCESS);
		LogUtil.endLog(ProtocolsPartnerController.class.toString(), ProtocolsPartnerDefine.INSERT_ACTION);
		return modelAndView;
	}

	/**
	 * 修改活动维护信息
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = ProtocolsPartnerDefine.UPDATE_ACTION, method = RequestMethod.POST)
	@RequiresPermissions(ProtocolsPartnerDefine.PERMISSIONS_MODIFY)
	public ModelAndView updateAction(HttpServletRequest request, Links form) {
		LogUtil.startLog(ProtocolsPartnerController.class.toString(), ProtocolsPartnerDefine.UPDATE_ACTION);
		ModelAndView modelAndView = new ModelAndView(ProtocolsPartnerDefine.INFO_PATH);

		// 调用校验
		if (validatorFieldCheck(modelAndView, form) != null) {
			// 失败返回
			return validatorFieldCheck(modelAndView, form);
		}
		// 根据id更新
		if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "id", form.getId().toString())) {
			return modelAndView;
		}
		// 数据插入(合作伙伴为2)
		form.setType(2);
		// 更新
		this.contentLinksService.updateRecord(form);
		// 跳转页面用（info里面有）
		modelAndView.addObject(ProtocolsPartnerDefine.SUCCESS, ProtocolsPartnerDefine.SUCCESS);
		LogUtil.endLog(ProtocolsPartnerController.class.toString(), ProtocolsPartnerDefine.UPDATE_ACTION);
		return modelAndView;
	}

	/**
	 * 删除配置信息
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(ProtocolsPartnerDefine.DELETE_ACTION)
	@RequiresPermissions(ProtocolsPartnerDefine.PERMISSIONS_DELETE)
	public ModelAndView deleteRecordAction(HttpServletRequest request, String ids) {
		LogUtil.startLog(ProtocolsPartnerController.class.toString(), ProtocolsPartnerDefine.DELETE_ACTION);

		ModelAndView modelAndView = new ModelAndView(ProtocolsPartnerDefine.RE_LIST_PATH);
		// 解析json字符串
		List<Integer> recordList = JSONArray.parseArray(ids, Integer.class);
		this.contentLinksService.deleteRecord(recordList);
		LogUtil.endLog(ProtocolsPartnerController.class.toString(), ProtocolsPartnerDefine.DELETE_ACTION);
		return modelAndView;
	}

	/**
	 * 修改状态
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(ProtocolsPartnerDefine.STATUS_ACTION)
	@RequiresPermissions(ProtocolsPartnerDefine.PERMISSIONS_MODIFY)
	public ModelAndView updateStatus(HttpServletRequest request ,RedirectAttributes attr, ContentLinksBean form) {
		LogUtil.startLog(ProtocolsPartnerController.class.toString(), ProtocolsPartnerDefine.DELETE_ACTION);
		ModelAndView modelAndView = new ModelAndView(ProtocolsPartnerDefine.RE_LIST_PATH);
		// 修改状态
		if (StringUtils.isNotEmpty(form.getIds())) {
			Integer id = Integer.valueOf(form.getIds());
			Links record = this.contentLinksService.getRecord(id.shortValue());
			if (record.getStatus() == 1) {
				record.setStatus((short) 0);
			} else {
				record.setStatus((short) 1);
			}
			this.contentLinksService.updateRecord(record);
		}
		attr.addFlashAttribute(ProtocolsPartnerDefine.PROTOCOLSPARTNER_FORM, form);
		LogUtil.endLog(ProtocolsPartnerController.class.toString(), ProtocolsPartnerDefine.DELETE_ACTION);
		return modelAndView;
	}

	/**
	 * 调用校验表单方法
	 *
	 * @param modelAndView
	 * @param form
	 * @return
	 */
	private ModelAndView validatorFieldCheck(ModelAndView modelAndView, Links form) {
		// 字段校验(非空判断和长度判断)
		if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "webName", form.getWebname())) {
			return modelAndView;
		}
		if (!ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "webName", form.getWebname(), 30, true)) {
			return modelAndView;
		}
		return null;
	}

	/**
	 * 资料上传
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = ProtocolsPartnerDefine.UPLOAD_FILE, method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value = { ProtocolsPartnerDefine.PERMISSIONS_ADD,
			ProtocolsPartnerDefine.PERMISSIONS_MODIFY }, logical = Logical.OR)
	public String uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LogUtil.startLog(ProtocolsPartnerController.class.toString(), ProtocolsPartnerDefine.UPLOAD_FILE);
		String files = borrowCommonService.uploadFile(request, response);
		LogUtil.endLog(ProtocolsPartnerController.class.toString(), ProtocolsPartnerDefine.UPLOAD_FILE);
		return files;
	}
}
