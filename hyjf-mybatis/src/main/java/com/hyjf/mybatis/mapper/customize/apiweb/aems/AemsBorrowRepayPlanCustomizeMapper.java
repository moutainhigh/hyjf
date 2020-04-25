/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.mybatis.mapper.customize.apiweb.aems;

import com.hyjf.mybatis.model.customize.apiweb.aems.AemsBorrowRepayPlanCustomize;

import java.util.List;
import java.util.Map;

/**
 * Aems系统:查询还款计划
 *
 * @author liuyang
 * @version AemsBorrowRepayPlanCustomizeMapper, v0.1 2018/10/16 19:07
 */
public interface AemsBorrowRepayPlanCustomizeMapper {

    List<AemsBorrowRepayPlanCustomize> selectBorrowRepayPlanList(Map<String, Object> param);


    Integer selectBorrowRepayPlanCountsByInstCode(Map<String, Object> param);
}
