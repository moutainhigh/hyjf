/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.bank.service.borrow.nifa.repayinfo;

import com.hyjf.bank.service.BaseService;

/**
 * @author PC-LIUSHOUYI
 * @version NifaRepayInfoService, v0.1 2018/7/11 13:57
 */
public interface NifaRepayInfoService extends BaseService {

    /**
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    boolean insertNifaRepayInfo(String borrowNid, Integer repayPeriod);

    /**
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    boolean insertNifaContractStatus(String borrowNid,Integer repayPeriod);

    /**
     * 出借人回款记录生成
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    boolean insertNifaReceivedPayments(String borrowNid, Integer repayPeriod);
}
