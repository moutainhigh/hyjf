/**
 * Description:用户出借服务
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: 郭勇
 * @version: 1.0
 *           Created at: 2015年12月4日 下午1:45:13
 *           Modification History:
 *           Modified by :
 */

package com.hyjf.web.bank.wechat.hjh.invest;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.mybatis.model.auto.HjhPlan;
import com.hyjf.mybatis.model.customize.coupon.CouponConfigCustomizeV2;
import com.hyjf.mybatis.model.customize.coupon.UserCouponConfigCustomize;
import com.hyjf.mybatis.model.customize.web.htj.DebtPlanDetailCustomize;
import com.hyjf.web.BaseService;

@Service("wxhjhPlanService")
public interface PlanService extends BaseService {

    // 查询计划
    public DebtPlanDetailCustomize selectDebtPlanDetail(String planNid);

    // 根据NID查询计划
    public HjhPlan getPlanByNid(String planNid);

    // 获取最优优惠券信息
    public UserCouponConfigCustomize getUserOptimalCoupon(String couponId, String planNid, Integer userId,
        String money, String platform);

    // 计算收益
    public void setProspectiveEarnings(InvestInfoResultVo resultVo, UserCouponConfigCustomize couponConfig,
        String planNid, Integer userId, String platform, String money);

    // 汇计划 参数校验
    public JSONObject checkHJHParam(String planNid, String account, String userid, String platform,
        CouponConfigCustomizeV2 cuc);

    // 汇计划出借
    public JSONObject investInfo(ModelAndView modelAndView,String planNid, String account, String userid, String platform,
        CouponConfigCustomizeV2 cuc, String ip, String couponGrantId);
    
    // 更新各种表
    boolean updateAfterPlanRedis(ModelAndView modelAndView,String planNid, String frzzeOrderId, Integer userId, String accountStr,
        String tenderUsrcustid, String ipAddr, String freezeTrxId, String frzzeOrderDate, String planOrderId, String couponGrantId,  String couponInterest,String platform)
        throws Exception;
}
