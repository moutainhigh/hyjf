package com.hyjf.mybatis.mapper.auto;

import com.hyjf.mybatis.model.auto.DebtFreezeLog;
import com.hyjf.mybatis.model.auto.DebtFreezeLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtFreezeLogMapper {
    int countByExample(DebtFreezeLogExample example);

    int deleteByExample(DebtFreezeLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtFreezeLog record);

    int insertSelective(DebtFreezeLog record);

    List<DebtFreezeLog> selectByExample(DebtFreezeLogExample example);

    DebtFreezeLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtFreezeLog record, @Param("example") DebtFreezeLogExample example);

    int updateByExample(@Param("record") DebtFreezeLog record, @Param("example") DebtFreezeLogExample example);

    int updateByPrimaryKeySelective(DebtFreezeLog record);

    int updateByPrimaryKey(DebtFreezeLog record);
}