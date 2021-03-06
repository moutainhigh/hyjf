package com.hyjf.bank.service.user.recharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.bank.service.BaseServiceImpl;
import com.hyjf.bank.service.sensorsdata.SensorsDataBean;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.mybatis.messageprocesser.AppMsMessage;
import com.hyjf.mybatis.messageprocesser.MessageDefine;
import com.hyjf.mybatis.messageprocesser.MessageProcesser;
import com.hyjf.mybatis.messageprocesser.SmsMessage;
import com.hyjf.mybatis.model.auto.*;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户充值Service实现类
 * 
 * @author liuyang
 *
 */
@Service
public class RechargeServiceImpl extends BaseServiceImpl implements RechargeService {

	@Autowired
	@Qualifier("myAmqpTemplate")
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	private TransactionDefinition transactionDefinition;
	// 充值状态:充值中
	private static final int RECHARGE_STATUS_WAIT = 1;
	// 充值状态:失败
	private static final int RECHARGE_STATUS_FAIL = 3;
	// 充值状态:成功
	private static final int RECHARGE_STATUS_SUCCESS = 2;

	@Autowired
	@Qualifier("smsProcesser")
	private MessageProcesser smsProcesser;

	@Autowired
	@Qualifier("appMsProcesser")
	private MessageProcesser appMsProcesser;

	/**
	 * 根据用户Id检索用户银行卡信息
	 * 
	 * @param userId
	 * @return
	 */
	public BankCard selectBankCardByUserId(Integer userId) {
		BankCardExample example = new BankCardExample();
		BankCardExample.Criteria cra = example.createCriteria();
		cra.andUserIdEqualTo(userId);// 用户Id
		cra.andStatusEqualTo(1);// 银行卡是否有效 0无效 1有效
		List<BankCard> bankCardList = bankCardMapper.selectByExample(example);
		if (bankCardList != null && bankCardList.size() > 0) {
			return bankCardList.get(0);
		}
		return null;
	}
	

