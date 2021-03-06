package com.hyjf.bank.service.user.repay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hyjf.mybatis.model.auto.BorrowRepayPlan;

public class RepayDetailBean extends BorrowRepayPlan implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 4073070104153180850L;
	/**
	 * 垫付机构ID
	 */
	public Integer repayUserId;
	
	/** 用户还款详情 */
	private List<RepayRecoverPlanBean> recoverPlanList = new ArrayList<RepayRecoverPlanBean>();

	public RepayDetailBean() {
		super();
	}

	public List<RepayRecoverPlanBean> getRecoverPlanList() {
		return recoverPlanList;
	}

	public void setRecoverPlanList(List<RepayRecoverPlanBean> recoverPlanList) {
		this.recoverPlanList = recoverPlanList;
	}

	public Integer getRepayUserId() {
		return repayUserId;
	}

	public void setRepayUserId(Integer repayUserId) {
		this.repayUserId = repayUserId;
	}

}
