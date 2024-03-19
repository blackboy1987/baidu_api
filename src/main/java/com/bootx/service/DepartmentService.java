
package com.bootx.service;


import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Department;

import java.util.List;
import java.util.Map;

/**
 * Service - 角色
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface DepartmentService extends BaseService<Department, Long> {

    List<Map<String,Object>> tree();

    List<Department> list(Department department);

    /**
     * 查找顶级地区
     *
     * @return 顶级地区
     */
    List<Department> findRoots();

    /**
     * 查找顶级地区
     *
     * @param count
     *            数量
     * @return 顶级地区
     */
    List<Department> findRoots(Integer count);

    /**
     * 查找上级地区
     *
     * @param department
     *            部门
     * @param recursive
     *            是否递归
     * @param count
     *            数量
     * @return 上级地区
     */
    List<Department> findParents(Department department, boolean recursive, Integer count);

    /**
     * 查找下级地区
     *
     * @param department
     *            部门
     * @param recursive
     *            是否递归
     * @param count
     *            数量
     * @return 下级地区
     */
    List<Department> findChildren(Department department, boolean recursive, Integer count);

    Page<Department> findPage(Pageable pageable, String name);
}