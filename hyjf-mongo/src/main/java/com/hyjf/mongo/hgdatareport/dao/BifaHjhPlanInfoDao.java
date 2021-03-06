/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.mongo.hgdatareport.dao;

import com.hyjf.mongo.base.BaseMongoDao;
import com.hyjf.mongo.hgdatareport.entity.BifaBorrowInfoEntity;
import com.hyjf.mongo.hgdatareport.entity.BifaHjhPlanInfoEntity;
import org.springframework.stereotype.Repository;

/**
 * 合规数据上报 BIFA 智投信息上报
 * jijun
 */
@Repository
public class BifaHjhPlanInfoDao extends BaseMongoDao<BifaHjhPlanInfoEntity> {
    @Override
    protected Class<BifaHjhPlanInfoEntity> getEntityClass() {
        return BifaHjhPlanInfoEntity.class;
    }
}
