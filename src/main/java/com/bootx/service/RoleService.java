
package com.bootx.service;


import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Role;

import java.util.Date;

/**
 * Service - 角色
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface RoleService extends BaseService<Role, Long> {

    Page<Role> findPage(Pageable pageable, String name, String description, Date beginDate, Date endDate);
}