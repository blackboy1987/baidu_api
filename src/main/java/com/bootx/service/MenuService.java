
package com.bootx.service;


import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Menu;

import java.util.List;
import java.util.Map;

/**
 * Service - 角色
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface MenuService extends BaseService<Menu, Long> {

    List<Map<String,Object>> tree();

    List<Menu> list(Menu menu);

    /**
     * 查找顶级地区
     *
     * @return 顶级地区
     */
    List<Menu> findRoots();

    /**
     * 查找顶级地区
     *
     * @param count
     *            数量
     * @return 顶级地区
     */
    List<Menu> findRoots(Integer count);

    /**
     * 查找上级地区
     *
     * @param menu
     *            菜单
     * @param recursive
     *            是否递归
     * @param count
     *            数量
     * @return 上级地区
     */
    List<Menu> findParents(Menu menu, boolean recursive, Integer count);

    /**
     * 查找下级地区
     *
     * @param menu
     *            菜单
     * @param recursive
     *            是否递归
     * @param count
     *            数量
     * @return 下级地区
     */
    List<Menu> findChildren(Menu menu, boolean recursive, Integer count);

    Page<Menu> findPage(Pageable pageable, String name);
}