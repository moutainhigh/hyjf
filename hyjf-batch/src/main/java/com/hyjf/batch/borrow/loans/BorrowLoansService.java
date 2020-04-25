package com.hyjf.batch.borrow.loans;

import java.util.List;
import java.util.Map;

import com.hyjf.batch.BaseService;
import com.hyjf.mybatis.model.auto.Account;
import com.hyjf.mybatis.model.auto.AccountBorrow;
import com.hyjf.mybatis.model.auto.BorrowApicron;
import com.hyjf.mybatis.model.auto.BorrowTender;
import com.hyjf.mybatis.model.auto.BorrowWithBLOBs;

public interface BorrowLoansService extends BaseService {

    /**
     * 自动放款（本金）
     *
     * @param apicron
     * @return
     */
    public List<Map<String, String>> updateBorrowLoans(BorrowApicron apicron, BorrowTender borrowTender) throws Exception;

    /**
     * 取得借款API任务表
     *
     * @return
     */
    public List<BorrowApicron> getBorrowApicronList(Integer status, Integer apiType);

    /**
     * 取得借款API任务表
     *
     * @return
     */
    public List<BorrowApicron> getBorrowApicronListWithRepayStatus(Integer status, Integer apiType);

    /**
     * 更新借款API任务表
     *
     * @return
     */
    public int updateBorrowApicron(Integer id, Integer status);

    /**
     * 更新借款API任务表
     *
     * @return
     */
    public int updateBorrowApicron(Integer id, Integer status, String data);

    /**
     * 更新借款API任务表
     *
     * @param id
     * @param status
     * @return
     */
    public int updateBorrowApicronOfRepayStatus(Integer id, Integer status);

    /**
     * 取得标的详情
     *
     * @return
     */
    public BorrowWithBLOBs getBorrow(String borrowNid);
    
    public AccountBorrow getAccountBorrow(String borrowNid);

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
     * 发送短信(出借成功)
     *
     * @param userId
     */
    public void sendSms(List<Map<String, String>> msgList);

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
}
