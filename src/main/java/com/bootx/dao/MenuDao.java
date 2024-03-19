
package com.bootx.dao;


import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Menu;

import java.util.List;

/**
 * Dao - 角色
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface MenuDao extends BaseDao<Menu, Long> {
    /**
     * 查找顶级菜单
     *
     * @param count
     *            数量
     * @return 顶级菜单
     */
    List<Menu> findRoots(Integer count);

    /**
     * 查找上级菜单
     *
     * @param menu
     *            菜单
     * @param recursive
     *            是否递归
     * @param count
     *            数量
     * @return 上级菜单
     */
    List<Menu> findParents(Menu menu, boolean recursive, Integer count);

    /**
     * 查找下级菜单
     *
     * @param menu
     *            菜单
     * @param recursive
     *            是否递归
     * @param count
     *            数量
     * @return 下级菜单
     */
    List<Menu> findChildren(Menu menu, boolean recursive, Integer count);

    Page<Menu> findPage(Pageable pageable, String name);
}