
package com.bootx.dao;


import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Department;

import java.util.List;

/**
 * Dao - 角色
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface DepartmentDao extends BaseDao<Department, Long> {
    /**
     * 查找顶级菜单
     *
     * @param count
     *            数量
     * @return 顶级菜单
     */
    List<Department> findRoots(Integer count);

    /**
     * 查找上级菜单
     *
     * @param department
     *            部门
     * @param recursive
     *            是否递归
     * @param count
     *            数量
     * @return 上级菜单
     */
    List<Department> findParents(Department department, boolean recursive, Integer count);

    /**
     * 查找下级菜单
     *
     * @param department
     *            部门
     * @param recursive
     *            是否递归
     * @param count
     *            数量
     * @return 下级菜单
     */
    List<Department> findChildren(Department department, boolean recursive, Integer count);

    Page<Department> findPage(Pageable pageable, String name);
}