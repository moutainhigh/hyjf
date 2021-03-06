package com.hyjf.batch.htj.planloans;

import java.util.List;
import java.util.Map;

import com.hyjf.batch.BaseService;
import com.hyjf.mybatis.model.auto.Account;
import com.hyjf.mybatis.model.auto.BorrowTender;
import com.hyjf.mybatis.model.auto.BorrowTenderCpn;
import com.hyjf.mybatis.model.auto.DebtApicron;
import com.hyjf.mybatis.model.auto.DebtBorrowWithBLOBs;
import com.hyjf.mybatis.model.auto.DebtInvest;
import com.hyjf.mybatis.model.auto.DebtPlanAccede;

public interface PlanLoansService extends BaseService {

	/**
	 * 取得借款API任务表
	 *
	 * @return
	 */
	public List<DebtApicron> getDebtApicronList(Integer status, Integer apiType);

	/**
	 * 取得借款API任务表
	 *
	 * @return
	 */
	public List<DebtApicron> getDebtApicronListWithRepayStatus(Integer status, Integer apiType);

	/**
	 * 更新借款API任务表
	 *
	 * @return
	 */
	public int updateDebtApicron(Integer id, Integer status);

	/**
	 * 更新借款API任务表
	 *
	 * @return
	 */
	public int updateDebtApicron(Integer id, Integer status, String data);

	/**
	 * 更新借款API任务表
	 *
	 * @param id
	 * @param status
	 * @return
	 */
	public int updateDebtApicronOfRepayStatus(Integer id, Integer status);

	/**
	 * 取得标的详情
	 *
	 * @return
	 */
	public DebtBorrowWithBLOBs getBorrow(String borrowNid);

	/**
	 * 取出账户信息
	 *
	 * @param userId
	 * @return
	 */
	public Account getAccountByUserId(Integer userId);

	/**
	 * 更新放款完成状态
	 *
	 * @param borrowNid
	 * @param borrow
	 * @param periodNow
	 */
	public void updateBorrowStatus(String nid, String borrowNid, Integer borrowUserId);

	/**
	 * 更新放款状态
	 *
	 * @param accountList
	 * @return
	 */
	public int updateBorrowTender(BorrowTender borrowTender);

	/**
	 * 更新放款状态(优惠券)
	 *
	 * @param accountList
	 * @return
	 */
	public int updateBorrowTenderCpn(BorrowTenderCpn borrowTenderCpn);

	/**
	 * 发送短信(出借成功)
	 *
	 * @param userId
	 */
	public void sendSms(List<Map<String, String>> msgList);

	/**
	 * 发送短信(优惠券出借成功)
	 *
	 * @param userId
	 */
	void sendSmsCoupon(List<Map<String, String>> msgList);

	/**
	 * 发送邮件(出借成功)
	 *
	 * @param userId
	 */
	public void sendMail(List<Map<String, String>> msgList, String borrowNid);

	/**
	 * @param msgList
	 */

	public void sendMessage(List<Map<String, String>> msgList);

	/**
	 * 
	 * 优惠券出借成功发送push消息
	 * 
	 * @author hsy
	 * @param msgList
	 */
	void sendAppMSCoupon(List<Map<String, String>> msgList);

	// 新做++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
	 * 取得借款列表
	 *
	 * @return
	 */
	public List<DebtInvest> getDebtInvestList(String borrowNid);

	/**
	 * 更新放款状态
	 *
	 * @param accountList
	 * @return
	 */
	public int updateDebtInvest(DebtInvest debtInvest);

	/**
	 * 
	 * @method: updateDebtLoans
	 * @description: 自动放款
	 * @param apicron
	 * @param debtInvest
	 * @return
	 * @return: List<Map<String,String>>
	 * @mender: zhouxiaoshuai
	 * @date: 2016年9月28日 下午3:12:32
	 */
	public List<Map<String, String>> updateDebtLoans(DebtApicron apicron, DebtInvest debtInvest) throws Exception;

	/**
	 * 
	 * @method: updateDebtLoans
	 * @description: 取得放款执行中的失败任务 持续了 多余一小时的执行中任务
	 * @param apicron
	 * @param debtInvest
	 * @return
	 * @return: List<Map<String,String>>
	 * @mender: zhouxiaoshuai
	 * @date: 2016年9月28日 下午3:12:32
	 */
	public List<DebtApicron> getDebtApicronListLate(Integer status, Integer apiType);

	DebtPlanAccede selectDebtPlanAccede(Integer id);

}
