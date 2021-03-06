/**
 * Description:汇盈金服调用银行存管接口
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 *
 * @author: wangkun
 * @version: 1.0
 * Created at: 2015年11月23日 下午4:26:10
 * Modification History:
 * Modified by :
 */
package com.hyjf.pay.lib.bank.call.impl;

import com.hyjf.common.http.HttpDealBank;
import com.hyjf.common.log.LogUtil;
import com.hyjf.common.util.PropUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallPnrApiBean;
import com.hyjf.pay.lib.bank.call.BankCallApi;
import com.hyjf.pay.lib.bank.util.*;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BankCallApiImpl implements BankCallApi {

    /**
     * THIS_CLASS
     */
    private static final String THIS_CLASS = BankCallApiImpl.class.getName();

    /**
     * 版本号
     */
    private String _version;

    /**
     * 银行编码
     */
    private String _bankcode;

    /**
     * 平台机构代码
     */
    private String _instCode;

    /**
     * 交易渠道
     */
    private String _coinstChannel;

    public BankCallApiImpl() {

        // 银行代码
        if (Validator.isNull(_bankcode)) {
            _bankcode = PropUtils.getSystem(BankCallConstant.BANK_BANKCODE);
        }
        // 交易渠道
        if (Validator.isNull(_coinstChannel)) {
            _coinstChannel = PropUtils.getSystem(BankCallConstant.BANK_COINST_CHANNEL);
        }
        // 接口默认版本号
        if (Validator.isNull(_version)) {
            _version = PropUtils.getSystem(BankCallConstant.BANK_VERSION);
        }
        // 平台机构代码
        if (Validator.isNull(_instCode)) {
            _instCode = PropUtils.getSystem(BankCallConstant.BANK_INSTCODE);
        }
    }

    /**
     * 调用汇付天下API接口
     *
     * @param bean
     * @return
     */
    @Override
    public String callChinaPnrApi(BankCallPnrApiBean bean) {

        // 方法名
        String methodName = "callChinaPnrApi";
        LogUtil.startLog(THIS_CLASS, methodName, "[调用汇付天下API接口开始]");
        LogUtil.debugLog(THIS_CLASS, methodName, "参数: " + bean == null ? "无" : bean.getAllParams() + "]");
        String result = null;
        // 未签名时,进行签名
        if (Validator.isNull(bean.get(BankCallConstant.PARAM_SIGN))) {
            bean.set(BankCallConstant.PARAM_SIGN, this.generateSignCall(bean));
        }
        // 签名为空时,返回空字符串
        if (Validator.isNull(bean.get(BankCallConstant.PARAM_SIGN))) {
            return result;
        }
        try {
            // 发送请求
            String HTTP_URL = PropUtils.getSystem(BankCallConstant.BANK_ONLINE_URL);
            result = HttpDealBank.post(HTTP_URL, bean.getAllParams());
            LogUtil.debugLog(THIS_CLASS, methodName, "[返回结果:" + result + "]");
        } catch (Exception e) {
            LogUtil.errorLog(THIS_CLASS, methodName, e);
        }
        LogUtil.endLog(THIS_CLASS, methodName, "[调用汇付天下API接口结束]");
        return result;
    }

    @Override
    public BankCallBean verifyChinaPnr(BankCallBean bean) {
        // 方法名
        String methodName = "verifyChinaPnr";
        LogUtil.startLog(THIS_CLASS, methodName, "[验证银行存管签名开始]");
        LogUtil.debugLog(THIS_CLASS, methodName, "参数: " + bean == null ? "无" : bean.getAllParams() + "]");
        BankCallBean ret = new BankCallBean();
        String result = BankCallConstant.RECEIVED_FAIL;
        try {
            String checkValue = BankCallSignUtils_.mergeMap(bean.getAllParams());
            // RSA方式验签
            boolean res = BankCallSignUtils_.verify(bean.get(BankCallConstant.PARAM_SIGN), checkValue);
            ret.setLogVerifyFlag(res);
            if (res) {
                result = BankCallConstant.RECEIVED_SUCCESS;
                ret.setLogVerifyResult(result);
            }
            LogUtil.debugLog(THIS_CLASS, methodName, "[RSA方式验签结果:" + result + "]");
        } catch (Exception e) {
            LogUtil.errorLog(THIS_CLASS, methodName, e);
        }
        LogUtil.endLog(THIS_CLASS, methodName, "[验证汇付天下签名结束]");
        return ret;
    }

    @Override
    public BankCallBean verifyChinaPnr(Map<String, String> mapParam) {
        // 方法名
        String methodName = "verifyChinaPnr";
        LogUtil.startLog(THIS_CLASS, methodName, "[验证银行存管签名开始]");
        LogUtil.debugLog(THIS_CLASS, methodName, "参数: " + mapParam == null ? "无" : mapParam + "]");
        BankCallBean ret = new BankCallBean();
        String result = BankCallConstant.RECEIVED_FAIL;
        try {
            String checkValue = BankCallSignUtils_.mergeMap(mapParam);
            // RSA方式验签
            boolean res = BankCallSignUtils_.verify(String.valueOf(mapParam.get(BankCallConstant.PARAM_SIGN)), checkValue);
            ret.setLogVerifyFlag(res);
            if (res) {
                result = BankCallConstant.RECEIVED_SUCCESS;
                ret.setLogVerifyResult(result);
            }
            LogUtil.debugLog(THIS_CLASS, methodName, "[RSA方式验签结果:" + result + "]");
        } catch (Exception e) {
            LogUtil.errorLog(THIS_CLASS, methodName, e);
        }
        LogUtil.endLog(THIS_CLASS, methodName, "[验证汇付天下签名结束]");
        return ret;
    }

    private String generateSignCall(BankCallPnrApiBean bean) {
        String mergedStr = BankCallSignUtils_.mergeMap(bean.getAllParams());
        return BankCallSignUtils_.sign(mergedStr);
    }


    /**
     * 设置共通项目(版本号,商户客户号)
     *
     * @param bean
     */
    private void setCommonItems(BankCallPnrApiBean bean) {
        // 版本号
        if (!bean.getAllParams().containsKey(BankCallConstant.PARAM_VERSION)) {
            bean.set(BankCallConstant.PARAM_VERSION, _version);
        }
        // 银行代码
        if (!bean.getAllParams().containsKey(BankCallConstant.PARAM_BANKCODE)) {
            bean.set(BankCallConstant.PARAM_BANKCODE, _bankcode);
        }
        // 平台机构代码
        if (!bean.getAllParams().containsKey(BankCallConstant.PARAM_INSTCODE)) {
            bean.set(BankCallConstant.PARAM_INSTCODE, _instCode);
        }
        // 交易渠道
        if (!bean.getAllParams().containsKey(BankCallConstant.PARAM_CHANNEL)) {
            bean.set(BankCallConstant.PARAM_CHANNEL, _coinstChannel);
        }
    }

    /**
     * 用户开户
     *
     * @param bean
     * @return
     */
    @Override
    public void rebuildBean(BankCallBean bean) {
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 加签
        String sign = generateSignCall(bean);
        bean.set(BankCallConstant.PARAM_SIGN, sign);
    }

}
