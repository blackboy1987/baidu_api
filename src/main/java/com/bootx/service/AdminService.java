
package com.bootx.service;


import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Admin;

/**
 * Service - 管理员
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface AdminService extends BaseService<Admin, Long> {

	/**
	 * 判断用户名是否存在
	 *
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 根据用户名查找管理员
	 *
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 管理员，若不存在则返回null
	 */
	Admin findByUsername(String username);


	Page<Admin> findPage(Pageable pageable, String username);
}