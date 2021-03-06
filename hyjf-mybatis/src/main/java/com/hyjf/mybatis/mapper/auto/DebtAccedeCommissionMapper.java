package com.hyjf.mybatis.mapper.auto;

import com.hyjf.mybatis.model.auto.DebtAccedeCommission;
import com.hyjf.mybatis.model.auto.DebtAccedeCommissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtAccedeCommissionMapper {
    int countByExample(DebtAccedeCommissionExample example);

    int deleteByExample(DebtAccedeCommissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtAccedeCommission record);

    int insertSelective(DebtAccedeCommission record);

    List<DebtAccedeCommission> selectByExample(DebtAccedeCommissionExample example);

    DebtAccedeCommission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtAccedeCommission record, @Param("example") DebtAccedeCommissionExample example);

    int updateByExample(@Param("record") DebtAccedeCommission record, @Param("example") DebtAccedeCommissionExample example);

    int updateByPrimaryKeySelective(DebtAccedeCommission record);

    int updateByPrimaryKey(DebtAccedeCommission record);
}