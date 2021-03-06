package com.hyjf.wechat.controller.user.auth.invesauth;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.bank.service.user.auth.AuthBean;
import com.hyjf.bank.service.user.auth.AuthService;
import com.hyjf.common.log.LogUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.PropUtils;
import com.hyjf.mybatis.model.auto.BankOpenAccount;
import com.hyjf.mybatis.model.auto.HjhUserAuth;
import com.hyjf.mybatis.model.auto.Users;
import com.hyjf.mybatis.model.auto.UsersInfo;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.wechat.annotation.SignValidate;
import com.hyjf.wechat.base.BaseController;
import com.hyjf.wechat.base.BaseMapBean;
import com.hyjf.wechat.controller.user.paymentauthpage.PaymentAuthPagDefine;
import com.hyjf.wechat.util.ResultEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自动出借授权
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = InvesAuthPagePlusDefine.REQUEST_MAPPING)
public class InvesAuthPagePlusController extends BaseController {

	Logger _log = LoggerFactory.getLogger(InvesAuthPagePlusController.class);

	@Autowired
	private AuthService authService;

	/**
     * 当前controller名称
     */
    public static final String THIS_CLASS = InvesAuthPagePlusController.class.getName();

	/**
	 * 
	 * 自动出借授权
	 * 
	 * @author pcc
	 * @param request
	 * @param response
	 * @return
	 */
    @SignValidate
	@RequestMapping(InvesAuthPagePlusDefine.INVES_AUTH_ACTION)
	public ModelAndView page(HttpServletRequest request,
			HttpServletResponse response) {

		LogUtil.startLog(InvesAuthPagePlusDefine.THIS_CLASS,
				InvesAuthPagePlusDefine.INVES_AUTH_ACTION);
		String sign = request.getParameter("sign");
		ModelAndView modelAndView = new ModelAndView();
        // 获取sign缓存

        //判断用户是否登录
        Integer userId = requestUtil.getRequestUserId(request);
        Users users = authService.getUsers(userId);
        
        if(userId==null||userId<=0){
        	_log.error("用户未开户...userid:["+users.getUserId()+"]");
            return getErrorModelAndView(ResultEnum.NOTLOGIN, sign,"0", null);
        }
        HjhUserAuth hjhUserAuth = authService.getHjhUserAuthByUserId(userId);
        BankOpenAccount account = this.authService.getBankOpenAccount(userId);
        if (account == null) {
        	_log.error("用户未开户...userid:["+users.getUserId()+"]");
            return getErrorModelAndView(ResultEnum.USER_ERROR_200, sign,"0", hjhUserAuth);
        }
        Users user = this.authService.getUsers(userId);
        if (user.getIsSetPassword() == 0) {// 未设置交易密码
        	_log.error("用户未设置交易密码...userid:["+users.getUserId()+"]");
            return getErrorModelAndView(ResultEnum.USER_ERROR_201, sign,"0", hjhUserAuth);
        }
        // 判断是否授权过  
        if(authService.checkIsAuth(user.getUserId(),AuthBean.AUTH_TYPE_AUTO_BID)){
        	ModelAndView mv = getErrorModelAndView(ResultEnum.USER_ERROR_201, sign,"0", hjhUserAuth);
        	return mv;
        }

		// 拼装参数 调用江西银行
		// 同步调用路径
		String retUrl = PropUtils.getSystem(CustomConstants.HYJF_WEB_URL)+ request.getContextPath()
				+ InvesAuthPagePlusDefine.REQUEST_MAPPING
				+ InvesAuthPagePlusDefine.RETURL_SYN_ACTION + ".page?authType="+AuthBean.AUTH_TYPE_AUTO_BID+"&sign="+sign;
		// 异步调用路
		String bgRetUrl = PropUtils.getSystem(CustomConstants.HYJF_WEB_URL)+ request.getContextPath()
				+ InvesAuthPagePlusDefine.REQUEST_MAPPING
				+ InvesAuthPagePlusDefine.RETURL_ASY_ACTION + ".do";

		UsersInfo usersInfo =authService.getUsersInfoByUserId(user.getUserId());
		
		// 用户ID
		AuthBean authBean = new AuthBean();
		authBean.setUserId(user.getUserId());
		authBean.setIp(CustomUtil.getIpAddr(request));
		authBean.setAccountId(account.getAccount());
		// 同步 异步
		authBean.setRetUrl(retUrl);
		authBean.setNotifyUrl(bgRetUrl);
		// 0：PC 1：微官网 2：Android 3：iOS 4：其他
		authBean.setPlatform(CustomConstants.CLIENT_WECHAT);
		authBean.setAuthType(AuthBean.AUTH_TYPE_AUTO_BID);
		authBean.setChannel(BankCallConstant.CHANNEL_WEI);
		authBean.setForgotPwdUrl(CustomConstants.FORGET_PASSWORD_URL + "?sign=" + sign);
		authBean.setName(usersInfo.getTruename());
		authBean.setIdNo(usersInfo.getIdcard());
		authBean.setIdentity(usersInfo.getRoleId()+"");
		authBean.setUserType(users.getUserType());
		// 跳转到汇付天下画面
		try {
			String orderId = GetOrderIdUtils.getOrderId2(authBean.getUserId());
			authBean.setOrderId(orderId);
			modelAndView = authService.getCallbankMV(authBean);
			authService.insertUserAuthLog(authBean.getUserId(), orderId,
					Integer.parseInt(authBean.getPlatform()), "1");
			LogUtil.endLog(InvesAuthPagePlusController.class.toString(),
					InvesAuthPagePlusDefine.INVES_AUTH_ACTION);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.errorLog(InvesAuthPagePlusController.class.toString(),
					InvesAuthPagePlusDefine.INVES_AUTH_ACTION, e);
			return getErrorModelAndView(ResultEnum.USER_ERROR_204, sign,"0", hjhUserAuth);
		}

		return modelAndView;
	}
	
