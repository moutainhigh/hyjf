/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.mybatis.mapper.customize.nifa;

import com.hyjf.mybatis.model.auto.NifaRepayInfo;
import com.hyjf.mybatis.model.customize.nifa.NifaRepayInfoCustomize;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaRepayInfoCustomizeMapper, v0.1 2018/7/6 11:37
 */
public interface NifaRepayInfoCustomizeMapper {
    /**
     * 获取前一天借款人项目还款记录
     * @return
     */
    List<NifaRepayInfoCustomize> selectNifaRepayInfoList();
}
