
package com.bootx.controller.admin;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.Admin;
import com.bootx.entity.BaseEntity;
import com.bootx.service.AdminService;
import com.bootx.service.DepartmentService;
import com.bootx.service.RoleService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

/**
 * Controller - 管理员
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@RestController("adminAdminController")
@RequestMapping("/admin/api/admin")
@CrossOrigin("*")
public class AdminController extends BaseController {

	@Resource
	private AdminService adminService;
	@Resource
	private RoleService roleService;

	@Resource
	private DepartmentService departmentService;

	/**
	 * 保存
	 */
	@Audit(action = "保存管理员")
	@PostMapping("/save")
	public Result save(Admin admin,Long departmentId, Long[] roleIds) {
		if(admin.getIsEnabled()==null){
			admin.setIsEnabled(false);
		}
		admin.setRoles(new HashSet<>(roleService.findList(roleIds)));
		admin.setDepartment(departmentService.find(departmentId));
		if (!isValid(admin, BaseEntity.Save.class)) {
			return Result.error("参数异常");
		}
		if (adminService.usernameExists(admin.getUsername())) {
			return Result.error("用户已被占用");
		}
		admin.setIsLocked(false);
		admin.setLockDate(null);
		admin.setLastLoginIp(null);
		admin.setLastLoginDate(null);
		return Result.success();
	}

	/**
	 * 更新
	 */
	@Audit(action = "修改管理员")
	@PostMapping("/update")
	public Result update(Admin admin, Long id,Long departmentId, Long[] roleIds, Boolean unlock) {
		if(admin.getIsEnabled()==null){
			admin.setIsEnabled(false);
		}
		admin.setRoles(new HashSet<>(roleService.findList(roleIds)));
		admin.setDepartment(departmentService.find(departmentId));
		if (!isValid(admin)) {
			return Result.error("参数异常");
		}
		Admin pAdmin = adminService.find(id);
		if (pAdmin == null) {
			return Result.error("参数异常");
		}
		if (BooleanUtils.isTrue(pAdmin.getIsLocked()) && BooleanUtils.isTrue(unlock)) {
			adminService.update(admin, "username", "encodedPassword", "lastLoginIp", "lastLoginDate");
		} else {
			adminService.update(admin, "username", "encodedPassword", "isLocked", "lockDate", "lastLoginIp", "lastLoginDate");
		}
		return Result.success();
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView({BaseEntity.PageView.class})
	public Result list(Pageable pageable,String username) {
		return Result.success(adminService.findPage(pageable,username));
	}

	/**
	 * 删除
	 */
	@Audit(action = "删除管理员")
	@PostMapping("/delete")
	public Result delete(Long[] ids) {
		if (ids.length >= adminService.count()) {
			return Result.error("禁止删除");
		}
		adminService.delete(ids);
		return Result.success();
	}

}