	/**
     * 组装跳转错误页面MV
     * @param param
     * @param sign
     * @param type
     * @param hjhUserAuth
     * @return
     */
    private ModelAndView getErrorModelAndView(ResultEnum param, String sign,String type, HjhUserAuth hjhUserAuth) {
        ModelAndView modelAndView = new ModelAndView(InvesAuthPagePlusDefine.JUMP_HTML);
        BaseMapBean baseMapBean = new BaseMapBean();
        baseMapBean.set(CustomConstants.APP_STATUS, param.getStatus());
        baseMapBean.set(CustomConstants.APP_STATUS_DESC, param.getStatusDesc());
        baseMapBean.set("authType", InvesAuthPagePlusDefine.AUTH_TYPE);
        
        baseMapBean.set(CustomConstants.APP_SIGN, sign);
        baseMapBean.set("autoInvesStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoInvesStatus()==null?"0":hjhUserAuth.getAutoInvesStatus()+ "");
        baseMapBean.set("autoCreditStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoCreditStatus()==null?"0":hjhUserAuth.getAutoCreditStatus() + "");
        baseMapBean.set("userAutoType", type);
		baseMapBean.set("sign", sign);
        if(ResultEnum.USER_ERROR_219.getStatus().equals(param.getStatus())){
        	baseMapBean.setCallBackAction(CustomConstants.HOST + InvesAuthPagePlusDefine.AUTH_TENDER_AGAIN_ERROR_PATH);
        }else{
        	baseMapBean.setCallBackAction(CustomConstants.HOST + InvesAuthPagePlusDefine.USER_AUTH_ERROR_PATH);
        }
        modelAndView.addObject("callBackForm", baseMapBean);
        return modelAndView;
    }
    
    /**
     * 组装跳转成功页面MV
     * @param sign
     * @param type
     * @return
     */
    private ModelAndView getSuccessModelAndView(String sign, String type, Integer userId) {
    	HjhUserAuth hjhUserAuth = authService.getHjhUserAuthByUserId(userId);
    	ModelAndView modelAndView = new ModelAndView(InvesAuthPagePlusDefine.JUMP_HTML);
        BaseMapBean baseMapBean = new BaseMapBean();
        baseMapBean.set(CustomConstants.APP_STATUS, ResultEnum.SUCCESS.getStatus());
        baseMapBean.set(CustomConstants.APP_STATUS_DESC, ResultEnum.SUCCESS.getStatusDesc());
        baseMapBean.set("authType", InvesAuthPagePlusDefine.AUTH_TYPE);
        baseMapBean.set(CustomConstants.APP_SIGN, sign);
        baseMapBean.set("autoInvesStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoInvesStatus()==null?"0":hjhUserAuth.getAutoInvesStatus()+ "");
        baseMapBean.set("autoCreditStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoCreditStatus()==null?"0":hjhUserAuth.getAutoCreditStatus() + "");
        baseMapBean.set("userAutoType", type);
         
        baseMapBean.setCallBackAction(CustomConstants.HOST + InvesAuthPagePlusDefine.USER_AUTH_SUCCESS_PATH);
        modelAndView.addObject("callBackForm", baseMapBean);
        return modelAndView;
    }

	/**
	 * 
	 * 自动出借授权
	 * 
	 * @author pcc
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
	@RequestMapping(InvesAuthPagePlusDefine.RETURL_SYN_ACTION)
	public ModelAndView invesAuthReturn(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute BankCallBean bean) {

		LogUtil.startLog(THIS_CLASS, PaymentAuthPagDefine.RETURL_SYN_ACTION, "[自动出借授权同步回调开始]");
        bean.convert();
        String sign = request.getParameter("sign");
        Integer userId = requestUtil.getRequestUserId(request);
        HjhUserAuth hjhUserAuth = authService.getHjhUserAuthByUserId(userId);
        String frontParams = request.getParameter("frontParams");
        if(StringUtils.isBlank(bean.getRetCode())&&StringUtils.isNotBlank(frontParams)){
            JSONObject jsonParm = JSONObject.parseObject(frontParams);
            if(jsonParm.containsKey("RETCODE")){
                bean.setRetCode(jsonParm.getString("RETCODE"));
            }
        }
        String retCode = bean.getRetCode();
        if(StringUtils.isNotBlank(retCode)&& !BankCallStatusConstant.RESPCODE_SUCCESS.equals(retCode)){
        	
        	return getErrorModelAndView(ResultEnum.USER_ERROR_204, sign, "0", hjhUserAuth);
        }
        String isSuccess = request.getParameter("isSuccess");
        if (!"1".equals(isSuccess)) {
        	return getErrorModelAndView(ResultEnum.USER_ERROR_204, sign, "0", hjhUserAuth);
        }
        // 出借人签约状态查询
        logger.info("自动出借授权同步回调调用查询接口查询状态");
        BankCallBean retBean=authService.getTermsAuthQuery(Integer.parseInt(bean.getLogUserId()),BankCallConstant.CHANNEL_WEI);
        
//        bean=retBean;
        // 返回失败
        if (retBean!=null&& BankCallStatusConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode())) {
            logger.info("自动出借授权同步回调调用查询接口查询状态结束  结果为:" + retBean.getRetCode() + "授权状态为：" +retBean.getPaymentAuth());
            // 成功
            if("1".equals(retBean.getPaymentAuth())){
            	try {
            		if(authService.checkDefaultConfig(retBean,AuthBean.AUTH_TYPE_AUTO_BID)){
                        return getErrorModelAndView(ResultEnum.USER_ERROR_219, sign, "0", hjhUserAuth); 
                    }
    				retBean.setOrderId(bean.getLogOrderId());
    				// 更新签约状态和日志表
    				this.authService.updateUserAuth(
    						Integer.parseInt(bean.getLogUserId()), retBean,AuthBean.AUTH_TYPE_AUTO_BID);
    			} catch (Exception e) {
    				e.printStackTrace();
    				LogUtil.errorLog(InvesAuthPagePlusDefine.THIS_CLASS,
    						InvesAuthPagePlusDefine.RETURL_ASY_ACTION, e);
    			}
            	
            	return getSuccessModelAndView(sign, "0", userId); 
            }else{
            	return getErrorModelAndView(ResultEnum.USER_ERROR_204, sign, "0", hjhUserAuth); 
            }
        } else {
            logger.info("自动出借授权同步回调调用查询接口查询失败");
            return getErrorModelAndView(ResultEnum.USER_ERROR_204, sign, "0", hjhUserAuth); 
        }
	}

	/**
	 * 自动出借授权异步回调
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(InvesAuthPagePlusDefine.RETURL_ASY_ACTION)
	public BankCallResult invesAuthBgreturn(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute BankCallBean bean) {
		BankCallResult result = new BankCallResult();
		_log.info("[自动出借授权异步回调开始]");
		LogUtil.startLog(InvesAuthPagePlusDefine.THIS_CLASS,
				InvesAuthPagePlusDefine.RETURL_ASY_ACTION, "[自动出借授权异步回调开始]");
		bean.convert();
		Integer userId = Integer.parseInt(bean.getLogUserId()); // 用户ID
		// 查询用户开户状态
		Users user = this.authService.getUsers(userId);
		if(authService.checkDefaultConfig(bean,AuthBean.AUTH_TYPE_AUTO_BID)){
	       	 _log.info("[用户自动出借授权完成后,回调结束]");
	         result.setMessage("授权成功");
	         result.setStatus(true);
	         return result;
	    }
		// 成功
		if (user != null
				&& bean != null
				&& (BankCallConstant.RESPCODE_SUCCESS.equals(bean
						.get(BankCallConstant.PARAM_RETCODE)) && "1"
						.equals(bean.getPaymentAuth()))) {
			try {
				bean.setOrderId(bean.getLogOrderId());
				// 更新签约状态和日志表
				this.authService.updateUserAuth(
						Integer.parseInt(bean.getLogUserId()), bean,AuthBean.AUTH_TYPE_AUTO_BID);
			} catch (Exception e) {
				e.printStackTrace();
				LogUtil.errorLog(InvesAuthPagePlusDefine.THIS_CLASS,
						InvesAuthPagePlusDefine.RETURL_ASY_ACTION, e);
			}
		}
		LogUtil.endLog(InvesAuthPagePlusDefine.THIS_CLASS,
				InvesAuthPagePlusDefine.RETURL_ASY_ACTION,
				"[用户自动出借授权完成后,回调结束]");
		_log.info("[用户自动出借授权完成后,回调结束]");
		result.setMessage("自动出借授权成功");
		result.setStatus(true);
		return result;
	}
}
