
package com.bootx.dao;


import com.bootx.entity.Menu;
import com.bootx.entity.Permission;

import java.util.List;

/**
 * Dao - 角色
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface PermissionDao extends BaseDao<Permission, Long> {

    List<Permission> list(Menu menu);
}