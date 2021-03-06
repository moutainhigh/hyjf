package com.hyjf.mybatis.mapper.auto;

import com.hyjf.mybatis.model.auto.UtmVisit;
import com.hyjf.mybatis.model.auto.UtmVisitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UtmVisitMapper {
    int countByExample(UtmVisitExample example);

    int deleteByExample(UtmVisitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UtmVisit record);

    int insertSelective(UtmVisit record);

    List<UtmVisit> selectByExample(UtmVisitExample example);

    UtmVisit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UtmVisit record, @Param("example") UtmVisitExample example);

    int updateByExample(@Param("record") UtmVisit record, @Param("example") UtmVisitExample example);

    int updateByPrimaryKeySelective(UtmVisit record);

    int updateByPrimaryKey(UtmVisit record);
}