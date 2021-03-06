/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.mongo.hgdatareport.dao;

import com.hyjf.mongo.hgdatareport.entity.CertReportEntity;
import org.springframework.stereotype.Repository;

import com.hyjf.mongo.base.BaseMongoDao;

/**
 * 国家互联网应急中心    CERT 用户信息上报
 * @author sss
 * @version BaseHgDataReportEntity, v0.1 2018/6/27 10:06
 */
@Repository
public class CertReportDao extends BaseMongoDao<CertReportEntity> {
    @Override
    protected Class<CertReportEntity> getEntityClass() {
        return CertReportEntity.class;
    }
}
