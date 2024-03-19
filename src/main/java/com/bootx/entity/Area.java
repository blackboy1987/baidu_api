
package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity - 地区
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Entity
public class Area extends OrderedEntity<Long> {

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
	@JsonView({PageView.class})
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
	 * 上级地区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Area parent;

	@NotNull
	@Column(nullable = false,updatable = false,unique = true)
	private String code;

	/**
	 * 下级地区
	 */
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("order asc")
	private Set<Area> children = new HashSet<>();

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
	 * 获取上级地区
	 * 
	 * @return 上级地区
	 */
	public Area getParent() {
		return parent;
	}

	/**
	 * 设置上级地区
	 * 
	 * @param parent
	 *            上级地区
	 */
	public void setParent(Area parent) {
		this.parent = parent;
	}

	/**
	 * 获取下级地区
	 * 
	 * @return 下级地区
	 */
	public Set<Area> getChildren() {
		return children;
	}

	/**
	 * 设置下级地区
	 * 
	 * @param children
	 *            下级地区
	 */
	public void setChildren(Set<Area> children) {
		this.children = children;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取所有上级地区ID
	 * 
	 * @return 所有上级地区ID
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
	 * 获取所有上级地区
	 * 
	 * @return 所有上级地区
	 */
	@Transient
	public List<Area> getParents() {
		List<Area> parents = new ArrayList<>();
		recursiveParents(parents, this);
		return parents;
	}

	/**
	 * 递归上级地区
	 * 
	 * @param parents
	 *            上级地区
	 * @param area
	 *            地区
	 */
	private void recursiveParents(List<Area> parents, Area area) {
		if (area == null) {
			return;
		}
		Area parent = area.getParent();
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