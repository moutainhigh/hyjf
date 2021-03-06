package com.hyjf.mybatis.mapper.auto;

import com.hyjf.mybatis.model.auto.HjhAssetBorrowType;
import com.hyjf.mybatis.model.auto.HjhAssetBorrowTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HjhAssetBorrowTypeMapper {
    int countByExample(HjhAssetBorrowTypeExample example);

    int deleteByExample(HjhAssetBorrowTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhAssetBorrowType record);

    int insertSelective(HjhAssetBorrowType record);

    List<HjhAssetBorrowType> selectByExample(HjhAssetBorrowTypeExample example);

    HjhAssetBorrowType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhAssetBorrowType record, @Param("example") HjhAssetBorrowTypeExample example);

    int updateByExample(@Param("record") HjhAssetBorrowType record, @Param("example") HjhAssetBorrowTypeExample example);

    int updateByPrimaryKeySelective(HjhAssetBorrowType record);

    int updateByPrimaryKey(HjhAssetBorrowType record);
}