package com.hyjf.mybatis.mapper.customize;


import org.apache.ibatis.annotations.Param;

import com.hyjf.mybatis.model.customize.EmployeeCustomize;

public interface EmployeeCustomizeMapper {

	/**
	 * 获取员工信息
	 * 
	 * @param  
	 * @return
	 */
	EmployeeCustomize selectEmployeeByUserId(@Param("userId") Integer userId);
	
	/**
	 * 根据平台userid查询其在crm的属性
	 * @param userId
	 * @return
	 */
	public Integer queryCuttype(@Param("userId") Integer userId);

}