	/**
	 * 根据用户Id检索用户的身份证号
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public String getUserIdcard(Integer userId) {
		// 取得身份证号
		UsersInfoExample usersInfoExample = new UsersInfoExample();
		usersInfoExample.createCriteria().andUserIdEqualTo(userId);
		List<UsersInfo> listUsersInfo = this.usersInfoMapper.selectByExample(usersInfoExample);
		if (listUsersInfo != null && listUsersInfo.size() > 0) {
			return listUsersInfo.get(0).getIdcard() == null ? "" : StringUtils.upperCase(listUsersInfo.get(0).getIdcard());
		}
		return "";
	}

	@Override
	public int insertRechargeInfo(BankCallBean bean) {
		int ret = 0;
		String ordId = bean.getLogOrderId() == null ? "" : bean.getLogOrderId(); // 订单号
		AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
		accountRechargeExample.createCriteria().andNidEqualTo(ordId == null ? "" : ordId);
		List<AccountRecharge> listAccountRecharge = this.accountRechargeMapper.selectByExample(accountRechargeExample);
		if (listAccountRecharge != null && listAccountRecharge.size() > 0) {
			return ret;
		}
		int nowTime = GetDate.getNowTime10(); // 当前时间
		// 银行卡号
		String cardNo = bean.getCardNo();
		// 根据银行卡号检索银行卡信息
		BankCard bankCard = this.getBankCardByCardNo(Integer.parseInt(bean.getLogUserId()), cardNo);
		BigDecimal money = new BigDecimal(bean.getTxAmount()); // 充值金额
		AccountRecharge record = new AccountRecharge();
		record.setNid(bean.getLogOrderId()); // 订单号
		record.setUserId(Integer.parseInt(bean.getLogUserId())); // 用户ID
		record.setUsername(bean.getLogUserName());// 用户 名
		record.setTxDate(Integer.parseInt(bean.getTxDate()));// 交易日期
		record.setTxTime(Integer.parseInt(bean.getTxTime()));// 交易时间
		record.setSeqNo(Integer.parseInt(bean.getSeqNo())); // 交易流水号
		record.setBankSeqNo(bean.getTxDate() + bean.getTxTime() + bean.getSeqNo()); // 交易日期+交易时间+交易流水号
		record.setStatus(RECHARGE_STATUS_WAIT); // 充值状态:0:初始,1:充值中,2:充值成功,3:充值失败
		record.setAccountId(bean.getAccountId());// 电子账号
		record.setMoney(money); // 金额
		record.setCardid(cardNo);// 银行卡号
		record.setFeeFrom(null);// 手续费扣除方式
		record.setFee(BigDecimal.ZERO); // 费用
		record.setDianfuFee(BigDecimal.ZERO);// 垫付费用
		record.setBalance(money); // 实际到账余额
		record.setPayment(bankCard == null ? "" : bankCard.getBank()); // 所属银行
		record.setGateType("QP"); // 网关类型：QP快捷支付
		record.setType(1); // 类型.1网上充值.0线下充值
		record.setRemark("快捷充值");// 备注
		record.setCreateTime(nowTime);
		record.setOperator(bean.getLogUserId());
		record.setAddtime(String.valueOf(nowTime));
		record.setAddip(bean.getUserIP());
		record.setClient(bean.getLogClient()); // 0pc
		record.setIsBank(1);// 资金托管平台 0:汇付,1:江西银行
		// 插入用户充值记录表
		return this.accountRechargeMapper.insertSelective(record);
	}

	/**
	 * 充值后,回调处理
	 * 
	 * @param bean
	 * @param params
	 * @return
	 */
	@Override
	public JSONObject handleRechargeInfo(BankCallBean bean, Map<String, String> params) {
		TransactionStatus txStatus = null;
		// 用户Id
		Integer userId = Integer.parseInt(bean.getLogUserId());
		// 充值订单号
		String orderId = bean.getLogOrderId();
		// 当前时间
		int nowTime = GetDate.getNowTime10();
		// 错误信息
		String errorMsg = this.getBankRetMsg(bean.getRetCode());
		// ip
		String ip = params.get("ip");
		// 交易日期
		String txDate = bean.getTxDate();
		// 交易时间
		String txTime = bean.getTxTime();
		// 交易流水号
		String seqNo = bean.getSeqNo();
		// 电子账户
		String accountId = bean.getAccountId();
		// 充值成功
		if (BankCallStatusConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
			// 查询用户账户,为了版本控制,必须把查询用户账户放在最前面
			AccountExample accountExample = new AccountExample();
			AccountExample.Criteria accountCriteria = accountExample.createCriteria();
			accountCriteria.andUserIdEqualTo(userId);
			Account account = this.accountMapper.selectByExample(accountExample).get(0);
			// 查询充值记录
			AccountRechargeExample example = new AccountRechargeExample();
			example.createCriteria().andNidEqualTo(orderId);
			List<AccountRecharge> accountRecharges = accountRechargeMapper.selectByExample(example);// 查询充值记录
			AccountRecharge accountRecharge = accountRecharges.get(0);
			// 如果没有充值记录
			if (accountRecharge != null) {
				//add by cwyang 增加redis防重校验 2017-08-02
				boolean reslut = RedisUtils.tranactionSet("recharge_orderid" + orderId, 10);
				if(!reslut){
					return jsonMessage("充值成功", "0");
				}
				//end
				// 交易金额
				BigDecimal txAmount = bean.getBigDecimal(BankCallConstant.PARAM_TXAMOUNT);
				// 判断充值记录状态
				if (accountRecharge.getStatus() == RECHARGE_STATUS_SUCCESS) {
					// 如果已经是成功状态
					return jsonMessage("充值成功", "0");
				} else {
					// 如果不是成功状态
					try {
						// 开启事务
						txStatus = this.transactionManager.getTransaction(transactionDefinition);
						// 将数据封装更新至充值记录
						AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
						accountRechargeExample.createCriteria().andNidEqualTo(orderId).andStatusEqualTo(accountRecharge.getStatus());
						accountRecharge.setFee(BigDecimal.ZERO); // 费用
						accountRecharge.setDianfuFee(BigDecimal.ZERO);// 商户垫付金额
						accountRecharge.setBalance(txAmount);// 实际到账余额
						accountRecharge.setUpdateTime(nowTime);// 更新时间
						accountRecharge.setStatus(RECHARGE_STATUS_SUCCESS);// 充值状态:0:初始,1:充值中,2:充值成功,3:充值失败
						accountRecharge.setAccountId(accountId);// 电子账户
						accountRecharge.setBankSeqNo(txDate + txTime + seqNo);// 交易流水号
						boolean isAccountRechargeFlag = this.accountRechargeMapper.updateByExampleSelective(accountRecharge, accountRechargeExample) > 0 ? true : false;
						if (!isAccountRechargeFlag) {
							throw new Exception("充值后,回调更新充值记录表失败!" + "充值订单号:" + orderId + ".用户ID:" + userId);
						}
						Account newAccount = new Account();
						// 更新账户信息
						newAccount.setUserId(userId);// 用户Id
						newAccount.setBankTotal(txAmount); // 累加到账户总资产
						newAccount.setBankBalance(txAmount); // 累加可用余额
						newAccount.setBankBalanceCash(txAmount);// 银行账户可用户
						boolean isAccountUpdateFlag = this.adminAccountCustomizeMapper.updateBankRechargeSuccess(newAccount) > 0 ? true : false;
						if (!isAccountUpdateFlag) {
							throw new Exception("提现后,更新用户Account表失败!");
						}
						
						// 重新获取用户账户信息
						account = this.getAccount(userId);
						// 生成交易明细
						AccountList accountList = new AccountList();
						accountList.setNid(orderId);
						accountList.setUserId(userId);
						accountList.setAmount(txAmount);
						accountList.setTxDate(Integer.parseInt(bean.getTxDate()));// 交易日期
						accountList.setTxTime(Integer.parseInt(bean.getTxTime()));// 交易时间
						accountList.setSeqNo(bean.getSeqNo());// 交易流水号
						accountList.setBankSeqNo((bean.getTxDate() + bean.getTxTime() + bean.getSeqNo()));
						accountList.setType(1);
						accountList.setTrade("recharge");
						accountList.setTradeCode("balance");
						accountList.setAccountId(accountId);
						accountList.setBankTotal(account.getBankTotal()); // 银行总资产
						accountList.setBankBalance(account.getBankBalance()); // 银行可用余额
						accountList.setBankFrost(account.getBankFrost());// 银行冻结金额
						accountList.setBankWaitCapital(account.getBankWaitCapital());// 银行待还本金
						accountList.setBankWaitInterest(account.getBankWaitInterest());// 银行待还利息
						accountList.setBankAwaitCapital(account.getBankAwaitCapital());// 银行待收本金
						accountList.setBankAwaitInterest(account.getBankAwaitInterest());// 银行待收利息
						accountList.setBankAwait(account.getBankAwait());// 银行待收总额
						accountList.setBankInterestSum(account.getBankInterestSum()); // 银行累计收益
						accountList.setBankInvestSum(account.getBankInvestSum());// 银行累计出借
						accountList.setBankWaitRepay(account.getBankWaitRepay());// 银行待还金额
						accountList.setPlanBalance(account.getPlanBalance());//汇计划账户可用余额
						accountList.setPlanFrost(account.getPlanFrost());
						accountList.setTotal(account.getTotal());
						accountList.setBalance(account.getBalance());
						accountList.setFrost(account.getFrost());
						accountList.setAwait(account.getAwait());
						accountList.setRepay(account.getRepay());
						accountList.setRemark("快捷充值");
						accountList.setCreateTime(nowTime);
						accountList.setBaseUpdate(nowTime);
						accountList.setOperator(userId + "");
						accountList.setIp(ip);
						accountList.setIsUpdate(0);
						accountList.setBaseUpdate(0);
						accountList.setInterest(null);
						accountList.setWeb(0);
						accountList.setIsBank(1);// 是否是银行的交易记录 0:否 ,1:是
						accountList.setCheckStatus(0);// 对账状态0：未对账 1：已对账
						accountList.setTradeStatus(1);// 成功状态
						// 插入交易明细
						boolean isAccountListUpdateFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;

						if (isAccountListUpdateFlag) {
							// 更新成功
							// 提交事务
							this.transactionManager.commit(txStatus);
							// 如果需要短信
							Users users = usersMapper.selectByPrimaryKey(userId);
							// 可以发送充值短信时
							if (users != null && users.getRechargeSms() != null && users.getRechargeSms() == 0) {
								// 替换参数
								Map<String, String> replaceMap = new HashMap<String, String>();
								replaceMap.put("val_amount", txAmount.toString());
								replaceMap.put("val_fee", "0");
								UsersInfo info = getUsersInfoByUserId(userId);
								replaceMap.put("val_name", info.getTruename().substring(0, 1));
								replaceMap.put("val_sex", info.getSex() == 2 ? "女士" : "先生");
								SmsMessage smsMessage = new SmsMessage(userId, replaceMap, null, null, MessageDefine.SMSSENDFORUSER, null, CustomConstants.PARAM_TPL_CHONGZHI_SUCCESS,
										CustomConstants.CHANNEL_TYPE_NORMAL);
								AppMsMessage appMsMessage = new AppMsMessage(userId, replaceMap, null, MessageDefine.APPMSSENDFORUSER, CustomConstants.JYTZ_TPL_CHONGZHI_SUCCESS);
								smsProcesser.gather(smsMessage);
								appMsProcesser.gather(appMsMessage);
							}else{
							 // 替换参数
                                Map<String, String> replaceMap = new HashMap<String, String>();
                                replaceMap.put("val_amount", txAmount.toString());
                                replaceMap.put("val_fee", "0");
                                UsersInfo info = getUsersInfoByUserId(userId);
                                replaceMap.put("val_name", info.getTruename().substring(0, 1));
                                replaceMap.put("val_sex", info.getSex() == 2 ? "女士" : "先生");
                                AppMsMessage appMsMessage = new AppMsMessage(userId, replaceMap, null, MessageDefine.APPMSSENDFORUSER, CustomConstants.JYTZ_TPL_CHONGZHI_SUCCESS);
                                appMsProcesser.gather(appMsMessage);
							}
							return jsonMessage("充值成功!", "0");
						} else {
							// 回滚事务
							transactionManager.rollback(txStatus);
							// 查询充值交易状态
							accountRecharges = accountRechargeMapper.selectByExample(example);// 查询充值记录
							accountRecharge = accountRecharges.get(0);
							if (RECHARGE_STATUS_SUCCESS == accountRecharge.getStatus()) {
								return jsonMessage("充值成功!", "0");
							} else {
								// 账户数据过期，请查看交易明细 跳转中间页
								return jsonMessage("账户数据过期，请查看交易明细", "1");
							}
						}
					} catch (Exception e) {
						// 回滚事务
						transactionManager.rollback(txStatus);
						System.err.println(e);
					}
				}
			} else {
				System.out.println("充值失败,未查询到相应的充值记录." + "用户ID:" + userId + ",充值订单号:" + orderId);
				return jsonMessage("充值失败,未查询到相应的充值记录", "1");
			}
		} else {
			// 更新订单信息
			AccountRechargeExample example = new AccountRechargeExample();
			example.createCriteria().andNidEqualTo(orderId);
			List<AccountRecharge> accountRecharges = this.accountRechargeMapper.selectByExample(example);
			if (accountRecharges != null && accountRecharges.size() == 1) {
				AccountRecharge accountRecharge = accountRecharges.get(0);
				if (RECHARGE_STATUS_WAIT == accountRecharge.getStatus()) {
					// 更新处理状态
					accountRecharge.setStatus(RECHARGE_STATUS_FAIL);// 充值状态:0:初始,1:充值中,2:充值成功,3:充值失败
					accountRecharge.setUpdateTime(nowTime);
					accountRecharge.setMessage(errorMsg);
					accountRecharge.setAccountId(accountId);// 电子账户
					accountRecharge.setBankSeqNo(txDate + txTime + seqNo);// 交易流水号
					this.accountRechargeMapper.updateByPrimaryKeySelective(accountRecharge);
				}
			}
			return jsonMessage(errorMsg, "1");
		}
		return null;
	}

