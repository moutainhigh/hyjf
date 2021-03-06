package com.hyjf.mybatis.mapper.auto;

import com.hyjf.mybatis.model.auto.UserCorner;
import com.hyjf.mybatis.model.auto.UserCornerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserCornerMapper {
    int countByExample(UserCornerExample example);

    int deleteByExample(UserCornerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserCorner record);

    int insertSelective(UserCorner record);

    List<UserCorner> selectByExample(UserCornerExample example);

    UserCorner selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserCorner record, @Param("example") UserCornerExample example);

    int updateByExample(@Param("record") UserCorner record, @Param("example") UserCornerExample example);

    int updateByPrimaryKeySelective(UserCorner record);

    int updateByPrimaryKey(UserCorner record);
}