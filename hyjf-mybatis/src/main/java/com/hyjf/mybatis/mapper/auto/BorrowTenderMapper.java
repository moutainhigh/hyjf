package com.hyjf.mybatis.mapper.auto;

import com.hyjf.mybatis.model.auto.BorrowTender;
import com.hyjf.mybatis.model.auto.BorrowTenderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BorrowTenderMapper {
    int countByExample(BorrowTenderExample example);

    int deleteByExample(BorrowTenderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowTender record);

    int insertSelective(BorrowTender record);

    List<BorrowTender> selectByExample(BorrowTenderExample example);

    BorrowTender selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowTender record, @Param("example") BorrowTenderExample example);

    int updateByExample(@Param("record") BorrowTender record, @Param("example") BorrowTenderExample example);

    int updateByPrimaryKeySelective(BorrowTender record);

    int updateByPrimaryKey(BorrowTender record);
}