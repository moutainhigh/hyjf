package com.hyjf.bank.service.user.auth;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.bank.service.BaseServiceImpl;
import com.hyjf.bank.service.sensorsdata.SensorsDataBean;
import com.hyjf.common.util.*;
import com.hyjf.common.util.HjhUserAuthConfig;
import com.hyjf.mybatis.model.auto.*;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl extends BaseServiceImpl implements AuthService {
	Logger _log = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	@Qualifier("myAmqpTemplate")
	private RabbitTemplate rabbitTemplate;
    /**
     * 
     * 根据用户id更新用户签约授权信息
     * @param userId
     * @param retBean
     * @param authType
     */
    @Override
    public void updateUserAuth(Integer userId,BankCallBean retBean, String authType) {
    	HjhUserAuth hjhUserAuth = this.getHjhUserAuthByUserId(userId);
		Integer nowTime = GetDate.getNowTime10();
		String orderId = retBean.getOrderId();
		if (StringUtils.isNotBlank(orderId)) {
			HjhUserAuthLogExample example = new HjhUserAuthLogExample();
			example.createCriteria().andOrderIdEqualTo(orderId);
			List<HjhUserAuthLog> list = hjhUserAuthLogMapper.selectByExample(example);
			HjhUserAuthLog hjhUserAuthLog = null;
			// 更新用户签约授权日志表
			if (list != null && list.size() > 0) {
				hjhUserAuthLog = list.get(0);
				hjhUserAuthLog.setUpdateTime(nowTime);
				hjhUserAuthLog.setUpdateUser(userId);
				hjhUserAuthLog.setOrderStatus(1);
				hjhUserAuthLog.setAuthCreateTime(nowTime);
				hjhUserAuthLogMapper.updateByPrimaryKeySelective(hjhUserAuthLog);
			}
		}

        if (retBean != null && BankCallConstant.RESPCODE_SUCCESS.equals(retBean.get(BankCallConstant.PARAM_RETCODE))) {
            // 更新user表状态为授权成功
            Users user=this.getUsers(userId);
            if(StringUtils.isNotBlank(retBean.getPaymentAuth())){
            	user.setPaymentAuthStatus(Integer.parseInt(retBean.getPaymentAuth()));
                this.usersMapper.updateByPrimaryKeySelective(user);
            }
            
            
            //更新用户签约授权状态信息表
            if(hjhUserAuth==null){
                hjhUserAuth=new HjhUserAuth();
                hjhUserAuth.setUserId(user.getUserId());
                // 设置状态
                setAuthType(hjhUserAuth,retBean,authType);
                
                hjhUserAuth.setUserName(user.getUsername());
                hjhUserAuth.setCreateUser(user.getUserId());
                hjhUserAuth.setCreateTime(nowTime);
                hjhUserAuth.setUpdateTime(nowTime);
                hjhUserAuth.setUpdateUser(userId);
                hjhUserAuth.setDelFlg(0);
                hjhUserAuthMapper.insertSelective(hjhUserAuth);
            }else{
                HjhUserAuth updateHjhUserAuth=new HjhUserAuth();
                updateHjhUserAuth.setUserId(user.getUserId());
                // 设置状态
                setAuthType(updateHjhUserAuth,retBean,authType);
                updateHjhUserAuth.setId(hjhUserAuth.getId());
                updateHjhUserAuth.setUpdateTime(nowTime);
                updateHjhUserAuth.setUpdateUser(userId);
                hjhUserAuthMapper.updateByPrimaryKeySelective(updateHjhUserAuth);
            }
        }
    }
    
 // 设置状态
    private void setAuthType(HjhUserAuth hjhUserAuth, BankCallBean bean, String authType) {
        // 授权类型
        String txcode = bean.getTxCode();
        HjhUserAuthConfig config=CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_AUTO_CREDIT_AUTH);
        if(BankCallConstant.TXCODE_TERMS_AUTH_QUERY.equals(txcode)){
            //自动投标功能开通标志
            String autoBidStatus = bean.getAutoBid();
            //自动债转功能开通标志
            String autoTransfer = bean.getAutoTransfer();
            //缴费授权
            String paymentAuth = bean.getPaymentAuth();
            //还款授权
            String repayAuth = bean.getRepayAuth();
            // 用户ID
            Integer userId = hjhUserAuth.getUserId();
            // 根据用户ID 查询用户信息
			Users users = this.getUsers(userId);

            switch (authType) {
			case AuthBean.AUTH_TYPE_AUTO_BID:
				if(StringUtils.isNotBlank(autoBidStatus)&&"1".equals(autoBidStatus)){
					hjhUserAuth.setAutoOrderId(bean.getOrderId());
	            	hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
	            	hjhUserAuth.setAutoBidTime(GetDate.getNowTime10());
	                hjhUserAuth.setAutoInvesStatus(Integer.parseInt(autoBidStatus));
	                hjhUserAuth.setAutoBidEndTime(bean.getAutoBidDeadline());
	                hjhUserAuth.setInvesMaxAmt(bean.getAutoBidMaxAmt());
	                // add by liuyang 神策数据统计修改 20180927 start
					if ("10000000".equals(users.getInstCode())) {
						try {
							SensorsDataBean sensorsDataBean = new SensorsDataBean();
							sensorsDataBean.setUserId(userId);
							// 汇计划授权结果
							sensorsDataBean.setEventCode("plan_auth_result");
							sensorsDataBean.setOrderId(bean.getOrderId());
							// 授权类型
							sensorsDataBean.setAuthType(AuthBean.AUTH_TYPE_AUTO_BID);
							this.sendSensorsDataMQ(sensorsDataBean);
						} catch (Exception e) {
							_log.info("自动投标授权 神策数据统计异常！用户id：{}", userId, this.getClass().getName(), e);
						}
					}
					// add by liuyang 神策数据统计修改 20180927 end

	            }
				break;
			case AuthBean.AUTH_TYPE_AUTO_CREDIT:
				if(StringUtils.isNotBlank(autoTransfer)&&"1".equals(autoTransfer)){
					hjhUserAuth.setAutoCreditOrderId(bean.getOrderId());
	                hjhUserAuth.setAutoCreditStatus(Integer.parseInt(autoTransfer));
	                hjhUserAuth.setAutoCreditTime(GetDate.getNowTime10());
	                hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
	                hjhUserAuth.setCreditMaxAmt(config.getPersonalMaxAmount()+"");
	                hjhUserAuth.setAutoCreditEndTime(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));

					// add by liuyang 神策数据统计修改 20180927 start
					if ("10000000".equals(users.getInstCode())) {
						try {
							SensorsDataBean sensorsDataBean = new SensorsDataBean();
							sensorsDataBean.setUserId(userId);
							// 汇计划授权结果
							sensorsDataBean.setEventCode("plan_auth_result");
							sensorsDataBean.setOrderId(bean.getOrderId());
							// 授权类型
							sensorsDataBean.setAuthType(AuthBean.AUTH_TYPE_AUTO_CREDIT);
							this.sendSensorsDataMQ(sensorsDataBean);
						} catch (Exception e) {
							_log.info("自动债转授权 神策数据统计异常！用户id：{}", userId, this.getClass().getName(), e);
						}
					}
					// add by liuyang 神策数据统计修改 20180927 end

	            }	
				break;
			case AuthBean.AUTH_TYPE_PAYMENT_AUTH:
				if(StringUtils.isNotBlank(paymentAuth)&&"1".equals(paymentAuth)){
	                hjhUserAuth.setAutoPaymentStatus(Integer.parseInt(paymentAuth));
	                hjhUserAuth.setAutoPaymentEndTime(bean.getPaymentDeadline());
	                hjhUserAuth.setAutoPaymentTime(GetDate.getNowTime10());
	                hjhUserAuth.setPaymentMaxAmt(bean.getPaymentMaxAmt());

					// add by liuyang 神策数据统计修改 20180927 start
					if ("10000000".equals(users.getInstCode())) {
						try {
							SensorsDataBean sensorsDataBean = new SensorsDataBean();
							sensorsDataBean.setUserId(userId);
							// 事件类型:服务费授权结果
							sensorsDataBean.setEventCode("fee_auth_result");
							sensorsDataBean.setOrderId(bean.getOrderId());
							// 授权类型
							sensorsDataBean.setAuthType(AuthBean.AUTH_TYPE_PAYMENT_AUTH);
							this.sendSensorsDataMQ(sensorsDataBean);
						} catch (Exception e) {
							_log.info("缴费授权 神策数据统计异常！用户id：{}", userId, this.getClass().getName(), e);
						}
					}
					// add by liuyang 神策数据统计修改 20180927 end
	            }
				break;
			case AuthBean.AUTH_TYPE_REPAY_AUTH:
				if(StringUtils.isNotBlank(repayAuth)&&"1".equals(repayAuth)){
	                hjhUserAuth.setAutoRepayStatus(Integer.parseInt(repayAuth));
	                hjhUserAuth.setAutoRepayEndTime(bean.getRepayDeadline());
	                hjhUserAuth.setAutoRepayTime(GetDate.getNowTime10());
	                hjhUserAuth.setRepayMaxAmt(bean.getRepayMaxAmt());
	            }
				break;
			case AuthBean.AUTH_TYPE_MERGE_AUTH:
				if(StringUtils.isNotBlank(autoBidStatus)&&"1".equals(autoBidStatus)){
					hjhUserAuth.setAutoOrderId(bean.getOrderId());
	            	hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
	            	hjhUserAuth.setAutoBidTime(GetDate.getNowTime10());
	                hjhUserAuth.setAutoInvesStatus(Integer.parseInt(autoBidStatus));
	                hjhUserAuth.setAutoBidEndTime(bean.getAutoBidDeadline());
	                hjhUserAuth.setInvesMaxAmt(bean.getAutoBidMaxAmt());
	            }
				if(StringUtils.isNotBlank(autoTransfer)&&"1".equals(autoTransfer)){
					
					hjhUserAuth.setAutoCreditOrderId(bean.getOrderId());
	                hjhUserAuth.setAutoCreditStatus(Integer.parseInt(autoTransfer));
	                hjhUserAuth.setAutoCreditTime(GetDate.getNowTime10());
	                hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
	                hjhUserAuth.setCreditMaxAmt(config.getPersonalMaxAmount()+"");
	                hjhUserAuth.setAutoCreditEndTime(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
	            }	
				if(StringUtils.isNotBlank(paymentAuth)&&"1".equals(paymentAuth)){
	                hjhUserAuth.setAutoPaymentStatus(Integer.parseInt(paymentAuth));
	                hjhUserAuth.setAutoPaymentEndTime(bean.getPaymentDeadline());
	                hjhUserAuth.setAutoPaymentTime(GetDate.getNowTime10());
	                hjhUserAuth.setPaymentMaxAmt(bean.getPaymentMaxAmt());
	            }

				// add by liuyang 神策数据统计修改 20180927 start
				if ("10000000".equals(users.getInstCode())) {
					try {
						SensorsDataBean sensorsDataBean = new SensorsDataBean();
						sensorsDataBean.setUserId(userId);
						// 汇计划授权结果
						sensorsDataBean.setEventCode("plan_auth_result");
						sensorsDataBean.setOrderId(bean.getOrderId());
						// 授权类型
						sensorsDataBean.setAuthType(AuthBean.AUTH_TYPE_MERGE_AUTH);
						this.sendSensorsDataMQ(sensorsDataBean);
					} catch (Exception e) {
						_log.info("自动投标、自动债转、缴费授权 三合一授权 神策数据统计异常！用户id：{}", userId, this.getClass().getName(), e);
					}
				}
				// add by liuyang 神策数据统计修改 20180927 end
				break;
			case AuthBean.AUTH_TYPE_PAY_REPAY_AUTH:
				if(StringUtils.isNotBlank(paymentAuth) && "1".equals(paymentAuth)){
					hjhUserAuth.setAutoPaymentStatus(Integer.parseInt(paymentAuth));
					hjhUserAuth.setAutoPaymentEndTime(bean.getPaymentDeadline());
					hjhUserAuth.setAutoPaymentTime(GetDate.getNowTime10());
					hjhUserAuth.setPaymentMaxAmt(bean.getPaymentMaxAmt());
				}
				if(StringUtils.isNotBlank(repayAuth) && "1".equals(repayAuth)){
					hjhUserAuth.setAutoRepayStatus(Integer.parseInt(repayAuth));
					hjhUserAuth.setAutoRepayEndTime(bean.getRepayDeadline());
					hjhUserAuth.setAutoRepayTime(GetDate.getNowTime10());
					hjhUserAuth.setRepayMaxAmt(bean.getRepayMaxAmt());
				}
				if ("10000000".equals(users.getInstCode())) {
					try {
						SensorsDataBean sensorsDataBean = new SensorsDataBean();
						sensorsDataBean.setUserId(userId);
						// 汇计划授权结果
						sensorsDataBean.setEventCode("plan_auth_result");
						sensorsDataBean.setOrderId(bean.getOrderId());
						// 授权类型
						sensorsDataBean.setAuthType(AuthBean.AUTH_TYPE_PAY_REPAY_AUTH);
						this.sendSensorsDataMQ(sensorsDataBean);
					} catch (Exception e) {
						_log.info("缴费、还款二合一授权 神策数据统计异常！用户id：{}", userId, this.getClass().getName(), e);
					}
				}
				break;
			default:
				break;
			}

        }else if(BankCallConstant.TXCODE_TERMS_AUTH_PAGE.equals(txcode)){
        	//自动投标功能开通标志
            String autoBidStatus = bean.getAutoBid();
            //自动债转功能开通标志
            String autoTransfer = bean.getAutoCredit();
            //缴费授权
            String paymentAuth = bean.getPaymentAuth();
            //还款授权
            String repayAuth = bean.getRepayAuth();
            if(StringUtils.isNotBlank(autoBidStatus)){
                hjhUserAuth.setAutoInvesStatus(Integer.parseInt(autoBidStatus));
                hjhUserAuth.setAutoOrderId(bean.getOrderId());
                hjhUserAuth.setAutoBidTime(GetDate.getNowTime10());
                hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
                hjhUserAuth.setAutoBidEndTime(bean.getAutoBidDeadline());
                hjhUserAuth.setInvesMaxAmt(bean.getAutoBidMaxAmt());
            }
            if(StringUtils.isNotBlank(autoTransfer)){
                hjhUserAuth.setAutoCreditStatus(Integer.parseInt(autoTransfer));
                hjhUserAuth.setAutoCreditOrderId(bean.getOrderId());
                hjhUserAuth.setAutoCreditTime(GetDate.getNowTime10());
                hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
                hjhUserAuth.setCreditMaxAmt(bean.getAutoCreditMaxAmt());
                hjhUserAuth.setAutoCreditEndTime(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
            }
            if(StringUtils.isNotBlank(paymentAuth)){
                hjhUserAuth.setAutoPaymentStatus(Integer.parseInt(paymentAuth));
                hjhUserAuth.setAutoPaymentEndTime(bean.getPaymentDeadline());
                hjhUserAuth.setPaymentMaxAmt(bean.getPaymentMaxAmt());
                hjhUserAuth.setAutoPaymentTime(GetDate.getNowTime10());
            }
            if(StringUtils.isNotBlank(repayAuth)){
                hjhUserAuth.setAutoRepayStatus(Integer.parseInt(repayAuth));
                hjhUserAuth.setAutoRepayEndTime(bean.getRepayDeadline());
                hjhUserAuth.setRepayMaxAmt(bean.getRepayMaxAmt());
                hjhUserAuth.setAutoRepayTime(GetDate.getNowTime10());
            }
        }

    }
    
    
    /**
     * 
     * 根据用户id查询用户签约授权信息
     * @param userId
     * @return
     */
    @Override
	public HjhUserAuth getHjhUserAuthByUserId(Integer userId) {
		HjhUserAuthExample example = new HjhUserAuthExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<HjhUserAuth> list = hjhUserAuthMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}

	}
    /**
     * 
     * 插入用户签约授权log
     * @param userId
     */
    @Override
    public void insertUserAuthLog(int userId, String orderId, Integer client, String authType) {
        Integer nowTime=GetDate.getNowTime10();
        Users user=this.getUsers(userId);
        HjhUserAuthLog hjhUserAuthLog=new HjhUserAuthLog();
        hjhUserAuthLog.setUserId(user.getUserId());
        hjhUserAuthLog.setUserName(user.getUsername());
        hjhUserAuthLog.setOrderId(orderId);
        hjhUserAuthLog.setOrderStatus(2);
        if(authType!=null&&authType.equals(BankCallConstant.QUERY_TYPE_2)){
            hjhUserAuthLog.setAuthType(4);
        }else{
            hjhUserAuthLog.setAuthType(Integer.valueOf(authType));
        }
        
        hjhUserAuthLog.setOperateEsb(client);
        hjhUserAuthLog.setCreateUser(user.getUserId());
        hjhUserAuthLog.setCreateTime(nowTime);
        hjhUserAuthLog.setUpdateTime(nowTime);                     
        hjhUserAuthLog.setUpdateUser(userId);
        hjhUserAuthLog.setDelFlg(0);
        hjhUserAuthLogMapper.insertSelective(hjhUserAuthLog);
    }
    
    @Override
    public ModelAndView getCallbankMV(AuthBean authBean) {
        ModelAndView mv = new ModelAndView();
        
        // 获取共同参数
        String bankCode = PropUtils.getSystem(BankCallConstant.BANK_BANKCODE);
        String bankInstCode = PropUtils.getSystem(BankCallConstant.BANK_INSTCODE);
        String orderDate = GetOrderIdUtils.getOrderDate();
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        // 调用开户接口
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallConstant.TXCODE_TERMS_AUTH_PAGE);// 多合一授权
        bean.setInstCode(bankInstCode);// 机构代码
        bean.setBankCode(bankCode);
        bean.setTxDate(txDate);
        bean.setTxTime(txTime);
        bean.setSeqNo(seqNo);
        bean.setChannel(authBean.getChannel());
        
        
        bean.setAccountId(authBean.getAccountId());
        if(authBean.getUserType()==1){
        	CorpOpenAccountRecordExample example = new CorpOpenAccountRecordExample();
    		example.createCriteria().andUserIdEqualTo(authBean.getUserId());
    		List<CorpOpenAccountRecord> corpList = this.corpOpenAccountRecordMapper.selectByExample(example);
    		
    		if (corpList !=null && corpList.size()>0) {
    			bean.setName(corpList.get(0).getBusiName());
                bean.setIdNo(corpList.get(0).getBusiCode());
    		}

        }else{
        	bean.setName(authBean.getName());
            bean.setIdNo(authBean.getIdNo());
        }
        
        bean.setIdentity(authBean.getIdentity());
        HjhUserAuthConfig config=null;
        switch (authBean.getAuthType()) {
		case AuthBean.AUTH_TYPE_AUTO_BID:
			config=CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_AUTO_TENDER_AUTH);
			bean.setAutoBid(AuthBean.AUTH_START_OPEN);
			if(authBean.getUserType()!=1){
				bean.setAutoBidMaxAmt(config.getPersonalMaxAmount()+"");
			}else{
				bean.setAutoBidMaxAmt(config.getEnterpriseMaxAmount()+"");
			}
			bean.setAutoBidDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
			bean.setLogRemark("自动出借授权");
			break;
		case AuthBean.AUTH_TYPE_AUTO_CREDIT:
			config=CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_AUTO_CREDIT_AUTH);
			bean.setAutoCredit(AuthBean.AUTH_START_OPEN);
			if(authBean.getUserType()!=1){
				bean.setAutoCreditMaxAmt(config.getPersonalMaxAmount()+"");
			}else{
				bean.setAutoCreditMaxAmt(config.getEnterpriseMaxAmount()+"");
			}
			bean.setAutoCreditDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
			bean.setLogRemark("自动债转授权");
			break;
		case AuthBean.AUTH_TYPE_PAYMENT_AUTH:
			config=CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_PAYMENT_AUTH);
			bean.setPaymentAuth(AuthBean.AUTH_START_OPEN);
			if(authBean.getUserType()!=1){
				bean.setPaymentMaxAmt(config.getPersonalMaxAmount()+"");
			}else{
				bean.setPaymentMaxAmt(config.getEnterpriseMaxAmount()+"");
			}
			bean.setPaymentDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
			bean.setLogRemark("服务费授权");
			break;
		case AuthBean.AUTH_TYPE_REPAY_AUTH:
			config=CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_REPAYMENT_AUTH);
			bean.setRepayAuth(AuthBean.AUTH_START_OPEN);
			if(authBean.getUserType()!=1){
				bean.setRepayMaxAmt(config.getPersonalMaxAmount()+"");
			}else{
				bean.setRepayMaxAmt(config.getEnterpriseMaxAmount()+"");
			}
			bean.setRepayDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
			bean.setLogRemark("还款授权");
			break;
		case AuthBean.AUTH_TYPE_MERGE_AUTH:
			
			if(!this.checkIsAuth(authBean.getUserId(), AuthBean.AUTH_TYPE_AUTO_BID)){
				config=CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_AUTO_TENDER_AUTH);
				bean.setAutoBid(AuthBean.AUTH_START_OPEN);
				if(authBean.getUserType()!=1){
					bean.setAutoBidMaxAmt(config.getPersonalMaxAmount()+"");
				}else{
					bean.setAutoBidMaxAmt(config.getEnterpriseMaxAmount()+"");
				}
				bean.setAutoBidDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
				authBean.setAutoBidStatus(true);
			}
			
			if(!this.checkIsAuth(authBean.getUserId(), AuthBean.AUTH_TYPE_AUTO_CREDIT)){
				config=CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_AUTO_CREDIT_AUTH);
				bean.setAutoCredit(AuthBean.AUTH_START_OPEN);
				if(authBean.getUserType()!=1){
					bean.setAutoCreditMaxAmt(config.getPersonalMaxAmount()+"");
				}else{
					bean.setAutoCreditMaxAmt(config.getEnterpriseMaxAmount()+"");
				}
				bean.setAutoCreditDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
				authBean.setAutoCreditStatus(true);
			}
			if(!this.checkIsAuth(authBean.getUserId(), AuthBean.AUTH_TYPE_PAYMENT_AUTH)){
				config=CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_PAYMENT_AUTH);
				bean.setPaymentAuth(AuthBean.AUTH_START_OPEN);
				if(authBean.getUserType()!=1){
					bean.setPaymentMaxAmt(config.getPersonalMaxAmount()+"");
				}else{
					bean.setPaymentMaxAmt(config.getEnterpriseMaxAmount()+"");
				}
				bean.setPaymentDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
				authBean.setPaymentAuthStatus(true);
			}
			bean.setLogRemark("多个授权");
			break;
		case AuthBean.AUTH_TYPE_PAY_REPAY_AUTH:
			if(!this.checkIsAuth(authBean.getUserId(), AuthBean.AUTH_TYPE_PAYMENT_AUTH)){
				config = CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_PAYMENT_AUTH);
				bean.setPaymentAuth(AuthBean.AUTH_START_OPEN);
				if(authBean.getUserType() != 1){
					bean.setPaymentMaxAmt(config.getPersonalMaxAmount()+"");
				}else{
					bean.setPaymentMaxAmt(config.getEnterpriseMaxAmount()+"");
				}
				bean.setPaymentDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
				authBean.setPaymentAuthStatus(true);
			}
			if(!this.checkIsAuth(authBean.getUserId(), AuthBean.AUTH_TYPE_REPAY_AUTH)){
				config = CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_REPAYMENT_AUTH);
				bean.setRepayAuth(AuthBean.AUTH_START_OPEN);
				if(authBean.getUserType() != 1){
					bean.setRepayMaxAmt(config.getPersonalMaxAmount()+"");
				}else{
					bean.setRepayMaxAmt(config.getEnterpriseMaxAmount()+"");
				}
				bean.setRepayDeadline(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
				authBean.setRepayAuthAuthStatus(true);
			}
			bean.setLogRemark("服务费、还款二合一授权");
			break;
		default:
			break;
		}
        
        bean.setRetUrl(authBean.getRetUrl());
        if(authBean.getRetUrl().indexOf("&isSuccess=")!=-1){
        	authBean.getRetUrl().replace("&isSuccess=", "&isSuccess=1");
        }else{
        	bean.setSuccessfulUrl(authBean.getRetUrl()+"&isSuccess=1");
        }
        
        bean.setNotifyUrl(authBean.getNotifyUrl());
        bean.setForgotPwdUrl(authBean.getForgotPwdUrl());

        // 页面调用必须传的
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_TERMS_AUTH_PAGE);
        bean.setLogOrderId(authBean.getOrderId());
        bean.setLogOrderDate(orderDate);
        bean.setLogUserId(String.valueOf(authBean.getUserId()));
        bean.setLogIp(authBean.getIp());
        bean.setLogClient(Integer.parseInt(authBean.getPlatform()));
        bean.setOrderId(authBean.getOrderId());
        try {
            mv = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    
    @Override
    public BankCallBean getTermsAuthQuery(int userId,String channel) {
        BankOpenAccount bankOpenAccount = getBankOpenAccount(userId);
        // 调用查询出借人签约状态查询
        BankCallBean selectbean = new BankCallBean();
        selectbean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        selectbean.setTxCode(BankCallConstant.TXCODE_TERMS_AUTH_QUERY);
        selectbean.setInstCode(PropUtils.getSystem(BankCallConstant.BANK_INSTCODE));// 机构代码
        selectbean.setBankCode(PropUtils.getSystem(BankCallConstant.BANK_BANKCODE));
        selectbean.setTxDate(GetOrderIdUtils.getTxDate());
        selectbean.setTxTime(GetOrderIdUtils.getTxTime());
        selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        selectbean.setChannel(channel);
        selectbean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));// 电子账号
        selectbean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        // 操作者ID
        selectbean.setLogUserId(String.valueOf(userId));
        selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        selectbean.setLogClient(0);
        // 返回参数
        BankCallBean retBean = null;
        // 调用接口
        retBean = BankCallUtils.callApiBg(selectbean);
        return retBean;
    }

    
    // 检查是否已经授权过了
    @Override
    public boolean checkIsAuth(Integer userId, String txcode) {
        HjhUserAuth hjhUserAuth = getHjhUserAuthByUserId(userId);
        String endTime = "";
        int status = 0;
        String nowTime = GetDate.date2Str(new Date(),GetDate.yyyyMMdd);
        // 缴费授权
        if(hjhUserAuth==null){
            return false;
        }
        if (AuthBean.AUTH_TYPE_PAYMENT_AUTH.equals(txcode)) {
            endTime = hjhUserAuth.getAutoPaymentEndTime()==null?"0":hjhUserAuth.getAutoPaymentEndTime();
            status = hjhUserAuth.getAutoPaymentStatus();
        }else if(AuthBean.AUTH_TYPE_REPAY_AUTH.equals(txcode)){
            endTime = hjhUserAuth.getAutoRepayEndTime()==null?"0":hjhUserAuth.getAutoRepayEndTime();
            status = hjhUserAuth.getAutoRepayStatus();
        }else if(AuthBean.AUTH_TYPE_AUTO_BID.equals(txcode)){
            endTime = hjhUserAuth.getAutoBidEndTime()==null?"0":hjhUserAuth.getAutoBidEndTime();
            status = hjhUserAuth.getAutoInvesStatus();
        }else if(AuthBean.AUTH_TYPE_AUTO_CREDIT.equals(txcode)){
        	endTime = nowTime+1;
            status = hjhUserAuth.getAutoCreditStatus();
        }

        if(AuthBean.AUTH_TYPE_PAY_REPAY_AUTH.equals(txcode)){
			//（服务费授权、还款授权）二合一
			endTime = hjhUserAuth.getAutoPaymentEndTime()==null?"0":hjhUserAuth.getAutoPaymentEndTime();
			status = hjhUserAuth.getAutoPaymentStatus();
			if (status == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
				return false;
			}
			endTime = hjhUserAuth.getAutoRepayEndTime()==null?"0":hjhUserAuth.getAutoRepayEndTime();
			status = hjhUserAuth.getAutoRepayStatus();
			// 0是未授权
			if (status == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
				return false;
			}
		}else if(AuthBean.AUTH_TYPE_MERGE_AUTH.equals(txcode)){
			//合并授权（自动投标、自动债转、服务费授权）三合一
			//自动债转
			endTime = nowTime+1;
			status = hjhUserAuth.getAutoCreditStatus();
			if (status == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
				return false;
			}
			//自动投标
			endTime = hjhUserAuth.getAutoBidEndTime()==null?"0":hjhUserAuth.getAutoBidEndTime();
			status = hjhUserAuth.getAutoInvesStatus();
			if (status == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
				return false;
			}
			//服务费授权
			endTime = hjhUserAuth.getAutoPaymentEndTime()==null?"0":hjhUserAuth.getAutoPaymentEndTime();
			status = hjhUserAuth.getAutoPaymentStatus();
			if (status == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
				return false;
			}
		}else{
			//单个授权
			if (status == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
				return false;
			}
		}

        return true;
    }

	@Override
	public boolean checkDefaultConfig(BankCallBean bean,String authType) {
		Users user=this.getUsers(Integer.parseInt(bean.getLogUserId()));
		// 授权类型
        String txcode = bean.getTxCode();
        try {
			if(BankCallConstant.TXCODE_TERMS_AUTH_QUERY.equals(txcode)){
	            //自动投标功能开通标志
	            String autoBidStatus = bean.getAutoBid();
	            //缴费授权
	            String paymentAuth = bean.getPaymentAuth();
	            //还款授权
	            String repayAuth = bean.getRepayAuth();
	            switch (authType) {
				case AuthBean.AUTH_TYPE_AUTO_BID:
					if(StringUtils.isNotBlank(autoBidStatus)&&"1".equals(autoBidStatus)
		            		&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),authType)){
		            	HjhUserAuthConfig config=CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_AUTO_TENDER_AUTH);
		            	if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getAutoBidDeadline())){
		            		return true;
		            	}
		            	if(user.getUserType()!=1){
		            		if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))){
			            		return true;
			            	}
		            	}else{
		            		if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))){
			            		return true;
			            	}
		            	}
		            	
		            }
					break;
				case AuthBean.AUTH_TYPE_PAYMENT_AUTH:
					if(StringUtils.isNotBlank(paymentAuth)&&"1".equals(paymentAuth)
		            		&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),authType)){
		            	HjhUserAuthConfig config=CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_PAYMENT_AUTH);
		            	if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getPaymentDeadline())){
		            		return true;
		            	}
		            	if(user.getUserType()!=1){
		            		if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
			            		return true;
			            	}
		            	}else{
		            		if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
			            		return true;
			            	}
		            	}
		            }
					break;
				case AuthBean.AUTH_TYPE_REPAY_AUTH:
					if(StringUtils.isNotBlank(repayAuth)&&"1".equals(repayAuth)
		            		&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),authType)){
		            	HjhUserAuthConfig config=CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_REPAYMENT_AUTH);
		            	if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getRepayDeadline())){
		            		return true;
		            	}
		            	if(user.getUserType()!=1){
		            		if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getRepayMaxAmt()))){
			            		return true;
			            	}
		            	}else{
		            		if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getRepayMaxAmt()))){
			            		return true;
			            	}
		            	}
		            }
					break;
				case AuthBean.AUTH_TYPE_MERGE_AUTH:
					if(StringUtils.isNotBlank(autoBidStatus)&&"1".equals(autoBidStatus)
		            		&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),AuthBean.AUTH_TYPE_AUTO_BID)){
		            	HjhUserAuthConfig config=CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_AUTO_TENDER_AUTH);
		            	if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getAutoBidDeadline())){
		            		return true;
		            	}
		            	if(user.getUserType()!=1){
		            		if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))){
			            		return true;
			            	}
		            	}else{
		            		if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))){
			            		return true;
			            	}
		            	}
		            }
					if(StringUtils.isNotBlank(paymentAuth)&&"1".equals(paymentAuth)
		            		&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),AuthBean.AUTH_TYPE_PAYMENT_AUTH)){
		            	HjhUserAuthConfig config=CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_PAYMENT_AUTH);
		            	if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getPaymentDeadline())){
		            		return true;
		            	}
		            	if(user.getUserType()!=1){
		            		if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
			            		return true;
			            	}
		            	}else{
		            		if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
			            		return true;
			            	}
		            	}
		            }
					break;
				case AuthBean.AUTH_TYPE_PAY_REPAY_AUTH:
					if(StringUtils.isNotBlank(paymentAuth)&&"1".equals(paymentAuth)
							&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),AuthBean.AUTH_TYPE_PAYMENT_AUTH)){
						HjhUserAuthConfig config=CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_PAYMENT_AUTH);
						if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getPaymentDeadline())){
							return true;
						}
						if(user.getUserType()!=1){
							if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
								return true;
							}
						}else{
							if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
								return true;
							}
						}
					}
					if(StringUtils.isNotBlank(repayAuth)&&"1".equals(repayAuth)
							&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),AuthBean.AUTH_TYPE_REPAY_AUTH)){
						HjhUserAuthConfig config=CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_REPAYMENT_AUTH);
						if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getRepayDeadline())){
							return true;
						}
						if(user.getUserType()!=1){
							if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getRepayAuth()))){
								return true;
							}
						}else{
							if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getRepayAuth()))){
								return true;
							}
						}
					}
					break;
				default:
					break;
				}

	        }else if(BankCallConstant.TXCODE_TERMS_AUTH_PAGE.equals(txcode)){
	        	//自动投标功能开通标志
	            String autoBidStatus = bean.getAutoBid();
	            //缴费授权
				String paymentAuth = bean.getPaymentAuth();
				//还款授权
				String repayAuth = bean.getRepayAuth();
				_log.info("autoBidStatus:{}；paymentAuth:{}；repayAuth:{}", autoBidStatus, paymentAuth, repayAuth);
				if(StringUtils.isNotBlank(autoBidStatus)&&"1".equals(autoBidStatus)
	            		&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),authType)){
	            	HjhUserAuthConfig config=CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_AUTO_TENDER_AUTH);
	            	if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getAutoBidDeadline())){
	            		return true;
	            	}
	            	if(user.getUserType()!=1){
	            		if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))){
		            		return true;
		            	}
	            	}else{
	            		if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))){
		            		return true;
		            	}
	            	}
	            }
	            if(StringUtils.isNotBlank(paymentAuth)&&"1".equals(paymentAuth)
	            		&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),authType)){
	            	HjhUserAuthConfig config=CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_PAYMENT_AUTH);
	            	if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getPaymentDeadline())){
	            		return true;
	            	}
	            	if(user.getUserType()!=1){
	            		if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
		            		return true;
		            	}
	            	}else{
	            		if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
		            		return true;
		            	}
	            	}
	            }
	            if(StringUtils.isNotBlank(repayAuth)&&"1".equals(repayAuth)
	            		&&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),authType)){
	            	HjhUserAuthConfig config=CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_REPAYMENT_AUTH);
	            	if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getRepayDeadline())){
	            		return true;
	            	}
	            	if(user.getUserType()!=1){
	            		if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getRepayMaxAmt()))){
		            		return true;
		            	}
	            	}else{
	            		if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getRepayMaxAmt()))){
		            		return true;
		            	}
	            	}
	            }
	        }
        } catch (Exception e) {
        	return true;
		}
		return false;
	}
	
	/**
     * 
     * 检查自动出借授权状态
     * @author pcc
     * @param userId
     * @return
     */
    @Override
    public boolean checkInvesAuthStatus(Integer userId) {
        // 如果用户ID没有 直接成功吧 不会出现这种
        if (userId == null) {
            return true;
        }
        // 检查开关是否打开 没打开 不用校验
        if (CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_AUTO_TENDER_AUTH).getEnabledStatus() - 1 != 0) {
            return true;
        }
        HjhUserAuth auth = getHjhUserAuthByUserId(userId);
        if (auth == null || auth.getAutoInvesStatus() - 1 != 0) {
            return false;
        }
        return true;
    }
    
    /**
     * 
     * 检查自动债转授权状态
     * @author pcc
     * @param userId
     * @return
     */
    @Override
    public boolean checkCreditAuthStatus(Integer userId) {
        // 如果用户ID没有 直接成功吧 不会出现这种
        if (userId == null) {
            return true;
        }
        // 检查开关是否打开 没打开 不用校验
        if (CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_AUTO_CREDIT_AUTH).getEnabledStatus() - 1 != 0) {
            return true;
        }
        HjhUserAuth auth = getHjhUserAuthByUserId(userId);
        if (auth == null || auth.getAutoCreditStatus() - 1 != 0) {
            return false;
        }
        return true;
    }
	
	/**
     * 
     * 检查服务费授权状态
     * @author pcc
     * @param userId
     * @return
     */
    @Override
    public boolean checkPaymentAuthStatus(Integer userId) {
        // 如果用户ID没有 直接成功吧 不会出现这种
        if (userId == null) {
            return true;
        }
        // 检查开关是否打开 没打开 不用校验
        if (CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_PAYMENT_AUTH).getEnabledStatus() - 1 != 0) {
            return true;
        }
        HjhUserAuth auth = getHjhUserAuthByUserId(userId);
        if (auth == null || auth.getAutoPaymentStatus() - 1 != 0) {
            return false;
        }
        return true;
    }
    
    /**
     * 检查还款授权状态
     * 此处为实现/覆载说明
     * @author pcc
     * @param userId
     * @return
     */
    @Override
    public boolean checkRepayAuthStatus(Integer userId) {
     // 如果用户ID没有 直接成功吧 不会出现这种
        if (userId == null) {
            return true;
        }
        // 检查开关是否打开 没打开 不用校验
        if (CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_REPAYMENT_AUTH).getEnabledStatus() - 1 != 0) {
            return true;
        }
        HjhUserAuth auth = getHjhUserAuthByUserId(userId);
        if (auth == null || auth.getAutoRepayStatus() - 1 != 0) {
            return false;
        }
        return true;
    }
    /**
     * 0未授权，1正常，2即将过期，3已过期
     * 检查授权过期
     * @author pcc
     * @param userId
     * @return
     */
	@Override
	public Integer checkAuthExpire(Integer userId, String txcode) {
		HjhUserAuth hjhUserAuth = getHjhUserAuthByUserId(userId);
        String endTime = "";
        int status = 0;
        String nowTime = GetDate.date2Str(new Date(),GetDate.yyyyMMdd);
        // 缴费授权
        if(hjhUserAuth==null){
            return 0;
        }
        if (AuthBean.AUTH_TYPE_PAYMENT_AUTH.equals(txcode)) {
            endTime = hjhUserAuth.getAutoPaymentEndTime();
            status = hjhUserAuth.getAutoPaymentStatus();
        }else if(AuthBean.AUTH_TYPE_REPAY_AUTH.equals(txcode)){
            endTime = hjhUserAuth.getAutoRepayEndTime();
            status = hjhUserAuth.getAutoRepayStatus();
        }else if(AuthBean.AUTH_TYPE_AUTO_BID.equals(txcode)){
            endTime = hjhUserAuth.getAutoBidEndTime();
            status = hjhUserAuth.getAutoInvesStatus();
        }else if(AuthBean.AUTH_TYPE_AUTO_CREDIT.equals(txcode)){
        	endTime = nowTime+1;
            status = hjhUserAuth.getAutoCreditStatus();
        }

		if(AuthBean.AUTH_TYPE_PAY_REPAY_AUTH.equals(txcode)){
			Integer paymentflag = 0;
			Integer repayflag = 0;
			//缴费授权
			endTime = hjhUserAuth.getAutoPaymentEndTime();
			status = hjhUserAuth.getAutoPaymentStatus();
			// 0是未授权
			if (status != 0) {
				Integer endTimeInt=Integer.parseInt(endTime);
				Integer nowTimeInt=Integer.parseInt(nowTime);
				//20180731-20180630=101  20180228-20180131=97
				if(endTimeInt - nowTimeInt > 101){
					paymentflag = 1;
				}else if(endTimeInt - nowTimeInt <= 101&&endTimeInt - nowTimeInt > 0){
					paymentflag = 2;
				}else {
					paymentflag = 3;
				}
			}
			//还款授权
			endTime = hjhUserAuth.getAutoRepayEndTime();
			status = hjhUserAuth.getAutoRepayStatus();
			// 0是未授权
			if (status != 0) {
				Integer endTimeInt=Integer.parseInt(endTime);
				Integer nowTimeInt=Integer.parseInt(nowTime);
				//20180731-20180630=101  20180228-20180131=97
				if(endTimeInt - nowTimeInt > 101){
					repayflag = 1;
				}else if(endTimeInt - nowTimeInt <= 101&&endTimeInt - nowTimeInt > 0){
					repayflag = 2;
				}else {
					repayflag = 3;
				}
			}
			if(paymentflag==0 || repayflag==0){
				return 0;
			}
			return repayflag>paymentflag ? repayflag : paymentflag;
		}else if(AuthBean.AUTH_TYPE_MERGE_AUTH.equals(txcode)){
			Integer paymentflag=0;
			Integer invesflag=0;
			Integer creditflag=0;
			endTime = nowTime+101;
			status = hjhUserAuth.getAutoCreditStatus();
			// 0是未授权
			if (status - 0 != 0 ) {
				creditflag=1;
			}else{
				creditflag=0;
			}
			endTime = hjhUserAuth.getAutoBidEndTime();
			status = hjhUserAuth.getAutoInvesStatus();

			// 0是未授权
			if (status - 0 == 0) {
				invesflag= 0;
			}else{
				Integer endTimeInt=Integer.parseInt(endTime);
				Integer nowTimeInt=Integer.parseInt(nowTime);
				//20180731-20180630=101  20180228-20180131=97
				if(endTimeInt - nowTimeInt > 101){
					invesflag=1;
				}else if(endTimeInt - nowTimeInt <= 101&&endTimeInt - nowTimeInt > 0){
					invesflag= 2;
				}else {
					invesflag= 3;
				}
			}
			endTime = hjhUserAuth.getAutoPaymentEndTime();
			status = hjhUserAuth.getAutoPaymentStatus();
			// 0是未授权
			if (status - 0 == 0) {
				paymentflag= 0;
			}else{
				Integer endTimeInt=Integer.parseInt(endTime);
				Integer nowTimeInt=Integer.parseInt(nowTime);
				//20180731-20180630=101  20180228-20180131=97
				if(endTimeInt - nowTimeInt > 101){
					paymentflag=1;
				}else if(endTimeInt - nowTimeInt <= 101&&endTimeInt - nowTimeInt > 0){
					paymentflag= 2;
				}else {
					paymentflag= 3;
				}
			}
			if(invesflag==0||creditflag==0||paymentflag==0){
				return 0;
			}
			return invesflag>paymentflag?invesflag:paymentflag;
		}else{
			// 0是未授权
			if (status - 0 == 0) {
				return 0;
			}else{
				Integer endTimeInt=Integer.parseInt(endTime);
				Integer nowTimeInt=Integer.parseInt(nowTime);
				//20180731-20180630=101  20180228-20180131=97
				if(endTimeInt - nowTimeInt > 101){
					return 1;
				}else if(endTimeInt - nowTimeInt <= 101&&endTimeInt - nowTimeInt > 0){
					return 2;
				}else {
					return 3;
				}
			}
		}
	}


	/**
	 * 开户成功后,发送神策数据统计MQ
	 *
	 * @param sensorsDataBean
	 */
	@Override
	public void sendSensorsDataMQ(SensorsDataBean sensorsDataBean) {
		// 加入到消息队列
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("eventCode", sensorsDataBean.getEventCode());
		params.put("userId", sensorsDataBean.getUserId());
		params.put("orderId",sensorsDataBean.getOrderId());
		params.put("authType",sensorsDataBean.getAuthType());
		params.put("mqMsgId", GetCode.getRandomCode(10));
		rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_COUPON, RabbitMQConstants.SENSORS_DATA_ROUTINGKEY_AUTH_RESULT, JSONObject.toJSONString(params));
	}
}
