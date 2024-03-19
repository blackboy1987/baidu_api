
package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Entity - 管理员
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Entity
public class Admin extends User {

	/**
	 * 用户名
	 */
	@NotEmpty(groups = Save.class)
	@Length(min = 4, max = 20)
	@Pattern.List({ @Pattern(regexp = "^[0-9a-zA-Z_\\u4e00-\\u9fa5]+$"), @Pattern(regexp = "^.*[^\\d].*$") })
	@Column(nullable = false, updatable = false)
	@JsonView({PageView.class})
	private String username;

	/**
	 * 密码
	 */
	@NotEmpty(groups = Save.class)
	@Length(min = 4, max = 20)
	@Transient
	private String password;

	/**
	 * 加密密码
	 */
	@Column(nullable = false)
	private String encodedPassword;

	/**
	 * 姓名
	 */
	@Length(max = 200)
	@JsonView({PageView.class})
	private String name;

	/**
	 * 部门
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Department department;

	/**
	 * 角色
	 */
	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Role> roles = new HashSet<>();

	/**
	 * 获取用户名
	 * 
	 * @return 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 * 
	 * @param username
	 *            用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取密码
	 * 
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码
	 * 
	 * @param password
	 *            密码
	 */
	public void setPassword(String password) {
		this.password = password;
		if (password != null) {
			setEncodedPassword(DigestUtils.md5Hex(password));
		}
	}

	/**
	 * 获取加密密码
	 * 
	 * @return 加密密码
	 */
	public String getEncodedPassword() {
		return encodedPassword;
	}

	/**
	 * 设置加密密码
	 * 
	 * @param encodedPassword
	 *            加密密码
	 */
	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
	}

	/**
	 * 获取姓名
	 * 
	 * @return 姓名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置姓名
	 * 
	 * @param name
	 *            姓名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取部门
	 * 
	 * @return 部门
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * 设置部门
	 * 
	 * @param department
	 *            部门
	 */
	public void setDepartment(Department department) {
		this.department = department;
	}

	/**
	 * 获取角色
	 * 
	 * @return 角色
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * 设置角色
	 * 
	 * @param roles
	 *            角色
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	@Transient
	public String getDisplayName() {
		return getUsername();
	}

	@Override
	@Transient
	public Object getPrincipal() {
		return getUsername();
	}

	@Override
	@Transient
	public Object getCredentials() {
		return getPassword();
	}

	@Override
	@Transient
	public boolean isValidCredentials(Object credentials) {
		return credentials != null && StringUtils.equals(DigestUtils.md5Hex(credentials instanceof char[] ? String.valueOf((char[]) credentials) : String.valueOf(credentials)), getEncodedPassword());
	}

	/**
	 * 持久化前处理
	 */
	@PrePersist
	public void prePersist() {
		setUsername(StringUtils.lowerCase(getUsername()));
	}

	/**
	 * 更新前处理
	 */
	@PreUpdate
	public void preUpdate() {
	}

	@Transient
	@JsonView({PageView.class})
	public Long getDepartmentId(){
		if(department!=null){
			return department.getId();
		}
		return null;
	}

	@Transient
	@JsonView({PageView.class})
	public String getDepartmentName(){
		if(department!=null){
			return department.getName();
		}
		return null;
	}

	@Transient
	@JsonView({PageView.class})
	public List<Long> getRoleIds() {
		List<Long> roleIds = new ArrayList<>();
		if(!roles.isEmpty()){
			roleIds = roles.stream().map(BaseEntity::getId).collect(Collectors.toList());
		}

		return roleIds;
	}

	@Transient
	@JsonView({PageView.class})
	public List<String> getRoleNames() {
		List<String> roleIds = new ArrayList<>();
		if(!roles.isEmpty()){
			roleIds = roles.stream().map(Role::getName).collect(Collectors.toList());
		}

		return roleIds;
	}

}