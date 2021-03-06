package com.hyjf.mybatis.mapper.auto;

import com.hyjf.mybatis.model.auto.ParamName;
import com.hyjf.mybatis.model.auto.ParamNameExample;
import com.hyjf.mybatis.model.auto.ParamNameKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ParamNameMapper {
    int countByExample(ParamNameExample example);

    int deleteByExample(ParamNameExample example);

    int deleteByPrimaryKey(ParamNameKey key);

    int insert(ParamName record);

    int insertSelective(ParamName record);

    List<ParamName> selectByExample(ParamNameExample example);

    ParamName selectByPrimaryKey(ParamNameKey key);

    int updateByExampleSelective(@Param("record") ParamName record, @Param("example") ParamNameExample example);

    int updateByExample(@Param("record") ParamName record, @Param("example") ParamNameExample example);

    int updateByPrimaryKeySelective(ParamName record);

    int updateByPrimaryKey(ParamName record);
}