	/**
	 * 拼装返回信息
	 * 
	 * @param errorDesc
	 * @param error
	 * @return
	 */
	public JSONObject jsonMessage(String errorDesc, String error) {
		JSONObject jo = null;
		if (Validator.isNotNull(errorDesc)) {
			jo = new JSONObject();
			jo.put("error", error);
			jo.put("errorDesc", errorDesc);
		}
		return jo;
	}

	/**
	 * 根据银行卡号,用户Id检索用户银行卡信息
	 * 
	 * @param userId
	 * @param cardNo
	 * @return
	 */
	private BankCard getBankCardByCardNo(Integer userId, String cardNo) {
		BankCardExample example = new BankCardExample();
		BankCardExample.Criteria cra = example.createCriteria();
		cra.andUserIdEqualTo(userId);// 用户Id
		cra.andCardNoEqualTo(cardNo);// 银行卡号
		cra.andStatusEqualTo(1);// 银行卡是否有效 0无效 1有效
		List<BankCard> bankCardList = bankCardMapper.selectByExample(example);
		if (bankCardList != null && bankCardList.size() > 0) {
			return bankCardList.get(0);
		}
		return null;
	}

	/**
	 * 根据用户ID查询企业用户信息
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public CorpOpenAccountRecord getCorpOpenAccountRecord(Integer userId) {
		CorpOpenAccountRecordExample example = new CorpOpenAccountRecordExample();
		CorpOpenAccountRecordExample.Criteria cra = example.createCriteria();
		cra.andUserIdEqualTo(userId);
		cra.andIsBankEqualTo(1);// 江西银行
		List<CorpOpenAccountRecord> list = this.corpOpenAccountRecordMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 发送验证码
	 *
	 * @param mobile
	 * @param mobile
	 *
	 * @return Map<String, Object> {success: 1,message:调用验证码接口成功,srvAuthCode:
	 *         srvAuthCode}
	 */
	@Override
	public BankCallBean sendRechargeOnlineSms(Integer userId, String cardNo, String mobile, String client) {
		// 调用存管接口发送验证码
		BankCallBean bean = new BankCallBean();
		bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
		bean.setTxCode(BankCallMethodConstant.TXCODE_SMSCODE_APPLY);// 交易代码cardBind
		bean.setInstCode(PropUtils.getSystem(BankCallConstant.BANK_INSTCODE));// 机构代码
		bean.setBankCode(PropUtils.getSystem(BankCallConstant.BANK_BANKCODE));// 银行代码
		bean.setTxDate(GetOrderIdUtils.getOrderDate());// 交易日期
		bean.setTxTime(GetOrderIdUtils.getOrderTime());// 交易时间
		bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
		bean.setChannel(client);// 交易渠道000001手机APP 000002网页
		bean.setMobile(mobile);
		// 当reqType=2时必填 
		String srvTxCode= "directRechargeOnline";
		bean.setReqType("2");
		bean.setSrvTxCode(srvTxCode); // directRechargeOnline
		bean.setCardNo(cardNo); //绑定的银行卡号
		
		
		bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));// 订单号
		bean.setLogUserId(String.valueOf(userId));// 请求用户名
		try {
			BankCallBean mobileBean = BankCallUtils.callApiBg(bean);
			if (Validator.isNull(mobileBean)) {
				return null;
			}
			// 短信发送返回结果码
			String retCode = mobileBean.getRetCode();
			if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode) && !BankCallConstant.RESPCODE_MOBILE_REPEAT.equals(retCode)) {
				return null;
			}
			if (Validator.isNull(mobileBean.getSrvAuthCode()) && !BankCallConstant.RESPCODE_MOBILE_REPEAT.equals(retCode)) {
				return null;
			}
			// 业务授权码
			String srvAuthCode = mobileBean.getSrvAuthCode();
			String smsSeq = mobileBean.getSmsSeq();
			if (Validator.isNotNull(mobileBean.getSrvAuthCode())) {
				boolean smsFlag = this.updateBankSmsLog(userId, srvTxCode,smsSeq, srvAuthCode);
				if (smsFlag) {
					return mobileBean;
				} else {
					return null;
				}
			} else {
				// 保存用户开户日志
				srvAuthCode = this.selectBankSmsLog(userId, srvTxCode, srvAuthCode);
				if (Validator.isNull(srvAuthCode)) {
					return null;
				} else {
					mobileBean.setSrvAuthCode(srvAuthCode);
					mobileBean.setSmsSeq(smsSeq);
					return mobileBean;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 初始化充值信息
	 * @param bean
	 * @return
	 */
	@Override
	public int insertRechargeOnlineInfo(BankCallBean bean) {
		int ret = 0;
		String ordId = bean.getLogOrderId() == null ? "" : bean.getLogOrderId(); // 订单号
		AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
		accountRechargeExample.createCriteria().andNidEqualTo(ordId == null ? "" : ordId);
		List<AccountRecharge> listAccountRecharge = this.accountRechargeMapper.selectByExample(accountRechargeExample);
		if (listAccountRecharge != null && listAccountRecharge.size() > 0) {
			return ret;
		}
		int nowTime = GetDate.getNowTime10(); // 当前时间
		// 银行卡号
		String cardNo = bean.getCardNo();
		// 根据银行卡号检索银行卡信息
		BankCard bankCard = this.getBankCardByCardNo(Integer.parseInt(bean.getLogUserId()), cardNo);
		BigDecimal money = new BigDecimal(bean.getTxAmount()); // 充值金额
		AccountRecharge record = new AccountRecharge();
		record.setNid(bean.getLogOrderId()); // 订单号
		record.setUserId(Integer.parseInt(bean.getLogUserId())); // 用户ID
		record.setUsername(bean.getLogUserName());// 用户 名
		record.setTxDate(Integer.parseInt(bean.getTxDate()));// 交易日期
		record.setTxTime(Integer.parseInt(bean.getTxTime()));// 交易时间
		record.setSeqNo(Integer.parseInt(bean.getSeqNo())); // 交易流水号
		record.setBankSeqNo(bean.getTxDate() + bean.getTxTime() + bean.getSeqNo()); // 交易日期+交易时间+交易流水号
		record.setStatus(RECHARGE_STATUS_WAIT); // 充值状态:0:初始,1:充值中,2:充值成功,3:充值失败
		record.setAccountId(bean.getAccountId());// 电子账号
		record.setMoney(money); // 金额
		record.setCardid(cardNo);// 银行卡号
		record.setFeeFrom(null);// 手续费扣除方式
		record.setFee(BigDecimal.ZERO); // 费用
		record.setDianfuFee(BigDecimal.ZERO);// 垫付费用
		record.setBalance(money); // 实际到账余额
		record.setPayment(bankCard == null ? "" : bankCard.getBank()); // 所属银行
		record.setGateType("QP"); // 网关类型：QP快捷支付
		record.setType(1); // 类型.1网上充值.0线下充值
		record.setRemark("快捷充值");// 备注
		record.setCreateTime(nowTime);
		record.setOperator(bean.getLogUserId());
		record.setAddtime(String.valueOf(nowTime));
		record.setAddip(bean.getUserIP());
		record.setClient(bean.getLogClient()); // 0pc
		record.setIsBank(1);// 资金托管平台 0:汇付,1:江西银行
		// 插入用户充值记录表
		return this.accountRechargeMapper.insertSelective(record);
	}

	/**
	 * 短信充值处理
	 * 
	 * @param bean
	 * @param params
	 * @return
	 */
	@Override
	public JSONObject handleRechargeOnlineInfo(BankCallBean bean, Map<String, String> params) {
		TransactionStatus txStatus = null;
		// 用户Id
		Integer userId = Integer.parseInt(bean.getLogUserId());
		// 充值订单号
		String orderId = bean.getLogOrderId();
		// 当前时间
		int nowTime = GetDate.getNowTime10();
		// 错误信息
		String errorMsg = this.getBankRetMsg(bean.getRetCode());
		// ip
		String ip = params.get("ip");// ip
		String mobile = params.get("mobile");
		// del by liuyang 20180119 银行文件对账功能修改 start
//		// 交易日期
//		String txDate = bean.getTxDate();
//		// 交易时间
//		String txTime = bean.getTxTime();
//		// 交易流水号
//		String seqNo = bean.getSeqNo();
		// del by liuyang 20180119 银行文件对账功能修改 end
		// 电子账户
		String accountId = bean.getAccountId();
		// 充值成功
		if (BankCallStatusConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
			// 查询用户账户,为了版本控制,必须把查询用户账户放在最前面
			AccountExample accountExample = new AccountExample();
			AccountExample.Criteria accountCriteria = accountExample.createCriteria();
			accountCriteria.andUserIdEqualTo(userId);
			Account account = this.accountMapper.selectByExample(accountExample).get(0);
			// 查询充值记录
			AccountRechargeExample example = new AccountRechargeExample();
			example.createCriteria().andNidEqualTo(orderId);
			List<AccountRecharge> accountRecharges = accountRechargeMapper.selectByExample(example);// 查询充值记录
			AccountRecharge accountRecharge = accountRecharges.get(0);
			// 如果没有充值记录
			if (accountRecharge != null) {
				// 交易金额
				BigDecimal txAmount = bean.getBigDecimal(BankCallConstant.PARAM_TXAMOUNT);
				// 判断充值记录状态
				if (accountRecharge.getStatus() == RECHARGE_STATUS_SUCCESS) {
					// 如果已经是成功状态
					return jsonMessage("充值成功", "0");
				} else {
					// 如果不是成功状态
					try {
						// 开启事务
						txStatus = this.transactionManager.getTransaction(transactionDefinition);
						// 将数据封装更新至充值记录
						AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
						accountRechargeExample.createCriteria().andNidEqualTo(orderId).andStatusEqualTo(accountRecharge.getStatus());
						accountRecharge.setFee(BigDecimal.ZERO); // 费用
						accountRecharge.setDianfuFee(BigDecimal.ZERO);// 商户垫付金额
						accountRecharge.setBalance(txAmount);// 实际到账余额
						accountRecharge.setUpdateTime(nowTime);// 更新时间
						accountRecharge.setStatus(RECHARGE_STATUS_SUCCESS);// 充值状态:0:初始,1:充值中,2:充值成功,3:充值失败
						accountRecharge.setAccountId(accountId);// 电子账户
						// del by liuyang 20180119 银行文件对账功能修改 start
						// accountRecharge.setBankSeqNo(txDate + txTime + seqNo);// 交易流水号
						// del by liuyang 20180119 银行文件对账功能修改 end
						boolean isAccountRechargeFlag = this.accountRechargeMapper.updateByExampleSelective(accountRecharge, accountRechargeExample) > 0 ? true : false;
						if (!isAccountRechargeFlag) {
							throw new Exception("充值后,回调更新充值记录表失败!" + "充值订单号:" + orderId + ".用户ID:" + userId);
						}
						Account newAccount = new Account();
						// 更新账户信息
						newAccount.setUserId(userId);// 用户Id
						newAccount.setBankTotal(txAmount); // 累加到账户总资产
						newAccount.setBankBalance(txAmount); // 累加可用余额
						newAccount.setBankBalanceCash(txAmount);// 银行账户可用户
						boolean isAccountUpdateFlag = this.adminAccountCustomizeMapper.updateBankRechargeSuccess(newAccount) > 0 ? true : false;
						if (!isAccountUpdateFlag) {
							throw new Exception("提现后,更新用户Account表失败!");
						}
						// 重新获取用户账户信息
						account = this.getAccount(userId);
						// 生成交易明细
						AccountList accountList = new AccountList();
						accountList.setNid(orderId);
						accountList.setUserId(userId);
						accountList.setAmount(txAmount);
						// mod by liuyang 20180119 银行文件对账功能修改 start
						accountList.setTxDate(accountRecharge.getTxDate());// 交易日期
						accountList.setTxTime(accountRecharge.getTxTime());// 交易时间
						accountList.setSeqNo(String.valueOf(accountRecharge.getSeqNo()));// 交易流水号
						accountList.setBankSeqNo((accountRecharge.getTxDate() + accountRecharge.getTxTime() + String.valueOf(accountRecharge.getSeqNo())));
						// mod by liuyang 20180119 银行文件对账功能修改 end
						accountList.setType(1);
						accountList.setTrade("recharge");
						accountList.setTradeCode("balance");
						accountList.setAccountId(accountId);
						accountList.setBankTotal(account.getBankTotal()); // 银行总资产
						accountList.setBankBalance(account.getBankBalance()); // 银行可用余额
						accountList.setBankFrost(account.getBankFrost());// 银行冻结金额
						accountList.setBankWaitCapital(account.getBankWaitCapital());// 银行待还本金
						accountList.setBankWaitInterest(account.getBankWaitInterest());// 银行待还利息
						accountList.setBankAwaitCapital(account.getBankAwaitCapital());// 银行待收本金
						accountList.setBankAwaitInterest(account.getBankAwaitInterest());// 银行待收利息
						accountList.setBankAwait(account.getBankAwait());// 银行待收总额
						accountList.setBankInterestSum(account.getBankInterestSum()); // 银行累计收益
						accountList.setBankInvestSum(account.getBankInvestSum());// 银行累计出借
						accountList.setBankWaitRepay(account.getBankWaitRepay());// 银行待还金额
						accountList.setPlanBalance(account.getPlanBalance());//汇计划账户可用余额
						accountList.setPlanFrost(account.getPlanFrost());
						accountList.setTotal(account.getTotal());
						accountList.setBalance(account.getBalance());
						accountList.setFrost(account.getFrost());
						accountList.setAwait(account.getAwait());
						accountList.setRepay(account.getRepay());
						accountList.setRemark("快捷充值");
						accountList.setCreateTime(nowTime);
						accountList.setBaseUpdate(nowTime);
						accountList.setOperator(userId + "");
						accountList.setIp(ip);
						accountList.setIsUpdate(0);
						accountList.setBaseUpdate(0);
						accountList.setInterest(null);
						accountList.setWeb(0);
						accountList.setIsBank(1);// 是否是银行的交易记录 0:否 ,1:是
						accountList.setCheckStatus(0);// 对账状态0：未对账 1：已对账
						accountList.setTradeStatus(1);// 成功状态
						// 插入交易明细
						boolean isAccountListUpdateFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;

						if (isAccountListUpdateFlag) {
							// 更新成功
							// 提交事务
							this.transactionManager.commit(txStatus);
							
							// updateBankMobile 除了异常不管
							updateBankMobile(userId,accountRecharge.getCardid(),mobile);
							
							// 如果需要短信
							Users users = usersMapper.selectByPrimaryKey(userId);
							// 可以发送充值短信时
							if (users != null && users.getRechargeSms() != null && users.getRechargeSms() == 0) {
								// 替换参数
								Map<String, String> replaceMap = new HashMap<String, String>();
								replaceMap.put("val_amount", txAmount.toString());
								replaceMap.put("val_fee", "0");
								UsersInfo info = getUsersInfoByUserId(userId);
								replaceMap.put("val_name", info.getTruename().substring(0, 1));
								replaceMap.put("val_sex", info.getSex() == 2 ? "女士" : "先生");
								SmsMessage smsMessage = new SmsMessage(userId, replaceMap, null, null, MessageDefine.SMSSENDFORUSER, null, CustomConstants.PARAM_TPL_CHONGZHI_SUCCESS,
										CustomConstants.CHANNEL_TYPE_NORMAL);
								AppMsMessage appMsMessage = new AppMsMessage(userId, replaceMap, null, MessageDefine.APPMSSENDFORUSER, CustomConstants.JYTZ_TPL_CHONGZHI_SUCCESS);
								smsProcesser.gather(smsMessage);
								appMsProcesser.gather(appMsMessage);
							}
							CommonSoaUtils.listedTwoRecharge(userId, txAmount);
							return jsonMessage("充值成功!", "0");
						} else {
							// 回滚事务
							transactionManager.rollback(txStatus);
							// 查询充值交易状态
							accountRecharges = accountRechargeMapper.selectByExample(example);// 查询充值记录
							accountRecharge = accountRecharges.get(0);
							if (RECHARGE_STATUS_SUCCESS == accountRecharge.getStatus()) {
								return jsonMessage("充值成功!", "0");
							} else {
								// 账户数据过期，请查看交易明细 跳转中间页
								return jsonMessage("账户数据过期，请查看交易明细", "1");
							}
						}
					} catch (Exception e) {
						// 回滚事务
						transactionManager.rollback(txStatus);
						System.err.println(e);
					}
				}
			} else {
				System.out.println("充值失败,未查询到相应的充值记录." + "用户ID:" + userId + ",充值订单号:" + orderId);
				return jsonMessage("充值失败,未查询到相应的充值记录", "1");
			}
		} else {
			// 更新订单信息
			AccountRechargeExample example = new AccountRechargeExample();
			example.createCriteria().andNidEqualTo(orderId);
			List<AccountRecharge> accountRecharges = this.accountRechargeMapper.selectByExample(example);
			if (accountRecharges != null && accountRecharges.size() == 1) {
				AccountRecharge accountRecharge = accountRecharges.get(0);
				if (RECHARGE_STATUS_WAIT == accountRecharge.getStatus()) {
					// 更新处理状态
					accountRecharge.setStatus(RECHARGE_STATUS_FAIL);// 充值状态:0:初始,1:充值中,2:充值成功,3:充值失败
					accountRecharge.setUpdateTime(nowTime);
					accountRecharge.setMessage(errorMsg);
					accountRecharge.setAccountId(accountId);// 电子账户
					// del by liuyang 20180119 银行文件对账功能修改 start
					// accountRecharge.setBankSeqNo(txDate + txTime + seqNo);// 交易流水号
					// del by liuyang 20180119 银行文件对账功能修改 end
					this.accountRechargeMapper.updateByPrimaryKeySelective(accountRecharge);
				}
			}
			return jsonMessage(errorMsg, "1");
		}
		return null;
	}

	/**
	 * 查询用户的短信序列号
	 * 
	 * @param userId
	 * @param srvTxCode
	 * @param srvTxCode
	 * @return
	 */
	public String selectBankSmsSeq(Integer userId, String srvTxCode) {
		BankSmsAuthCodeExample example = new BankSmsAuthCodeExample();
		example.createCriteria().andUserIdEqualTo(userId).andSrvTxCodeEqualTo(srvTxCode);
		List<BankSmsAuthCode> smsAuthCodeList = bankSmsAuthCodeMapper.selectByExample(example);
		if (smsAuthCodeList != null && smsAuthCodeList.size() == 1) {
			BankSmsAuthCode smsAuthCode = smsAuthCodeList.get(0);
			return smsAuthCode.getSmsSeq();
		}
		return null;
	}
	
	/**
	 * 更新银行预留手机号
	 * @param userId
	 * @param cardNo
	 * @param newMobile
	 */
	private void updateBankMobile(Integer userId, String cardNo, String newMobile){

		// 成功后更新银行预留手机号码
		if (StringUtils.isBlank(newMobile) || StringUtils.isBlank(cardNo)) {
			return;
		}
		BankCardExample bankCardExample = new BankCardExample();
		bankCardExample.createCriteria().andUserIdEqualTo(userId).andCardNoEqualTo(cardNo).andStatusEqualTo(1);
		List<BankCard> bankCards = this.bankCardMapper.selectByExample(bankCardExample);
		if (bankCards != null && bankCards.size() == 1) {
			BankCard bankCard = bankCards.get(0);
			
			if(bankCard.getMobile() == null || !bankCard.getMobile().equals(newMobile)){
				bankCard.setMobile(newMobile);
				bankCard.setUpdateTime(new Date());
				bankCard.setUpdateUserId(userId);
				
				this.bankCardMapper.updateByPrimaryKeySelective(bankCard);
			}
			
		}
		
	}

	/**
	 * 查询充值是否成功
	 * @param userId
	 * @param nid
	 * @return
	 */
	@Override
	public AccountRecharge selectRechargeInfo(int userId, String nid) {
		AccountRecharge accountRecharge = null;
		AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
		accountRechargeExample.createCriteria().andNidEqualTo(nid).andUserIdEqualTo(userId);
		List<AccountRecharge> listAccountRecharge = this.accountRechargeMapper.selectByExample(accountRechargeExample);
		if (listAccountRecharge != null && listAccountRecharge.size() > 0) {
			accountRecharge = listAccountRecharge.get(0);
		}
		
		return accountRecharge;
	}


	/**
	 * 获取24小时内充值记录
	 * @return 充值记录
	 * @author lisheng
	 */
	@Override
	public List<AccountRecharge> getTodayRecharge(Integer userId) {
		Integer nowtime = GetDate.getNowTime10();
		Integer addtime = GetDate.countDate(GetDate.getNowTime10(),5,-1);
		AccountRechargeExample example = new AccountRechargeExample();
		AccountRechargeExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId).andAddtimeBetween(String.valueOf(addtime),String.valueOf(nowtime)).andStatusEqualTo(2);
		List<AccountRecharge> list = accountRechargeMapper.selectByExample(example);
		return list;
	}
	/**
	 * 获取用户出借记录
	 * @param userId
	 * @return 用户出借记录
	 * @author lisheng
	 */
	@Override
	public int getBorrowTender(Integer userId) {
		int tenderRecord = borrowCustomizeMapper.countInvest(userId);
		return tenderRecord;
	}
	/**
	 * 获取用户角色
	 * @param userId
	 * @return 用户角色
	 * @author lisheng
	 */
	@Override
	public String getUserRoId(Integer userId) {
		UsersInfoExample usersInfoExample = new UsersInfoExample();
		UsersInfoExample.Criteria usersInfoExampleCriteria = usersInfoExample.createCriteria();
		usersInfoExampleCriteria.andUserIdEqualTo(userId);
		List<UsersInfo> userInfos = usersInfoMapper.selectByExample(usersInfoExample);
		if(userInfos!=null&&!userInfos.isEmpty()){
			UsersInfo usersInfo = userInfos.get(0);
			Integer roleId = usersInfo.getRoleId();
			return roleId+"";
		}
		return null;
	}

	// 江西银行绑卡接口修改 update by wj 2018-5-17 start
	/**
	 * 判断江西银行绑卡使用新/旧接口
	 * @param interfaceType
	 * @author: WangJun
	 * @return 0旧接口/1新接口
	 */
	@Override
	public Integer getBandCardBindUrlType(String interfaceType) {
		BankInterfaceExample bankInterfaceExample = new BankInterfaceExample();
		BankInterfaceExample.Criteria criteria= bankInterfaceExample.createCriteria();
		criteria.andInterfaceTypeEqualTo(interfaceType);
		criteria.andIsDeleteEqualTo(0);
		criteria.andIsUsableEqualTo(1);
		bankInterfaceExample.setLimitStart(0);
		bankInterfaceExample.setLimitEnd(1);
		bankInterfaceExample.setOrderByClause("`update_time` DESC");
		List<BankInterface> bankInterfaces = bankInterfaceMapper.selectByExample(bankInterfaceExample);
		if(bankInterfaces.size() > 0){
			//返回接口类型
			return bankInterfaces.get(0).getInterfaceStatus();
		}else {
			//默认绑卡旧接口
			return 0;
		}
	}
	// 江西银行绑卡接口修改 update by wj 2018-5-17 end

	/**
	 * 发送神策数据统计MQ
	 *
	 * @param sensorsDataBean
	 */
	@Override
	public void sendSensorsDataMQ(SensorsDataBean sensorsDataBean) {
		// 加入到消息队列
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("eventCode", sensorsDataBean.getEventCode());
		params.put("orderId",sensorsDataBean.getOrderId());
		params.put("userId",sensorsDataBean.getUserId());
		params.put("mqMsgId", GetCode.getRandomCode(10));
		rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_COUPON, RabbitMQConstants.ROUTINGKEY_SENSORS_DATA_RECHARGE_RESULT, JSONObject.toJSONString(params));
	}
}
