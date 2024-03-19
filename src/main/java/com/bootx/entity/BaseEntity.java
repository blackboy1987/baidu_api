
package com.bootx.entity;

import com.bootx.audit.AuditingEntityListener;
import com.bootx.audit.CreatedDate;
import com.bootx.audit.LastModifiedDate;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.groups.Default;

import java.io.Serializable;
import java.util.Date;

/**
 * @author black
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> implements Serializable {

	/**
	 * "ID"属性名称
	 */
	public static final String ID_PROPERTY_NAME = "id";

	/**
	 * "创建日期"属性名称
	 */
	public static final String CREATED_DATE_PROPERTY_NAME = "createdDate";

	/**
	 * "最后修改日期"属性名称
	 */
	public static final String LAST_MODIFIED_DATE_PROPERTY_NAME = "lastModifiedDate";

	/**
	 * "版本"属性名称
	 */
	public static final String VERSION_PROPERTY_NAME = "version";

	/**
	 * 保存验证组
	 */
	public interface Save extends Default {

	}

	/**
	 * 更新验证组
	 */
	public interface Update extends Default {

	}
	public interface DefaultView {

	}
	/**
	 * 基础视图
	 */
	public interface BaseView {

	}

	public interface ListView {}

	public interface PageView {}

	public interface ViewView{}

	public interface EditView{}

	public interface TreeView{}

	public interface IdView {}


	/**
	 * ID
	 */
	@JsonView({BaseView.class,ListView.class,ViewView.class,EditView.class,PageView.class,TreeView.class,IdView.class})
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false,updatable = false,columnDefinition = "bigint comment '主键'")
	private ID id;

	/**
	 * 创建日期
	 */
	@JsonView({ListView.class,BaseView.class,PageView.class})
	@CreatedDate
	@Column(nullable = false, updatable = false,columnDefinition = "datetime comment '创建时间'")
	private Date createdDate;

	/**
	 * 最后修改日期
	 */
	@JsonView({BaseView.class,PageView.class})
	@LastModifiedDate
	@Column(nullable = false,columnDefinition = "datetime comment '最后一次修改时间'")
	private Date lastModifiedDate;

	/**
	 * 版本
	 */
	@Version
	@Column(nullable = false,columnDefinition = "int comment '版本号'")
	private Long version;

	/**
	 * 获取ID
	 * 
	 * @return ID
	 */
	public ID getId() {
		return id;
	}

	/**
	 * 设置ID
	 * 
	 * @param id
	 *            ID
	 */
	public void setId(ID id) {
		this.id = id;
	}

	/**
	 * 获取创建日期
	 * 
	 * @return 创建日期
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * 设置创建日期
	 * 
	 * @param createdDate
	 *            创建日期
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * 获取最后修改日期
	 * 
	 * @return 最后修改日期
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * 设置最后修改日期
	 * 
	 * @param lastModifiedDate
	 *            最后修改日期
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * 获取版本
	 * 
	 * @return 版本
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * 设置版本
	 * 
	 * @param version
	 *            版本
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * 判断是否为新建对象
	 * 
	 * @return 是否为新建对象
	 */
	@Transient
	public boolean isNew() {
		return getId() == null;
	}

	/**
	 * 重写toString方法
	 * 
	 * @return 字符串
	 */
	@Override
	public String toString() {
		return String.format("Entity of type %s with id: %s", getClass().getName(), getId());
	}

	/**
	 * 重写equals方法
	 * 
	 * @param obj
	 *            对象
	 * @return 是否相等
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!BaseEntity.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		BaseEntity<?> other = (BaseEntity<?>) obj;
		return getId() != null ? getId().equals(other.getId()) : false;
	}

	/**
	 * 重写hashCode方法
	 * 
	 * @return HashCode
	 */
	@Override
	public int hashCode() {
		int hashCode = 17;
		hashCode += getId() != null ? getId().hashCode() * 31 : 0;
		return hashCode;
	}

}