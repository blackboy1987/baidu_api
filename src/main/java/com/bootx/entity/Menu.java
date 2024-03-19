
package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity - 菜单
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Entity
public class Menu extends OrderedEntity<Long> {

	private static final long serialVersionUID = -2158109459123036967L;

	/**
	 * 树路径分隔符
	 */
	public static final String TREE_PATH_SEPARATOR = ",";

	/**
	 * 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	@JsonView({PageView.class,ListView.class})
	private String name;

	/**
	 * 路径
	 */
	@JsonView({ListView.class})
	private String url;

	/**
	 * 树路径
	 */
	@Column(nullable = false)
	private String treePath;

	/**
	 * 层级
	 */
	@Column(nullable = false)
	private Integer grade;

	/**
	 * 上级菜单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Menu parent;

	/**
	 * 下级菜单
	 */
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("order asc")
	private Set<Menu> children = new HashSet<>();

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取树路径
	 * 
	 * @return 树路径
	 */
	public String getTreePath() {
		return treePath;
	}

	/**
	 * 设置树路径
	 * 
	 * @param treePath
	 *            树路径
	 */
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	/**
	 * 获取层级
	 * 
	 * @return 层级
	 */
	public Integer getGrade() {
		return grade;
	}

	/**
	 * 设置层级
	 * 
	 * @param grade
	 *            层级
	 */
	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	/**
	 * 获取上级菜单
	 * 
	 * @return 上级菜单
	 */
	public Menu getParent() {
		return parent;
	}

	/**
	 * 设置上级菜单
	 * 
	 * @param parent
	 *            上级菜单
	 */
	public void setParent(Menu parent) {
		this.parent = parent;
	}

	/**
	 * 获取下级菜单
	 * 
	 * @return 下级菜单
	 */
	public Set<Menu> getChildren() {
		return children;
	}

	/**
	 * 设置下级菜单
	 * 
	 * @param children
	 *            下级菜单
	 */
	public void setChildren(Set<Menu> children) {
		this.children = children;
	}

	/**
	 * 获取所有上级菜单ID
	 * 
	 * @return 所有上级菜单ID
	 */
	@Transient
	public Long[] getParentIds() {
		String[] parentIds = StringUtils.split(getTreePath(), TREE_PATH_SEPARATOR);
		Long[] result = new Long[parentIds.length];
		for (int i = 0; i < parentIds.length; i++) {
			result[i] = Long.valueOf(parentIds[i]);
		}
		return result;
	}

	/**
	 * 获取所有上级菜单
	 * 
	 * @return 所有上级菜单
	 */
	@Transient
	public List<Menu> getParents() {
		List<Menu> parents = new ArrayList<>();
		recursiveParents(parents, this);
		return parents;
	}

	/**
	 * 递归上级菜单
	 * 
	 * @param parents
	 *            上级菜单
	 * @param menu
	 *            菜单
	 */
	private void recursiveParents(List<Menu> parents, Menu menu) {
		if (menu == null) {
			return;
		}
		Menu parent = menu.getParent();
		if (parent != null) {
			parents.add(0, parent);
			recursiveParents(parents, parent);
		}
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {

	}

}