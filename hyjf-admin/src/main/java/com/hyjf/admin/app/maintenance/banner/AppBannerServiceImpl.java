package com.hyjf.admin.app.maintenance.banner;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hyjf.admin.BaseServiceImpl;
import com.hyjf.common.util.GetDate;
import com.hyjf.mybatis.model.auto.Ads;
import com.hyjf.mybatis.model.auto.AdsExample;
import com.hyjf.mybatis.model.auto.AdsType;
import com.hyjf.mybatis.model.auto.AdsTypeExample;
import com.hyjf.mybatis.model.auto.AdsWithBLOBs;

@Service
public class AppBannerServiceImpl extends BaseServiceImpl implements AppBannerService {

    /**
     * 获取广告列表
     * 
     * @return
     */
    @Override
	public List<Ads> getRecordList(AppBannerBean bean, int limitStart, int limitEnd) {
        AdsExample example = new AdsExample();
        AdsExample.Criteria criteria = example.createCriteria();
        criteria.andClientTypeEqualTo(1);//手机端广告
        // 条件查询
        if (bean.getPlatformType() != null) {
            criteria.andPlatformType(bean.getPlatformType());
        }
        if (bean.getTypeid() != null) {
            criteria.andTypeidEqualTo(bean.getTypeid());
        }
        if (StringUtils.isNotEmpty(bean.getName())) {
            criteria.andNameLike("%" + bean.getName() + "%");
        }
        if (bean.getStatus() != null) {
            criteria.andStatusEqualTo(bean.getStatus());
        }
        if (StringUtils.isNotEmpty(bean.getStartCreate()) && StringUtils.isNotEmpty(bean.getEndCreate())) {
            criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.strYYYYMMDDHHMMSS2Timestamp2(GetDate.getDayStart(bean.getStartCreate())));
        }
        if(StringUtils.isNotEmpty(bean.getEndCreate())){
        	criteria.andCreateTimeLessThanOrEqualTo(GetDate.strYYYYMMDDHHMMSS2Timestamp2(GetDate.getDayEnd(bean.getEndCreate())));
        }
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        example.setOrderByClause("create_time Desc");
        return adsMapper.selectByExample(example);
    }

    
	/**
	 * 获取列表数
	 * @param bean
	 * @return
	 * @author Michael
	 */
	@Override
	public Integer countRecordList(AppBannerBean bean) {
        AdsExample example = new AdsExample();
        AdsExample.Criteria criteria = example.createCriteria();
        criteria.andClientTypeEqualTo(1);//手机端广告
        // 条件查询
        if (bean.getTypeid() != null) {
            criteria.andTypeidEqualTo(bean.getTypeid());
        }
        if (bean.getPlatformType() != null) {
            criteria.andPlatformType(bean.getPlatformType());
        }
        if (StringUtils.isNotEmpty(bean.getName())) {
            criteria.andNameLike("%" + bean.getName() + "%");
        }
        if (bean.getStatus() != null) {
            criteria.andStatusEqualTo(bean.getStatus());
        }
        if (StringUtils.isNotEmpty(bean.getStartCreate()) && StringUtils.isNotEmpty(bean.getEndCreate())) {
            criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.strYYYYMMDDHHMMSS2Timestamp2(GetDate.getDayStart(bean.getStartCreate())));
        }
        if(StringUtils.isNotEmpty(bean.getEndCreate())){
        	criteria.andCreateTimeLessThanOrEqualTo(GetDate.strYYYYMMDDHHMMSS2Timestamp2(GetDate.getDayEnd(bean.getEndCreate())));
        }
       
        int cnt = adsMapper.countByExample(example);
        if(cnt > 0){
        	return cnt;
        }
        return 0;
	}
    
    
    /**
     * 获取单个广告维护
     * 
     * @return
     */
    @Override
	public AdsWithBLOBs getRecord(Integer record) {
        AdsWithBLOBs ads = adsMapper.selectByPrimaryKey(record.shortValue());
        return ads;
    }

    /**
     * 根据主键判断广告维护中数据是否存在
     * 
     * @return
     */
    @Override
	public boolean isExistsRecord(Ads record) {
        if (record.getId() == null) {
            return false;
        }
        AdsExample example = new AdsExample();
        AdsExample.Criteria cra = example.createCriteria();
        cra.andIdEqualTo(record.getId());
        int cnt = adsMapper.countByExample(example);
        if (cnt > 0) {
            return true;
        }
        return false;
    }

    /**
     * 广告维护插入
     * 
     * @param record
     */
    @Override
	public void insertRecord(AdsWithBLOBs record) {
    	if(record.getIsIndex()==null){
        	record.setIsIndex(new Short("0"));
    	}
    	record.setClientType(1);
        record.setCreateTime(GetDate.getMyTimeInMillis());
        record.setUpdateTime(GetDate.getMyTimeInMillis());
        adsMapper.insertSelective(record);
    }

    /**
     * 广告维护更新
     * 
     * @param record
     */
    @Override
	public void updateRecord(AdsWithBLOBs record) {
    	if(record.getIsIndex()==null){
        	record.setIsIndex(new Short("0"));
    	}
        record.setUpdateTime(GetDate.getMyTimeInMillis());
        adsMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据主键删除环境
     * 
     * @param recordList
     */
    @Override
	public void deleteRecord(List<Integer> recordList) {
        for (Integer id : recordList) {
            adsMapper.deleteByPrimaryKey(id.shortValue());
        }
    }

    /**
     * 获取广告类型列表
     * 
     * @return
     */
    @Override
	public List<AdsType> getAdsTypeList() {
        AdsTypeExample example = new AdsTypeExample();
        AdsTypeExample.Criteria cra =  example.createCriteria();
        cra.andClientTypeEqualTo(1);
        return adsTypeMapper.selectByExample(example);
    }

    /**
     * 获取单个广告类型
     * 
     * @return
     */
    @Override
	public AdsType getAdsType(Integer typeid) {
        return adsTypeMapper.selectByPrimaryKey(typeid);
    }



}
