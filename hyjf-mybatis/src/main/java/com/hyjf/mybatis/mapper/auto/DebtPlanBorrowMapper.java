package com.hyjf.mybatis.mapper.auto;

import com.hyjf.mybatis.model.auto.DebtPlanBorrow;
import com.hyjf.mybatis.model.auto.DebtPlanBorrowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtPlanBorrowMapper {
    int countByExample(DebtPlanBorrowExample example);

    int deleteByExample(DebtPlanBorrowExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtPlanBorrow record);

    int insertSelective(DebtPlanBorrow record);

    List<DebtPlanBorrow> selectByExample(DebtPlanBorrowExample example);

    DebtPlanBorrow selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtPlanBorrow record, @Param("example") DebtPlanBorrowExample example);

    int updateByExample(@Param("record") DebtPlanBorrow record, @Param("example") DebtPlanBorrowExample example);

    int updateByPrimaryKeySelective(DebtPlanBorrow record);

    int updateByPrimaryKey(DebtPlanBorrow record);
}