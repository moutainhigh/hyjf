package com.hyjf.admin.maintenance.role;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.mybatis.model.auto.AdminRole;
import com.hyjf.mybatis.model.auto.AdminRoleMenuPermissions;
import com.hyjf.mybatis.model.customize.AdminRoleCustomize;

public interface AdminRoleService {

	 /**
     * 获取角色数
     * 
     * @param AdminRole
     * @param limitStart
     * @param limitEnd
     * @return
     * @author Administrator
     */
    public long getRecordCount(AdminRoleBean AdminRole);
    
	/**
	 * 获取角色列表
	 * 
	 * @return
	 */
	public List<AdminRole> getRecordList(AdminRoleBean adminRole, int limitStart, int limitEnd);

	/**
	 * 获取单个角色维护
	 * 
	 * @return
	 */
	public AdminRole getRecord(Integer id);

	/**
	 * 根据主键判断角色维护中数据是否存在
	 * 
	 * @return
	 */
	public boolean isExistsRecord(Integer id);

	/**
	 * 角色维护插入
	 * 
	 * @param record
	 */
	public void insertRecord(AdminRole record);

	/**
	 * 角色维护更新
	 * 
	 * @param record
	 */
	public void updateRecord(AdminRole record);

	/**
	 * 角色维护删除
	 * 
	 * @param record
	 */
	public void deleteRecord(List<Integer> ids);

    /**
     * 获取角色菜单信息
     * 
     * @return
     */
    public JSONArray getAdminRoleMenu(AdminRoleCustomize adminRoleCustomize);
    
    /**
     * 插入或更新[角色菜单权限表]数据
     * 
     * @param record
     */
    public int updatePermission(Integer roleId, List<AdminRoleMenuPermissions> record);

    /**
     * 检查角色名称唯一性
     * 
     * @param id
     * @param username
     */
    public int countRoleByname(Integer id, String roleName);
}
