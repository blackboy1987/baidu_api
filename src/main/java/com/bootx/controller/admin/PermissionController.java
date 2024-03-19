
package com.bootx.controller.admin;

import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Permission;
import com.bootx.service.MenuService;
import com.bootx.service.PermissionService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller - 角色
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@RestController("adminPermissionController")
@RequestMapping("/admin/api/permission")
@CrossOrigin("*")
public class PermissionController extends BaseController {

	@Resource
	private PermissionService permissionService;

	@Resource
	private MenuService menuService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Result save(Permission permission,Long menuId) {
		permission.setMenu(menuService.find(menuId));
		if (!isValid(permission)) {
			return Result.error("error");
		}
		permissionService.save(permission);
		return Result.success();
	}


	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Result update(Permission permission,Long menuId) {
		permission.setMenu(menuService.find(menuId));
		if (!isValid(permission)) {
			return Result.error("error");
		}
		permissionService.update(permission);
		return Result.success();
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(BaseEntity.ListView.class)
	public Result list(Long menuId) {
		return Result.success(permissionService.list(menuService.find(menuId)));
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Result delete(Long[] ids) {
		if (ids != null) {
			permissionService.delete(ids);
		}
		return Result.success();
	}
}