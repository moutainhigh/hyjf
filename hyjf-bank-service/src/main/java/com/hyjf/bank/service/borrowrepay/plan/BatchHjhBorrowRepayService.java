package com.hyjf.bank.service.borrowrepay.plan;

import java.util.List;
import java.util.Map;

import com.hyjf.bank.service.BaseService;
import com.hyjf.mybatis.model.auto.Account;
import com.hyjf.mybatis.model.auto.BankOpenAccount;
import com.hyjf.mybatis.model.auto.BorrowApicron;
import com.hyjf.mybatis.model.auto.BorrowRecover;
import com.hyjf.mybatis.model.auto.BorrowTender;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

public interface BatchHjhBorrowRepayService extends BaseService {

	/**
	 * 取得借款API任务表
	 *
	 * @return
	 */
	public List<BorrowApicron> getBorrowApicronList(Integer apiType);

	/**
	 * 更新借款API任务表
	 * 
	 * @param borrowNid
	 * @param batchNo
	 *
	 * @return
	 * @throws Exception 
	 */
	public boolean updateBorrowApicron(BorrowApicron borrowApicron,int status) throws Exception;

	/**
	 * 取出账户信息
	 *
	 * @param userId
	 * @return
	 */
	public Account getAccountByUserId(Integer userId);

	/**
	 * 取得借款列表
	 *
	 * @return
	 */
	public List<BorrowTender> getBorrowTenderList(String borrowNid);

	/**
	 * 获取江西银行账户信息
	 * 
	 * @param userId
	 * @return
	 */
	BankOpenAccount getBankOpenAccount(Integer userId);

	/**
	 * 查询批次还款状态
	 * 
	 * @param apicron
	 * @return
	 */
	public BankCallBean batchQuery(BorrowApicron apicron);

	/***
	 * 发起还款请求
	 * 
	 * @param apicron
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map requestRepay(BorrowApicron apicron);

	/**
	 * 查询还款请求明细
	 * 
	 * @param apicron
	 * @return
	 */
	public boolean updateBatchDetailsQuery(BorrowApicron apicron);

	/**
	 * 获取相应的还款记录
	 * @param id
	 * @return
	 */
	public BorrowApicron getBorrowApicron(Integer id);

	/**
	 * 查询相应的放款明细
	 * @param borrowNid
	 * @return
	 */
	List<BorrowRecover> getBorrowRecoverList(String borrowNid);

	/**
	 * 退出计划
	 * @param accedeOrderId
	 */
	public void updateQuitRepayInfo(String accedeOrderId);

	/**
	 * 锁定计划
	 * @param accedeOrderId
	 */
	public void updateLockRepayInfo(String accedeOrderId);

	/**
	 * 获得优先待处理的还款任务
	 * @param borrowNid
	 */
    public BorrowApicron getRepayPeriodSort(String borrowNid);

	/**
	 * 发送到mq
	 * @param borrowApicron
	 */
	void sendToMQ(BorrowApicron borrowApicron);
}
