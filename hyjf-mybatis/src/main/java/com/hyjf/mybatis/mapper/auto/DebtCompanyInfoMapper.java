package com.hyjf.mybatis.mapper.auto;

import com.hyjf.mybatis.model.auto.DebtCompanyInfo;
import com.hyjf.mybatis.model.auto.DebtCompanyInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtCompanyInfoMapper {
    int countByExample(DebtCompanyInfoExample example);

    int deleteByExample(DebtCompanyInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtCompanyInfo record);

    int insertSelective(DebtCompanyInfo record);

    List<DebtCompanyInfo> selectByExampleWithBLOBs(DebtCompanyInfoExample example);

    List<DebtCompanyInfo> selectByExample(DebtCompanyInfoExample example);

    DebtCompanyInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtCompanyInfo record, @Param("example") DebtCompanyInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") DebtCompanyInfo record, @Param("example") DebtCompanyInfoExample example);

    int updateByExample(@Param("record") DebtCompanyInfo record, @Param("example") DebtCompanyInfoExample example);

    int updateByPrimaryKeySelective(DebtCompanyInfo record);

    int updateByPrimaryKeyWithBLOBs(DebtCompanyInfo record);

    int updateByPrimaryKey(DebtCompanyInfo record);
}