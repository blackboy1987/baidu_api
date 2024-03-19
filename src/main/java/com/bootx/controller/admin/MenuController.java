
package com.bootx.controller.admin;

import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Menu;
import com.bootx.service.MenuService;
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
@RestController("adminMenuController")
@RequestMapping("/admin/api/menu")
@CrossOrigin("*")
public class MenuController extends BaseController {

	@Resource
	private MenuService menuService;

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Result save(Menu menu,Long parentId) {
		menu.setParent(menuService.find(parentId));
		menuService.save(menu);
		menu.setTreePath(null);
		menu.setGrade(null);
		menu.setChildren(null);
		return Result.success();
	}


	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Result update(Menu menu,Long parentId) {
		menu.setParent(menuService.find(parentId));
		if (menu.getParent() != null) {
			Menu parent = menu.getParent();
			if (parent.equals(menu)) {
				return Result.error("参数异常");
			}
			List<Menu> children = menuService.findChildren(parent, true, null);
			if (children != null && children.contains(parent)) {
				return Result.error("参数异常");
			}
		}
		menuService.update(menu,"treePath", "grade","children");
		return Result.success();
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(BaseEntity.ListView.class)
	public Result list(Long parentId) {
		return Result.success(menuService.list(menuService.find(parentId)));
	}

	@PostMapping("/tree")
	public Result tree() {
		return Result.success(menuService.tree());
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Result delete(Long[] ids) {
		if (ids != null) {
			menuService.delete(ids);
		}
		return Result.success();
	}

}