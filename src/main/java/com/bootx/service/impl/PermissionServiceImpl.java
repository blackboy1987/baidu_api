
package com.bootx.service.impl;

import com.bootx.dao.PermissionDao;
import com.bootx.entity.Menu;
import com.bootx.entity.Permission;
import com.bootx.service.PermissionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service - 角色
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission, Long> implements PermissionService {

    @Resource
    private PermissionDao permissionDao;

    @Override
    public List<Permission> list(Menu menu) {
        return permissionDao.list(menu);
    }
}