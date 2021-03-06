package com.hyjf.web.api.user.login;

import com.hyjf.common.enums.utils.MsgEnum;
import com.hyjf.common.util.ApiSignUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.web.api.base.ApiBaseController;
import com.hyjf.web.api.common.ApiCommonService;
import com.hyjf.web.api.skip.ApiSkipFormBean;
import com.hyjf.web.api.user.ApiUserPostBean;
import com.hyjf.web.api.user.NmcfPostBean;
import com.hyjf.web.bank.web.borrow.BorrowDefine;
import com.hyjf.web.common.WebViewUser;
import com.hyjf.web.user.login.LoginDefine;
import com.hyjf.web.user.login.LoginService;
import com.hyjf.web.user.pandect.PandectDefine;
import com.hyjf.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liubin
 */
@Controller
@RequestMapping(value = ApiUserLoginDefine.REQUEST_MAPPING)
public class ApiUserLoginServer extends ApiBaseController {
	/** 跳转的jsp页面 */
	private static final String SEND_JSP = "/bank/bank_send";
	
	@Autowired
	private ApiCommonService apiCommonService;
	@Autowired
	private LoginService loginService;

    @RequestMapping(value = ApiUserLoginDefine.LOGIN_THIRDAPI)
    public ModelAndView thirdLoginApi(ApiUserPostBean bean){
    	// 设置接口结果页的信息（返回Url）
    	this.initCheckUtil(bean);
    	
		// 验证
        apiCommonService.checkPostBeanOfWeb(bean);
		
		ModelAndView modelAndView = new ModelAndView(SEND_JSP);
		ApiSkipFormBean apiSkipFormBean = new ApiSkipFormBean();
		String returl = CustomConstants.HOST + ApiUserLoginDefine.REQUEST_MAPPING + ApiUserLoginDefine.LOGIN_THIRDAPI_NOTIFY + ".do";
		
		apiSkipFormBean.setAction(returl);
		apiSkipFormBean.set("bindUniqueIdScy", bean.getBindUniqueIdScy());
		apiSkipFormBean.set("pid", bean.getPid()+"");
		apiSkipFormBean.set("retUrl", bean.getRetUrl());
		apiSkipFormBean.set("timestamp", String.valueOf(bean.getTimestamp()));
		apiSkipFormBean.set("chkValue", bean.getChkValue());
		modelAndView.addObject("bankForm", apiSkipFormBean);
		return modelAndView;
    }
	
	 /**
     * 第三方登录
     * 
     * @param modelAndView
     * @param request
     * @return
     */
    @RequestMapping(value = ApiUserLoginDefine.LOGIN_THIRDAPI_NOTIFY)
    public ModelAndView thirdLogin(HttpServletRequest request,HttpServletResponse response, @ModelAttribute ApiUserPostBean bean){
		// 验证
        apiCommonService.checkPostBeanOfWeb(bean);
		// 验签
		apiCommonService.checkSign(bean);
		// 解密
		Long bindUniqueId = apiCommonService.decrypt(bean);
		// 查询Userid
		Integer userId = apiCommonService.getUserIdByBind(bindUniqueId, bean.getPid());

		// 登陆
        WebViewUser webUser = loginService.getWebViewUserByUserId(userId);
        WebUtils.sessionLogin(request, response, webUser);
        
        // 返回
        return new ModelAndView("redirect:" + CustomConstants.HOST
                + PandectDefine.REQUEST_MAPPING + PandectDefine.PANDECT_ACTION + ".do");
    }

	/**
	 * 纳觅财富登录
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
	@RequestMapping(value = ApiUserLoginDefine.NMCF_LOGIN)
    public ModelAndView nfcfLogin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute NmcfPostBean bean) {
		// 验证
		apiCommonService.checkNmcfPostBean(bean);
		// 验签
		String sign = bean.getInstCode() + bean.getUserId() + (bean.getBorrowNid()==null?"":bean.getBorrowNid()) + bean.getTimestamp() + bean.getInstCode();
		CheckUtil.check(ApiSignUtil.verifyByRSA(bean.getInstCode(), bean.getChkValue(), sign),
				MsgEnum.ERR_SIGN);
/*		// 解密
        String nmUserId = bean.getUserId();
        // 数字验证
        CheckUtil.check(Validator.isDigit(nmUserId), "Object.digit", "userId");*/
		Long nmUserId = apiCommonService.RSAdecrypt(bean);
        // 查询userid
		Integer userId = apiCommonService.getUserIdByBind(nmUserId, Integer.valueOf(bean.getInstCode()));

		// userid不存在,跳转登录页面
		if(userId == null) {
            return new ModelAndView("redirect:" + CustomConstants.HOST
                    + LoginDefine.CONTROLLOR_REQUEST_MAPPING + LoginDefine.INIT + ".do");
        }

		// 登陆
		WebViewUser webUser = loginService.getWebViewUserByUserId(userId);
		WebUtils.sessionLogin(request, response, webUser);

		// 先跳转纳觅传过来的url
		if (bean.getRetUrl() != null) {
			return new ModelAndView("redirect:" + bean.getRetUrl());
		} else {
			// 如果纳觅没传url,有borrowNid,跳标的详情,无borowNid,跳个人中心
			if (bean.getBorrowNid() == null) {
				return new ModelAndView("redirect:" + CustomConstants.HOST
						+ PandectDefine.REQUEST_MAPPING + PandectDefine.PANDECT_ACTION + ".do");
			} else {
				return new ModelAndView("redirect:" + CustomConstants.HOST
						+ BorrowDefine.REQUEST_MAPPING + BorrowDefine.BORROW_DETAIL_ACTION + ".do?borrowNid=" + bean.getBorrowNid());
			}
		}
	}

    /**
     * 第三方退出登录
     * 
     * @param modelAndView
     * @param request
     * @return
     *//*
    @ResponseBody
    @RequestMapping(value = ApiUserLoginDefine.LOGOUT_THREEAPI)
    public ResultBean<?> threeHjsLoginout(HttpServletRequest request,HttpServletResponse response,@RequestBody ApiUserPostBean bean) {
        // 验证
        ApiCommonService.checkPostBean(bean);
        // 验签
        ApiCommonService.checkSign(bean);

        //判断当前登录用户
        boolean isLgin = loginService.checkLoginUser(request,response);
        if(!isLgin){
            return new ResultBean<>();
        }else{
            String sessionId = WebUtils.getCookie(request, CustomConstants.SESSIONID);
            loginService.removeCookie(request, response, sessionId);
            
        }
        // 返回
        return new ResultBean<>();
    }*/
}
