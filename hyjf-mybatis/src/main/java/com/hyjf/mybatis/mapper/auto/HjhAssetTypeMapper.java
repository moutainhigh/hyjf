package com.hyjf.mybatis.mapper.auto;

import com.hyjf.mybatis.model.auto.HjhAssetType;
import com.hyjf.mybatis.model.auto.HjhAssetTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HjhAssetTypeMapper {
    int countByExample(HjhAssetTypeExample example);

    int deleteByExample(HjhAssetTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhAssetType record);

    int insertSelective(HjhAssetType record);

    List<HjhAssetType> selectByExample(HjhAssetTypeExample example);

    HjhAssetType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhAssetType record, @Param("example") HjhAssetTypeExample example);

    int updateByExample(@Param("record") HjhAssetType record, @Param("example") HjhAssetTypeExample example);

    int updateByPrimaryKeySelective(HjhAssetType record);

    int updateByPrimaryKey(HjhAssetType record);
}