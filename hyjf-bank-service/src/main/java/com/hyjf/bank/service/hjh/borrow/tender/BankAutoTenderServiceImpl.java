package com.hyjf.bank.service.hjh.borrow.tender;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.bank.service.BaseServiceImpl;
import com.hyjf.bank.service.fdd.fddgeneratecontract.FddGenerateContractBean;
import com.hyjf.bank.service.hjh.common.HjhCommonService;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.calculate.CalculatesUtil;
import com.hyjf.common.calculate.FinancingServiceChargeUtils;
import com.hyjf.common.calculate.HJHServiceFeeUtils;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.mybatis.model.auto.*;
import com.hyjf.mybatis.model.customize.EmployeeCustomize;
import com.hyjf.mybatis.model.customize.HjhAccedeCustomize;
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
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 汇计划自动投资/承接
 * @author lb
 */
@Service
public class BankAutoTenderServiceImpl extends BaseServiceImpl implements BankAutoTenderService{

	Logger _log = LoggerFactory.getLogger(BankAutoTenderServiceImpl.class);
//	@Autowired
//	@Qualifier("smsProcesser")
//	private MessageProcesser smsProcesser;

	@Autowired
	@Qualifier("myAmqpTemplate")
	private RabbitTemplate rabbitTemplate;

	// 分期数据结果
	protected final String ASSIGN_RESULT = "assignResult";
	// 承接总金额
	protected final String ASSIGN_ACCOUNT = "assignAccount";
	// 承接本金
	protected final String ASSIGN_CAPITAL = "assignCapital";
	// 承接利息
	protected final String ASSIGN_INTEREST = "assignInterest";
	// 承接支付金额
	protected final String ASSIGN_PAY = "assignPay";
	// 承接垫付利息
	protected final String ASSIGN_ADVANCEMENT_INTEREST = "assignAdvanceMentInterest";
	// 承接人应收取延期利息
	protected final String ASSIGN_REPAY_DELAY_INTEREST = "assignRepayDelayInterest";
	// 承接人应收取逾期利息
	protected final String ASSIGN_REPAY_LATE_INTEREST = "assignRepayLateInterest";
	// 分期本金
	protected final String ASSIGN_PERIOD_CAPITAL = "assignPeriodCapital";
	// 分期利息
	protected final String ASSIGN_PERIOD_INTEREST = "assignPeriodInterest";
	// 分期垫付利息
	protected final String ASSIGN_PERIOD_ADVANCEMENT_INTEREST = "assignPeriodAdvanceMentInterest";
	// 分期承接人应收取延期利息
	protected final String ASSIGN_PERIOD_REPAY_DELAY_INTEREST = "assignPeriodRepayDelayInterest";
	// 分期承接人应收取延期利息
	protected final String ASSIGN_PERIOD_REPAY_LATE_INTEREST = "assignPeriodRepayLateInterest";
	// 承接服务费
	protected final String SERVICE_FEE = "serviceFee";
	// add 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 start
	// 承接服务费率
	protected final String SERVICE_APR = "serviceApr";
	// add 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 end
	/** 出让标识 */
	public final String HJH_SELL = "sell";
	/** 承接标识 */
	public final String HJH_ASSIGN = "assign";

	public static JedisPool pool = RedisUtils.getPool();
	/**
	 * 汇消费的项目类型编号
	 */
	public static String PROJECT_TYPE_HXF = "8";

	@Autowired
	HjhCommonService hjhCommonService;

	/**
	 * 查询计划加入明细
	 * @return
	 */
	@Override
	public List<HjhAccedeCustomize> selectPlanJoinList() {
		//取得 自动投标中，可投金额>0 或者 锁定中，可投金额>0，计划订单到期前3天以前 的 计划订单
		Map<String,Object> paramMap = new HashMap<String,Object>();
		// 计划订单到期前3天不进行复投
		paramMap.put("liquidateDays", CustomConstants.HJH_LIQUIDATE_DAYS);
//		paramMap.put("minTenderAccount", CustomConstants.HJH_TENDER_MIN_ACCOUNT);
		paramMap.put("minRetenderAccount", CustomConstants.HJH_RETENDER_MIN_ACCOUNT);
		List<HjhAccedeCustomize> list = this.batchHjhAccedeCustomizeMapper.selectHjhAutoTenderList(paramMap);
		return list;
	}

