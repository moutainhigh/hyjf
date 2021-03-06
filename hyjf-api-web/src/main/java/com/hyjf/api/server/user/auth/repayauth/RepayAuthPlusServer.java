package com.hyjf.api.server.user.auth.repayauth;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.api.server.user.auth.paymentauth.PaymentAuthPagePlusDefine;
import com.hyjf.api.server.user.auth.paymentauth.PaymentAuthPagePlusServer;
import com.hyjf.api.server.user.autoup.AutoPlusRetBean;
import com.hyjf.api.server.util.ErrorCodeConstant;
import com.hyjf.api.web.BaseController;
import com.hyjf.bank.service.user.auth.AuthBean;
import com.hyjf.bank.service.user.auth.AuthService;
import com.hyjf.base.bean.BaseDefine;
import com.hyjf.base.bean.BaseResultBean;
import com.hyjf.common.log.LogUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.PropUtils;
import com.hyjf.mybatis.model.auto.BankOpenAccount;
import com.hyjf.mybatis.model.auto.Users;
import com.hyjf.mybatis.model.auto.UsersInfo;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import com.hyjf.vip.apply.ApplyDefine;

@Controller(RepayAuthPlusDefine.CONTROLLER_NAME)
@RequestMapping(value = RepayAuthPlusDefine.REQUEST_MAPPING)
public class RepayAuthPlusServer extends BaseController {
    
