
package com.bootx.controller.admin;

import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Department;
import com.bootx.service.DepartmentService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller - 角色
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@RestController("adminDepartmentController")
@RequestMapping("/admin/api/department")
@CrossOrigin("*")
public class DepartmentController extends BaseController {

	@Resource
	private DepartmentService departmentService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Result save(Department department,Long parentId) {
		department.setParent(departmentService.find(parentId));
		departmentService.save(department);
		department.setTreePath(null);
		department.setGrade(null);
		department.setChildren(null);
		return Result.success();
	}


	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Result update(Department department,Long parentId) {
		department.setParent(departmentService.find(parentId));
		if (department.getParent() != null) {
			Department parent = department.getParent();
			if (parent.equals(department)) {
				return Result.error("上级部门不能是当前部门");
			}
			List<Department> children = departmentService.findChildren(parent, true, null);
			if (children != null && children.contains(parent)) {
				return Result.error("上级部门不能是当前部门的子部门");
			}
		}
		departmentService.update(department,"treePath", "grade","children","admins");
		return Result.success();
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(BaseEntity.ListView.class)
	public Result list(Long parentId) {
		return Result.success(departmentService.list(departmentService.find(parentId)));
	}

	@PostMapping("/tree")
	public Result tree() {
		return Result.success(departmentService.tree());
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Result delete(Long[] ids) {
		if (ids != null) {
			departmentService.delete(ids);
		}
		return Result.success();
	}

}