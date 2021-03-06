/**
 * Description:汇付天下认证用类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: GOGTZ-T
 * @version: 1.0
 * Created at: 2015年11月23日 上午11:48:46
 * Modification History:
 * Modified by :
 */
package com.hyjf.pay.lib.chinapnr.util;

import java.io.Serializable;

import com.hyjf.common.log.LogUtil;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.chinapnr.ChinaPnrApiImpl;

import chinapnr.SecureLink;

public class ChinaPnrSignUtils implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 3640874934537168392L;

    /** THIS_CLASS */
    private static final String THIS_CLASS = ChinaPnrApiImpl.class.getName();

    /** MD5签名类型 **/
    public static final String SIGN_TYPE_MD5 = "M";

    /** RSA签名类型 **/
    public static final String SIGN_TYPE_RSA = "R";

    /** RSA验证签名成功结果 **/
    public static final int RAS_VERIFY_SIGN_SUCCESS = 0;

    /** 商户客户号 **/
    public static final String RECV_MER_ID = ChinaPnrPropUtils.getSystem(ChinaPnrConstant.PROP_MERID);

    /** 商户公钥文件地址 **/
    public static final String MER_PUB_KEY_PATH = ChinaPnrPropUtils.getSystem(ChinaPnrConstant.PROP_MER_PUB_KEY_PATH);

    /** 商户私钥文件地址 **/
    public static final String MER_PRI_KEY_PATH = ChinaPnrPropUtils.getSystem(ChinaPnrConstant.PROP_MER_PRI_KEY_PATH);

    /**
     * RSA方式加签
     *
     * @param custId
     * @param forEncryptionStr
     * @param charset
     * @return
     * @throws Exception
     */
    public static String encryptByRSA(String forEncryptionStr) throws Exception {
        String methodName = "encryptByRSA";
        LogUtil.startLog(THIS_CLASS, methodName, "加签处理开始");
        if (Validator.isNull(forEncryptionStr)) {
            throw new Exception("加签内容不能为空!");
        }
        SecureLink sl = new SecureLink();
        LogUtil.debugLog(THIS_CLASS, methodName, "加签内容:" + forEncryptionStr);
        int result = sl.SignMsg(RECV_MER_ID, MER_PRI_KEY_PATH, forEncryptionStr.getBytes(StringPool.UTF8));
        if (result < 0) {
            // 打印日志
            throw new Exception("加签处理失败![result:" +result+"]");
        }
        LogUtil.endLog(THIS_CLASS, methodName, "加签处理结束");
        return sl.getChkValue();
    }

    /**
     * RSA方式验签
     *
     * @param forEncryptionStr
     * @param chkValue
     * @return
     * @throws Exception
     */
    public static boolean verifyByRSA(String forEncryptionStr, String chkValue) throws Exception {
        String methodName = "verifyByRSA";
        LogUtil.startLog(THIS_CLASS, methodName, "检证处理开始");

        LogUtil.debugLog(THIS_CLASS, methodName, "检证内容:" + forEncryptionStr);
        int verifySignResult = -1;
        SecureLink sl = new SecureLink();
        try {
            verifySignResult = sl.VeriSignMsg(MER_PUB_KEY_PATH, forEncryptionStr, chkValue);
//            System.out.println("***************被签名的数据体forEncryptionStr:\n"+forEncryptionStr);
//            System.out.println("***************需要验证的签名chkValue:\n"+chkValue);
//            System.out.println("***************验签结果:"+verifySignResult);
        } catch (Exception e) {
            LogUtil.errorLog(THIS_CLASS, methodName, e);
            // 打印日志
            throw new Exception("检证处理失败!");
        }
        LogUtil.endLog(THIS_CLASS, methodName, "检证处理结束");
        return verifySignResult == RAS_VERIFY_SIGN_SUCCESS;
    }
}
