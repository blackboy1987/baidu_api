
package com.bootx.controller.admin;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Role;
import com.bootx.service.RoleService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Controller - 角色
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@RestController("adminRoleController")
@RequestMapping("/admin/api/role")
@CrossOrigin("*")
public class RoleController extends BaseController {

	@Resource
	private RoleService roleService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Result save(Role role) {
		if(role.getIsEnabled()==null){
			role.setIsEnabled(false);
		}
		if (!isValid(role)) {
			return Result.error("error");
		}
		role.setAdmins(null);
		roleService.save(role);
		return Result.success();
	}


	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Result update(Role role) {
		if(role.getIsEnabled()==null){
			role.setIsEnabled(false);
		}
		if (!isValid(role)) {
			return Result.error("error");
		}
		roleService.update(role, "admins");
		return Result.success();
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(BaseEntity.PageView.class)
	public Result list(Pageable pageable, String name, String description, Date beginDate, Date endDate) {
		return Result.success(roleService.findPage(pageable,name,description,beginDate,endDate));
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Result delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				Role role = roleService.find(id);
				if (role != null && CollectionUtils.isNotEmpty(role.getAdmins())) {
					return Result.error("admin.role.deleteExistNotAllowed");
				}
			}
			roleService.delete(ids);
		}
		return Result.success();
	}

	@PostMapping("/all")
	@JsonView({BaseEntity.ListView.class})
	public Result all() {
		return Result.success(roleService.findAll());
	}
}