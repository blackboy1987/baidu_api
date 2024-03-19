
package com.bootx.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Role;
import com.bootx.repository.RoleRepository;
import com.bootx.service.RoleService;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Service - 角色
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {

	@Resource
	private RoleRepository roleRepository;

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Role save(Role role) {
		return super.save(role);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Role update(Role role) {
		return super.update(role);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Role update(Role role, String... ignoreProperties) {
		return super.update(role, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Role role) {
		super.delete(role);
	}

	@Override
	public Page<Role> findPage(Pageable pageable, String name, String description, Date beginDate, Date endDate) {
		org.springframework.data.domain.Page<Role> all = roleRepository.findAll((Specification<Role>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate restrictions = criteriaBuilder.conjunction();
            if (StringUtils.isNotBlank(name)) {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("name"), "%"+name+"%"));
            }
            if (StringUtils.isNotBlank(description)) {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("description"), "%"+description+"%"));
            }
			if (beginDate!=null) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), beginDate));
			}
			if (endDate != null) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), endDate));
			}
            criteriaQuery.where(restrictions);
            return restrictions;
        }, org.springframework.data.domain.Pageable.ofSize(pageable.getPageSize()).withPage(pageable.getPageNumber()-1));

		return new Page<Role>(all.getContent(),all.getTotalElements(),pageable);
	}
}