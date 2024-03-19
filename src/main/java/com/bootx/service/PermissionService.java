
package com.bootx.service;


import com.bootx.entity.Menu;
import com.bootx.entity.Permission;

import java.util.List;

/**
 * Service - 角色
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface PermissionService extends BaseService<Permission, Long> {

    List<Permission> list(Menu menu);
}