    Logger _log = LoggerFactory.getLogger("AutoPlusServer ");

    
    @Autowired
    private AuthService authService;
    /**
     * 
     * 还款授权
     * @author sss
     * @param request
     * @param response
     * @param payRequestBean
     * @return
     */
    @RequestMapping(value = RepayAuthPlusDefine.USER_REPAY_AUTH_ACTION)
    @ResponseBody
    public ModelAndView repayAuth(HttpServletRequest request, HttpServletResponse response, @RequestBody RepayAuthPlusRequestBean payRequestBean){
        ModelAndView modelAndView = new ModelAndView(RepayAuthPlusDefine.PATH_TRUSTEE_PAY_ERROR);
        _log.info("还款授权请求参数" + JSONObject.toJSONString(payRequestBean, true) + "]");
        // 检查参数是否为空
        if(payRequestBean.checkParmIsNull(modelAndView)){
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000001);
            payRequestBean.doNotify(payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000001, "请求参数异常"));
            _log.info("请求参数异常" + JSONObject.toJSONString(payRequestBean, true) + "]");
            return modelAndView;
        }
        
        // 验签  暂时去掉验签
       if (!this.verifyRequestSign(payRequestBean, BaseDefine.METHOD_REPAY_AUTH_PLUS)) {
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000002);
            payRequestBean.doNotify(payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000002, "验签失败"));
            _log.info("请求参数异常" + JSONObject.toJSONString(payRequestBean, true) + "]");
            return modelAndView;
        }
        
       // 根据电子账户号查询用户ID
       BankOpenAccount bankOpenAccount = this.authService.getBankOpenAccount(payRequestBean.getAccountId());
        if(bankOpenAccount == null){
            _log.info("-------------------没有根据电子银行卡找到用户"+payRequestBean.getAccountId()+"！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000004,"没有根据电子银行卡找到用户");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000004);
            return modelAndView;
        }
        
        // 检查用户是否存在
        Users user = authService.getUsers(bankOpenAccount.getUserId());//用户ID
        if (user == null) {
            _log.info("-------------------用户不存在汇盈金服账户！"+payRequestBean.getAccountId()+"！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000007,"用户不存在汇盈金服账户！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000007);
            return modelAndView;
        }
        
        Integer userId = user.getUserId();
        if (user.getBankOpenAccount().intValue() != 1) {// 未开户
            _log.info("-------------------用户未开户！"+payRequestBean.getAccountId()+"！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000006,"用户未开户！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000006);
            return modelAndView;
        }
        
        UsersInfo usersInfo=authService.getUsersInfoByUserId(userId);
        if (usersInfo.getRoleId() == 1) {// 未开户
            _log.info("-------------------用户为出借人角色无法授权！"+payRequestBean.getAccountId()+"！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000010,"用户为出借账户！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000010);
            return modelAndView;
        }
        
        // 检查是否设置交易密码
        Integer passwordFlag = user.getIsSetPassword();
        if (passwordFlag != 1) {// 未设置交易密码
            _log.info("-------------------未设置交易密码！"+payRequestBean.getAccountId()+"！--------------------status"+user.getIsSetPassword());
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_TP000002,"未设置交易密码！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_TP000002);
            return modelAndView;
        }
        
        // 查询是否已经授权过
        if(authService.checkIsAuth(user.getUserId(),AuthBean.AUTH_TYPE_REPAY_AUTH)){
            _log.info("-------------------已经授权过！"+payRequestBean.getAccountId());
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000009,"已授权,请勿重复授权！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000009);
            return modelAndView;
        }
        
        // 同步调用路径
        String retUrl = PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) + request.getContextPath()
                + RepayAuthPlusDefine.REQUEST_MAPPING + RepayAuthPlusDefine.RETURL_SYN_ACTION + ".do?acqRes="
                + payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getRetUrl().replace("#", "*-*-*");
        // 异步调用路
        String bgRetUrl = PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) + request.getContextPath()
                + RepayAuthPlusDefine.REQUEST_MAPPING + RepayAuthPlusDefine.RETURL_ASY_ACTION + ".do?acqRes="
                + payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getNotifyUrl().replace("#", "*-*-*");
        try {
			// 用户ID
			AuthBean authBean = new AuthBean();
			authBean.setUserId(user.getUserId());
			authBean.setIp(CustomUtil.getIpAddr(request));
			authBean.setAccountId(bankOpenAccount.getAccount());
			// 同步 异步
			authBean.setRetUrl(retUrl);
			authBean.setNotifyUrl(bgRetUrl);
			// 0：PC 1：微官网 2：Android 3：iOS 4：其他
			authBean.setPlatform(payRequestBean.getPlatform());
			authBean.setAuthType(AuthBean.AUTH_TYPE_PAYMENT_AUTH);
			authBean.setChannel(BankCallConstant.CHANNEL_PC);
			authBean.setForgotPwdUrl(CustomConstants.FORGET_PASSWORD_URL);
			authBean.setName(usersInfo.getTruename());
			authBean.setIdNo(usersInfo.getIdcard());
			authBean.setIdentity(usersInfo.getRoleId()+"");
			authBean.setUserType(user.getUserType());
		// 跳转到汇付天下画面
			String orderId = GetOrderIdUtils.getOrderId2(authBean.getUserId());
			authBean.setOrderId(orderId);
			modelAndView = authService.getCallbankMV(authBean);
			authService.insertUserAuthLog(authBean.getUserId(), orderId,
					Integer.parseInt(authBean.getPlatform()), "5");
			LogUtil.endLog(PaymentAuthPagePlusServer.class.toString(),
					PaymentAuthPagePlusDefine.PAYMENT_AUTH_ACTION);
		} catch (Exception e) {
	        e.printStackTrace();
	        _log.info("缴费授权页面异常,异常信息:[" + e.toString() + "]");
	        return null;
	    }

        return modelAndView;
    }
    
    private ModelAndView getErrorMV(RepayAuthPlusRequestBean payRequestBean, ModelAndView modelAndView, String status) {
        AutoPlusRetBean repwdResult = new AutoPlusRetBean();
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        repwdResult.setCallBackAction(payRequestBean.getRetUrl());
        repwdResult.set("chkValue", resultBean.getChkValue());
        repwdResult.set("status", resultBean.getStatus());
        repwdResult.setAcqRes(payRequestBean.getAcqRes());
        modelAndView.addObject("callBackForm", repwdResult);
        return modelAndView;
    }
    
    // 还款授权同步回调
    @RequestMapping(RepayAuthPlusDefine.RETURL_SYN_ACTION)
    public ModelAndView repayAuthReturn(HttpServletRequest request, HttpServletResponse response,
         BankCallBean bean) {
        _log.info("还款授权同步回调start,请求参数为：【"+JSONObject.toJSONString(bean, true)+"】");
        ModelAndView modelAndView = new ModelAndView(ApplyDefine.CALL_BACK_TRUSTEEPAY_VIEW);
        AutoPlusRetBean repwdResult = new AutoPlusRetBean();
        repwdResult.setCallBackAction(request.getParameter("callback").replace("*-*-*","#"));
        bean.convert();
        repwdResult.set("accountId", bean.getAccountId());
        //出借人签约状态查询
        _log.info("还款授权同步回调调用查询接口查询状态");
        BankCallBean retBean=authService.getTermsAuthQuery(Integer.parseInt(bean.getLogUserId()), BankCallConstant.CHANNEL_PC);
        _log.info("还款授权同步回调调用查询接口查询状态结束  结果为:"+(retBean==null?"空":retBean.getRetCode()));
        if(authService.checkDefaultConfig(retBean,AuthBean.AUTH_TYPE_REPAY_AUTH)){
			// 失败
            modelAndView.addObject("statusDesc", "还款授权申请失败,失败原因：授权期限过短或额度过低，<br>请重新授权！");
            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
            modelAndView.addObject("callBackForm", repwdResult);
			return modelAndView;
        }
        if (retBean!=null&& BankCallStatusConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode()) && "1".equals(retBean.getRepayAuth())) {
            try {
                modelAndView.addObject("statusDesc", "还款授权申请成功！");
                retBean.setOrderId(bean.getLogOrderId());
                this.authService.updateUserAuth(Integer.parseInt(bean.getLogUserId()),retBean,AuthBean.AUTH_TYPE_REPAY_AUTH);
                // 成功
                modelAndView.addObject("statusDesc", "还款授权申请成功！");
                BaseResultBean resultBean = new BaseResultBean();
                resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
                repwdResult.set("chkValue", resultBean.getChkValue());
                repwdResult.set("status", resultBean.getStatus());
                repwdResult.set("deadline", bean.getDeadline());
                
            } catch (Exception e) {
                _log.info("还款授权申请同步插入数据库失败，错误原因:"+e.getMessage());
                BaseResultBean resultBean = new BaseResultBean();
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
                repwdResult.set("chkValue", resultBean.getChkValue());
                repwdResult.set("status", resultBean.getStatus());
            }
        } else {
            // 失败
            modelAndView.addObject("statusDesc", "还款授权申请失败,失败原因：" + authService.getBankRetMsg(bean.getRetCode()));
            
            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
        }
        
        //------------------------------------------------
        repwdResult.setAcqRes(request.getParameter("acqRes"));
        modelAndView.addObject("callBackForm", repwdResult);
        _log.info("还款授权申请同步回调end");
        return modelAndView;
    }
    

    // 异步回调
    @ResponseBody
    @RequestMapping(RepayAuthPlusDefine.RETURL_ASY_ACTION)
    public BankCallResult repayAuthBgreturn(HttpServletRequest request, HttpServletResponse response,
                                                @ModelAttribute BankCallBean bean) {
        _log.info("还款授权申请异步回调start");
        BankCallResult result = new BankCallResult();
        BaseResultBean resultBean = new BaseResultBean();
        
        
        String message = "";
        String status = "";
        Map<String, String> params = new HashMap<String, String>();
        
        if (bean == null) {
            _log.info("调用江西银行还款授权接口,银行异步返回空");
            params.put("status", BaseResultBean.STATUS_FAIL);
            resultBean.setStatusForResponse(BaseResultBean.STATUS_FAIL);
            params.put("statusDesc", "还款授权失败,调用银行接口失败");
            result.setMessage("还款授权失败");
            params.put("chkValue", resultBean.getChkValue());
            params.put("acqRes", request.getParameter("acqRes"));
            result.setStatus(false);
            CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*", "#"), params);
            return result;
        }
        // 返回值修改 end
        bean.convert();
        int userId = Integer.parseInt(bean.getLogUserId());
        Users user = this.authService.getUsers(userId);
        if(authService.checkDefaultConfig(bean,AuthBean.AUTH_TYPE_REPAY_AUTH)){
        	 _log.info("还款授权异步回调end");
            params.put("status", BaseResultBean.STATUS_FAIL);
            resultBean.setStatusForResponse(BaseResultBean.STATUS_FAIL);
            params.put("statusDesc", "还款授权失败,调用银行接口失败");
            result.setMessage("授权成功");
            params.put("chkValue", resultBean.getChkValue());
            params.put("acqRes", request.getParameter("acqRes"));
            result.setStatus(true);
            CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*", "#"), params);
            return result;
 	    }
        if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))&& "1"
				.equals(bean.getRepayAuth())) {
            try {
                bean.setOrderId(bean.getLogOrderId());
                // 更新签约状态和日志表
                this.authService.updateUserAuth(userId,bean,AuthBean.AUTH_TYPE_REPAY_AUTH);
                message = "还款授权成功";
                status = ErrorCodeConstant.SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                _log.info("还款授权出错,userId:【"+userId+"】错误原因："+e.getMessage());
                message = "还款授权失败";
                status = ErrorCodeConstant.STATUS_CE999999;
            }
            
        }else{
         // 失败
            message = "还款授权失败,失败原因：" + authService.getBankRetMsg(bean.getRetCode());
            status = ErrorCodeConstant.STATUS_CE999999;
        }
        // 返回值
        params.put("accountId", bean.getAccountId());
        params.put("status", status);
        params.put("statusDesc",message);
        params.put("deadline", bean.getDeadline());
        resultBean.setStatusForResponse(status);
        params.put("chkValue", resultBean.getChkValue());
        params.put("acqRes",request.getParameter("acqRes"));
        CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*","#"), params);
        _log.info("还款授权异步回调end");
        result.setMessage("还款授权成功");
        result.setStatus(true);
        return result;
    }
    
}
