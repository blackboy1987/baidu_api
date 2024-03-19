
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
 * Entity - 部门
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Entity
public class Department extends OrderedEntity<Long> {

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
	 * 全称
	 */
	@Column(nullable = false, length = 4000)
	private String fullName;

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
	 * 上级部门
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Department parent;

	/**
	 * 下级部门
	 */
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("order asc")
	private Set<Department> children = new HashSet<>();

	@OneToMany(mappedBy = "department",fetch = FetchType.LAZY)
	private Set<Admin> admins = new HashSet<>();

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

	/**
	 * 获取全称
	 * 
	 * @return 全称
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * 设置全称
	 * 
	 * @param fullName
	 *            全称
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	 * 获取上级部门
	 * 
	 * @return 上级部门
	 */
	public Department getParent() {
		return parent;
	}

	/**
	 * 设置上级部门
	 * 
	 * @param parent
	 *            上级部门
	 */
	public void setParent(Department parent) {
		this.parent = parent;
	}

	/**
	 * 获取下级部门
	 * 
	 * @return 下级部门
	 */
	public Set<Department> getChildren() {
		return children;
	}

	/**
	 * 设置下级部门
	 * 
	 * @param children
	 *            下级部门
	 */
	public void setChildren(Set<Department> children) {
		this.children = children;
	}

	public Set<Admin> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<Admin> admins) {
		this.admins = admins;
	}

	/**
	 * 获取所有上级部门ID
	 * 
	 * @return 所有上级部门ID
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
	 * 获取所有上级部门
	 * 
	 * @return 所有上级部门
	 */
	@Transient
	public List<Department> getParents() {
		List<Department> parents = new ArrayList<>();
		recursiveParents(parents, this);
		return parents;
	}

	/**
	 * 递归上级部门
	 * 
	 * @param parents
	 *            上级部门
	 * @param department
	 *            部门
	 */
	private void recursiveParents(List<Department> parents, Department department) {
		if (department == null) {
			return;
		}
		Department parent = department.getParent();
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