	/**
	 * 查询计划by Plannid
	 *
	 * @param planNid
	 * @return
	 */
	@Override
	public HjhPlan selectHjhPlanByPlanNid(String planNid) {
		HjhPlanExample example = new HjhPlanExample();
		HjhPlanExample.Criteria criteria = example.createCriteria();
		criteria.andPlanNidEqualTo(planNid);
		List<HjhPlan> list = this.hjhPlanMapper.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}else {
			return null;
		}
	}

	/**
	 * 查询计划订单by 加入计划订单号
	 *
	 * @param accedeOrderId
	 * @return
	 */
	@Override
	public HjhAccede selectHjhAccedeByAccedeOrderId(String accedeOrderId) {
		HjhAccedeExample example = new HjhAccedeExample();
		HjhAccedeExample.Criteria criteria = example.createCriteria();
		criteria.andAccedeOrderIdEqualTo(accedeOrderId);
		List<HjhAccede> list = this.hjhAccedeMapper.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}else {
			return null;
		}
	}

	/**
	 * 插入 自动投资临时表
	 * @param borrow
	 * @param hjhAccede
	 * @param ketouplanAmoust
	 * @param hjhUserAuth
	 * @return
	 */
	@Override
	public Integer insertBorrowTmp(Borrow borrow, HjhDebtCredit credit,HjhAccede hjhAccede, BigDecimal ketouplanAmoust,HjhUserAuth hjhUserAuth,BankCallBean bean,String borrowFlag, boolean isLast) {

		HjhPlanBorrowTmp record = new HjhPlanBorrowTmp();
		record.setAccedeOrderId(hjhAccede.getAccedeOrderId());
		record.setPlanNid(hjhAccede.getPlanNid());
		record.setUserId(hjhAccede.getUserId());
		record.setUserName(hjhAccede.getUserName());
		record.setAccedeAccount(hjhAccede.getAccedeAccount());
		record.setAlreadyInvest(hjhAccede.getAlreadyInvest());
		record.setAccount(ketouplanAmoust);
		if (borrowFlag.equals(RedisConstants.HJH_BORROW_CREDIT)) {
			// 汇计划自动投资债转
			record.setInstCode(credit.getInstCode());
			record.setBorrowNid(credit.getCreditNid());//债转编号
			record.setBorrowAccount(credit.getCreditCapital());//债转总本金
			record.setBorrowPeriod(credit.getRemainDays());//剩余天数
			record.setBorrowStyle(credit.getBorrowStyle());
			record.setBorrowType(1);
			record.setSellUserId(credit.getUserId());
			record.setSellOrderId(credit.getSellOrderId());
		}else {
			// 汇计划自动投资原始标的
			record.setInstCode(borrow.getInstCode());
			record.setBorrowNid(borrow.getBorrowNid());
			record.setBorrowAccount(borrow.getAccount());
			record.setBorrowPeriod(borrow.getBorrowPeriod());
			record.setBorrowStyle(borrow.getBorrowStyle());
			record.setBorrowType(0);
			record.setSellUserId(null);
			record.setSellOrderId(null);
		}
		record.setStatus(0);
		record.setOrderId(bean.getOrderId());
		record.setIsLast(isLast);
		record.setCreateUserId(1);
		int nowTime = GetDate.getNowTime10();
		record.setCreateTime(nowTime);
		record.setUpdateUserId(1);
		record.setUpdateTime(nowTime);
		_log.info("插入hjhPlanBorrowTmp表：" + JSON.toJSONString(record));
		hjhPlanBorrowTmpMapper.insertSelective(record);

		return record.getId();

	}

	/**
	 * 删除 自动投资临时表
	 * @param borrowNid
	 * @param hjhAccede
	 * @return
	 */
	@Override
	public boolean deleteBorrowTmp(String borrowNid, HjhAccede hjhAccede, BankCallBean bean) {

		HjhPlanBorrowTmpExample hjhPlanBorrowTmpExample = new HjhPlanBorrowTmpExample();
		HjhPlanBorrowTmpExample.Criteria crt = hjhPlanBorrowTmpExample.createCriteria();
		crt.andAccedeOrderIdEqualTo(hjhAccede.getAccedeOrderId());
		crt.andBorrowNidEqualTo(borrowNid);
		crt.andUserIdEqualTo(hjhAccede.getUserId());
		crt.andOrderIdEqualTo(bean.getOrderId());

		return this.hjhPlanBorrowTmpMapper.deleteByExample(hjhPlanBorrowTmpExample)>0?true:false;

	}

	@Override
	public boolean deleteBorrowTmp(String borrowNid, String accedeOrderId) {
		HjhPlanBorrowTmpExample hjhPlanBorrowTmpExample = new HjhPlanBorrowTmpExample();
		HjhPlanBorrowTmpExample.Criteria crt = hjhPlanBorrowTmpExample.createCriteria();
		crt.andAccedeOrderIdEqualTo(accedeOrderId);
		crt.andBorrowNidEqualTo(borrowNid);
		return this.hjhPlanBorrowTmpMapper.deleteByExample(hjhPlanBorrowTmpExample)>0?true:false;

	}

	@Override
	public boolean deleteHjhPlanBorrowTmpByOrderId(String orderId) {
		HjhPlanBorrowTmpExample hjhPlanBorrowTmpExample = new HjhPlanBorrowTmpExample();
		HjhPlanBorrowTmpExample.Criteria crt = hjhPlanBorrowTmpExample.createCriteria();
		crt.andAccedeOrderIdEqualTo(orderId);
		return this.hjhPlanBorrowTmpMapper.deleteByExample(hjhPlanBorrowTmpExample)>0?true:false;
	}

	/**
	 *
	 * 用户投资一个标的
	 *
	 * @param borrow
	 * @param bean
	 * @return
	 * @author Administrator
	 */
	@Override
	public boolean updateBorrow(Borrow borrowR,HjhAccede hjhAccedeR, BigDecimal ketouplanAmoust,BankCallBean bean){
		boolean result = false;
		// 防范主从数据库不同步，取读库传参改从写库拉数据
		Borrow borrow = this.selectBorrowByNid(borrowR.getBorrowNid());
		HjhAccede hjhAccede = this.selectHjhAccedeByAccedeOrderId(hjhAccedeR.getAccedeOrderId());
		// add 主库读取修改后异常处理问题修正 liubin 20180906 start
		// OrderStatus = 0 or 80 or 90 变更成 0
		_log.info("前OrderStatus："+hjhAccede.getOrderStatus());
		hjhAccede.setOrderStatus(hjhAccede.getOrderStatus() % 10);
		_log.info("后OrderStatus："+hjhAccede.getOrderStatus());
		// add 主库读取修改后异常处理问题修正 liubin 20180906 end

		String txAmount = bean.getTxAmount();// 借款金额
		BigDecimal accountDecimal = new BigDecimal(txAmount);// 冻结前验证

		//先插入冻结表
		insertFreezeList(borrow, hjhAccede, bean);

		// 判定该笔投资是投资还是复投
		String hjhProcess = null;
		if (hjhAccede.getOrderStatus() == 0) {
			// 投资
			hjhProcess = CustomConstants.HJH_PROCESS_B;
		} else {
			// 复投
			hjhProcess = CustomConstants.HJH_PROCESS_BF;
		}
		// 汇计划重算更新用户账户信息表(投资人)
		hjhCommonService.updateAccountForHjh(hjhProcess, hjhAccede.getUserId(), accountDecimal, null);
		// 汇计划重算更新汇计划加入明细表(投资人)
		hjhCommonService.updateHjhAccedeForHjh(hjhProcess, hjhAccede.getId(), accountDecimal, null, null);

		// 投资表
		insertBorrowTender(borrow, hjhAccede, bean);

		// 交易明细
		insertAccountList(borrow, hjhAccede, bean);

		// 满标处理
		updateBorrowFull(borrow, hjhAccede, bean);

		// 删除临时表
		deleteBorrowTmp(borrow.getBorrowNid(), hjhAccede, bean);

		// 复投时，减去该计划的开放额度
		updateAvailableInvestAccount(hjhAccede, accountDecimal);
		result = true;
		return result;
	}

	/**
	 * 投资api
	 */
	@Override
	public BankCallBean autotenderApi(Borrow borrow, HjhAccede hjhAccede, HjhUserAuth hjhUserAuth,BigDecimal account,String tenderUsrcustid, boolean isLast){
		String borrowNid = borrow.getBorrowNid();
		String userId = hjhAccede.getUserId().toString();

		BankCallBean registResult = null;
		BankCallBean bean = new BankCallBean();
		// 获取共同参数
		String bankCode = PropUtils.getSystem(BankCallConstant.BANK_BANKCODE);
		String instCode = PropUtils.getSystem(BankCallConstant.BANK_INSTCODE);
		// 生成订单
		String orderId = GetOrderIdUtils.getOrderId2(Integer.valueOf(userId));

		bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
		bean.setTxCode(BankCallConstant.TXCODE_BIDAUTO_APPLY);// 交易代码
		bean.setInstCode(instCode);
		bean.setBankCode(bankCode);
		bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
		bean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
		bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
		bean.setChannel(BankCallConstant.CHANNEL_PC);// 交易渠道

		bean.setAccountId(tenderUsrcustid);// 电子账号
		bean.setOrderId(orderId);// 订单号
		bean.setTxAmount(CustomUtil.formatAmount(account.toString()));// 交易金额
		bean.setProductId(borrowNid);// 标的号
//		bean.setFrzFlag(BankCallConstant.DEBT_FRZFLAG_FREEZE);// 是否冻结金额
		bean.setFrzFlag(BankCallConstant.DEBT_FRZFLAG_UNFREEZE);
		bean.setContOrderId(hjhUserAuth.getAutoOrderId());

		bean.setLogOrderId(orderId);// 订单号
		bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单日期
		bean.setLogUserId(String.valueOf(userId));// 投资用户
		bean.setLogRemark("自动投标申请");
		bean.setLogClient(hjhAccede.getClient());
		try {

			Integer tmpId = this.insertBorrowTmp(borrow, null, hjhAccede, account,hjhUserAuth,bean,RedisConstants.HJH_BORROW_INVEST, isLast);
			_log.info(hjhAccede.getAccedeOrderId()+" 自动投资接口调用中  "+borrow.getBorrowNid()+" 请求订单号:"+orderId);
			registResult = BankCallUtils.callApiBg(bean);

			String retCode = StringUtils.isNotBlank(registResult.getRetCode()) ? registResult.getRetCode() : "";
			HjhPlanBorrowTmp record = new HjhPlanBorrowTmp();
			record.setId(tmpId);
			record.setRespCode(retCode);
			record.setStatus(1);
			record.setRespDesc(registResult.getRetMsg());
			int nowTime = GetDate.getNowTime10();
			record.setUpdateTime(nowTime);
			hjhPlanBorrowTmpMapper.updateByPrimaryKeySelective(record);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return registResult;
	}

	/**
	 * 自动债转api
	 */
	@Override
	public BankCallBean autoCreditApi(HjhDebtCredit credit, HjhAccede hjhAccede, HjhUserAuth hjhUserAuth,
									  BigDecimal account,BigDecimal assignCapital, BigDecimal serviceFee,
									  String tenderUsrcustid, String sellUsrcustid,
									  String orderId, String orderDate, boolean isLast){
		String borrowNid = credit.getBorrowNid();
		Integer userId = hjhAccede.getUserId();
		BankCallBean registResult = null;

		// 取得当前债权在清算前已经发生债转的本金
		BigDecimal preCreditCapital = getPreCreditCapital(credit);

		// 生成订单
//		String orderId = GetOrderIdUtils.getOrderId2(userId);
		// 交易渠道
		String channel = BankCallConstant.CHANNEL_PC;
		// 银行接口用BEAN
		BankCallBean bean = new BankCallBean(BankCallConstant.VERSION_10,
				BankCallConstant.TXCODE_CREDIT_AUTO_INVEST,
				userId,channel,orderId);

		bean.setAccountId(tenderUsrcustid);// 电子账号
		bean.setTxDate(orderDate);// 订单日期
		bean.setTxAmount(CustomUtil.formatAmount(account.toString()));// 交易金额		
		bean.setProductId(borrowNid);// 标的号
		bean.setTxFee(CustomUtil.formatAmount(serviceFee.toString()));	//手续费 交易金额*当前清算服务费率=债转服务费

		bean.setTsfAmount(CustomUtil.formatAmount(assignCapital.toString()));	//转让金额  实际承接本金

		bean.setForAccountId(sellUsrcustid);	//对手电子账号
		bean.setOrgOrderId(credit.getSellOrderId());	//原订单号 原投资订单号
		bean.setOrgTxAmount(CustomUtil.formatAmount((credit.getCreditCapital().add(preCreditCapital)).toString()));	//原交易金额  债转总本金
		bean.setContOrderId(hjhUserAuth.getAutoCreditOrderId());
		bean.setLogRemark("自动购买债权");
		bean.setLogClient(hjhAccede.getClient());

		try {
			Integer tmpId = this.insertBorrowTmp(null, credit, hjhAccede, account, hjhUserAuth, bean, RedisConstants.HJH_BORROW_CREDIT, isLast);
			_log.info(hjhAccede.getAccedeOrderId()+" 银行自动投资接口调用中  "+credit.getCreditNid()+" 请求订单号:"+orderId);
			registResult = BankCallUtils.callApiBg(bean);

			String retCode = StringUtils.isNotBlank(registResult.getRetCode()) ? registResult.getRetCode() : "";
			HjhPlanBorrowTmp record = new HjhPlanBorrowTmp();
			record.setId(tmpId);
			record.setRespCode(retCode);
			record.setStatus(1);
			record.setRespDesc(registResult.getRetMsg());
			int nowTime = GetDate.getNowTime10();
			record.setUpdateTime(nowTime);
			hjhPlanBorrowTmpMapper.updateByPrimaryKeySelective(record);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return registResult;
	}

	/**
	 * 取得当前债权在清算前已经发生债转的本金
	 * @param credit
	 * @return
	 */
	private BigDecimal getPreCreditCapital(HjhDebtCredit credit) {
		BigDecimal result = BigDecimal.ZERO;

		HjhDebtCreditExample example = new HjhDebtCreditExample();
		HjhDebtCreditExample.Criteria criteria = example.createCriteria();
		criteria.andSellOrderIdEqualTo(credit.getSellOrderId());
		criteria.andCreditStatusEqualTo(3);
		List<HjhDebtCredit> list = this.hjhDebtCreditMapper.selectByExample(example);
		// mod 汇计划三期 清算后债转前发生的还款金额改为合计（连续还款） liubin 20180411 start
		if (list != null && list.size() > 0) {
			for (HjhDebtCredit hjhDebtCredit : list) {
				result = result.add(hjhDebtCredit.getCreditCapitalAssigned());
			}
		}
		return result;
//		if (list != null && list.size() == 1) {
//			result = list.get(0).getCreditCapitalAssigned();
//		}
//		if (list.size() > 1) {
//			result = null;
//		}
//		return result;
		// mod 汇计划三期 清算后债转前发生的还款金额改为合计（连续还款） liubin 20180411 end
	}

	/**
	 *
	 * 插入冻结表
	 *
	 */
	private boolean insertFreezeList(Borrow borrow,HjhAccede hjhAccede, BankCallBean bean) {

		int nowTime = GetDate.getNowTime10();
		String accountId = bean.getAccountId();// 获取投资用户的投资客户号
		Integer userId = hjhAccede.getUserId();// 投资人id
		String txAmount = bean.getTxAmount();// 借款金额
		String orderId = bean.getOrderId();// 订单id
		String retCode = bean.getRetCode();// 投资结果返回码
		String borrowNid = borrow.getBorrowNid();// 项目编号
		BigDecimal accountDecimal = new BigDecimal(txAmount);// 冻结前验证

		// 插入冻结表
		FreezeList record = new FreezeList();
		record.setAmount(accountDecimal);
		record.setBorrowNid(borrowNid);
		record.setCreateTime(nowTime);
		record.setOrdid(orderId);
		record.setUserId(userId);
		record.setRespcode(retCode);
		record.setTrxid("");
		record.setOrdid(orderId);
		record.setUsrcustid(accountId);
		record.setXfrom(1);
		record.setStatus(0);
		record.setUnfreezeManual(0);
		boolean freezeFlag = freezeListMapper.insertSelective(record) > 0 ? true : false;
		_log.info("用户:" + userId + "***********************************插入FreezeList，订单号：" + orderId);

		return freezeFlag;
	}

	/**
	 * 插入投资表
	 * @param borrow
	 * @param hjhAccede
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public boolean insertBorrowTender(Borrow borrow,HjhAccede hjhAccede, BankCallBean bean) {
		int nowTime = GetDate.getNowTime10();
		String ip = bean.getLogIp();// 操作ip
		int client = hjhAccede.getClient();// 操作平台
//		String accountId = bean.getAccountId();// 获取投资用户的投资客户号
		Integer userId = Integer.parseInt(bean.getLogUserId());// 投资人id
		String txAmount = bean.getTxAmount();// 借款金额
		String orderId = bean.getOrderId();// 订单id
		String orderDate = bean.getLogOrderDate(); // 订单日期
//		String retCode = bean.getRetCode();// 投资结果返回码
		String authCode = bean.getAuthCode();// 投资结果授权码
		String borrowNid = borrow.getBorrowNid();// 项目编号
		String borrowStyle = borrow.getBorrowStyle();// 项目的还款方式
//		int projectType = borrow.getProjectType();// 项目类型
		BigDecimal serviceFeeRate = Validator.isNull(borrow.getServiceFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getServiceFeeRate()); // 服务费率
		Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();// 借款期数
		Integer borrowId = borrow.getId();// 借款项目主键
		BigDecimal accountDecimal = new BigDecimal(txAmount);// 冻结前验证

		String accedeOrderId = hjhAccede.getAccedeOrderId();// 计划订单号


		BigDecimal perService = new BigDecimal(0);
		if (StringUtils.isNotEmpty(borrowStyle)) {
			BigDecimal serviceScale = serviceFeeRate;
			// 到期还本还息end/先息后本endmonth/等额本息month/等额本金principal
			if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_END.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
					|| CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
				perService = FinancingServiceChargeUtils.getMonthsPrincipalServiceCharge(accountDecimal, serviceScale);
			}
			// 按天计息到期还本还息
			else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
				perService = FinancingServiceChargeUtils.getDaysPrincipalServiceCharge(accountDecimal, serviceScale, borrowPeriod);
			}
		}

		Integer tenderType = null;//投资类型 0投资1复投
		if (hjhAccede.getOrderStatus() == 0) {
			//0自动投标中
			tenderType = 0;
		}else {
			//2自动投资成功或者3锁定中 
			tenderType = 1;
		}

		BorrowTender borrowTender = new BorrowTender();
		borrowTender.setTenderType(tenderType);//投资类型 0投资1复投
		borrowTender.setAccedeOrderId(accedeOrderId);//计划关联字段
		borrowTender.setAccount(accountDecimal);
		borrowTender.setAccountTender(new BigDecimal(0));
		borrowTender.setActivityFlag(0);
		borrowTender.setAddip(ip);
		borrowTender.setAddtime(nowTime);
		borrowTender.setApiStatus(0);
		borrowTender.setAutoStatus(0);
		borrowTender.setBorrowNid(borrowNid);
		borrowTender.setChangePeriod(0);
		borrowTender.setChangeUserid(0);
		borrowTender.setContents("");
		borrowTender.setFlag(0);
		borrowTender.setIsok(0);
		borrowTender.setIsReport(0);
		borrowTender.setChangeStatus(0);
		borrowTender.setLoanAmount(accountDecimal.subtract(perService));
		borrowTender.setNid(orderId);
		borrowTender.setOrderDate(orderDate);
		borrowTender.setPeriodStatus(0);//
		borrowTender.setRecoverAccountAll(new BigDecimal(0));//
		borrowTender.setRecoverAccountCapitalWait(new BigDecimal(0));//
		borrowTender.setRecoverAccountCapitalYes(new BigDecimal(0));
		borrowTender.setRecoverAccountInterest(new BigDecimal(0));
		borrowTender.setRecoverAccountInterestWait(new BigDecimal(0));
		borrowTender.setRecoverAccountInterestYes(new BigDecimal(0));
		borrowTender.setRecoverAccountWait(new BigDecimal(0));
		borrowTender.setRecoverAccountYes(new BigDecimal(0));
		borrowTender.setRecoverAdvanceFee(new BigDecimal(0));
		borrowTender.setRecoverFee(new BigDecimal(0));
		borrowTender.setRecoverFullStatus(0);
		borrowTender.setRecoverLateFee(new BigDecimal(0));
		borrowTender.setRecoverTimes(0);
		borrowTender.setRecoverType("");
		borrowTender.setStatus(0);
		borrowTender.setTenderAwardAccount(new BigDecimal(0));
		borrowTender.setTenderAwardFee(new BigDecimal(0));
		borrowTender.setTenderNid(borrowNid);
		borrowTender.setTenderStatus(0);
		borrowTender.setUserId(userId);
		// 投资人信息
		Users users = getUsers(userId);//TODO:放到上面不用每次都取
		UsersInfo userInfo = null;

		if (users != null) {
			// 投资用户名
			borrowTender.setTenderUserName(users.getUsername());
			// 获取投资人属性
			// modify by zhangjp vip投资记录 start
			// UsersInfo userInfo = getUserInfo(userId);
			userInfo = getUserInfo(userId);
			// modify by zhangjp vip投资记录 end
			// 用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工
			Integer attribute = null;
			if (userInfo != null) {
				// 获取投资用户的用户属性
				attribute = userInfo.getAttribute();
				if (attribute != null) {
					// 投资人用户属性
					borrowTender.setTenderUserAttribute(attribute);
					// 如果是线上员工或线下员工，推荐人的userId和username不插
					if (attribute == 2 || attribute == 3) {
						EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(userId);
						if (employeeCustomize != null) {
							borrowTender.setInviteRegionId(employeeCustomize.getRegionId());
							borrowTender.setInviteRegionName(employeeCustomize.getRegionName());
							borrowTender.setInviteBranchId(employeeCustomize.getBranchId());
							borrowTender.setInviteBranchName(employeeCustomize.getBranchName());
							borrowTender.setInviteDepartmentId(employeeCustomize.getDepartmentId());
							borrowTender.setInviteDepartmentName(employeeCustomize.getDepartmentName());
						}
					} else if (attribute == 1) {
						SpreadsUsersExample spreadsUsersExample = new SpreadsUsersExample();
						SpreadsUsersExample.Criteria spreadsUsersExampleCriteria = spreadsUsersExample.createCriteria();
						spreadsUsersExampleCriteria.andUserIdEqualTo(userId);
						List<SpreadsUsers> sList = spreadsUsersMapper.selectByExample(spreadsUsersExample);
						if (sList != null && sList.size() == 1) {
							int refUserId = sList.get(0).getSpreadsUserid();
							// 查找用户推荐人
							Users userss = getUsers(refUserId);
							if (userss != null) {
								borrowTender.setInviteUserId(userss.getUserId());
								borrowTender.setInviteUserName(userss.getUsername());
							}
							// 推荐人信息
							UsersInfo refUsers = getUserInfo(refUserId);
							// 推荐人用户属性
							if (refUsers != null) {
								borrowTender.setInviteUserAttribute(refUsers.getAttribute());
							}
							// 查找用户推荐人部门
							EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(refUserId);
							if (employeeCustomize != null) {
								borrowTender.setInviteRegionId(employeeCustomize.getRegionId());
								borrowTender.setInviteRegionName(employeeCustomize.getRegionName());
								borrowTender.setInviteBranchId(employeeCustomize.getBranchId());
								borrowTender.setInviteBranchName(employeeCustomize.getBranchName());
								borrowTender.setInviteDepartmentId(employeeCustomize.getDepartmentId());
								borrowTender.setInviteDepartmentName(employeeCustomize.getDepartmentName());
							}
						}
					} else if (attribute == 0) {
						SpreadsUsersExample spreadsUsersExample = new SpreadsUsersExample();
						SpreadsUsersExample.Criteria spreadsUsersExampleCriteria = spreadsUsersExample.createCriteria();
						spreadsUsersExampleCriteria.andUserIdEqualTo(userId);
						List<SpreadsUsers> sList = spreadsUsersMapper.selectByExample(spreadsUsersExample);
						if (sList != null && sList.size() == 1) {
							int refUserId = sList.get(0).getSpreadsUserid();
							// 查找推荐人
							Users userss = getUsers(refUserId);
							if (userss != null) {
								borrowTender.setInviteUserId(userss.getUserId());
								borrowTender.setInviteUserName(userss.getUsername());
							}
							// 推荐人信息
							UsersInfo refUsers = getUserInfo(refUserId);
							// 推荐人用户属性
							if (refUsers != null) {
								borrowTender.setInviteUserAttribute(refUsers.getAttribute());
							}
						}
					}
				}
			}
		}
		borrowTender.setWeb(0);
		borrowTender.setWebStatus(0);
		borrowTender.setClient(client);
		borrowTender.setInvestType(2); //投资类型 0手动投标 1预约投标 2自动投标
		// 单笔投资的融资服务费
		borrowTender.setLoanFee(perService);
		//投资授权码
		if(StringUtils.isNotBlank(authCode)){
			borrowTender.setAuthCode(authCode);
		}
		borrowTender.setRemark("自动投资");
		boolean trenderFlag = borrowTenderMapper.insertSelective(borrowTender) > 0 ? true : false;
		if (!trenderFlag) {
			throw new RuntimeException("borrowtender表更新失败 "+orderId);
		}

		_log.info("用户:" + userId + "***********************************插入borrowTender，订单号：" + orderId);

		// 更新borrow表
		Map<String, Object> borrowParam = new HashMap<String, Object>();
		borrowParam.put("borrowAccountYes", accountDecimal);
		borrowParam.put("borrowService", perService);
		borrowParam.put("borrowId", borrowId);
		boolean updateBorrowAccountFlag = borrowCustomizeMapper.updateOfBorrow(borrowParam) > 0 ? true : false;
		if (!updateBorrowAccountFlag) {
			throw new RuntimeException("borrow表更新失败");
		}

		return trenderFlag&&updateBorrowAccountFlag;
	}


	/**
	 * 插入交易明细表
	 * @param borrow
	 * @param hjhAccede
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	private boolean insertAccountList(Borrow borrow,HjhAccede hjhAccede, BankCallBean bean) {

		int nowTime = GetDate.getNowTime10();
		String ip = bean.getLogIp();// 操作ip
		String accountId = bean.getAccountId();// 获取投资用户的投资客户号
		Integer userId = Integer.parseInt(bean.getLogUserId());// 投资人id
		String txAmount = bean.getTxAmount();// 借款金额
		String orderId = bean.getOrderId();// 订单id
		String borrowNid = borrow.getBorrowNid();// 项目编号
		BigDecimal accountDecimal = new BigDecimal(txAmount);// 冻结前验证

		String accedeOrderId = hjhAccede.getAccedeOrderId();// 计划订单号


		// 插入account_list表
		_log.info("用户:" + userId + "***********************************更新account，订单号：" + orderId);
		Account account = this.getAccount(userId);
		AccountList accountList = new AccountList();
		/** 计划标的专存字段 */
		accountList.setAccedeOrderId(accedeOrderId);
		accountList.setIsShow(1);

		accountList.setAmount(accountDecimal);
		/** 银行存管相关字段设置 */
		accountList.setAccountId(accountId);
		accountList.setBankAwait(account.getBankAwait());
		accountList.setBankAwaitCapital(account.getBankAwaitCapital());
		accountList.setBankAwaitInterest(account.getBankAwaitInterest());
		accountList.setBankBalance(account.getBankBalance());
		accountList.setBankFrost(account.getBankFrost());
		accountList.setBankInterestSum(account.getBankInterestSum());
		accountList.setBankTotal(account.getBankTotal());
		accountList.setBankWaitCapital(account.getBankWaitCapital());
		accountList.setBankWaitInterest(account.getBankWaitInterest());
		accountList.setBankWaitRepay(account.getBankWaitRepay());

		// 汇计划更新字段
		accountList.setPlanFrost(account.getPlanFrost());
		accountList.setPlanBalance(account.getPlanBalance());

		accountList.setCheckStatus(0);
		accountList.setTradeStatus(1);// 交易状态 update by cwyang 2017-4-26
		// 0:失败 1:成功
		accountList.setIsBank(1);
		accountList.setTxDate(Integer.parseInt(bean.getTxDate()));
		accountList.setTxTime(Integer.parseInt(bean.getTxTime()));
		accountList.setSeqNo(bean.getSeqNo());
		accountList.setBankSeqNo(bean.getTxDate() + bean.getTxTime() + bean.getSeqNo());
		/** 非银行存管相关字段 */
		accountList.setAwait(new BigDecimal(0));
		accountList.setBalance(account.getBalance());
		accountList.setBaseUpdate(0);
		accountList.setCreateTime(nowTime);
		accountList.setFrost(account.getFrost());
		accountList.setInterest(new BigDecimal(0));
		accountList.setIp(ip);
		accountList.setIsUpdate(0);
		accountList.setNid(orderId);
		accountList.setOperator(userId + "");
		accountList.setRemark(borrowNid);
		accountList.setRepay(new BigDecimal(0));
		accountList.setTotal(account.getTotal());
		accountList.setTrade("hjh_tender_frost");// 投标冻结-汇计划
		accountList.setTradeCode("frost");// 投标冻结后为frost
		accountList.setType(3);// 收支类型1收入2支出3冻结
		accountList.setUserId(userId);
		accountList.setWeb(0);
		accountList.setIsBank(1);// 是否是银行的交易记录(0:否,1:是)
		boolean accountListFlag = accountListMapper.insertSelective(accountList) > 0 ? true : false;

		_log.info("用户:" + userId + "***********************************插入accountList，订单号：" + orderId);

		return accountListFlag;

	}

	private boolean updateBorrowFull(Borrow borrow,HjhAccede hjhAccede, BankCallBean bean){
		Integer userId = Integer.parseInt(bean.getLogUserId());// 投资人id
		String orderId = bean.getOrderId();// 订单id
		String borrowNid = borrow.getBorrowNid();// 项目编号
		Integer borrowId = borrow.getId();// 借款项目主键

		// 计算此时的剩余可投资金额
		BigDecimal accountWait = this.getBorrowByNid(borrowNid).getBorrowAccountWait();
		// 满标处理
		if (accountWait.compareTo(new BigDecimal(0)) == 0) {
			_log.info("用户:" + userId + "***项目满标，标号："+borrowNid+" 订单号：" + orderId);
			Map<String, Object> borrowFull = new HashMap<String, Object>();
			borrowFull.put("borrowId", borrowId);
			boolean fullFlag = borrowCustomizeMapper.updateOfFullBorrow(borrowFull) > 0 ? true : false;
			if (!fullFlag) {
				throw new RuntimeException("满标更新borrow表失败");
			}

			// 更新资产表
			HjhPlanAsset hjhPlanAssetnew = new HjhPlanAsset();
			hjhPlanAssetnew.setBorrowNid(borrowNid);
			hjhPlanAssetnew.setStatus(9); // 复审中
			//获取当前时间
			int nowTime = GetDate.getNowTime10();
			hjhPlanAssetnew.setUpdateTime(nowTime);
			hjhPlanAssetnew.setUpdateUserId(1);

			HjhPlanAssetExample hjhPlanAssetExample = new HjhPlanAssetExample();
			HjhPlanAssetExample.Criteria crt = hjhPlanAssetExample.createCriteria();
			crt.andBorrowNidEqualTo(borrow.getBorrowNid());
			crt.andInstCodeEqualTo(borrow.getInstCode());
			crt.andAssetTypeEqualTo(borrow.getAssetType());

			int count = this.hjhPlanAssetMapper.updateByExampleSelective(hjhPlanAssetnew, hjhPlanAssetExample);
			if (count == 0) {
				_log.info("散标满标没有更新hjhPlanAsset表(散标标的号：" + borrow.getBorrowNid() + ")");
			}
		} else if (accountWait.compareTo(BigDecimal.ZERO) < 0) {
			throw new RuntimeException("用户:" + userId + "项目编号:" + borrowNid + "***********************************项目暴标"+hjhAccede.getAccedeOrderId());
		}
		return true;
	}

	/**
	 * 获取用户信息
	 *
	 * @param userId
	 * @return
	 * @author b
	 */
	private UsersInfo getUserInfo(Integer userId) {
		UsersInfoExample usersInfoExample = new UsersInfoExample();
		UsersInfoExample.Criteria usersInfoExampleCriteria = usersInfoExample.createCriteria();
		usersInfoExampleCriteria.andUserIdEqualTo(userId);
		List<UsersInfo> userInfoList = usersInfoMapper.selectByExample(usersInfoExample);
		UsersInfo usersInfo = null;
		if (userInfoList != null && !userInfoList.isEmpty()) {
			usersInfo = userInfoList.get(0);
		}
		return usersInfo;

	}

	/**
	 *获取投资授权码
	 */
	@Override
	public HjhUserAuth selectUserAuthByUserId(int userId) {
		HjhUserAuth hjhUserAuth = null;
		HjhUserAuthExample hjhUserAuthExample = new HjhUserAuthExample();
		hjhUserAuthExample.createCriteria().andUserIdEqualTo(userId).andDelFlgEqualTo(0);
		List<HjhUserAuth> hjhUserAuths = this.hjhUserAuthMapper.selectByExample(hjhUserAuthExample);
		if (hjhUserAuths != null && hjhUserAuths.size() == 1) {
			hjhUserAuth = hjhUserAuths.get(0);
		}

		return hjhUserAuth;
	}


	/**
	 * 获取相应的标的详情
	 */
	@Override
	public Borrow selectBorrowByNid(String borrowNid) {

		BorrowExample borrowExample = new BorrowExample();
		BorrowExample.Criteria borrowCra = borrowExample.createCriteria();
		borrowCra.andBorrowNidEqualTo(borrowNid);
		Borrow borrow = null;
		List<Borrow> borrowList = this.borrowMapper.selectByExample(borrowExample);// 获取相应的标的信息
		if (borrowList != null && borrowList.size() == 1) {
			borrow = borrowList.get(0);
		}

		return borrow;

	}

	/**
	 * 获取相应的债转详情
	 */
	@Override
	public HjhDebtCredit selectCreditByNid(String creditNid) {
		HjhDebtCreditExample hjhDebtCreditExample  = new HjhDebtCreditExample();
		HjhDebtCreditExample.Criteria debtCreditCra = hjhDebtCreditExample.createCriteria();
		debtCreditCra.andCreditNidEqualTo(creditNid);
		HjhDebtCredit hjhDebtCredit = null;
		List<HjhDebtCredit> hjhDebtCreditList = this.hjhDebtCreditMapper.selectByExample(hjhDebtCreditExample);// 获取相应的债转信息
		if (hjhDebtCreditList != null && hjhDebtCreditList.size() == 1) {
			hjhDebtCredit = hjhDebtCreditList.get(0);
		}
		return hjhDebtCredit;
	}

	/**
	 * 投资完成更新计划明细
	 */
	@Override
	public int updateHjhAccede(HjhAccede hjhAccede, int orderStaus) {
		HjhAccede newHjhAccede = new HjhAccede();
		newHjhAccede.setId(hjhAccede.getId());
		newHjhAccede.setOrderStatus(orderStaus); // 加入计划成功
		//获取当前时间
		int nowTime = GetDate.getNowTime10();
		newHjhAccede.setUpdateTime(nowTime);
		newHjhAccede.setUpdateUser(1);
		return this.hjhAccedeMapper.updateByPrimaryKeySelective(newHjhAccede);

	}

	/**
	 * 汇计划自动承接成功后数据库更新操作
	 * @param creditR
	 * @param hjhAccedeR
	 * @param hjhPlanR
	 * @param bean
	 * @param tenderUsrcustid
	 * @param sellerUsrcustid
	 * @param resultMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean updateCredit(HjhDebtCredit creditR, HjhAccede hjhAccedeR, HjhPlan hjhPlanR, BankCallBean bean,
								String tenderUsrcustid, String sellerUsrcustid, Map<String, Object> resultMap) throws Exception {
		boolean result = false;

		// 防范主从数据库不同步，取读库传参改从写库拉数据
		HjhDebtCredit credit = this.selectCreditByNid(creditR.getCreditNid());
		HjhAccede hjhAccede = this.selectHjhAccedeByAccedeOrderId(hjhAccedeR.getAccedeOrderId());
		HjhPlan hjhPlan = this.selectHjhPlanByPlanNid(hjhPlanR.getPlanNid());
		// add 主库读取修改后异常处理问题修正 liubin 20180906 start
		// OrderStatus = 0 or 80 or 90 变更成 0
		_log.info("前OrderStatus："+hjhAccede.getOrderStatus());
		hjhAccede.setOrderStatus(hjhAccede.getOrderStatus() % 10);
		_log.info("后OrderStatus："+hjhAccede.getOrderStatus());
		// add 主库读取修改后异常处理问题修正 liubin 20180906 end

		String txAmount = bean.getTxAmount();// 交易金额
		BigDecimal accountDecimal = new BigDecimal(txAmount);// 交易金额(实际支付金额)
		// 出让人加入计划信息
		HjhAccede sellerHjhAccede = this.selectHjhAccedeByAccedeOrderId(credit.getPlanOrderId());
		if (sellerHjhAccede == null) {
			throw new RuntimeException("出让人加入计划订单未取得，计划订单号：" + credit.getPlanOrderId());
		}

		/** 用户账户信息表更新 */
		// 汇计划重算更新用户账户信息表(债转人)
		Date nowDate = GetDate.getDate();// 当前Date
		// 清算前3天的Date（锁定期最后3天不复投）
		Date conditionDate = GetDate.getSomeDayBeforeOrAfter(sellerHjhAccede.getEndDate(), -CustomConstants.HJH_LIQUIDATE_DAYS+1);//
		String hjhProcess = null;
		if (nowDate.before(conditionDate)) {
			// (债转人)(计划订单锁定中)
			hjhProcess = CustomConstants.HJH_PROCESS_F;
		} else {
			// (债转人)(计划订单清算中)
			hjhProcess = CustomConstants.HJH_PROCESS_H;
		}

		// mod 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 start
		//hjhCommonService.updateAccountForHjh(hjhProcess, credit.getUserId(), accountDecimal, null);
		BigDecimal serviceFee = (BigDecimal) resultMap.get(this.SERVICE_FEE);
		// 汇计划重算更新用户账户信息表(债转人)
		hjhCommonService.updateAccountForHjh(hjhProcess, credit.getUserId(), accountDecimal.subtract(serviceFee), null);
		// 汇计划重算更新汇计划加入明细表(债转人)
		hjhCommonService.updateHjhAccedeForHjh(hjhProcess, sellerHjhAccede.getId(), accountDecimal.subtract(serviceFee), null, serviceFee);
		// mod 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 end

		// 判定该笔承接是投资还是复投
		if (hjhAccede.getOrderStatus() == 0) {
			// 投资
			hjhProcess = CustomConstants.HJH_PROCESS_D;
		} else {
			// 复投
			hjhProcess = CustomConstants.HJH_PROCESS_DF;
		}

		// 汇计划重算更新用户账户信息表(承接人)
		hjhCommonService.updateAccountForHjh(hjhProcess, hjhAccede.getUserId(), accountDecimal, null);
		// 汇计划重算更新汇计划加入明细表(承接人)
		hjhCommonService.updateHjhAccedeForHjh(hjhProcess, hjhAccede.getId(), accountDecimal, null, null);

		// 债权承接成功后后续处理
		// mod 汇计划三期 汇计划自动投资 liubin 20180515 start
		boolean creditTenderFlag = this.saveCreditTender(sellerHjhAccede, credit,
				hjhAccede, bean, bean.getOrderId(),
				bean.getTxDate(),
				hjhPlan.getExpectApr(), resultMap,
				tenderUsrcustid, sellerUsrcustid);
		// mod 汇计划三期 汇计划自动投资 liubin 20180515 end
		if (!creditTenderFlag) {
			return creditTenderFlag;
		}

		// 删除临时表 OK
		deleteBorrowTmp(credit.getCreditNid(), hjhAccede, bean);

		// 复投时，减去该计划的开放额度
		updateAvailableInvestAccount(hjhAccede, accountDecimal);

		// 调用MQ,生成计划债权转让协议
		planCreditGenerateContractByMQ(bean.getOrderId());
		result = true;
		return result;
	}

	/**
	 * 插入汇计划债转投资
	 * @param borrow
	 * @param hjhAccede
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public boolean insertCreditTender(Borrow borrow,HjhAccede hjhAccede, BankCallBean bean) {
		int nowTime = GetDate.getNowTime10();
		String ip = bean.getLogIp();// 操作ip
		int client = bean.getLogClient() != 0 ? bean.getLogClient() : 0;// 操作平台
//		String accountId = bean.getAccountId();// 获取投资用户的投资客户号
		Integer userId = Integer.parseInt(bean.getLogUserId());// 投资人id
		String txAmount = bean.getTxAmount();// 借款金额
		String orderId = bean.getOrderId();// 订单id
		String orderDate = bean.getLogOrderDate(); // 订单日期
//		String retCode = bean.getRetCode();// 投资结果返回码
		String authCode = bean.getAuthCode();// 投资结果授权码
		String borrowNid = borrow.getBorrowNid();// 项目编号
		String borrowStyle = borrow.getBorrowStyle();// 项目的还款方式
		int projectType = borrow.getProjectType();// 项目类型
		BigDecimal serviceFeeRate = Validator.isNull(borrow.getServiceFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getServiceFeeRate()); // 服务费率
		Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();// 借款期数
		Integer borrowId = borrow.getId();// 借款项目主键
		BigDecimal accountDecimal = new BigDecimal(txAmount);// 冻结前验证

		String accedeOrderId = hjhAccede.getAccedeOrderId();// 计划订单号


		BigDecimal perService = new BigDecimal(0);
		if (StringUtils.isNotEmpty(borrowStyle)) {
			BigDecimal serviceScale = serviceFeeRate;
			// 到期还本还息end/先息后本endmonth/等额本息month/等额本金principal
			if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_END.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
					|| CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
				perService = FinancingServiceChargeUtils.getMonthsPrincipalServiceCharge(accountDecimal, serviceScale);
			}
			// 按天计息到期还本还息
			else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
				perService = FinancingServiceChargeUtils.getDaysPrincipalServiceCharge(accountDecimal, serviceScale, borrowPeriod);
			}
		}
		BorrowTender borrowTender = new BorrowTender();
		borrowTender.setAccedeOrderId(accedeOrderId);//计划关联字段
		borrowTender.setAccount(accountDecimal);
		borrowTender.setAccountTender(new BigDecimal(0));
		borrowTender.setActivityFlag(0);
		borrowTender.setAddip(ip);
		borrowTender.setAddtime(nowTime);
		borrowTender.setApiStatus(0);
		borrowTender.setAutoStatus(0);
		borrowTender.setBorrowNid(borrowNid);
		borrowTender.setChangePeriod(0);
		borrowTender.setChangeUserid(0);
		borrowTender.setContents("");
		borrowTender.setFlag(0);
		borrowTender.setIsok(0);
		borrowTender.setIsReport(0);
		borrowTender.setChangeStatus(0);
		borrowTender.setLoanAmount(accountDecimal.subtract(perService));
		borrowTender.setNid(orderId);
		borrowTender.setOrderDate(orderDate);
		borrowTender.setPeriodStatus(0);//
		borrowTender.setRecoverAccountAll(new BigDecimal(0));//
		borrowTender.setRecoverAccountCapitalWait(new BigDecimal(0));//
		borrowTender.setRecoverAccountCapitalYes(new BigDecimal(0));
		borrowTender.setRecoverAccountInterest(new BigDecimal(0));
		borrowTender.setRecoverAccountInterestWait(new BigDecimal(0));
		borrowTender.setRecoverAccountInterestYes(new BigDecimal(0));
		borrowTender.setRecoverAccountWait(new BigDecimal(0));
		borrowTender.setRecoverAccountYes(new BigDecimal(0));
		borrowTender.setRecoverAdvanceFee(new BigDecimal(0));
		borrowTender.setRecoverFee(new BigDecimal(0));
		borrowTender.setRecoverFullStatus(0);
		borrowTender.setRecoverLateFee(new BigDecimal(0));
		borrowTender.setRecoverTimes(0);
		borrowTender.setRecoverType("");
		borrowTender.setStatus(0);
		borrowTender.setTenderAwardAccount(new BigDecimal(0));
		borrowTender.setTenderAwardFee(new BigDecimal(0));
		borrowTender.setTenderNid(borrowNid);
		borrowTender.setTenderStatus(0);
		borrowTender.setUserId(userId);
		// 投资人信息
		Users users = getUsers(userId);//TODO:放到上面不用每次都取
		UsersInfo userInfo = null;

		if (users != null && projectType != 8) {
			// 更新渠道统计用户累计投资
			AppChannelStatisticsDetailExample channelExample = new AppChannelStatisticsDetailExample();
			AppChannelStatisticsDetailExample.Criteria crt = channelExample.createCriteria();
			crt.andUserIdEqualTo(userId);
			List<AppChannelStatisticsDetail> appChannelStatisticsDetails = this.appChannelStatisticsDetailMapper.selectByExample(channelExample);
			if (appChannelStatisticsDetails != null && appChannelStatisticsDetails.size() == 1) {
				AppChannelStatisticsDetail channelDetail = appChannelStatisticsDetails.get(0);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("id", channelDetail.getId());
				params.put("accountDecimal", accountDecimal);
				// 投资时间
				params.put("investTime", nowTime);
				// 项目类型
				if (borrow.getProjectType() == 13) {
					params.put("projectType", "汇金理财");
				} else {
					params.put("projectType", "汇直投");
				}
				// 首次投标项目期限
				String investProjectPeriod = "";
				if (borrow.getBorrowStyle().equals("endday")) {
					investProjectPeriod = borrow.getBorrowPeriod() + "天";
				} else {
					investProjectPeriod = borrow.getBorrowPeriod() + "个月";
				}
				params.put("investProjectPeriod", investProjectPeriod);
				// 更新渠道统计用户累计投资
				if (users.getInvestflag() == 1) {
					this.appChannelStatisticsDetailCustomizeMapper.updateAppChannelStatisticsDetail(params);
				} else if (users.getInvestflag() == 0) {
					// 更新首投投资
					this.appChannelStatisticsDetailCustomizeMapper.updateFirstAppChannelStatisticsDetail(params);
				}
				_log.info("用户:" + userId + "***********************************预更新渠道统计表AppChannelStatisticsDetail，订单号：" + orderId);
			} else {
				// 更新huiyingdai_utm_reg的首投信息
				UtmRegExample utmRegExample = new UtmRegExample();
				UtmRegExample.Criteria utmRegCra = utmRegExample.createCriteria();
				utmRegCra.andUserIdEqualTo(userId);
				List<UtmReg> utmRegList = this.utmRegMapper.selectByExample(utmRegExample);
				if (utmRegList != null && utmRegList.size() > 0) {
					UtmReg utmReg = utmRegList.get(0);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", utmReg.getId());
					params.put("accountDecimal", accountDecimal);
					// 投资时间
					params.put("investTime", nowTime);
					// 项目类型
					if (borrow.getProjectType() == 13) {
						params.put("projectType", "汇金理财");
					} else {
						params.put("projectType", "汇直投");
					}
					// 首次投标项目期限
					String investProjectPeriod = "";
					if (borrow.getBorrowStyle().equals("endday")) {
						investProjectPeriod = borrow.getBorrowPeriod() + "天";
					} else {
						investProjectPeriod = borrow.getBorrowPeriod() + "个月";
					}
					params.put("investProjectPeriod", investProjectPeriod);

					// 更新渠道统计用户累计投资
					if (users.getInvestflag() == 0) {
						// 更新huiyingdai_utm_reg的首投信息
						this.appChannelStatisticsDetailCustomizeMapper.updateFirstUtmReg(params);
					}
				}
			}
		}

		if (users != null) {
			if (users.getInvestflag() == 0) {
				users.setInvestflag(1);
				UsersExample userExample = new UsersExample();
				userExample.createCriteria().andUserIdEqualTo(userId).andInvestflagEqualTo(0);
				this.usersMapper.updateByExampleSelective(users, userExample);
			}

			// 投资用户名
			borrowTender.setTenderUserName(users.getUsername());
			// 获取投资人属性
			// modify by zhangjp vip投资记录 start
			// UsersInfo userInfo = getUserInfo(userId);
			userInfo = getUserInfo(userId);
			// modify by zhangjp vip投资记录 end
			// 用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工
			Integer attribute = null;
			if (userInfo != null) {
				// 获取投资用户的用户属性
				attribute = userInfo.getAttribute();
				if (attribute != null) {
					// 投资人用户属性
					borrowTender.setTenderUserAttribute(attribute);
					// 如果是线上员工或线下员工，推荐人的userId和username不插
					if (attribute == 2 || attribute == 3) {
						EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(userId);
						if (employeeCustomize != null) {
							borrowTender.setInviteRegionId(employeeCustomize.getRegionId());
							borrowTender.setInviteRegionName(employeeCustomize.getRegionName());
							borrowTender.setInviteBranchId(employeeCustomize.getBranchId());
							borrowTender.setInviteBranchName(employeeCustomize.getBranchName());
							borrowTender.setInviteDepartmentId(employeeCustomize.getDepartmentId());
							borrowTender.setInviteDepartmentName(employeeCustomize.getDepartmentName());
						}
					} else if (attribute == 1) {
						SpreadsUsersExample spreadsUsersExample = new SpreadsUsersExample();
						SpreadsUsersExample.Criteria spreadsUsersExampleCriteria = spreadsUsersExample.createCriteria();
						spreadsUsersExampleCriteria.andUserIdEqualTo(userId);
						List<SpreadsUsers> sList = spreadsUsersMapper.selectByExample(spreadsUsersExample);
						if (sList != null && sList.size() == 1) {
							int refUserId = sList.get(0).getSpreadsUserid();
							// 查找用户推荐人
							Users userss = getUsers(refUserId);
							if (userss != null) {
								borrowTender.setInviteUserId(userss.getUserId());
								borrowTender.setInviteUserName(userss.getUsername());
							}
							// 推荐人信息
							UsersInfo refUsers = getUserInfo(refUserId);
							// 推荐人用户属性
							if (refUsers != null) {
								borrowTender.setInviteUserAttribute(refUsers.getAttribute());
							}
							// 查找用户推荐人部门
							EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(refUserId);
							if (employeeCustomize != null) {
								borrowTender.setInviteRegionId(employeeCustomize.getRegionId());
								borrowTender.setInviteRegionName(employeeCustomize.getRegionName());
								borrowTender.setInviteBranchId(employeeCustomize.getBranchId());
								borrowTender.setInviteBranchName(employeeCustomize.getBranchName());
								borrowTender.setInviteDepartmentId(employeeCustomize.getDepartmentId());
								borrowTender.setInviteDepartmentName(employeeCustomize.getDepartmentName());
							}
						}
					} else if (attribute == 0) {
						SpreadsUsersExample spreadsUsersExample = new SpreadsUsersExample();
						SpreadsUsersExample.Criteria spreadsUsersExampleCriteria = spreadsUsersExample.createCriteria();
						spreadsUsersExampleCriteria.andUserIdEqualTo(userId);
						List<SpreadsUsers> sList = spreadsUsersMapper.selectByExample(spreadsUsersExample);
						if (sList != null && sList.size() == 1) {
							int refUserId = sList.get(0).getSpreadsUserid();
							// 查找推荐人
							Users userss = getUsers(refUserId);
							if (userss != null) {
								borrowTender.setInviteUserId(userss.getUserId());
								borrowTender.setInviteUserName(userss.getUsername());
							}
							// 推荐人信息
							UsersInfo refUsers = getUserInfo(refUserId);
							// 推荐人用户属性
							if (refUsers != null) {
								borrowTender.setInviteUserAttribute(refUsers.getAttribute());
							}
						}
					}
				}
			}
		}
		borrowTender.setWeb(0);
		borrowTender.setWebStatus(0);
		borrowTender.setClient(client);
		borrowTender.setInvestType(2); //投资类型 0手动投标 1预约投标 2自动投标
		// 单笔投资的融资服务费
		borrowTender.setLoanFee(perService);
		//投资授权码
		if(StringUtils.isNotBlank(authCode)){
			borrowTender.setAuthCode(authCode);
		}
		borrowTender.setRemark("自动投资");
		boolean trenderFlag = borrowTenderMapper.insertSelective(borrowTender) > 0 ? true : false;
		if (!trenderFlag) {
			throw new RuntimeException("borrowtender表更新失败 "+orderId);
		}

		_log.info("用户:" + userId + "***********************************插入borrowTender，订单号：" + orderId);

		// 更新borrow表
		Map<String, Object> borrowParam = new HashMap<String, Object>();
		borrowParam.put("borrowAccountYes", accountDecimal);
		borrowParam.put("borrowService", perService);
		borrowParam.put("borrowId", borrowId);
		boolean updateBorrowAccountFlag = borrowCustomizeMapper.updateOfBorrow(borrowParam) > 0 ? true : false;
		if (!updateBorrowAccountFlag) {
			throw new RuntimeException("borrow表更新失败");
		}

		return trenderFlag&&updateBorrowAccountFlag;
	}

	/**
	 * 保存用户的债转承接log数据
	 *
	 * @param credit
	 * @param debtPlanAccede
	 * @param creditOrderId
	 * @param creditOrderDate
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> saveCreditTenderLog(HjhDebtCredit credit, HjhAccede debtPlanAccede, String creditOrderId, String creditOrderDate, BigDecimal account, Boolean isLast) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		// 清算出的债权编号
		String creditNid = credit.getCreditNid();
		// 清算债权标号
		String liquidatesPlanNid = credit.getPlanNid();
		// 清算债权加入订单号
		String liquidatesPlanOrderId = credit.getPlanOrderId();
		// 债权原有投资订单号
		String sellOrderId = credit.getSellOrderId();
		// 项目原标标号
		String borrowNid = credit.getBorrowNid();
		// 已还多少期
		int repayPeriod = credit.getRepayPeriod();
		// 债转期数
		int creditPeriod = credit.getCreditPeriod();
		// 承接垫付利息
		BigDecimal sellerInterestAdvance = credit.getCreditInterestAdvance();
		// 剩余待承接本金
		BigDecimal sellerCapitalWait = credit.getCreditCapitalWait();
		// 此次债权本金
		BigDecimal sellerCapital = credit.getCreditCapital();
		// 待承接垫付利息
		BigDecimal sellerInterestAdvanceWait = credit.getCreditInterestAdvanceWait();
		// 加入用户userId
		int userId = debtPlanAccede.getUserId();
		// 加入用户userName
		String userName = debtPlanAccede.getUserName();
		// 计划nid
		String planNid = debtPlanAccede.getPlanNid();
		// 计划加入订单号
		String planOrderId = debtPlanAccede.getAccedeOrderId();
		// 加入平台
		int client = debtPlanAccede.getClient();
		// 获取借款数据
		BorrowExample borrowExample = new BorrowExample();
		BorrowExample.Criteria borrowCra = borrowExample.createCriteria();
		borrowCra.andBorrowNidEqualTo(credit.getBorrowNid());
		List<Borrow> borrowList = this.borrowMapper.selectByExample(borrowExample);
		if (borrowList != null && borrowList.size() > 0) {
			Borrow borrow = borrowList.get(0);
			String borrowStyle = borrow.getBorrowStyle();
			// 承接本息
			BigDecimal assignAccount = BigDecimal.ZERO;
			// 承接本金
			BigDecimal assignCapital = BigDecimal.ZERO;
			// 债转利息
			BigDecimal assignInterest = BigDecimal.ZERO;
			// 垫付利息
			BigDecimal assignAdvanceMentInterest = BigDecimal.ZERO;
			// 投资人收取延期利息
			BigDecimal assignRepayDelayInterest = BigDecimal.ZERO;
			// 投资人收取逾期利息
			BigDecimal assignRepayLateInterest = BigDecimal.ZERO;
			// 实付金额
			BigDecimal assignPay = account;//BigDecimal.ZERO;

			// 按天 or 按月（不分期）
			if (borrowStyle.equals(CalculatesUtil.STYLE_ENDDAY) || borrowStyle.equals(CalculatesUtil.STYLE_END)) {
//				// 承接人承接本金
//				assignCapital = HTJServiceFeeUtils.getAssignCapital(account, sellerCapitalWait, sellerInterestAdvanceWait, sellerCapital, sellerInterestAdvance);
				// 出让人的债权信息
				HjhDebtDetailExample debtDetailOldExample = new HjhDebtDetailExample();
				HjhDebtDetailExample.Criteria debtDetailOldCrt = debtDetailOldExample.createCriteria();
				debtDetailOldCrt.andPlanNidEqualTo(liquidatesPlanNid);
				debtDetailOldCrt.andPlanOrderIdEqualTo(liquidatesPlanOrderId);
				debtDetailOldCrt.andInvestOrderIdEqualTo(credit.getInvestOrderId());
				debtDetailOldCrt.andOrderIdEqualTo(credit.getSellOrderId());
				debtDetailOldCrt.andRepayStatusEqualTo(0);
				debtDetailOldCrt.andDelFlagEqualTo(1);
				debtDetailOldCrt.andStatusEqualTo(1);
				debtDetailOldExample.setOrderByClause("repay_period ASC");
				List<HjhDebtDetail> debtDetailOldList = this.hjhDebtDetailMapper.selectByExample(debtDetailOldExample);
				if (debtDetailOldList != null && debtDetailOldList.size() == 1) {
					HjhDebtDetail debtDetailOld = debtDetailOldList.get(0);
					// 承接人承接本金
					assignCapital = HJHServiceFeeUtils.getCurrentPeriodAssignCapital(assignPay, credit.getLiquidationFairValue(),
							debtDetailOld.getLoanCapital(), debtDetailOld.getRepayCapitalWait(), isLast);
					// 承接人承接利息
					assignInterest = HJHServiceFeeUtils.getCurrentPeriodAssignInterest(assignPay, credit.getLiquidationFairValue(),
							debtDetailOld.getLoanInterest(), debtDetailOld.getRepayInterestWait(), isLast);
					// 承接人延期利息
					assignRepayDelayInterest = BigDecimal.ZERO;//HTJServiceFeeUtils.getEndDayAssignRepayInterestDelay(sellerInterest, delayDays, sellerTotalDays, assignCapital, sellerCapital, sellerCapitalWait, sellerDelayInterestWait);
					// 承接人逾期利息
					assignRepayLateInterest = BigDecimal.ZERO;//HTJServiceFeeUtils.getEndDayAssignRepayInterestLate(sellerInterest, lateDays, sellerTotalDays, assignCapital, sellerCapital, sellerCapitalWait, sellerLateInterestWait);
					// 垫付利息
					assignAdvanceMentInterest = assignPay.subtract(assignCapital);
				}
				// 债转本息
				assignAccount = assignCapital.add(assignInterest);
			}
			// 先息后本 or 等额本金 or 等额本息 (分期)
			else if (borrowStyle.equals(CalculatesUtil.STYLE_ENDMONTH) || borrowStyle.equals(CalculatesUtil.STYLE_PRINCIPAL) || borrowStyle.equals(CalculatesUtil.STYLE_MONTH)) {
				// 出让人的债权信息
				HjhDebtDetailExample debtDetailOldExample = new HjhDebtDetailExample();
				HjhDebtDetailExample.Criteria debtDetailOldCrt = debtDetailOldExample.createCriteria();
				debtDetailOldCrt.andPlanNidEqualTo(liquidatesPlanNid);
				debtDetailOldCrt.andPlanOrderIdEqualTo(liquidatesPlanOrderId);
				debtDetailOldCrt.andInvestOrderIdEqualTo(credit.getInvestOrderId());
				debtDetailOldCrt.andOrderIdEqualTo(credit.getSellOrderId());
				debtDetailOldCrt.andRepayStatusEqualTo(0);
				debtDetailOldCrt.andDelFlagEqualTo(1);
				debtDetailOldCrt.andStatusEqualTo(1);
				debtDetailOldExample.setOrderByClause("repay_period ASC");
				List<HjhDebtDetail> debtDetailOldList = this.hjhDebtDetailMapper.selectByExample(debtDetailOldExample);
				// 承接人此次承接的总待收本金
				BigDecimal assignPeriodCapitalTotal = BigDecimal.ZERO;
				// 承接人此次承接的总待收利息
				BigDecimal assignPeriodInterestTotal = BigDecimal.ZERO;
				// 校验数据完整性
				if (debtDetailOldList != null && debtDetailOldList.size() > 0) {
					Map<Integer, Object> assignResult = new HashMap<Integer, Object>();
					for (int i = 0; i < debtDetailOldList.size(); i++) {
						// 承接人此次承接的分期承接本金
						BigDecimal assignPeriodCapital = BigDecimal.ZERO;
						// 承接人此次承接的分期承接利息
						BigDecimal assignPeriodInterest = BigDecimal.ZERO;
						// 承接人此次承接的分期垫付利息
						BigDecimal assignPeriodAdvanceMentInterest = BigDecimal.ZERO;
						// 承接人此次承接的待收取分期延期利息
						BigDecimal assignPeriodRepayDelayInterest = BigDecimal.ZERO;
						// 承接人此次承接的待收取分期逾期利息
						BigDecimal assignPeriodRepayLateInterest = BigDecimal.ZERO;
						// 债权信息
						HjhDebtDetail debtDetailOld = debtDetailOldList.get(i);
						// 还款期数
						int waitRepayPeriod = debtDetailOld.getRepayPeriod();
						// 承接人此次承接的分期待收本金 TODO
						assignPeriodCapital = HJHServiceFeeUtils.getCurrentPeriodAssignCapital(assignPay, credit.getLiquidationFairValue(),
								debtDetailOld.getLoanCapital(), debtDetailOld.getRepayCapitalWait(), isLast);
						// 承接人此次承接的分期待收利息 TODO
						assignPeriodInterest = HJHServiceFeeUtils.getCurrentPeriodAssignInterest(assignPay, credit.getLiquidationFairValue(),
								debtDetailOld.getLoanInterest(), debtDetailOld.getRepayInterestWait(), isLast);
						// 本期延期利息(0) 
						assignPeriodRepayDelayInterest = BigDecimal.ZERO;
						// 本期逾期利息(0) 
						assignPeriodRepayLateInterest = BigDecimal.ZERO;
						// 承接人此次承接的总待收本金
						assignPeriodCapitalTotal = assignPeriodCapitalTotal.add(assignPeriodCapital);
						// 承接人此次承接的总待收利息
						assignPeriodInterestTotal = assignPeriodInterestTotal.add(assignPeriodInterest);
						// 返回结果集
						Map<String, BigDecimal> assignPeriodMap = new HashMap<String, BigDecimal>();
						assignPeriodMap.put(ASSIGN_PERIOD_CAPITAL, assignPeriodCapital);
						assignPeriodMap.put(ASSIGN_PERIOD_INTEREST, assignPeriodInterest);
						assignPeriodMap.put(ASSIGN_PERIOD_ADVANCEMENT_INTEREST, assignPeriodAdvanceMentInterest);
						assignPeriodMap.put(ASSIGN_PERIOD_REPAY_DELAY_INTEREST, assignPeriodRepayDelayInterest);
						assignPeriodMap.put(ASSIGN_PERIOD_REPAY_LATE_INTEREST, assignPeriodRepayLateInterest);
						assignResult.put(waitRepayPeriod, assignPeriodMap);
					}
					// 分期利息计算结果
					result.put(ASSIGN_RESULT, assignResult);
				}
				// 重置承接人承接总本金
				assignCapital = assignPeriodCapitalTotal;
				// 承接总利息
				assignInterest = assignPeriodInterestTotal;
				// 承接总本息
				assignAccount = assignCapital.add(assignInterest);
				// 承接人此次承接待收取的总延期利息(0)
				assignRepayDelayInterest = BigDecimal.ZERO;//assignPeriodRepayDelayInterestTotal;
				// 承接人此次承接待收取的总逾期利息(0)
				assignRepayLateInterest = BigDecimal.ZERO;//assignPeriodRepayLateInterestTotal;
				// 垫付总利息(实际支付-承接人每期承接本金之和)
				assignAdvanceMentInterest = assignPay.subtract(assignPeriodCapitalTotal);
			}
			// mod 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 start
			// 计算服务费
			//BigDecimal serviceFee = BigDecimal.ZERO;//this.calculateServiceFee(credit, assignCapital.add(assignAdvanceMentInterest));
			// 出让人加入计划信息
			HjhAccede sellerHjhAccede = this.selectHjhAccedeByAccedeOrderId(credit.getPlanOrderId());
			BigDecimal serviceApr = sellerHjhAccede.getLqdServiceApr();
			BigDecimal serviceFee = assignPay.multiply(serviceApr).setScale(2, BigDecimal.ROUND_DOWN);
			// mod 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 end
			// 返回结果封装
			result.put(ASSIGN_ACCOUNT, assignAccount);
			result.put(ASSIGN_CAPITAL, assignCapital);
			result.put(ASSIGN_INTEREST, assignInterest);
			result.put(ASSIGN_ADVANCEMENT_INTEREST, assignAdvanceMentInterest);
			result.put(ASSIGN_REPAY_DELAY_INTEREST, assignRepayDelayInterest);
			result.put(ASSIGN_REPAY_LATE_INTEREST, assignRepayLateInterest);
			result.put(ASSIGN_PAY, assignPay);
			result.put(SERVICE_FEE, serviceFee);
			// add 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 start
			result.put(SERVICE_APR, serviceApr);
			// add 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 end
			// 获取相应的用户信息
			Users user = this.usersMapper.selectByPrimaryKey(userId);
			if (Validator.isNotNull(user)) {
				// 保存credit_tender_log表
				HjhDebtCreditTenderLog debtCreditTenderLog = new HjhDebtCreditTenderLog();
				debtCreditTenderLog.setUserId(userId);
				debtCreditTenderLog.setUserName(user.getUsername());
				debtCreditTenderLog.setCreditUserId(credit.getUserId());
				debtCreditTenderLog.setCreditUserName(credit.getUserName());
				debtCreditTenderLog.setStatus(0);
				debtCreditTenderLog.setLiquidatesPlanNid(liquidatesPlanNid);
				debtCreditTenderLog.setLiquidatesPlanOrderId(liquidatesPlanOrderId);
				debtCreditTenderLog.setAssignPlanNid(planNid);
				debtCreditTenderLog.setAssignPlanOrderId(planOrderId);
				debtCreditTenderLog.setBorrowNid(credit.getBorrowNid());
				debtCreditTenderLog.setCreditNid(credit.getCreditNid());
				debtCreditTenderLog.setInvestOrderId(credit.getInvestOrderId());
				debtCreditTenderLog.setSellOrderId(credit.getSellOrderId());
				debtCreditTenderLog.setAssignOrderId(creditOrderId);
				debtCreditTenderLog.setAssignOrderDate(creditOrderDate);
				debtCreditTenderLog.setAssignCapital(assignCapital);
				debtCreditTenderLog.setAssignAccount(assignAccount);
				debtCreditTenderLog.setAssignInterest(assignInterest);
				debtCreditTenderLog.setAssignRepayDelayInterest(assignRepayDelayInterest);
				debtCreditTenderLog.setAssignRepayLateInterest(assignRepayLateInterest);
				debtCreditTenderLog.setAssignInterestAdvance(assignAdvanceMentInterest);
				debtCreditTenderLog.setAssignPrice(assignPay);
				debtCreditTenderLog.setAssignPay(assignPay);
				debtCreditTenderLog.setAssignRepayAccount(assignCapital.add(assignInterest));
				debtCreditTenderLog.setAssignRepayCapital(assignCapital);
				debtCreditTenderLog.setAssignRepayInterest(assignInterest);
				debtCreditTenderLog.setAssignRepayEndTime(credit.getCreditRepayEndTime());
				debtCreditTenderLog.setAssignRepayLastTime(credit.getCreditRepayLastTime());
				debtCreditTenderLog.setAssignRepayNextTime(credit.getCreditRepayNextTime());
				debtCreditTenderLog.setAssignRepayYesTime(0);
				debtCreditTenderLog.setAssignRepayPeriod(credit.getCreditPeriod());
				debtCreditTenderLog.setRepayPeriod(repayPeriod);
				debtCreditTenderLog.setAssignRepayPeriod(creditPeriod);
				debtCreditTenderLog.setAssignOrderDate(creditOrderDate);
				// mod 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 start
				debtCreditTenderLog.setAssignServiceApr(serviceApr);
				// mod 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 end
				debtCreditTenderLog.setAssignServiceFee(serviceFee);
				debtCreditTenderLog.setCreateUserId(userId);
				debtCreditTenderLog.setCreateUserName(userName);
				debtCreditTenderLog.setCreateTime(GetDate.getNowTime10());
				debtCreditTenderLog.setClient(client);
				boolean flag = this.hjhDebtCreditTenderLogMapper.insertSelective(debtCreditTenderLog) > 0 ? true : false;
				if (flag) {
					return result;
				} else {
					throw new RuntimeException("债转日志debtCrditTenderLog表保存失败，债权标号：" + creditNid + ",投资编号：" + sellOrderId);
				}
			} else {
				throw new RuntimeException("未查询到相应的用户信息，用户userId：" + userId);
			}
		} else {
			throw new RuntimeException("未查询到相应的标的信息，项目编号：" + borrowNid + ",还款期数：" + repayPeriod + 1);
		}
	}

	/**
	 * 保存用户的债转承接log数据
	 *
	 * @param credit
	 * @param debtPlanAccede
	 * @param creditOrderId
	 * @param creditOrderDate
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> saveCreditTenderLogNoSave(HjhDebtCredit credit, HjhAccede debtPlanAccede, String creditOrderId, String creditOrderDate, BigDecimal account, Boolean isLast) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		// 清算出的债权编号
		String creditNid = credit.getCreditNid();
		// 清算债权标号
		String liquidatesPlanNid = credit.getPlanNid();
		// 清算债权加入订单号
		String liquidatesPlanOrderId = credit.getPlanOrderId();
		// 债权原有投资订单号
		String sellOrderId = credit.getSellOrderId();
		// 项目原标标号
		String borrowNid = credit.getBorrowNid();
		// 已还多少期
		int repayPeriod = credit.getRepayPeriod();
		// 债转期数
		int creditPeriod = credit.getCreditPeriod();
		// 承接垫付利息
		BigDecimal sellerInterestAdvance = credit.getCreditInterestAdvance();
		// 剩余待承接本金
		BigDecimal sellerCapitalWait = credit.getCreditCapitalWait();
		// 此次债权本金
		BigDecimal sellerCapital = credit.getCreditCapital();
		// 待承接垫付利息
		BigDecimal sellerInterestAdvanceWait = credit.getCreditInterestAdvanceWait();
		// 加入用户userId
		int userId = debtPlanAccede.getUserId();
		// 加入用户userName
		String userName = debtPlanAccede.getUserName();
		// 计划nid
		String planNid = debtPlanAccede.getPlanNid();
		// 计划加入订单号
		String planOrderId = debtPlanAccede.getAccedeOrderId();
		// 加入平台
		int client = debtPlanAccede.getClient();
		// 获取借款数据
		BorrowExample borrowExample = new BorrowExample();
		BorrowExample.Criteria borrowCra = borrowExample.createCriteria();
		borrowCra.andBorrowNidEqualTo(credit.getBorrowNid());
		List<Borrow> borrowList = this.borrowMapper.selectByExample(borrowExample);
		if (borrowList != null && borrowList.size() > 0) {
			Borrow borrow = borrowList.get(0);
			String borrowStyle = borrow.getBorrowStyle();
			// 承接本息
			BigDecimal assignAccount = BigDecimal.ZERO;
			// 承接本金
			BigDecimal assignCapital = BigDecimal.ZERO;
			// 债转利息
			BigDecimal assignInterest = BigDecimal.ZERO;
			// 垫付利息
			BigDecimal assignAdvanceMentInterest = BigDecimal.ZERO;
			// 投资人收取延期利息
			BigDecimal assignRepayDelayInterest = BigDecimal.ZERO;
			// 投资人收取逾期利息
			BigDecimal assignRepayLateInterest = BigDecimal.ZERO;
			// 实付金额
			BigDecimal assignPay = account;//BigDecimal.ZERO;

			// 按天 or 按月（不分期）
			if (borrowStyle.equals(CalculatesUtil.STYLE_ENDDAY) || borrowStyle.equals(CalculatesUtil.STYLE_END)) {
//				// 承接人承接本金
//				assignCapital = HTJServiceFeeUtils.getAssignCapital(account, sellerCapitalWait, sellerInterestAdvanceWait, sellerCapital, sellerInterestAdvance);
				// 出让人的债权信息
				HjhDebtDetailExample debtDetailOldExample = new HjhDebtDetailExample();
				HjhDebtDetailExample.Criteria debtDetailOldCrt = debtDetailOldExample.createCriteria();
				debtDetailOldCrt.andPlanNidEqualTo(liquidatesPlanNid);
				debtDetailOldCrt.andPlanOrderIdEqualTo(liquidatesPlanOrderId);
				debtDetailOldCrt.andInvestOrderIdEqualTo(credit.getInvestOrderId());
				debtDetailOldCrt.andOrderIdEqualTo(credit.getSellOrderId());
				debtDetailOldCrt.andRepayStatusEqualTo(0);
				debtDetailOldCrt.andDelFlagEqualTo(1);
				debtDetailOldCrt.andStatusEqualTo(1);
				debtDetailOldExample.setOrderByClause("repay_period ASC");
				List<HjhDebtDetail> debtDetailOldList = this.hjhDebtDetailMapper.selectByExample(debtDetailOldExample);
				if (debtDetailOldList != null && debtDetailOldList.size() == 1) {
					HjhDebtDetail debtDetailOld = debtDetailOldList.get(0);
					// 承接人承接本金
					assignCapital = HJHServiceFeeUtils.getCurrentPeriodAssignCapital(assignPay, credit.getLiquidationFairValue(),
							debtDetailOld.getLoanCapital(), debtDetailOld.getRepayCapitalWait(), isLast);
					// 承接人承接利息
					assignInterest = HJHServiceFeeUtils.getCurrentPeriodAssignInterest(assignPay, credit.getLiquidationFairValue(),
							debtDetailOld.getLoanInterest(), debtDetailOld.getRepayInterestWait(), isLast);
					// 承接人延期利息
					assignRepayDelayInterest = BigDecimal.ZERO;//HTJServiceFeeUtils.getEndDayAssignRepayInterestDelay(sellerInterest, delayDays, sellerTotalDays, assignCapital, sellerCapital, sellerCapitalWait, sellerDelayInterestWait);
					// 承接人逾期利息
					assignRepayLateInterest = BigDecimal.ZERO;//HTJServiceFeeUtils.getEndDayAssignRepayInterestLate(sellerInterest, lateDays, sellerTotalDays, assignCapital, sellerCapital, sellerCapitalWait, sellerLateInterestWait);
					// 垫付利息
					assignAdvanceMentInterest = assignPay.subtract(assignCapital);
				}
				// 债转本息
				assignAccount = assignCapital.add(assignInterest);
			}
			// 先息后本 or 等额本金 or 等额本息 (分期)
			else if (borrowStyle.equals(CalculatesUtil.STYLE_ENDMONTH) || borrowStyle.equals(CalculatesUtil.STYLE_PRINCIPAL) || borrowStyle.equals(CalculatesUtil.STYLE_MONTH)) {
				// 出让人的债权信息
				HjhDebtDetailExample debtDetailOldExample = new HjhDebtDetailExample();
				HjhDebtDetailExample.Criteria debtDetailOldCrt = debtDetailOldExample.createCriteria();
				debtDetailOldCrt.andPlanNidEqualTo(liquidatesPlanNid);
				debtDetailOldCrt.andPlanOrderIdEqualTo(liquidatesPlanOrderId);
				debtDetailOldCrt.andInvestOrderIdEqualTo(credit.getInvestOrderId());
				debtDetailOldCrt.andOrderIdEqualTo(credit.getSellOrderId());
				debtDetailOldCrt.andRepayStatusEqualTo(0);
				debtDetailOldCrt.andDelFlagEqualTo(1);
				debtDetailOldCrt.andStatusEqualTo(1);
				debtDetailOldExample.setOrderByClause("repay_period ASC");
				List<HjhDebtDetail> debtDetailOldList = this.hjhDebtDetailMapper.selectByExample(debtDetailOldExample);
				// 承接人此次承接的总待收本金
				BigDecimal assignPeriodCapitalTotal = BigDecimal.ZERO;
				// 承接人此次承接的总待收利息
				BigDecimal assignPeriodInterestTotal = BigDecimal.ZERO;
				// 校验数据完整性
				if (debtDetailOldList != null && debtDetailOldList.size() > 0) {
					Map<Integer, Object> assignResult = new HashMap<Integer, Object>();
					for (int i = 0; i < debtDetailOldList.size(); i++) {
						// 承接人此次承接的分期承接本金
						BigDecimal assignPeriodCapital = BigDecimal.ZERO;
						// 承接人此次承接的分期承接利息
						BigDecimal assignPeriodInterest = BigDecimal.ZERO;
						// 承接人此次承接的分期垫付利息
						BigDecimal assignPeriodAdvanceMentInterest = BigDecimal.ZERO;
						// 承接人此次承接的待收取分期延期利息
						BigDecimal assignPeriodRepayDelayInterest = BigDecimal.ZERO;
						// 承接人此次承接的待收取分期逾期利息
						BigDecimal assignPeriodRepayLateInterest = BigDecimal.ZERO;
						// 债权信息
						HjhDebtDetail debtDetailOld = debtDetailOldList.get(i);
						// 还款期数
						int waitRepayPeriod = debtDetailOld.getRepayPeriod();
						// 承接人此次承接的分期待收本金 TODO
						assignPeriodCapital = HJHServiceFeeUtils.getCurrentPeriodAssignCapital(assignPay, credit.getLiquidationFairValue(),
								debtDetailOld.getLoanCapital(), debtDetailOld.getRepayCapitalWait(), isLast);
						// 承接人此次承接的分期待收利息 TODO
						assignPeriodInterest = HJHServiceFeeUtils.getCurrentPeriodAssignInterest(assignPay, credit.getLiquidationFairValue(),
								debtDetailOld.getLoanInterest(), debtDetailOld.getRepayInterestWait(), isLast);
						// 本期延期利息(0) TODO
						assignPeriodRepayDelayInterest = BigDecimal.ZERO;
						// 本期逾期利息(0) TODO
						assignPeriodRepayLateInterest = BigDecimal.ZERO;
						// 承接人此次承接的总待收本金
						assignPeriodCapitalTotal = assignPeriodCapitalTotal.add(assignPeriodCapital);
						// 承接人此次承接的总待收利息
						assignPeriodInterestTotal = assignPeriodInterestTotal.add(assignPeriodInterest);
						// 返回结果集
						Map<String, BigDecimal> assignPeriodMap = new HashMap<String, BigDecimal>();
						assignPeriodMap.put(ASSIGN_PERIOD_CAPITAL, assignPeriodCapital);
						assignPeriodMap.put(ASSIGN_PERIOD_INTEREST, assignPeriodInterest);
						assignPeriodMap.put(ASSIGN_PERIOD_ADVANCEMENT_INTEREST, assignPeriodAdvanceMentInterest);
						assignPeriodMap.put(ASSIGN_PERIOD_REPAY_DELAY_INTEREST, assignPeriodRepayDelayInterest);
						assignPeriodMap.put(ASSIGN_PERIOD_REPAY_LATE_INTEREST, assignPeriodRepayLateInterest);
						assignResult.put(waitRepayPeriod, assignPeriodMap);
					}
					// 分期利息计算结果
					result.put(ASSIGN_RESULT, assignResult);
				}
				// 重置承接人承接总本金
				assignCapital = assignPeriodCapitalTotal;
				// 承接总利息
				assignInterest = assignPeriodInterestTotal;
				// 承接总本息
				assignAccount = assignCapital.add(assignInterest);
				// 承接人此次承接待收取的总延期利息(0)
				assignRepayDelayInterest = BigDecimal.ZERO;//assignPeriodRepayDelayInterestTotal;
				// 承接人此次承接待收取的总逾期利息(0)
				assignRepayLateInterest = BigDecimal.ZERO;//assignPeriodRepayLateInterestTotal;
				// 垫付总利息(实际支付-承接人每期承接本金之和)
				assignAdvanceMentInterest = assignPay.subtract(assignPeriodCapitalTotal);
			}
			// mod 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 start
			// 计算服务费
			//BigDecimal serviceFee = BigDecimal.ZERO;//this.calculateServiceFee(credit, assignCapital.add(assignAdvanceMentInterest));
			// 出让人加入计划信息
			HjhAccede sellerHjhAccede = this.selectHjhAccedeByAccedeOrderId(credit.getPlanOrderId());
			BigDecimal serviceApr = sellerHjhAccede.getLqdServiceApr();
			BigDecimal serviceFee = assignPay.multiply(serviceApr).setScale(2, BigDecimal.ROUND_DOWN);
			// mod 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 end

			// 返回结果封装
			result.put(ASSIGN_ACCOUNT, assignAccount);
			result.put(ASSIGN_CAPITAL, assignCapital);
			result.put(ASSIGN_INTEREST, assignInterest);
			result.put(ASSIGN_ADVANCEMENT_INTEREST, assignAdvanceMentInterest);
			result.put(ASSIGN_REPAY_DELAY_INTEREST, assignRepayDelayInterest);
			result.put(ASSIGN_REPAY_LATE_INTEREST, assignRepayLateInterest);
			result.put(ASSIGN_PAY, assignPay);
			result.put(SERVICE_FEE, serviceFee);
			// add 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 start
			result.put(SERVICE_APR, serviceApr);
			// add 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 end
			return result;
		} else {
			throw new RuntimeException("未查询到相应的标的信息，项目编号：" + borrowNid + ",还款期数：" + repayPeriod + 1);
		}
	}

	/**
	 * 查询用户的相应的加入订单
	 *
	 * @param userId
	 * @param liquidatesPlanNid
	 * @param liquidatesAccedeOrderId
	 * @return
	 */
	private HjhAccede selectDebtPlanAccede(int userId, String liquidatesPlanNid, String liquidatesAccedeOrderId) {

		HjhAccedeExample example = new HjhAccedeExample();
		HjhAccedeExample.Criteria crt = example.createCriteria();
		crt.andUserIdEqualTo(userId);
		crt.andPlanNidEqualTo(liquidatesPlanNid);
		crt.andAccedeOrderIdEqualTo(liquidatesAccedeOrderId);
		List<HjhAccede> debtPlanAccedeList = this.hjhAccedeMapper.selectByExample(example);
		if (debtPlanAccedeList != null && debtPlanAccedeList.size() == 1) {
			return debtPlanAccedeList.get(0);
		}
		return null;

	}

	/**
	 * 获取借款信息
	 *
	 * @param borrowNid
	 * @return 借款信息
	 */
	private Borrow selectDebtBorrowByNid(String borrowNid) {
		Borrow borrow = null;
		BorrowExample example = new BorrowExample();
		BorrowExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		List<Borrow> list = borrowMapper.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			borrow = list.get(0);
		}
		return borrow;
	}

	/**
	 * 债转汇付交易成功后回调处理
	 *
	 * @param sellerDebtPlanAccede
	 *
	 * @param creditOrderDate
	 * @param creditOrderId
	 * @param expectApr
	 * @param resultMap
	 *
	 * @return
	 * @throws Exception
	 */
	private boolean saveCreditTender(HjhAccede sellerDebtPlanAccede,
									 HjhDebtCredit debtCredit, HjhAccede assignDebtPlanAccede,
									 BankCallBean bean, String creditOrderId,
									 String creditOrderDate,
									 BigDecimal expectApr, Map<String, Object> resultMap,
									 String tenderUsrcustid, String sellerUsrcustid) throws Exception {
		// add 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 start
		// 债转服务费
		BigDecimal serviceFee = (BigDecimal) resultMap.get(this.SERVICE_FEE);
		// add 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 end

		// 清算出的债权编号
		String creditNid = debtCredit.getCreditNid();
		// 清算债权标号
		String liquidatesPlanNid = debtCredit.getPlanNid();
		// 清算债权加入订单号
		String liquidatesPlanOrderId = debtCredit.getPlanOrderId();
		// 清算所在期数
		int liquidatesPeriod = debtCredit.getLiquidatesPeriod();
		// 债权原有投资订单号
		String sellerOrderId = debtCredit.getSellOrderId();
		// 原标标号
		String borrowNid = debtCredit.getBorrowNid();
		// 原标年华收益率
		BigDecimal borrowApr = debtCredit.getBorrowApr();
		// 已还款期数
		int repayPeriod = debtCredit.getRepayPeriod();
		// 出让人用户id
		int sellerUserId = debtCredit.getUserId();
		// 出让人用户名
		String sellerUserName = debtCredit.getUserName();
		// 债权出让剩余本金
		BigDecimal sellerCapitalWait = debtCredit.getCreditCapitalWait();
		// 债权上次清算时间
		int lastLiquidationTime = debtCredit.getCreateTime();
		// 加入用户userId
		int userId = assignDebtPlanAccede.getUserId();
		// 加入用户名
		String userName = assignDebtPlanAccede.getUserName();
		// 计划nid
		String planNid = assignDebtPlanAccede.getPlanNid();
		// 计划加入订单号
		String planOrderId = assignDebtPlanAccede.getAccedeOrderId();
		// 加入平台
		int client = assignDebtPlanAccede.getClient();
		// 债权承接订单号
		String orderId = bean.getOrderId();
		// 债权承接日期
		String orderDate = bean.getTxDate();
		// 当前时间
		int nowTime = GetDate.getNowTime10();
		Borrow borrow = this.selectDebtBorrowByNid(borrowNid);
		if (Validator.isNotNull(borrow)) {
			String borrowStyle = borrow.getBorrowStyle();
			// 是否月标(true:月标, false:天标)
			boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
			// 检验回调是否已经存入CreditTender表中
			HjhDebtCreditTenderExample creditTenderExample = new HjhDebtCreditTenderExample();
			HjhDebtCreditTenderExample.Criteria debtCreditTenderCrt = creditTenderExample.createCriteria();
			debtCreditTenderCrt.andLiquidatesPlanNidEqualTo(liquidatesPlanNid);
			debtCreditTenderCrt.andAssignPlanNidEqualTo(planNid);
			debtCreditTenderCrt.andCreditNidEqualTo(creditNid);
			debtCreditTenderCrt.andSellOrderIdEqualTo(sellerOrderId);
			debtCreditTenderCrt.andAssignOrderIdEqualTo(creditOrderId);
			debtCreditTenderCrt.andUserIdEqualTo(userId);
			List<HjhDebtCreditTender> creditTenderList = this.hjhDebtCreditTenderMapper.selectByExample(creditTenderExample);
			if (creditTenderList == null || creditTenderList.size() <= 0) {
				// 获取CreditTenderLog信息
				HjhDebtCreditTenderLogExample debtCreditTenderLogExample = new HjhDebtCreditTenderLogExample();
				HjhDebtCreditTenderLogExample.Criteria debtCreditTenderLogCrt = debtCreditTenderLogExample.createCriteria();
				debtCreditTenderLogCrt.andLiquidatesPlanNidEqualTo(liquidatesPlanNid);
				debtCreditTenderLogCrt.andAssignPlanNidEqualTo(planNid);
				debtCreditTenderLogCrt.andCreditNidEqualTo(creditNid);
				debtCreditTenderLogCrt.andSellOrderIdEqualTo(sellerOrderId);
				debtCreditTenderLogCrt.andAssignOrderIdEqualTo(creditOrderId);
				debtCreditTenderLogCrt.andUserIdEqualTo(userId);
				List<HjhDebtCreditTenderLog> debtCreditTenderLogList = this.hjhDebtCreditTenderLogMapper.selectByExample(debtCreditTenderLogExample);
				if (debtCreditTenderLogList != null && debtCreditTenderLogList.size() == 1) {
					// 首先更新CreditTenderLog状态是10
					HjhDebtCreditTenderLog debtCreditTenderLog = debtCreditTenderLogList.get(0);
					debtCreditTenderLog.setStatus(10);
					debtCreditTenderLog.setUpdateTime(nowTime);
					debtCreditTenderLog.setUpdateUserId(userId);
					debtCreditTenderLog.setUpdateUserName(userName);
					boolean debtCreditTenderLogFlag = this.hjhDebtCreditTenderLogMapper.updateByPrimaryKeySelective(debtCreditTenderLog) > 0 ? true : false;
					if (debtCreditTenderLogFlag) {
						// ↓↓↓↓↓↓↓↓汇计划2期临时使用 ，按标的利率，非按计划利率更新hjhrepay表↓↓↓↓↓↓↓↓
						boolean hjhRepayFlag = updateHjhRepay(sellerDebtPlanAccede.getAccedeOrderId(), debtCreditTenderLog);
						if (!hjhRepayFlag) {
							throw new RuntimeException("出让人的计划还款表HjhRepay更新失败，出让人加入订单号：" + sellerDebtPlanAccede.getAccedeOrderId());
						}
						// ↑↑↑↑↑↑↑↑汇计划2期临时使用 ，按标的利率，非按计划利率更新hjhrepay表↑↑↑↑↑↑↑↑

						/*// 取得债权出让人的用户在汇付天下的客户号
						AccountChinapnr accountChinapnrSeller = bean.this.getAccountChinapnr(debtCreditTenderLog.getCreditUserId());
						// 取得承接债转的用户在汇付天下的客户号
						AccountChinapnr accountChinapnrTender = this.getAccountChinapnr(userId);
						// 取得债权出让人的用户在银行的客户号
						if (orderId.equals(creditOrderId) && orderDate.equals(creditOrderDate) && accountChinapnrSeller.getChinapnrUsrcustid().toString().equals(bean.getSellCustId()) && accountChinapnrTender.getChinapnrUsrcustid().toString().equals(bean.getBuyCustId())) {*/
						// 承接总本金
						BigDecimal assignTotalCapital = debtCreditTenderLog.getAssignCapital();
						// 1.插入credit_tender
						HjhDebtCreditTender debtCreditTender = new HjhDebtCreditTender();
						debtCreditTender.setLiquidatesPlanNid(liquidatesPlanNid);// 清算计划nid
						debtCreditTender.setLiquidatesPlanOrderId(liquidatesPlanOrderId);// 清算计划订单号
						debtCreditTender.setAssignPlanNid(planNid);// 承接计划编号
						debtCreditTender.setAssignPlanOrderId(planOrderId);// 承接计划加入订单号
						debtCreditTender.setBorrowNid(borrowNid);// 原标标号
						debtCreditTender.setCreditNid(creditNid);// 债转标号
						debtCreditTender.setCreditUserId(debtCredit.getUserId());// 出让人id
						debtCreditTender.setCreditUserName(debtCredit.getUserName());
						debtCreditTender.setInvestOrderId(debtCredit.getInvestOrderId());
						debtCreditTender.setSellOrderId(sellerOrderId);// 债转投标单号
						debtCreditTender.setAssignOrderId(creditOrderId);// 认购单号
						debtCreditTender.setAssignOrderDate(creditOrderDate);// 认购日期
						debtCreditTender.setAssignPay(debtCreditTenderLog.getAssignPay());// 支付金额
						debtCreditTender.setAssignServiceFee(serviceFee); // 服务费
						// mod 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 start
						debtCreditTender.setAssignServiceApr(debtCreditTenderLog.getAssignServiceApr());// 债转服务费率
						// mod 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 end
						debtCreditTender.setUserId(userId);// 用户id
						debtCreditTender.setUserName(userName);// 承接人用户名
						debtCreditTender.setStatus(0);// 状态
						debtCreditTender.setAssignAccount(debtCreditTenderLog.getAssignAccount());// 回收总额
						debtCreditTender.setAssignCapital(debtCreditTenderLog.getAssignCapital()); // 投资本金
						debtCreditTender.setAssignInterest(debtCreditTenderLog.getAssignInterest());// 债转利息\
						debtCreditTender.setAssignRepayDelayInterest(debtCreditTenderLog.getAssignRepayDelayInterest());
						debtCreditTender.setAssignRepayLateInterest(debtCreditTenderLog.getAssignRepayLateInterest());
						debtCreditTender.setAssignInterestAdvance(debtCreditTenderLog.getAssignInterestAdvance());// 垫付利息
						debtCreditTender.setAssignPrice(debtCreditTenderLog.getAssignPrice());// 购买价格
						debtCreditTender.setRepayAccountWait(debtCreditTenderLog.getAssignRepayAccount());// 已还总额
						debtCreditTender.setRepayCapitalWait(debtCreditTenderLog.getAssignRepayCapital());// 已还本金
						debtCreditTender.setRepayInterestWait(debtCreditTenderLog.getAssignRepayInterest());// 已还利息
						debtCreditTender.setAssignRepayEndTime(debtCreditTenderLog.getAssignRepayEndTime());// 最后还款日
						debtCreditTender.setAssignRepayLastTime(debtCreditTenderLog.getAssignRepayLastTime());// 上次还款时间
						debtCreditTender.setAssignRepayNextTime(debtCreditTenderLog.getAssignRepayNextTime());// 下次还款时间
						debtCreditTender.setAssignRepayYesTime(0);// 最终实际还款时间
						debtCreditTender.setAssignRepayPeriod(debtCreditTenderLog.getAssignRepayPeriod());// 还款期数
						debtCreditTender.setClient(client);// 客户端
						debtCreditTender.setRepayPeriod(repayPeriod);
						debtCreditTender.setCreateUserId(userId);
						debtCreditTender.setCreateUserName(userName);
						debtCreditTender.setCreateTime(nowTime);
						debtCreditTender.setAuthCode(bean.getAuthCode());//银行返回码
						// add 汇计划二期迭代 复投债转的状态追加 liubin 20180330 start
						// 判定该笔承接是投资还是复投
						if (assignDebtPlanAccede.getOrderStatus() == 0) {
							debtCreditTender.setTenderType(0);// 投资0
						} else {
							debtCreditTender.setTenderType(1);// // 复投1
						}
						// add 汇计划二期迭代 复投债转的状态追加 liubin 20180330 end
						boolean debtCreditTenderFlag = this.hjhDebtCreditTenderMapper.insertSelective(debtCreditTender) > 0 ? true : false;
						if (debtCreditTenderFlag) {
							// 5.更新borrow_credit
							debtCredit.setCreditAccountWait(debtCredit.getCreditAccountWait().subtract(debtCreditTender.getAssignAccount()));// 待承接总金额 认购本息（不包含垫付利息）
							debtCredit.setCreditCapitalWait(debtCredit.getCreditCapitalWait().subtract(debtCreditTender.getAssignCapital()));//待承接本金
							debtCredit.setCreditInterestWait(debtCredit.getCreditInterestWait().subtract(debtCreditTender.getAssignInterest()));//待承接利息
							debtCredit.setCreditInterestAdvanceWait(debtCredit.getCreditInterestAdvanceWait().subtract(debtCreditTender.getAssignInterestAdvance()));//待承接垫付总利息
							debtCredit.setCreditAccountAssigned(debtCredit.getCreditAccountAssigned().add(debtCreditTender.getAssignAccount()));// 已承接总金额
							debtCredit.setCreditCapitalAssigned(debtCredit.getCreditCapitalAssigned().add(debtCreditTender.getAssignCapital()));// 已承接本金
							debtCredit.setCreditInterestAssigned(debtCredit.getCreditInterestAssigned().add(debtCreditTender.getAssignInterest()));// 已承接待还总利息
							debtCredit.setCreditInterestAdvanceAssigned(debtCredit.getCreditInterestAdvanceAssigned().add(debtCreditTender.getAssignInterestAdvance()));//已承接垫付总利息
							debtCredit.setCreditDelayInterestAssigned(debtCredit.getCreditDelayInterestAssigned().add(debtCreditTender.getAssignRepayDelayInterest()));//承接已垫付的延期利息
							debtCredit.setCreditLateInterestAssigned(debtCredit.getCreditLateInterestAssigned().add(debtCreditTender.getAssignRepayLateInterest()));//承接已垫付的逾期利息
							// mod 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 start
//								debtCredit.setCreditServiceFee(debtCredit.getCreditServiceFee().add(debtCreditTender.getAssignServiceFee()));// 服务费
							debtCredit.setCreditServiceFee(debtCredit.getCreditServiceFee().add(serviceFee));// 服务费
							// mod 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 end
							debtCredit.setCreditIncome(debtCredit.getCreditIncome().add(debtCreditTender.getAssignPay().subtract(serviceFee)));// 总收入,本金+垫付利息-服务费
							debtCredit.setCreditPrice(debtCredit.getCreditPrice().add(debtCreditTender.getAssignPay()));// 总出让价格（每次承接累加）,本金+垫付利息
							debtCredit.setUpdateTime(nowTime);// 认购时间
							debtCredit.setUpdateUserId(userId);// 认购时间
							debtCredit.setUpdateUserName(userName);// 认购时间
							debtCredit.setAssignNum(debtCredit.getAssignNum() + 1);// 投资次数
//								if (debtCredit.getCreditCapitalWait().compareTo(new BigDecimal(0)) == 0) {
//									debtCredit.setCreditStatus(2);//转让状态 2完全承接
//									debtCredit.setIsLiquidates(1);//TODO
//								} else {
							debtCredit.setCreditStatus(1);//转让状态 1.部分承接
//								}
							// add 汇计划二期迭代 债转结束时间追加 liubin 20180402 start
							if (debtCredit.getCreditCapitalWait().compareTo(new BigDecimal(0)) == 0) {
								debtCredit.setEndTime(nowTime);
							}
							// add 汇计划二期迭代 债转结束时间追加 liubin 20180402 end
							boolean debtCreditFlag = hjhDebtCreditMapper.updateByPrimaryKey(debtCredit) > 0 ? true : false;
							if (debtCreditFlag) {
//									// 承接人计划订单信息更新，余额扣减
//									DebtPlanAccede assignDebtPlanAccedeNew = new DebtPlanAccede();
//									assignDebtPlanAccedeNew.setAccedeBalance(debtCreditTenderLog.getAssignPay());
//									assignDebtPlanAccedeNew.setId(assignDebtPlanAccede.getId());
//									boolean assignDebtPlanAccedeFlag = this.batchDebtPlanAccedeCustomizeMapper.updateDebtPlanAccedeAssign(assignDebtPlanAccedeNew) > 0 ? true : false;
//									if (assignDebtPlanAccedeFlag) {
//										HjhPlan assignDebtPlan = new HjhPlan();
//										assignDebtPlan.setPlanNid(planNid);
//										assignDebtPlan.setPlanBalance(debtCreditTenderLog.getAssignPay());
//										assignDebtPlan.setUpdateTime(nowTime);
//										assignDebtPlan.setUpdateUserId(userId);
//										assignDebtPlan.setUpdateUserName(userName);
//										boolean debtPlanFlag = batchDebtPlanCustomizeMapper.updateDebtPlanAssign(assignDebtPlan) > 0 ? true : false;
//										if (debtPlanFlag) {
//											// 更新承接用户账户余额表
//											Account creditAssignAccount = new Account();
//											// 承接用户id
//											creditAssignAccount.setUserId(userId);
//											// 承接人可用余额
//											creditAssignAccount.setPlanBalance(debtCreditTenderLog.getAssignPay());
//											// 承接人计划真实可用余额
//											creditAssignAccount.setPlanAccedeBalance(debtCreditTenderLog.getAssignPay());
//											// 更新承接人计划账户
//											boolean assignAccountFlag = this.adminAccountCustomizeMapper.updateOfPlanCreditAssign(creditAssignAccount) > 0 ? true : false;
//											if (assignAccountFlag) {
								Account assignAccount = this.selectUserAccount(userId);
								if (Validator.isNotNull(assignAccount)) {
									// 插入相应的承接人汇添金资金明细表
									boolean assignDebtAccountListFlag = insertAccountListForCredit(assignDebtPlanAccede, bean,
											userId, tenderUsrcustid,
											this.HJH_ASSIGN, serviceFee);
									if (assignDebtAccountListFlag) {
//														DebtPlan sellerDebtPlan = new DebtPlan();
//														sellerDebtPlan.setDebtPlanNid(liquidatesPlanNid);
//														sellerDebtPlan.setServiceFee(serviceFee);
//														sellerDebtPlan.setLiquidateArrivalAmount(debtCreditTenderLog.getAssignPay().subtract(serviceFee));
//														sellerDebtPlan.setUpdateTime(nowTime);
//														sellerDebtPlan.setUpdateUserId(userId);
//														sellerDebtPlan.setUpdateUserName(userName);
//														boolean debtPlanSellerFlag = batchDebtPlanCustomizeMapper.updateDebtPlanSeller(sellerDebtPlan) > 0 ? true : false;
//														if (debtPlanSellerFlag) {
//															// 出让人计划订单信息更新，清算承接债权余额增加
//															DebtPlanAccede sellerDebtPlanAccedeNew = new DebtPlanAccede();
//															sellerDebtPlanAccedeNew.setLiquidatesCreditFrost(debtCreditTenderLog.getAssignPay().subtract(serviceFee));
//															sellerDebtPlanAccedeNew.setServiceFee(serviceFee);
//															sellerDebtPlanAccedeNew.setId(sellerDebtPlanAccede.getId());
//															sellerDebtPlanAccedeNew.setStatus(2);// 1投资完成2清算中3清算完成
//															boolean sellerdebtPlanAccedeFlag = this.batchDebtPlanAccedeCustomizeMapper.updateDebtPlanAccedeSeller(sellerDebtPlanAccedeNew) > 0 ? true : false;
//															if (sellerdebtPlanAccedeFlag) {
//																// 更新出让人用户账户余额表
//																Account creditSellerAccount = new Account();
//																// 出让人用户id
//																creditSellerAccount.setUserId(sellerUserId);
//																// 出让人计划真实可用余额
//																creditSellerAccount.setPlanBalance(debtCreditTenderLog.getAssignPay().subtract(serviceFee));
//																// 更新出让人计划账户
//																boolean sellerAccountFlag = this.adminAccountCustomizeMapper.updateOfPlanCreditSeller(creditSellerAccount) > 0 ? true : false;
//																if (sellerAccountFlag) {
										Account sellerAccount = this.selectUserAccount(sellerUserId);
										if (Validator.isNotNull(sellerAccount)) {
//																		sellerDebtPlanAccede = selectDebtPlanAccede(sellerDebtPlanAccede.getId());
											// 插入相应的出让人汇添金资金明细表
											boolean sellerDebtAccountListFlag = insertAccountListForCredit(sellerDebtPlanAccede, bean,
													sellerUserId, sellerUsrcustid,
													this.HJH_SELL, serviceFee);
											if (sellerDebtAccountListFlag) {
												//7.生成还款信息
												// 不分期
												if (!isMonth) {
													// 出让人的债权信息
													HjhDebtDetailExample debtDetailExample = new HjhDebtDetailExample();
													HjhDebtDetailExample.Criteria debtDetailCrt = debtDetailExample.createCriteria();
													debtDetailCrt.andPlanNidEqualTo(liquidatesPlanNid);
													debtDetailCrt.andPlanOrderIdEqualTo(liquidatesPlanOrderId);
													debtDetailCrt.andInvestOrderIdEqualTo(debtCredit.getInvestOrderId());
													debtDetailCrt.andOrderIdEqualTo(sellerOrderId);
													debtDetailCrt.andRepayStatusEqualTo(0);
													debtDetailCrt.andDelFlagEqualTo(1);
													debtDetailCrt.andStatusEqualTo(1);
													debtDetailExample.setOrderByClause("repay_period ASC");
													List<HjhDebtDetail> debtDetailList = this.hjhDebtDetailMapper.selectByExample(debtDetailExample);
													if (debtDetailList != null && debtDetailList.size() == 1) {
														HjhDebtDetail debtDetailOld = debtDetailList.get(0);
														// 还款期数
														int waitRepayPeriod = debtDetailOld.getRepayPeriod();
														// 承接人此次承接的分期待收本金
														BigDecimal assignPeriodCapital = debtCreditTenderLog.getAssignCapital();
														// 承接人此次承接的分期待收利息
														BigDecimal assignPeriodInterest = debtCreditTenderLog.getAssignInterest();
														// 债转本息
														BigDecimal assignPeriodAccount = assignPeriodCapital.add(assignPeriodInterest);
														// 承接人此次承接的分期提前利息
														//BigDecimal assignPeriodRepayAdvanceInterest = debtCreditTenderLog.getAssignRepayAdvanceInterest();
														// 承接人此次承接的分期延期利息
														BigDecimal assignPeriodRepayDelayInterest = debtCreditTenderLog.getAssignRepayDelayInterest();
														// 承接人此次承接的分期逾期利息
														BigDecimal assignPeriodRepayLateInterest = debtCreditTenderLog.getAssignRepayLateInterest();
														HjhDebtDetail debtDetail = new HjhDebtDetail();
														debtDetail.setUserId(userId);
														debtDetail.setUserName(userName);
														debtDetail.setBorrowUserId(borrow.getUserId());
														debtDetail.setBorrowUserName(borrow.getBorrowUserName());
														debtDetail.setBorrowName(borrow.getName());
														debtDetail.setBorrowNid(borrowNid);
														debtDetail.setBorrowApr(borrowApr);
														debtDetail.setBorrowStyle(borrow.getBorrowStyle());
														debtDetail.setBorrowPeriod(borrow.getBorrowPeriod());
														debtDetail.setPlanNid(planNid);
														debtDetail.setPlanOrderId(planOrderId);
														debtDetail.setCreditNid(creditNid);
														debtDetail.setInvestOrderId(debtCredit.getInvestOrderId());
														debtDetail.setOrderId(creditOrderId);
														debtDetail.setOrderDate(creditOrderDate);
														debtDetail.setOrderType(1);
														debtDetail.setSourceType(0);
														debtDetail.setAccount(assignPeriodCapital);
														debtDetail.setLoanCapital(assignPeriodCapital);
														debtDetail.setLoanInterest(assignPeriodInterest);
														debtDetail.setLoanTime(debtDetailOld.getLoanTime());
														debtDetail.setRepayCapitalWait(assignPeriodCapital);
														debtDetail.setRepayInterestWait(assignPeriodInterest);
														debtDetail.setRepayCapitalYes(BigDecimal.ZERO);
														debtDetail.setRepayInterestYes(BigDecimal.ZERO);
														// mod 汇计划三期 汇计划自动投资 liubin 20180515 start
														//debtDetail.setServiceFee(BigDecimal.ZERO);
														debtDetail.setServiceFee(serviceFee);
														// mod 汇计划三期 汇计划自动投资 liubin 20180515 end
														debtDetail.setManageFee(BigDecimal.ZERO);
														debtDetail.setClient(client);
														debtDetail.setStatus(1);
														debtDetail.setRepayStatus(0);
														debtDetail.setAdvanceStatus(debtDetailOld.getAdvanceStatus());
														debtDetail.setAdvanceDays(debtDetailOld.getAdvanceDays());
														debtDetail.setAdvanceInterest(BigDecimal.ZERO);
														debtDetail.setDelayDays(debtDetailOld.getDelayDays());
														debtDetail.setDelayInterest(assignPeriodRepayDelayInterest);
														debtDetail.setDelayInterestAssigned(BigDecimal.ZERO);
														debtDetail.setLateDays(debtDetailOld.getLateDays());
														debtDetail.setLateInterest(assignPeriodRepayLateInterest);
														debtDetail.setLateInterestAssigned(BigDecimal.ZERO);
														debtDetail.setRepayPeriod(waitRepayPeriod);
														debtDetail.setRepayTime(debtDetailOld.getRepayTime());
														debtDetail.setRepayActionTime(0);
														debtDetail.setDelFlag(0);
														debtDetail.setLastLiquidationTime(lastLiquidationTime);
														debtDetail.setCreateTime(nowTime);
														debtDetail.setCreateUserId(userId);
														debtDetail.setCreateUserName(userName);
														debtDetail.setCreditTimes(debtCredit.getCreditTimes());//债权出让次数
														boolean debtDetailFlag = this.hjhDebtDetailMapper.insertSelective(debtDetail) > 0 ? true : false;
														if (debtDetailFlag) {
															HjhDebtCreditRepay debtCreditRepay = new HjhDebtCreditRepay();
															debtCreditRepay.setUserId(userId);// 用户名称
															debtCreditRepay.setUserName(userName);
															debtCreditRepay.setCreditUserId(debtCreditTender.getCreditUserId());// 出让人id
															debtCreditRepay.setCreditUserName(debtCreditTender.getCreditUserName());
															debtCreditRepay.setBorrowNid(debtCreditTender.getBorrowNid());// 原标标号
															debtCreditRepay.setCreditNid(debtCreditTender.getCreditNid());// 债转标号
															debtCreditRepay.setInvestOrderId(debtCredit.getInvestOrderId());
															debtCreditRepay.setSellOrderId(debtCreditTender.getSellOrderId());// 认购单号
															debtCreditRepay.setAssignPlanNid(debtCreditTender.getAssignPlanNid());// 债转承接计划编号
															debtCreditRepay.setAssignPlanOrderId(debtCreditTender.getAssignPlanOrderId());// 债转承接订单号
															debtCreditRepay.setAssignOrderId(debtCreditTender.getAssignOrderId());// 债转投标单号
															debtCreditRepay.setAdvanceStatus(debtDetailOld.getAdvanceStatus());
															debtCreditRepay.setAdvanceDays(debtDetailOld.getAdvanceDays());
															debtCreditRepay.setDelayDays(debtDetailOld.getDelayDays());
															debtCreditRepay.setLateDays(debtDetailOld.getLateDays());
															debtCreditRepay.setRepayAccount(assignPeriodAccount);// 应还总额
															debtCreditRepay.setRepayCapital(assignPeriodCapital);// 应还本金
															debtCreditRepay.setRepayInterest(assignPeriodInterest);// 应还利息
															debtCreditRepay.setRepayAccountWait(assignPeriodAccount);// 应还总额
															debtCreditRepay.setRepayCapitalWait(assignPeriodCapital);// 应还本金
															debtCreditRepay.setRepayInterestWait(assignPeriodInterest);// 应还利息
															debtCreditRepay.setRepayAccountYes(BigDecimal.ZERO);// 已还总额
															debtCreditRepay.setRepayCapitalYes(BigDecimal.ZERO);// 已还本金
															debtCreditRepay.setRepayInterestYes(BigDecimal.ZERO);// 已还利息
															debtCreditRepay.setRepayAdvanceInterest(BigDecimal.ZERO);
															debtCreditRepay.setRepayDelayInterest(BigDecimal.ZERO);
															debtCreditRepay.setRepayLateInterest(BigDecimal.ZERO);
															debtCreditRepay.setReceiveAccountYes(BigDecimal.ZERO);
															debtCreditRepay.setReceiveCapitalYes(BigDecimal.ZERO);
															debtCreditRepay.setReceiveInterestYes(BigDecimal.ZERO);
															debtCreditRepay.setReceiveAdvanceInterest(BigDecimal.ZERO);
															debtCreditRepay.setReceiveDelayInterest(assignPeriodRepayDelayInterest);
															debtCreditRepay.setReceiveLateInterest(assignPeriodRepayLateInterest);
															debtCreditRepay.setAssignRepayEndTime(debtCreditTender.getAssignRepayEndTime());// 最后还款日
															debtCreditRepay.setAssignRepayLastTime(debtCreditTender.getAssignRepayLastTime());// 上次还款时间
															debtCreditRepay.setAssignRepayNextTime(debtCreditTender.getAssignRepayNextTime());// 下次还款时间
															debtCreditRepay.setAssignRepayYesTime(0);// 最终实际还款时间
															debtCreditRepay.setAssignRepayTime(debtDetailOld.getRepayTime());
															debtCreditRepay.setAssignRepayPeriod(1);// 还款期数
															debtCreditRepay.setAssignCreateDate(Integer.valueOf(creditOrderDate));// 认购日期
//																						debtCreditRepay.setAddip(debtCreditTender.getAddip());// ip
															debtCreditRepay.setClient(0);// 客户端
															debtCreditRepay.setDelFlag(0);
															debtCreditRepay.setRepayStatus(0);// 状态
															debtCreditRepay.setRepayPeriod(1);// 原标还款期数
															debtCreditRepay.setManageFee(BigDecimal.ZERO);// 管理费
															// mod 汇计划三期 汇计划自动投资 liubin 20180515 start
															//debtCreditRepay.setLiquidatesServiceFee(BigDecimal.ZERO);
															debtCreditRepay.setLiquidatesServiceFee(serviceFee);
															// mod 汇计划三期 汇计划自动投资 liubin 20180515 end
															debtCreditRepay.setUniqueNid(creditOrderId + "_" + waitRepayPeriod);// 唯一nid
															debtCreditRepay.setCreateTime(nowTime);// 添加时间
															debtCreditRepay.setCreateUserId(userId);// 添加用户
															debtCreditRepay.setCreateUserName(userName);// 添加用户名
															debtCreditRepay.setAuthCode(bean.getAuthCode());//银行返回码
															boolean debtCreditRepayFlag = hjhDebtCreditRepayMapper.insertSelective(debtCreditRepay) > 0 ? true : false;
															if (debtCreditRepayFlag) {
																// 剩余本金
																debtDetailOld.setUpdateTime(nowTime);
																debtDetailOld.setUpdateUserId(sellerUserId);
																debtDetailOld.setUpdateUserName(sellerUserName);
																debtDetailOld.setStatus(0);
																// 更新老债权数据的待还本金，待还利息
																boolean debtDetailOldFlag = this.hjhDebtDetailMapper.updateByPrimaryKeySelective(debtDetailOld) > 0 ? true : false;
																if (debtDetailOldFlag) {
																	if (sellerCapitalWait.compareTo(assignTotalCapital) > 0) {
																		HjhDebtDetail debtDetailNew = new HjhDebtDetail();
																		debtDetailNew.setUserId(debtDetailOld.getUserId());
																		debtDetailNew.setUserName(debtDetailOld.getUserName());
																		debtDetailNew.setBorrowNid(debtDetailOld.getBorrowNid());
																		debtDetailNew.setBorrowName(debtDetailOld.getBorrowName());
																		debtDetailNew.setBorrowUserId(borrow.getUserId());
																		debtDetailNew.setBorrowUserName(borrow.getBorrowUserName());
																		debtDetailNew.setBorrowApr(debtDetailOld.getBorrowApr());
																		debtDetailNew.setBorrowPeriod(debtDetailOld.getBorrowPeriod());
																		debtDetailNew.setBorrowStyle(debtDetailOld.getBorrowStyle());
																		debtDetailNew.setPlanNid(debtDetailOld.getPlanNid());
																		debtDetailNew.setPlanOrderId(debtDetailOld.getPlanOrderId());
																		debtDetailNew.setCreditNid(debtDetailOld.getCreditNid());
																		debtDetailNew.setInvestOrderId(debtCredit.getInvestOrderId());
																		debtDetailNew.setOrderId(debtDetailOld.getOrderId());
																		debtDetailNew.setOrderDate(debtDetailOld.getOrderDate());
																		debtDetailNew.setOrderType(debtDetailOld.getOrderType());
																		debtDetailNew.setSourceType(debtDetailOld.getSourceType());
																		debtDetailNew.setAccount(debtDetailOld.getAccount().subtract(assignPeriodCapital));
																		debtDetailNew.setLoanCapital(debtDetailOld.getLoanCapital());
																		debtDetailNew.setLoanInterest(debtDetailOld.getLoanInterest());
																		debtDetailNew.setLoanTime(debtDetailOld.getLoanTime());
																		debtDetailNew.setRepayCapitalWait(debtDetailOld.getRepayCapitalWait().subtract(assignPeriodCapital));
																		debtDetailNew.setRepayInterestWait(debtDetailOld.getRepayInterestWait().subtract(assignPeriodInterest));
																		debtDetailNew.setRepayCapitalYes(BigDecimal.ZERO);
																		debtDetailNew.setRepayInterestYes(BigDecimal.ZERO);
																		debtDetailNew.setServiceFee(debtDetailOld.getServiceFee().add(serviceFee));
																		debtDetailNew.setManageFee(BigDecimal.ZERO);
																		debtDetailNew.setClient(client);
																		debtDetailNew.setStatus(1);
																		debtDetailNew.setRepayStatus(0);
																		debtDetailNew.setAdvanceStatus(debtDetailOld.getAdvanceStatus());
																		debtDetailNew.setAdvanceDays(debtDetailOld.getAdvanceDays());
																		debtDetailNew.setAdvanceInterest(debtDetailOld.getAdvanceInterest());
																		debtDetailNew.setDelayDays(debtDetailOld.getDelayDays());
																		debtDetailNew.setDelayInterest(debtDetailOld.getDelayInterest());
																		debtDetailNew.setDelayInterestAssigned(debtDetailOld.getDelayInterestAssigned().add(assignPeriodRepayDelayInterest));
																		debtDetailNew.setLateDays(debtDetailOld.getLateDays());
																		debtDetailNew.setLateInterest(debtDetailOld.getLateInterest());
																		debtDetailNew.setLateInterestAssigned(debtDetailOld.getLateInterestAssigned().add(assignPeriodRepayLateInterest));
																		debtDetailNew.setRepayTime(debtDetailOld.getRepayTime());
																		debtDetailNew.setRepayActionTime(0);
																		debtDetailNew.setRepayPeriod(waitRepayPeriod);
																		debtDetailNew.setDelFlag(1);
																		debtDetailNew.setExpireFairValue(debtDetailOld.getExpireFairValue());
																		debtDetailNew.setLastLiquidationTime(debtDetailOld.getLastLiquidationTime());
																		debtDetailNew.setCreateTime(nowTime);
																		debtDetailNew.setCreateUserId(debtDetailOld.getUserId());
																		debtDetailNew.setCreateUserName(debtDetailOld.getUserName());
																		boolean debtDetailNewFlag = this.hjhDebtDetailMapper.insertSelective(debtDetailNew) > 0 ? true : false;
																		if (!debtDetailNewFlag) {
																			throw new RuntimeException("出让人债权详情表debtdetail插入失败，加入订单号：" + liquidatesPlanOrderId);
																		}
																	}
																} else {
																	throw new RuntimeException("出让人债权详情表debtdetail老数据更新失败，加入订单号：" + planOrderId);
																}
																// 如果是非原始债权
																if (debtDetailOld.getSourceType() == 0) {
																	// 查询此笔清算数据是否有相应的债转还款信息
																	HjhDebtCreditRepay sellerDebtCreditRepayOld = this.selectDebtCreditRepay(liquidatesPlanNid, liquidatesPlanOrderId, sellerOrderId, waitRepayPeriod);
																	if (Validator.isNotNull(sellerDebtCreditRepayOld)) {
																		sellerDebtCreditRepayOld.setDelFlag(1);
																		boolean sellerDebtCreditRepayOldFlag = this.hjhDebtCreditRepayMapper.updateByPrimaryKeySelective(sellerDebtCreditRepayOld) > 0 ? true : false;
																		if (sellerDebtCreditRepayOldFlag) {
																			if (sellerCapitalWait.compareTo(assignTotalCapital) > 0) {
																				HjhDebtCreditRepay sellerDebtCreditRepay = new HjhDebtCreditRepay();
																				sellerDebtCreditRepay.setUserId(sellerDebtCreditRepayOld.getUserId());// 用户名称
																				sellerDebtCreditRepay.setUserName(sellerDebtCreditRepayOld.getUserName());
																				sellerDebtCreditRepay.setCreditUserId(sellerDebtCreditRepayOld.getCreditUserId());// 出让人id
																				sellerDebtCreditRepay.setCreditUserName(sellerDebtCreditRepayOld.getCreditUserName());
																				sellerDebtCreditRepay.setBorrowNid(sellerDebtCreditRepayOld.getBorrowNid());// 原标标号
																				sellerDebtCreditRepay.setCreditNid(sellerDebtCreditRepayOld.getCreditNid());// 债转标号
																				sellerDebtCreditRepay.setInvestOrderId(debtCredit.getInvestOrderId());
																				sellerDebtCreditRepay.setSellOrderId(sellerDebtCreditRepayOld.getSellOrderId());// 认购单号
																				sellerDebtCreditRepay.setAssignPlanNid(sellerDebtCreditRepayOld.getAssignPlanNid());// 债转承接计划编号
																				sellerDebtCreditRepay.setAssignPlanOrderId(sellerDebtCreditRepayOld.getAssignPlanOrderId());// 债转承接订单号
																				sellerDebtCreditRepay.setAssignOrderId(sellerDebtCreditRepayOld.getAssignOrderId());// 债转投标单号
																				sellerDebtCreditRepay.setAdvanceStatus(debtDetailOld.getAdvanceStatus());
																				sellerDebtCreditRepay.setAdvanceDays(debtDetailOld.getAdvanceDays());
																				sellerDebtCreditRepay.setDelayDays(debtDetailOld.getDelayDays());
																				sellerDebtCreditRepay.setLateDays(debtDetailOld.getLateDays());
																				sellerDebtCreditRepay.setRepayAccount(sellerDebtCreditRepayOld.getRepayAccount().subtract(assignPeriodAccount));// 应还总额
																				sellerDebtCreditRepay.setRepayCapital(sellerDebtCreditRepayOld.getRepayCapital().subtract(assignPeriodCapital));// 应还本金
																				sellerDebtCreditRepay.setRepayInterest(sellerDebtCreditRepayOld.getRepayInterest().subtract(assignPeriodInterest));// 应还利息
																				sellerDebtCreditRepay.setRepayAdvanceInterest(BigDecimal.ZERO);
																				sellerDebtCreditRepay.setRepayDelayInterest(BigDecimal.ZERO);
																				sellerDebtCreditRepay.setRepayLateInterest(BigDecimal.ZERO);
																				sellerDebtCreditRepay.setRepayAccountWait(sellerDebtCreditRepayOld.getRepayAccountWait().subtract(assignPeriodAccount));// 应还总额
																				sellerDebtCreditRepay.setRepayCapitalWait(sellerDebtCreditRepayOld.getRepayCapital().subtract(assignPeriodCapital));// 应还本金
																				sellerDebtCreditRepay.setRepayInterestWait(sellerDebtCreditRepayOld.getRepayInterest().subtract(assignPeriodInterest));// 应还利息
																				sellerDebtCreditRepay.setRepayAccountYes(BigDecimal.ZERO);// 已还总额
																				sellerDebtCreditRepay.setRepayCapitalYes(BigDecimal.ZERO);// 已还本金
																				sellerDebtCreditRepay.setRepayInterestYes(BigDecimal.ZERO);// 已还利息
																				sellerDebtCreditRepay.setReceiveAccountYes(BigDecimal.ZERO);
																				sellerDebtCreditRepay.setReceiveCapitalYes(BigDecimal.ZERO);
																				sellerDebtCreditRepay.setReceiveInterestYes(BigDecimal.ZERO);
																				sellerDebtCreditRepay.setReceiveAdvanceInterest(BigDecimal.ZERO);
																				sellerDebtCreditRepay.setReceiveDelayInterest(sellerDebtCreditRepayOld.getReceiveDelayInterest().subtract(assignPeriodRepayDelayInterest));
																				sellerDebtCreditRepay.setReceiveLateInterest(sellerDebtCreditRepayOld.getReceiveLateInterest().subtract(assignPeriodRepayLateInterest));
																				sellerDebtCreditRepay.setAssignRepayEndTime(debtCreditTender.getAssignRepayEndTime());// 最后还款日
																				sellerDebtCreditRepay.setAssignRepayLastTime(debtCreditTender.getAssignRepayLastTime());// 上次还款时间
																				sellerDebtCreditRepay.setAssignRepayNextTime(debtCreditTender.getAssignRepayNextTime());// 下次还款时间
																				sellerDebtCreditRepay.setAssignRepayYesTime(0);// 最终实际还款时间
																				sellerDebtCreditRepay.setAssignRepayTime(debtDetailOld.getRepayTime());
																				sellerDebtCreditRepay.setAssignRepayPeriod(1);// 还款期数
																				sellerDebtCreditRepay.setAssignCreateDate(sellerDebtCreditRepayOld.getAssignCreateDate());// 认购日期
//																											sellerDebtCreditRepay.setAddip(debtCreditTender.getAddip());// ip
																				sellerDebtCreditRepay.setRepayStatus(0);// 状态
																				sellerDebtCreditRepay.setClient(0);// 客户端
																				sellerDebtCreditRepay.setDelFlag(0);
																				sellerDebtCreditRepay.setRepayPeriod(1);// 原标还款期数
																				sellerDebtCreditRepay.setManageFee(BigDecimal.ZERO);// 管理费
																				sellerDebtCreditRepay.setLiquidatesServiceFee(debtDetailOld.getServiceFee().add(serviceFee));
																				sellerDebtCreditRepay.setUniqueNid(sellerDebtCreditRepayOld.getAssignOrderId() + "_" + creditOrderId + "_" + waitRepayPeriod);// 唯一nid
																				sellerDebtCreditRepay.setCreateTime(nowTime);// 添加时间
																				sellerDebtCreditRepay.setCreateUserId(sellerUserId);// 添加时间
																				sellerDebtCreditRepay.setCreateUserName(sellerUserName);// 添加时间
																				sellerDebtCreditRepay.setAuthCode(sellerDebtCreditRepayOld.getAuthCode());//银行返回码
																				boolean sellerDebtCreditRepayFlag = hjhDebtCreditRepayMapper.insertSelective(sellerDebtCreditRepay) > 0 ? true : false;
																				if (!sellerDebtCreditRepayFlag) {
																					throw new RuntimeException("出让人债转还款表debtcreditrepay插入失败,承接订单号" + debtDetailOld.getOrderId());
																				}
																			}
																		} else {
																			throw new RuntimeException("出让人债转还款表debtcreditrepay更新为无效失败，加入订单号：" + liquidatesPlanOrderId);
																		}
																	}
																} else {
																	// 更新相应的债转承接金额
																	BorrowRecoverExample debtLoanExample = new BorrowRecoverExample();
																	BorrowRecoverExample.Criteria debtLoanCrt = debtLoanExample.createCriteria();
																	debtLoanCrt.andNidEqualTo(debtCredit.getInvestOrderId());//TODO
																	List<BorrowRecover> debtLoanList = this.borrowRecoverMapper.selectByExample(debtLoanExample);
																	if (debtLoanList != null && debtLoanList.size() == 1) {
																		BorrowRecover debtLoan = debtLoanList.get(0);
																		debtLoan.setCreditAmount(debtLoan.getCreditAmount().add(assignPeriodCapital));
																		debtLoan.setCreditInterestAmount(debtLoan.getCreditInterestAmount().add(assignPeriodInterest));
																		// 最后一笔时BorrowRecover设置为结束债权
																		if (debtCredit.getCreditCapitalWait().compareTo(BigDecimal.ZERO) == 0) {
																			debtLoan.setCreditStatus(2);
																		}
																		boolean debtLoanFlag = this.borrowRecoverMapper.updateByPrimaryKeySelective(debtLoan) > 0 ? true : false;
																		if (!debtLoanFlag) {
																			throw new RuntimeException("更新相应的投资订单的放款信息失败，投资订单号：" + debtCredit.getInvestOrderId());
																		}
																	} else {
																		throw new RuntimeException("未查询到相应的投资订单的放款信息，投资订单号：" + debtCredit.getInvestOrderId());
																	}
																}
															} else {
																throw new RuntimeException("承接人债转还款表debtcreditrepay插入失败，加入订单号：" + planOrderId);
															}
														} else {
															throw new RuntimeException("承接人债权详情表debtdetail插入失败，加入订单号：" + planOrderId);
														}
													} else {
														throw new RuntimeException("出让人债权详情表debtdetail老数据查询失败，清算计划，计划加入订单号：" + liquidatesPlanOrderId);
													}
												}
												// 分期
												else if (borrow.getBorrowStyle().equals(CalculatesUtil.STYLE_ENDMONTH) || borrow.getBorrowStyle().equals(CalculatesUtil.STYLE_PRINCIPAL) || borrow.getBorrowStyle().equals(CalculatesUtil.STYLE_MONTH)) {
													// 出让人的债权信息
													HjhDebtDetailExample debtDetailExample = new HjhDebtDetailExample();
													HjhDebtDetailExample.Criteria debtDetailCrt = debtDetailExample.createCriteria();
													debtDetailCrt.andPlanNidEqualTo(liquidatesPlanNid);
													debtDetailCrt.andPlanOrderIdEqualTo(liquidatesPlanOrderId);
													debtDetailCrt.andInvestOrderIdEqualTo(debtCredit.getInvestOrderId());
													debtDetailCrt.andOrderIdEqualTo(debtCredit.getSellOrderId());
													debtDetailCrt.andRepayStatusEqualTo(0);
													debtDetailCrt.andDelFlagEqualTo(1);
													debtDetailCrt.andStatusEqualTo(1);
													debtDetailExample.setOrderByClause("repay_period ASC");
													List<HjhDebtDetail> debtDetailList = this.hjhDebtDetailMapper.selectByExample(debtDetailExample);
													if (debtDetailList != null && debtDetailList.size() > 0) {
														for (int i = 0; i < debtDetailList.size(); i++) {
															HjhDebtDetail debtDetailOld = debtDetailList.get(i);
															// 还款期数
															int waitRepayPeriod = debtDetailOld.getRepayPeriod();
															@SuppressWarnings("unchecked")
															Map<Integer, Object> result = (Map<Integer, Object>) resultMap.get(this.ASSIGN_RESULT);
															@SuppressWarnings("unchecked")
															Map<String, BigDecimal> periodResult = (Map<String, BigDecimal>) result.get(waitRepayPeriod);
															// 承接人此次承接的分期待收本金
															BigDecimal assignPeriodCapital = periodResult.get(this.ASSIGN_PERIOD_CAPITAL);
															// 承接人此次承接的分期待收利息
															BigDecimal assignPeriodInterest = periodResult.get(this.ASSIGN_PERIOD_INTEREST);
															// 承接人此次承接的分期延期利息
															BigDecimal assignPeriodRepayDelayInterest = periodResult.get(this.ASSIGN_PERIOD_REPAY_DELAY_INTEREST);
															// 承接人此次承接的分期逾期利息
															BigDecimal assignPeriodRepayLateInterest = periodResult.get(this.ASSIGN_PERIOD_REPAY_LATE_INTEREST);
															// 债转本息
															BigDecimal assignPeriodAccount = assignPeriodCapital.add(assignPeriodInterest);
															HjhDebtDetail debtDetail = new HjhDebtDetail();
															debtDetail.setUserId(userId);
															debtDetail.setUserName(userName);
															debtDetail.setBorrowUserId(borrow.getUserId());
															debtDetail.setBorrowName(borrow.getName());
															debtDetail.setBorrowUserName(borrow.getBorrowUserName());
															debtDetail.setBorrowNid(borrowNid);
															debtDetail.setBorrowApr(borrowApr);
															debtDetail.setBorrowPeriod(borrow.getBorrowPeriod());
															debtDetail.setBorrowStyle(borrow.getBorrowStyle());
															debtDetail.setPlanNid(planNid);
															debtDetail.setPlanOrderId(planOrderId);
															debtDetail.setCreditNid(creditNid);
															debtDetail.setInvestOrderId(debtCredit.getInvestOrderId());
															debtDetail.setOrderId(creditOrderId);
															debtDetail.setOrderDate(creditOrderDate);
															debtDetail.setOrderType(1);
															debtDetail.setSourceType(0);
															debtDetail.setAccount(assignPeriodCapital);
															debtDetail.setLoanCapital(assignPeriodCapital);
															debtDetail.setLoanInterest(assignPeriodInterest);
															debtDetail.setLoanTime(debtDetailOld.getLoanTime());
															debtDetail.setRepayCapitalWait(assignPeriodCapital);
															debtDetail.setRepayInterestWait(assignPeriodInterest);
															debtDetail.setRepayCapitalYes(BigDecimal.ZERO);
															debtDetail.setRepayInterestYes(BigDecimal.ZERO);
															// mod 汇计划三期 汇计划自动投资 liubin 20180515 start
															//debtDetail.setServiceFee(BigDecimal.ZERO);
															debtDetail.setServiceFee(serviceFee);
															// mod 汇计划三期 汇计划自动投资 liubin 20180515 end
															debtDetail.setManageFee(BigDecimal.ZERO);
															debtDetail.setClient(client);
															debtDetail.setStatus(1);
															debtDetail.setRepayStatus(0);
															debtDetail.setAdvanceStatus(debtDetailOld.getAdvanceStatus());
															debtDetail.setAdvanceDays(debtDetailOld.getAdvanceDays());
															debtDetail.setAdvanceInterest(BigDecimal.ZERO);
															debtDetail.setDelayDays(debtDetailOld.getDelayDays());
															debtDetail.setDelayInterest(assignPeriodRepayDelayInterest);
															debtDetail.setDelayInterestAssigned(BigDecimal.ZERO);
															debtDetail.setLateDays(debtDetailOld.getLateDays());
															debtDetail.setLateInterest(assignPeriodRepayLateInterest);
															debtDetail.setLateInterestAssigned(BigDecimal.ZERO);
															debtDetail.setRepayPeriod(waitRepayPeriod);
															debtDetail.setRepayTime(debtDetailOld.getRepayTime());
															debtDetail.setRepayActionTime(0);
															debtDetail.setDelFlag(0);
															debtDetail.setLastLiquidationTime(lastLiquidationTime);
															debtDetail.setCreateTime(nowTime);
															debtDetail.setCreateUserId(userId);
															debtDetail.setCreateUserName(userName);
															debtDetail.setCreditTimes(debtCredit.getCreditTimes());//债权出让次数
															boolean debtDetailFlag = this.hjhDebtDetailMapper.insertSelective(debtDetail) > 0 ? true : false;
															if (debtDetailFlag) {
																HjhDebtCreditRepay debtCreditRepay = new HjhDebtCreditRepay();
																debtCreditRepay.setUserId(userId);// 用户名称
																debtCreditRepay.setUserName(userName);
																debtCreditRepay.setCreditUserId(sellerUserId);// 出让人id
																debtCreditRepay.setCreditUserName(sellerUserName);
																debtCreditRepay.setRepayStatus(0);// 状态
																debtCreditRepay.setBorrowNid(debtCreditTender.getBorrowNid());// 原标标号
																debtCreditRepay.setCreditNid(debtCreditTender.getCreditNid());// 债转标号
																debtCreditRepay.setInvestOrderId(debtCredit.getInvestOrderId());
																debtCreditRepay.setSellOrderId(debtCreditTender.getSellOrderId());// 认购单号
																debtCreditRepay.setAssignPlanNid(debtCreditTender.getAssignPlanNid());// 债转承接计划编号
																debtCreditRepay.setAssignPlanOrderId(debtCreditTender.getAssignPlanOrderId());// 债转承接订单号
																debtCreditRepay.setAssignOrderId(debtCreditTender.getAssignOrderId());// 债转投标单号
																debtCreditRepay.setAdvanceStatus(debtDetailOld.getAdvanceStatus());
																debtCreditRepay.setAdvanceDays(debtDetailOld.getAdvanceDays());
																debtCreditRepay.setDelayDays(debtDetailOld.getDelayDays());
																debtCreditRepay.setLateDays(debtDetailOld.getLateDays());
																debtCreditRepay.setRepayAccount(assignPeriodAccount);// 应还总额
																debtCreditRepay.setRepayCapital(assignPeriodCapital);// 应还本金
																debtCreditRepay.setRepayInterest(assignPeriodInterest);// 应还利息
																debtCreditRepay.setRepayAccountWait(assignPeriodAccount);// 应还总额
																debtCreditRepay.setRepayCapitalWait(assignPeriodCapital);// 应还本金
																debtCreditRepay.setRepayInterestWait(assignPeriodInterest);// 应还利息
																debtCreditRepay.setRepayAccountYes(BigDecimal.ZERO);// 已还总额
																debtCreditRepay.setRepayCapitalYes(BigDecimal.ZERO);// 已还本金
																debtCreditRepay.setRepayInterestYes(BigDecimal.ZERO);// 已还利息
																debtCreditRepay.setRepayAdvanceInterest(BigDecimal.ZERO);
																debtCreditRepay.setRepayDelayInterest(BigDecimal.ZERO);
																debtCreditRepay.setRepayLateInterest(BigDecimal.ZERO);
																debtCreditRepay.setReceiveAccountYes(BigDecimal.ZERO);
																debtCreditRepay.setReceiveCapitalYes(BigDecimal.ZERO);
																debtCreditRepay.setReceiveInterestYes(BigDecimal.ZERO);
																debtCreditRepay.setReceiveAdvanceInterest(BigDecimal.ZERO);
																debtCreditRepay.setReceiveDelayInterest(assignPeriodRepayDelayInterest);
																debtCreditRepay.setReceiveLateInterest(assignPeriodRepayLateInterest);
																debtCreditRepay.setAssignRepayEndTime(debtCreditTender.getAssignRepayEndTime());// 最后还款日
																if (i == 0) {
																	if (waitRepayPeriod <= 1) {
																		debtCreditRepay.setAssignRepayLastTime(0);// 上次还款时间
																	} else {
																		debtCreditRepay.setAssignRepayLastTime(debtCredit.getCreditRepayLastTime());// 上次还款时间
																	}
																	if (debtDetailList.size() > 1) {
																		debtCreditRepay.setAssignRepayNextTime(debtDetailList.get(i + 1).getRepayTime());// 下次还款时间
																	} else {
																		debtCreditRepay.setAssignRepayNextTime(debtDetailList.get(i).getRepayTime());// 下次还款时间
																	}
																} else if (i == debtDetailList.size() - 1) {
																	debtCreditRepay.setAssignRepayLastTime(debtDetailList.get(i - 1).getRepayTime());// 上次还款时间
																	debtCreditRepay.setAssignRepayNextTime(debtDetailList.get(i).getRepayTime());// 下次还款时间
																} else {
																	debtCreditRepay.setAssignRepayLastTime(debtDetailList.get(i - 1).getRepayTime());// 上次还款时间
																	debtCreditRepay.setAssignRepayNextTime(debtDetailList.get(i + 1).getRepayTime());// 下次还款时间
																}
																debtCreditRepay.setAssignRepayYesTime(0);// 最终实际还款时间
																debtCreditRepay.setAssignRepayTime(debtDetailOld.getRepayTime());
																debtCreditRepay.setAssignRepayPeriod(i + 1);// 还款期数
																debtCreditRepay.setAssignCreateDate(Integer.valueOf(creditOrderDate));// 认购日期
//																							debtCreditRepay.setAddip(debtCreditTender.getAddip());// ip
																debtCreditRepay.setClient(0);// 客户端
																debtCreditRepay.setDelFlag(0);
																debtCreditRepay.setRepayPeriod(debtDetailOld.getRepayPeriod());// 原标还款期数
																debtCreditRepay.setManageFee(BigDecimal.ZERO);// 管理费
																// mod 汇计划三期 汇计划自动投资 liubin 20180515 start
																//debtCreditRepay.setLiquidatesServiceFee(BigDecimal.ZERO);// 服务费
																debtCreditRepay.setLiquidatesServiceFee(serviceFee);// 服务费
																// mod 汇计划三期 汇计划自动投资 liubin 20180515 end
																debtCreditRepay.setUniqueNid(creditOrderId + "_" + waitRepayPeriod);// 唯一nid
																debtCreditRepay.setCreateTime(nowTime);
																debtCreditRepay.setCreateUserId(debtDetailOld.getUserId());
																debtCreditRepay.setCreateUserName(debtDetailOld.getUserName());
																debtCreditRepay.setAuthCode(bean.getAuthCode());//银行返回码

																boolean debtCreditRepayFlag = hjhDebtCreditRepayMapper.insertSelective(debtCreditRepay) > 0 ? true : false;
																if (debtCreditRepayFlag) {
																	debtDetailOld.setUpdateTime(nowTime);
																	debtDetailOld.setUpdateUserId(sellerUserId);
																	debtDetailOld.setUpdateUserName(sellerUserName);
																	debtDetailOld.setStatus(0);
																	// 更新老债权数据的待还本金，待还利息
																	boolean debtDetailOldFlag = this.hjhDebtDetailMapper.updateByPrimaryKeySelective(debtDetailOld) > 0 ? true : false;
																	if (debtDetailOldFlag) {
																		if (sellerCapitalWait.compareTo(assignTotalCapital) > 0) {
																			HjhDebtDetail debtDetailNew = new HjhDebtDetail();
																			debtDetailNew.setUserId(debtDetailOld.getUserId());
																			debtDetailNew.setUserName(debtDetailOld.getUserName());
																			debtDetailNew.setBorrowNid(debtDetailOld.getBorrowNid());
																			debtDetailNew.setBorrowName(debtDetailOld.getBorrowName());
																			debtDetailNew.setBorrowUserId(borrow.getUserId());
																			debtDetailNew.setBorrowUserName(borrow.getBorrowUserName());
																			debtDetailNew.setBorrowApr(debtDetailOld.getBorrowApr());
																			debtDetailNew.setBorrowPeriod(debtDetailOld.getBorrowPeriod());
																			debtDetailNew.setBorrowStyle(debtDetailOld.getBorrowStyle());
																			debtDetailNew.setPlanNid(debtDetailOld.getPlanNid());
																			debtDetailNew.setPlanOrderId(debtDetailOld.getPlanOrderId());
																			debtDetailNew.setCreditNid(debtDetailOld.getCreditNid());
																			debtDetailNew.setInvestOrderId(debtCredit.getInvestOrderId());
																			debtDetailNew.setOrderId(debtDetailOld.getOrderId());
																			debtDetailNew.setOrderDate(debtDetailOld.getOrderDate());
																			debtDetailNew.setOrderType(debtDetailOld.getOrderType());
																			debtDetailNew.setSourceType(debtDetailOld.getSourceType());
																			debtDetailNew.setAccount(debtDetailOld.getAccount().subtract(assignPeriodCapital));
																			debtDetailNew.setLoanCapital(debtDetailOld.getLoanCapital());
																			debtDetailNew.setLoanInterest(debtDetailOld.getLoanInterest());
																			debtDetailNew.setLoanTime(debtDetailOld.getLoanTime());
																			debtDetailNew.setRepayCapitalWait(debtDetailOld.getRepayCapitalWait().subtract(assignPeriodCapital));
																			debtDetailNew.setRepayInterestWait(debtDetailOld.getRepayInterestWait().subtract(assignPeriodInterest));
																			debtDetailNew.setRepayCapitalYes(BigDecimal.ZERO);
																			debtDetailNew.setRepayInterestYes(BigDecimal.ZERO);
																			if (i == 0) {
																				debtDetailNew.setServiceFee(debtDetailOld.getServiceFee().add(serviceFee));
																			} else {
																				// mod 汇计划三期 汇计划自动投资 liubin 20180515 start
																				//debtDetailNew.setServiceFee(BigDecimal.ZERO);
																				debtDetailNew.setServiceFee(serviceFee);
																				// mod 汇计划三期 汇计划自动投资 liubin 20180515 end
																			}
																			debtDetailNew.setManageFee(BigDecimal.ZERO);
																			debtDetailNew.setClient(client);
																			debtDetailNew.setStatus(1);
																			debtDetailNew.setRepayStatus(0);
																			debtDetailNew.setAdvanceStatus(debtDetailOld.getAdvanceStatus());
																			debtDetailNew.setAdvanceDays(debtDetailOld.getAdvanceDays());
																			debtDetailNew.setAdvanceInterest(debtDetailOld.getAdvanceInterest());
																			debtDetailNew.setDelayDays(debtDetailOld.getDelayDays());
																			debtDetailNew.setDelayInterest(debtDetailOld.getDelayInterest());
																			debtDetailNew.setDelayInterestAssigned(debtDetailOld.getDelayInterestAssigned().add(assignPeriodRepayDelayInterest));
																			debtDetailNew.setLateDays(debtDetailOld.getLateDays());
																			debtDetailNew.setLateInterest(debtDetailOld.getLateInterest());
																			debtDetailNew.setLateInterestAssigned(debtDetailOld.getLateInterestAssigned().add(assignPeriodRepayLateInterest));
																			debtDetailNew.setRepayTime(debtDetailOld.getRepayTime());
																			debtDetailNew.setRepayActionTime(0);
																			debtDetailNew.setRepayPeriod(waitRepayPeriod);
																			debtDetailNew.setDelFlag(1);
																			debtDetailNew.setExpireFairValue(debtDetailOld.getExpireFairValue());
																			debtDetailNew.setLastLiquidationTime(debtDetailOld.getLastLiquidationTime());
																			debtDetailNew.setCreateTime(nowTime);
																			debtDetailNew.setCreateUserId(debtDetailOld.getUserId());
																			debtDetailNew.setCreateUserName(debtDetailOld.getUserName());
																			boolean debtDetailNewFlag = this.hjhDebtDetailMapper.insertSelective(debtDetailNew) > 0 ? true : false;
																			if (!debtDetailNewFlag) {
																				throw new RuntimeException("出让人债权详情表debtdetail插入失败，加入订单号：" + liquidatesPlanOrderId);
																			}
																		}
																	} else {
																		throw new RuntimeException("出让人债权详情表debtdetail老数据更新失败，加入订单号：" + planOrderId);
																	}
																	// 如果是非原始债权
																	if (debtDetailOld.getSourceType() == 0) {
																		HjhDebtCreditRepay sellerDebtCreditRepayOld = this.selectDebtCreditRepay(liquidatesPlanNid, liquidatesPlanOrderId, sellerOrderId, waitRepayPeriod);
																		if (Validator.isNotNull(sellerDebtCreditRepayOld)) {
																			sellerDebtCreditRepayOld.setDelFlag(1);
																			boolean sellerDebtCreditRepayOldFlag = this.hjhDebtCreditRepayMapper.updateByPrimaryKeySelective(sellerDebtCreditRepayOld) > 0 ? true : false;
																			if (sellerDebtCreditRepayOldFlag) {
																				if (sellerCapitalWait.compareTo(assignTotalCapital) > 0) {
																					HjhDebtCreditRepay sellerDebtCreditRepay = new HjhDebtCreditRepay();
																					sellerDebtCreditRepay.setUserId(sellerDebtCreditRepayOld.getUserId());// 用户名称
																					sellerDebtCreditRepay.setUserName(sellerDebtCreditRepayOld.getUserName());
																					sellerDebtCreditRepay.setCreditUserId(sellerDebtCreditRepayOld.getCreditUserId());// 出让人id
																					sellerDebtCreditRepay.setCreditUserName(sellerDebtCreditRepayOld.getCreditUserName());
																					sellerDebtCreditRepay.setRepayStatus(0);// 状态
																					sellerDebtCreditRepay.setBorrowNid(sellerDebtCreditRepayOld.getBorrowNid());// 原标标号
																					sellerDebtCreditRepay.setCreditNid(sellerDebtCreditRepayOld.getCreditNid());// 债转标号
																					sellerDebtCreditRepay.setInvestOrderId(debtCredit.getInvestOrderId());
																					sellerDebtCreditRepay.setSellOrderId(sellerDebtCreditRepayOld.getSellOrderId());// 认购单号
																					sellerDebtCreditRepay.setAssignPlanNid(sellerDebtCreditRepayOld.getAssignPlanNid());// 债转承接计划编号
																					sellerDebtCreditRepay.setAssignPlanOrderId(sellerDebtCreditRepayOld.getAssignPlanOrderId());// 债转承接订单号
																					sellerDebtCreditRepay.setAssignOrderId(sellerDebtCreditRepayOld.getAssignOrderId());// 债转投标单号
																					sellerDebtCreditRepay.setAdvanceStatus(debtDetailOld.getAdvanceStatus());
																					sellerDebtCreditRepay.setAdvanceDays(debtDetailOld.getAdvanceDays());
																					sellerDebtCreditRepay.setDelayDays(debtDetailOld.getDelayDays());
																					sellerDebtCreditRepay.setLateDays(debtDetailOld.getLateDays());
																					sellerDebtCreditRepay.setRepayAccount(sellerDebtCreditRepayOld.getRepayAccount().subtract(assignPeriodAccount));// 应还总额
																					sellerDebtCreditRepay.setRepayCapital(sellerDebtCreditRepayOld.getRepayCapital().subtract(assignPeriodCapital));// 应还本金
																					sellerDebtCreditRepay.setRepayInterest(sellerDebtCreditRepayOld.getRepayInterest().subtract(assignPeriodInterest));// 应还利息
																					sellerDebtCreditRepay.setRepayAdvanceInterest(BigDecimal.ZERO);
																					sellerDebtCreditRepay.setRepayDelayInterest(BigDecimal.ZERO);
																					sellerDebtCreditRepay.setRepayLateInterest(BigDecimal.ZERO);
																					sellerDebtCreditRepay.setRepayAccountWait(sellerDebtCreditRepayOld.getRepayAccountWait().subtract(assignPeriodAccount));// 应还总额
																					sellerDebtCreditRepay.setRepayCapitalWait(sellerDebtCreditRepayOld.getRepayCapital().subtract(assignPeriodCapital));// 应还本金
																					sellerDebtCreditRepay.setRepayInterestWait(sellerDebtCreditRepayOld.getRepayInterest().subtract(assignPeriodInterest));// 应还利息
																					sellerDebtCreditRepay.setRepayAccountYes(BigDecimal.ZERO);// 已还总额
																					sellerDebtCreditRepay.setRepayCapitalYes(BigDecimal.ZERO);// 已还本金
																					sellerDebtCreditRepay.setRepayInterestYes(BigDecimal.ZERO);// 已还利息
																					sellerDebtCreditRepay.setReceiveAccountYes(BigDecimal.ZERO);
																					sellerDebtCreditRepay.setReceiveCapitalYes(BigDecimal.ZERO);
																					sellerDebtCreditRepay.setReceiveInterestYes(BigDecimal.ZERO);
																					sellerDebtCreditRepay.setReceiveAdvanceInterest(BigDecimal.ZERO);
																					sellerDebtCreditRepay.setReceiveDelayInterest(sellerDebtCreditRepayOld.getReceiveDelayInterest().subtract(assignPeriodRepayDelayInterest));
																					sellerDebtCreditRepay.setReceiveLateInterest(sellerDebtCreditRepayOld.getReceiveLateInterest().subtract(assignPeriodRepayLateInterest));
																					sellerDebtCreditRepay.setAssignRepayEndTime(debtCreditTender.getAssignRepayEndTime());// 最后还款日
																					sellerDebtCreditRepay.setAssignRepayLastTime(debtCreditTender.getAssignRepayLastTime());// 上次还款时间
																					sellerDebtCreditRepay.setAssignRepayNextTime(debtCreditTender.getAssignRepayNextTime());// 下次还款时间
																					sellerDebtCreditRepay.setAssignRepayYesTime(0);// 最终实际还款时间
																					sellerDebtCreditRepay.setAssignRepayTime(debtDetailOld.getRepayTime());
																					sellerDebtCreditRepay.setAssignRepayPeriod(1);// 还款期数
																					sellerDebtCreditRepay.setAssignCreateDate(sellerDebtCreditRepayOld.getAssignCreateDate());// 认购日期
//																												sellerDebtCreditRepay.setAddip(debtCreditTender.getAddip());// ip
																					sellerDebtCreditRepay.setClient(0);// 客户端
																					sellerDebtCreditRepay.setDelFlag(0);
																					sellerDebtCreditRepay.setRepayPeriod(waitRepayPeriod);// 原标还款期数
																					sellerDebtCreditRepay.setManageFee(BigDecimal.ZERO);// 管理费
																					if (i == 0) {
																						sellerDebtCreditRepay.setLiquidatesServiceFee(debtDetailOld.getServiceFee().add(serviceFee));// 清算服务费
																					} else {
																						// mod 汇计划三期 汇计划自动投资 liubin 20180515 start
																						//sellerDebtCreditRepay.setLiquidatesServiceFee(BigDecimal.ZERO);// 清算服务费
																						sellerDebtCreditRepay.setLiquidatesServiceFee(serviceFee);// 清算服务费
																						// mod 汇计划三期 汇计划自动投资 liubin 20180515 end
																					}
																					sellerDebtCreditRepay.setUniqueNid(sellerDebtCreditRepayOld.getAssignOrderId() + "_" + creditOrderId + "_" + waitRepayPeriod);// 唯一nid
																					sellerDebtCreditRepay.setCreateTime(nowTime);// 添加时间
																					sellerDebtCreditRepay.setCreateUserId(sellerUserId);// 添加时间
																					sellerDebtCreditRepay.setCreateUserName(sellerUserName);// 添加时间
																					sellerDebtCreditRepay.setAuthCode(sellerDebtCreditRepayOld.getAuthCode());//银行返回码
																					boolean sellerDebtCreditRepayFlag = hjhDebtCreditRepayMapper.insertSelective(sellerDebtCreditRepay) > 0 ? true : false;
																					if (!sellerDebtCreditRepayFlag) {
																						throw new RuntimeException("出让人债转还款表debtcreditrepay插入失败,承接订单号" + debtDetailOld.getOrderId());
																					}
																				}
																			} else {
																				throw new RuntimeException("出让人债转还款表debtcreditrepay更新为无效失败，加入订单号：" + liquidatesPlanOrderId);
																			}
																		}
																	} else {
																		// 更新相应的债转承接金额
																		BorrowRecoverExample debtLoanExample = new BorrowRecoverExample();
																		BorrowRecoverExample.Criteria debtLoanCrt = debtLoanExample.createCriteria();
																		debtLoanCrt.andNidEqualTo(debtCredit.getInvestOrderId());
																		List<BorrowRecover> debtLoanList = this.borrowRecoverMapper.selectByExample(debtLoanExample);
																		if (debtLoanList != null && debtLoanList.size() == 1) {
																			BorrowRecover debtLoan = debtLoanList.get(0);
																			debtLoan.setCreditAmount(debtLoan.getCreditAmount().add(assignPeriodCapital));
																			debtLoan.setCreditInterestAmount(debtLoan.getCreditInterestAmount().add(assignPeriodInterest));
																			boolean debtLoanFlag = this.borrowRecoverMapper.updateByPrimaryKeySelective(debtLoan) > 0 ? true : false;
																			if (!debtLoanFlag) {
																				throw new RuntimeException("更新相应的huiyingdai_borrow_recover信息失败，投资订单号：" + debtCredit.getInvestOrderId());
																			}
																		} else {
																			throw new RuntimeException("未查询到相应的投资订单的放款信息，投资订单号：" + debtCredit.getInvestOrderId());
																		}

																		// add 汇计划二期迭代 分前原始债转recoverplan表变更债转金额追加 liubin 20180410 start
																		// 更新recoverplan表的相应的债转承接金额
																		BorrowRecoverPlanExample borrowRecoverPlanExample = new BorrowRecoverPlanExample();
																		BorrowRecoverPlanExample.Criteria borrowRecoverPlanCrt = borrowRecoverPlanExample.createCriteria();
																		borrowRecoverPlanCrt.andNidEqualTo(debtCredit.getInvestOrderId());//投资订单
																		borrowRecoverPlanCrt.andRecoverPeriodEqualTo(debtDetailOld.getRepayPeriod()); // 原标还款期数
																		List<BorrowRecoverPlan> borrowRecoverPlanList = this.borrowRecoverPlanMapper.selectByExample(borrowRecoverPlanExample);
																		if (borrowRecoverPlanList != null && borrowRecoverPlanList.size() == 1) {
																			BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlanList.get(0);
																			borrowRecoverPlan.setCreditAmount(borrowRecoverPlan.getCreditAmount().add(assignPeriodCapital)); // 已承接债转本金
																			borrowRecoverPlan.setCreditInterestAmount(borrowRecoverPlan.getCreditInterestAmount().add(assignPeriodInterest));
																			borrowRecoverPlan.setCreditManageFee(BigDecimal.ZERO);
																			boolean borrowRecoverPlanFlag = this.borrowRecoverPlanMapper.updateByPrimaryKeySelective(borrowRecoverPlan) > 0 ? true : false;
																			if (!borrowRecoverPlanFlag) {
																				throw new RuntimeException("更新相应的huiyingdai_borrow_recover_plan信息失败，投资订单号：" + debtCredit.getInvestOrderId() + "期数：" + debtDetailOld.getRepayPeriod());
																			}
																		} else {
																			throw new RuntimeException("未查询到相应的投资订单的放款信息，投资订单号：" + debtCredit.getInvestOrderId());
																		}


																		// add 汇计划二期迭代 分前原始债转recoverplan表变更债转金额追加 liubin 20180410 end
																	}
																} else {
																	throw new RuntimeException("承接人债转还款表debtcreditrepay插入失败,承接订单号" + debtDetailOld.getOrderId());
																}
															} else {
																throw new RuntimeException("承接人债权详情表debtdetail插入失败，承接订单号：" + debtDetailOld.getOrderId());
															}
														}
													} else {
														throw new RuntimeException("出让人债权详情表debtdetail老数据查询失败，加入订单号：" + planOrderId);
													}
												}
//																			DebtPlanBorrowExample planBorrowExample = new DebtPlanBorrowExample();
//																			DebtPlanBorrowExample.Criteria planBorrowCrt = planBorrowExample.createCriteria();
//																			planBorrowCrt.andDebtPlanNidEqualTo(planNid);
//																			planBorrowCrt.andBorrowNidEqualTo(creditNid);
//																			planBorrowCrt.andTypeEqualTo(1);
//																			List<DebtPlanBorrow> debtPlanBorrows = this.debtPlanBorrowMapper.selectByExample(planBorrowExample);
//																			if (debtPlanBorrows != null && debtPlanBorrows.size() > 0) {
//																				DebtPlanBorrow debtPlanBorrow = new DebtPlanBorrow();
//																				debtPlanBorrow.setDelFlag(0);
//																				debtPlanBorrow.setAddType(1);
//																				debtPlanBorrow.setUpdateTime(nowTime);
//																				debtPlanBorrow.setUpdateUserId(userId);
//																				debtPlanBorrow.setUpdateUserName(userName);
//																				boolean debtPlanBorrowFlag = this.debtPlanBorrowMapper.updateByExampleSelective(debtPlanBorrow, planBorrowExample) > 0 ? true : false;
//																				if (!debtPlanBorrowFlag) {
//																					throw new RuntimeException("债权同计划关联表插入失败，计划编号：" + planNid + ",债转编号：" + creditNid);
//																				}
//																			} else {
//																				DebtPlanBorrow debtPlanBorrow = new DebtPlanBorrow();
//																				debtPlanBorrow.setDebtPlanNid(planNid);
//																				debtPlanBorrow.setBorrowNid(creditNid);
//																				debtPlanBorrow.setDelFlag(0);
//																				debtPlanBorrow.setType(1);
//																				debtPlanBorrow.setAddType(1);
//																				debtPlanBorrow.setCreateTime(nowTime);
//																				debtPlanBorrow.setCreateUserId(userId);
//																				debtPlanBorrow.setCreateUserName(userName);
//																				boolean debtPlanBorrowFlag = this.debtPlanBorrowMapper.insertSelective(debtPlanBorrow) > 0 ? true : false;
//																				if (!debtPlanBorrowFlag) {
//																					throw new RuntimeException("债权同计划关联表插入失败，计划编号：" + planNid + ",债转编号：" + creditNid);
//																				}
//																			}
												// 产生平台收取费用时 ****后期准备用,现在投资无服务，后期修改*****
												if (debtCreditTender.getAssignServiceFee().compareTo(new BigDecimal(0)) != 0) {
													// 承接人用户详细信息
													UsersInfoExample usersInfoExample = new UsersInfoExample();
													UsersInfoExample.Criteria userInfoCra = usersInfoExample.createCriteria();
													userInfoCra.andUserIdEqualTo(userId);
													List<UsersInfo> usersInfoList = this.usersInfoMapper.selectByExample(usersInfoExample);
													if (usersInfoList != null && usersInfoList.size() == 1) {
														// 4.添加网站收支明细
														AccountWebList accountWebList = new AccountWebList();
														accountWebList.setOrdid(debtCreditTender.getAssignOrderId() + "_" + borrowNid + "_" + liquidatesPeriod);
														accountWebList.setBorrowNid(debtCreditTender.getBorrowNid());
														accountWebList.setAmount(debtCreditTender.getAssignServiceFee());
														accountWebList.setType(1);
														accountWebList.setTrade("hjh_credit_fee");
														accountWebList.setTradeType("汇计划债转服务费");
														accountWebList.setUserId(debtCreditTender.getUserId());
														accountWebList.setUsrcustid(tenderUsrcustid);
														accountWebList.setTruename(usersInfoList != null ? usersInfoList.get(0).getTruename() : "");
														accountWebList.setRegionName(null);
														accountWebList.setBranchName(null);
														accountWebList.setDepartmentName(null);
														accountWebList.setRemark(debtCreditTender.getAssignOrderId());
														accountWebList.setNote(null);
														accountWebList.setCreateTime(nowTime);
														accountWebList.setOperator(null);
														accountWebList.setFlag(1);
														// 设置部门信息
														setDepartments(accountWebList);
														// 插入
														boolean accountWebListFlag = this.accountWebListMapper.insertSelective(accountWebList) > 0 ? true : false;
														if (accountWebListFlag) {
															return true;
														} else {
															throw new RuntimeException("网站收支插入失败，用户userId" + userId + ",承接订单号：" + debtCreditTender.getAssignOrderId());
														}
													} else {
														throw new RuntimeException("未查询到相应的债权诚接人的userInfo信息，用户userId" + userId);
													}
												}
												return true;
											} else {
												throw new RuntimeException("汇添金交易明细表debtaccountlist出让人插入失败，用户id：" + sellerUserId);
											}
										} else {
											throw new RuntimeException("汇添金出让人用户账户表account表出让人查询失败，用户id：" + sellerUserId);
										}
//																} else {
//																	throw new RuntimeException("汇添金用户账户表account表出让人更新失败，用户id：" + sellerUserId);
//																}
//															} else {
//																throw new RuntimeException("汇添金清算账户debtplanaccede表出让人更新失败，用户id：" + sellerUserId + ",计划订单号：" + sellerDebtPlanAccede.getAccedeOrderId());
//															}
//														} else {
//															throw new RuntimeException("更新出让计划debtplan信息失败，计划编号：" + liquidatesPlanNid);
//														}
									} else {
										throw new RuntimeException("汇添金交易明细表debtaccountlist承接人插入失败，用户id：" + userId);
									}
								} else {
									throw new RuntimeException("查询承接用户account表失败，用户userId：" + userId);
								}
//											} else {
//												throw new RuntimeException("汇添金用户账户表account表承接人更新失败" + userId);
//											}
//										} else {
//											throw new RuntimeException("更新承接计划debtplan信息失败，计划编号：" + planNid);
//										}
//									} else {
//										throw new RuntimeException("汇添金计划加入表debtplanaccede更新失败，加入订单号：" + assignDebtPlanAccede.getAccedeOrderId());
//									}
							} else {
								throw new RuntimeException("债权信息表debtCredit更新失败，债权编号：" + debtCredit.getCreditNid());
							}
						} else {
							throw new RuntimeException("汇添金债权承接表debtcredittender表插入失败，债权编号：" + debtCredit.getCreditNid());
						}
//						} else {
//							throw new RuntimeException("汇添金债权承接数据校验错误：" + JSONObject.toJSONString(bean));
//						}
					} else {
						throw new RuntimeException("汇添金债权承接log表debtcredittenderlog表更新失败，承接订单号编号：" + debtCreditTenderLog.getAssignOrderId());
					}
				} else {
					throw new RuntimeException("汇添金债权承接log表debtcredittenderlog表查询失败，债权编号：" + debtCredit.getCreditNid());
				}
			} else {
				throw new RuntimeException("汇添金债权承接表debtcredittender表数据已经存在，承接订单号：" + creditOrderId);
			}
		} else {
			throw new RuntimeException("未查询到相应的标的信息，标号：" + borrowNid);
		}
	}

	/**
	 * 根据userId获取用户账户信息
	 *
	 * @param userId
	 * @return
	 */

	private Account selectUserAccount(Integer userId) {

		AccountExample example = new AccountExample();
		AccountExample.Criteria crt = example.createCriteria();
		crt.andUserIdEqualTo(userId);
		List<Account> accountList = this.accountMapper.selectByExample(example);
		if (accountList != null && accountList.size() == 1) {
			return accountList.get(0);
		}
		return null;
	}

	/**
	 * 根据相应的计划加入订单记录id获取计划加入记录
	 *
	 * @param id
	 * @return
	 * @author Administrator
	 */
	private HjhAccede selectDebtPlanAccede(Integer id) {
		HjhAccede debtPlanAccede = this.hjhAccedeMapper.selectByPrimaryKey(id);
		return debtPlanAccede;
	}

	/**
	 * 查询相应的清算数据的原始还款数据
	 *
	 * @param liquidatesPlanNid
	 * @param liquidatesPlanOrderId
	 * @param sellerOrderId
	 * @param waitRepayPeriod
	 * @return
	 * @throws Exception
	 */
	private HjhDebtCreditRepay selectDebtCreditRepay(String liquidatesPlanNid, String liquidatesPlanOrderId, String sellerOrderId, int waitRepayPeriod) throws Exception {

		HjhDebtCreditRepayExample example = new HjhDebtCreditRepayExample();
		HjhDebtCreditRepayExample.Criteria crt = example.createCriteria();
		crt.andAssignPlanNidEqualTo(liquidatesPlanNid);
		crt.andAssignPlanOrderIdEqualTo(liquidatesPlanOrderId);
		crt.andRepayPeriodEqualTo(waitRepayPeriod);
		crt.andAssignOrderIdEqualTo(sellerOrderId);
		crt.andDelFlagEqualTo(0);
		List<HjhDebtCreditRepay> debtCreditRepays = this.hjhDebtCreditRepayMapper.selectByExample(example);
		if (debtCreditRepays != null && debtCreditRepays.size() == 1) {
			return debtCreditRepays.get(0);
		} else {
			throw new RuntimeException("未查询到相应的债转还款记录" + "承接订单号：" + sellerOrderId + ",还款期数：" + waitRepayPeriod);
		}
	}

	/**
	 * 设置部门名称 TODO
	 *
	 * @param accountWebList
	 */
	private void setDepartments(AccountWebList accountWebList) {
		if (accountWebList != null) {
			Integer userId = accountWebList.getUserId();
			UsersInfo usersInfo = getUsersInfoByUserId(userId);
			if (usersInfo != null) {
				Integer attribute = usersInfo.getAttribute();
				if (attribute != null) {
					// 查找用户的的推荐人
					Users users = getUsersByUserId(userId);
					Integer refUserId = users.getReferrer();
					SpreadsUsersExample spreadsUsersExample = new SpreadsUsersExample();
					SpreadsUsersExample.Criteria spreadsUsersExampleCriteria = spreadsUsersExample.createCriteria();
					spreadsUsersExampleCriteria.andUserIdEqualTo(userId);
					List<SpreadsUsers> sList = spreadsUsersMapper.selectByExample(spreadsUsersExample);
					if (sList != null && !sList.isEmpty()) {
						refUserId = sList.get(0).getSpreadsUserid();
					}
					// 如果是线上员工或线下员工，推荐人的userId和username不插
					if (users != null && (attribute == 2 || attribute == 3)) {
						// 查找用户信息
						EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(userId);
						if (employeeCustomize != null) {
							accountWebList.setRegionName(employeeCustomize.getRegionName());
							accountWebList.setBranchName(employeeCustomize.getBranchName());
							accountWebList.setDepartmentName(employeeCustomize.getDepartmentName());
						}
					}
					// 如果是无主单，全插
					else if (users != null && (attribute == 1)) {
						// 查找用户推荐人
						EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(refUserId);
						if (employeeCustomize != null) {
							accountWebList.setRegionName(employeeCustomize.getRegionName());
							accountWebList.setBranchName(employeeCustomize.getBranchName());
							accountWebList.setDepartmentName(employeeCustomize.getDepartmentName());
						}
					}
					// 如果是有主单
					else if (users != null && (attribute == 0)) {
						// 查找用户推荐人
						EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(refUserId);
						if (employeeCustomize != null) {
							accountWebList.setRegionName(employeeCustomize.getRegionName());
							accountWebList.setBranchName(employeeCustomize.getBranchName());
							accountWebList.setDepartmentName(employeeCustomize.getDepartmentName());
						}
					}
				}
				accountWebList.setTruename(usersInfo.getTruename());
				accountWebList.setFlag(1);
			}
		}

	}

	/**
	 * 根据是否原始债权获出让人投标成功的授权号
	 * @param tenderOrderId
	 * @param SourceType
	 * @return
	 */
	@Override
	public String getSellerAuthCode(String tenderOrderId, Integer SourceType) {
		String authCode = null;
		if (SourceType.compareTo(1) == 0) {
			// 1原始债权
			BorrowTender borrowtender = this.selectBorrowTenderByNid(tenderOrderId);
			if (borrowtender == null || borrowtender.getAuthCode() == null) {
				_log.error("未从BorrowTender获取出让人"+tenderOrderId+"的投标成功的授权号。  ");
				return null;
			}
			authCode = borrowtender.getAuthCode();
		}else {
			// 0非原始债权
			HjhDebtCreditTender hjhDebtCreditTender = this.selectHjhDebtCreditTenderByAssignOrderId(tenderOrderId);
			if (hjhDebtCreditTender == null || hjhDebtCreditTender.getAuthCode() == null) {
				_log.error("未从HjhDebtCreditTender获取出让人"+tenderOrderId+"的债转成功的授权号。  ");
				return null;
			}
			authCode = hjhDebtCreditTender.getAuthCode();
		}
		return authCode;
	}

	/**
	 * 银行结束债权后，更新债权表为完全承接
	 * @param hjhDebtCredit
	 * @return
	 */
	@Override
	public Boolean updateCreditForEnd(HjhDebtCredit hjhDebtCredit) {
		HjhDebtCredit hjhDebtCreditNew = new HjhDebtCredit();
		hjhDebtCreditNew.setId(hjhDebtCredit.getId());
		hjhDebtCreditNew.setCreditStatus(2);//转让状态 2完全承接
		hjhDebtCreditNew.setIsLiquidates(1);//TODO
		hjhDebtCreditNew.setUpdateTime(GetDate.getNowTime10());
		return hjhDebtCreditMapper.updateByPrimaryKeySelective(hjhDebtCreditNew) > 0 ? true : false;
	}

	/**
	 * 根据nid查找borrowTender
	 * @param tenderNid
	 * @return
	 */
	@Override
	public BorrowTender selectBorrowTenderByNid(String tenderNid) {
		BorrowTenderExample borrowTenderExample = new BorrowTenderExample();
		BorrowTenderExample.Criteria criteria = borrowTenderExample.createCriteria();
		criteria.andNidEqualTo(tenderNid);
		List<BorrowTender> borrowTenders = borrowTenderMapper.selectByExample(borrowTenderExample);
		if (borrowTenders != null && borrowTenders.size() > 0) {
			return borrowTenders.get(0);
		}
		return null;
	}

	/**
	 * 通过AssignOrderId查找HjhDebtCreditTender
	 * @param assignOrderId
	 * @return
	 */
	@Override
	public HjhDebtCreditTender selectHjhDebtCreditTenderByAssignOrderId(String assignOrderId) {
		HjhDebtCreditTenderExample example = new HjhDebtCreditTenderExample();
		HjhDebtCreditTenderExample.Criteria criteria = example.createCriteria();
		criteria.andAssignOrderIdEqualTo(assignOrderId);
		List<HjhDebtCreditTender> list = hjhDebtCreditTenderMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	// add 出让人没有缴费授权临时对应（不收取服务费） liubin 20181113 start
	@Override
	public boolean checkAutoPayment(String creditNid) {
		return this.batchHjhAccedeCustomizeMapper.countNoAutoPayment(creditNid) > 0 ? false:true;
	}
	// add 出让人没有缴费授权临时对应（不收取服务费） liubin 20181113 end

	/**
	 * 插入交易明细表
	 * @param hjhAccede
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	private boolean insertAccountListForCredit(HjhAccede hjhAccede, BankCallBean bean,
											   Integer userId, String accountId, String flag, BigDecimal serviceFee) {

		int nowTime = GetDate.getNowTime10();
		String txAmount = bean.getTxAmount();// 交易金额
		String orderId = bean.getOrderId();// 订单id
		BigDecimal assignPay = new BigDecimal(txAmount);// 交易金额
		String accedeOrderId = hjhAccede.getAccedeOrderId();// 计划订单号

		// 插入account_list表
		_log.info("用户:" + userId + "***********************************更新account，订单号：" + orderId);
		Account account = this.getAccount(userId);
		AccountList accountList = new AccountList();
		/** 计划标的专存字段 */
		accountList.setAccedeOrderId(accedeOrderId);
		accountList.setIsShow(1);

		/** 银行存管相关字段设置 */
		accountList.setAccountId(accountId);
		accountList.setBankAwait(account.getBankAwait());
		accountList.setBankAwaitCapital(account.getBankAwaitCapital());
		accountList.setBankAwaitInterest(account.getBankAwaitInterest());
		accountList.setBankBalance(account.getBankBalance());
		accountList.setBankFrost(account.getBankFrost());
		accountList.setBankInterestSum(account.getBankInterestSum());
		accountList.setBankTotal(account.getBankTotal());
		accountList.setBankWaitCapital(account.getBankWaitCapital());
		accountList.setBankWaitInterest(account.getBankWaitInterest());
		accountList.setBankWaitRepay(account.getBankWaitRepay());
		// 汇计划更新字段
		accountList.setPlanFrost(account.getPlanFrost());
		accountList.setPlanBalance(account.getPlanBalance());

		/** 非银行存管相关字段 */
		accountList.setAwait(new BigDecimal(0));
		accountList.setBalance(account.getBalance());
		accountList.setFrost(account.getFrost());
		accountList.setInterest(new BigDecimal(0));
		accountList.setRepay(new BigDecimal(0));
		accountList.setTotal(account.getTotal());

		/** 银行交易信息  */
		accountList.setNid(orderId);//承接订单号
		accountList.setTxDate(Integer.parseInt(bean.getTxDate()));
		accountList.setTxTime(Integer.parseInt(bean.getTxTime()));
		accountList.setSeqNo(bean.getSeqNo());
		accountList.setBankSeqNo(bean.getTxDate() + bean.getTxTime() + bean.getSeqNo());

		//flag
		accountList.setWeb(0); //网站收支计算标识
		accountList.setCheckStatus(0);
		accountList.setTradeStatus(1);// 交易状态  0:失败 1:成功
		accountList.setIsBank(1);
		//记录信息
		accountList.setBaseUpdate(0);
		accountList.setIsUpdate(0);
		accountList.setIp(bean.getLogIp());
		accountList.setOperator(userId + "");
		accountList.setCreateTime(nowTime);

		accountList.setUserId(userId); //出让人或者承接人
		accountList.setIsBank(1);// 是否是银行的交易记录(0:否,1:是)
		accountList.setTradeCode("balance");
		accountList.setRemark(accedeOrderId);

		if (flag.equals(this.HJH_SELL)) {
			accountList.setAmount(assignPay.subtract(serviceFee));
			accountList.setType(1);// 收支类型1收入2支出3冻结
			accountList.setTrade("liquidates_sell");
		}else {
			accountList.setAmount(assignPay);
			accountList.setType(2);// 收支类型1收入2支出3冻结
			accountList.setTrade("accede_assign");
		}


		boolean accountListFlag = accountListMapper.insertSelective(accountList) > 0 ? true : false;

		_log.info("用户:" + userId + "***********************************插入accountList，订单号：" + orderId);

		return accountListFlag;
	}

	/**
	 * 更新计划加入订单的还款表
	 * @param sellerPlanOrderId
	 * @param debtCreditTenderLog
	 * @return
	 */
	private Boolean updateHjhRepay(String sellerPlanOrderId, HjhDebtCreditTenderLog debtCreditTenderLog) {
		HjhRepayExample example = new HjhRepayExample();
		HjhRepayExample.Criteria criteria = example.createCriteria();
		criteria.andAccedeOrderIdEqualTo(sellerPlanOrderId);
		List<HjhRepay> list = hjhRepayMapper.selectByExample(example);
		if (list == null || list.size() <= 0) {
			return false;
		}
		HjhRepay hjhRepay = list.get(0);

		//modify by cwyang 变更改值方式,防并发修改
		HjhRepay paramInfo = new HjhRepay();
		paramInfo.setId(hjhRepay.getId());
		paramInfo.setPlanRepayCapital(debtCreditTenderLog.getAssignCapital());//已还本金 (累加本次承接本金)
		paramInfo.setPlanRepayInterest(debtCreditTenderLog.getAssignInterestAdvance());//已还利息 (累加本次垫付利息)
		paramInfo.setRepayTotal(debtCreditTenderLog.getAssignCapital().add(debtCreditTenderLog.getAssignInterestAdvance()));//已还总额
		paramInfo.setRepayAlready(debtCreditTenderLog.getAssignCapital().add(debtCreditTenderLog.getAssignInterestAdvance()));//已还金额

		return this.hjhPlanCustomizeMapper.updateHjhRepayForHjhRepay(paramInfo) > 0 ? true : false;
	}

	/**
	 * 复投时，减去该计划的开放额度
	 * @param hjhAccede
	 * @param subAmount
	 * @return
	 */
	private Boolean updateAvailableInvestAccount(HjhAccede hjhAccede, BigDecimal subAmount) {
		Boolean result = false;
		if (hjhAccede.getOrderStatus() == 0) {
			//0自动投标中,非复投不减开放额度
			return true;
		}
		HjhPlan hjhPlan = new HjhPlan();
		hjhPlan.setPlanNid(hjhAccede.getPlanNid());
		hjhPlan.setAvailableInvestAccount(subAmount);
		result = this.hjhPlanCustomizeMapper.updateRepayPlanAccount(hjhPlan) > 0 ? true : false;
		String oldOpenAmount = RedisUtils.get(RedisConstants.HJH_PLAN + hjhAccede.getPlanNid());
		if (!result) {
			_log.error("[" + hjhAccede.getAccedeOrderId() + "] " + "复投减开放额度更新表失败！,计划编号:" + hjhAccede.getPlanNid() + "redis原开放额度:" + oldOpenAmount  + "，应减额度:" + subAmount.toString());
			return result;
		}
		_log.info("[" + hjhAccede.getAccedeOrderId() + "] " + "复投减开封额度！,计划编号:" + hjhAccede.getPlanNid() + "redis原开放额度:" + oldOpenAmount  + "，应减额度:" + subAmount.toString());
		redisSub(RedisConstants.HJH_PLAN + hjhAccede.getPlanNid(),subAmount.toString());//增加redis相应计划可投金额
		return result;
	}

	/**
	 * 并发情况下保证设置一个值
	 * @param key
	 * @param value
	 */
	private void redisSub(String key,String value){

		Jedis jedis = pool.getResource();

		while ("OK".equals(jedis.watch(key))) {
			List<Object> results = null;

			String balance = jedis.get(key);
			BigDecimal bal = new BigDecimal(0);
			if (balance != null) {
				bal =  new BigDecimal(balance);
			}
			BigDecimal val =  new BigDecimal(value);

			Transaction tx = jedis.multi();
			String valbeset = bal.subtract(val).toString();
			tx.set(key, valbeset);
			results = tx.exec();
			if (results == null || results.isEmpty()) {
				jedis.unwatch();
			} else {
				String ret = (String) results.get(0);
				if (ret != null && ret.equals("OK")) {
					// 成功后
					break;
				} else {
					jedis.unwatch();
				}
			}
		}
	}

	/**
	 * 调用MQ,生成计划债权转让协议
	 * @param assignNid
	 */
	private void planCreditGenerateContractByMQ(String assignNid) {
		try {
			FddGenerateContractBean bean = new FddGenerateContractBean();
			bean.setTransType(4);
			bean.setTenderType(3);
			bean.setAssignNid(assignNid);
			bean.setOrdid(assignNid);
			rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_GENERATE_CONTRACT, JSONObject.toJSONString(bean));
		} catch (Exception e) {
			_log.info("-----------------生成计划债权转让协议失败，assignNid:" + assignNid + ",异常信息：" + e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
