/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.mybatis.mapper.customize.nifa;

import com.hyjf.mybatis.model.auto.NifaContractEssence;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaUploadCustomizeMapper, v0.1 2018/7/6 11:25
 */
public interface NifaUploadCustomizeMapper {
    /**
     * 拉取前一天的合同要素信息
     * @return
     */
    public List<NifaContractEssence> selectNifaContractEssenceList();


}
