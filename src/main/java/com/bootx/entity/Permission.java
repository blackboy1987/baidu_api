
package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity - 权限
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Entity
public class Permission extends BaseEntity<Long> {

	@ManyToOne(fetch = FetchType.LAZY)
	private Menu menu;

	/**
	 * 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	@JsonView({ListView.class})
	private String name;

	/**
	 * 描述
	 */
	@Length(max = 200)
	@JsonView({ListView.class})
	private String description;

	@ManyToMany(mappedBy = "permissions")
	private Set<Role> roles = new HashSet<>();

	/**
	 * 路径
	 */
	@JsonView({ListView.class})
	@Length(max = 2000)
	@Column(nullable = false,columnDefinition = "varchar(2000)")
	private String url;

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Transient
	@JsonView({ListView.class})
	public String getMenuName(){
		return menu.getName();
	}

	@Transient
	@JsonView({ListView.class})
	public Long getMenuId(){
		return menu.getId();
	}
}