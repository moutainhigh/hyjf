package com.hyjf.mybatis.mapper.auto;

import com.hyjf.mybatis.model.auto.Ads;
import com.hyjf.mybatis.model.auto.AdsExample;
import com.hyjf.mybatis.model.auto.AdsWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdsMapper {
    int countByExample(AdsExample example);

    int deleteByExample(AdsExample example);

    int deleteByPrimaryKey(Short id);

    int insert(AdsWithBLOBs record);

    int insertSelective(AdsWithBLOBs record);

    List<AdsWithBLOBs> selectByExampleWithBLOBs(AdsExample example);

    List<Ads> selectByExample(AdsExample example);

    AdsWithBLOBs selectByPrimaryKey(Short id);

    int updateByExampleSelective(@Param("record") AdsWithBLOBs record, @Param("example") AdsExample example);

    int updateByExampleWithBLOBs(@Param("record") AdsWithBLOBs record, @Param("example") AdsExample example);

    int updateByExample(@Param("record") Ads record, @Param("example") AdsExample example);

    int updateByPrimaryKeySelective(AdsWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(AdsWithBLOBs record);

    int updateByPrimaryKey(Ads record);
}