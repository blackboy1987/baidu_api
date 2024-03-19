
package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity - 角色
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Entity
public class Role extends BaseEntity<Long> {

	/**
	 * 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	@JsonView({PageView.class,ListView.class})
	private String name;

	/**
	 * 描述
	 */
	@Length(max = 200)
	@JsonView({PageView.class})
	private String description;

	/**
	 * 权限
	 */
	@ManyToMany
	private Set<Permission> permissions = new HashSet<>();

	/**
	 * 管理员
	 */
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	private Set<Admin> admins = new HashSet<>();

	/**
	 * 是否启用
	 */
	@NotNull
	@Column(nullable = false)
	@JsonView({PageView.class})
	private Boolean isEnabled;

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
	 * 获取描述
	 * 
	 * @return 描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置描述
	 * 
	 * @param description
	 *            描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取管理员
	 * 
	 * @return 管理员
	 */
	public Set<Admin> getAdmins() {
		return admins;
	}

	/**
	 * 设置管理员
	 * 
	 * @param admins
	 *            管理员
	 */
	public void setAdmins(Set<Admin> admins) {
		this.admins = admins;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * 获取是否启用
	 *
	 * @return 是否启用
	 */
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	/**
	 * 设置是否启用
	 *
	 * @param isEnabled
	 *            是否启用
	 */
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

}