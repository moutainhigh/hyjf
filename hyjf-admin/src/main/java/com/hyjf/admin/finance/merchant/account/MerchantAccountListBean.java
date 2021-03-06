/**
 * Description:商户子账户记录列表前端查询所用vo
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */

package com.hyjf.admin.finance.merchant.account;

import java.io.Serializable;
import java.util.List;

import com.hyjf.common.paginator.Paginator;
import com.hyjf.mybatis.model.auto.MerchantAccount;

/**
 * @author 王坤
 */

public class MerchantAccountListBean implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 7768418442884796575L;
	
	private String accountBalanceSum;
	
	private String availableBalanceSum;
	
	private String frostSum;
	
	private List<MerchantAccount> recordList;

	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;

	/**
	 * 列表画面自定义标签上的用翻页对象：paginator
	 */
	private Paginator paginator;

	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	public List<MerchantAccount> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<MerchantAccount> recordList) {
		this.recordList = recordList;
	}

	public String getAccountBalanceSum() {
		return accountBalanceSum;
	}

	public void setAccountBalanceSum(String accountBalanceSum) {
		this.accountBalanceSum = accountBalanceSum;
	}

	public String getAvailableBalanceSum() {
		return availableBalanceSum;
	}

	public void setAvailableBalanceSum(String availableBalanceSum) {
		this.availableBalanceSum = availableBalanceSum;
	}

	public String getFrostSum() {
		return frostSum;
	}

	public void setFrostSum(String frostSum) {
		this.frostSum = frostSum;
	}

}
