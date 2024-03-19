
package com.bootx.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.AdminDao;
import com.bootx.entity.Admin;
import com.bootx.repository.AdminRepository;
import com.bootx.service.AdminService;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 管理员
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, Long> implements AdminService {

    @Resource
    private AdminRepository adminRepository;

    @Resource
    private AdminDao adminDao;

    @Override
    @Transactional(readOnly = true)
    public boolean usernameExists(String username) {
        return adminDao.exists("username", StringUtils.lowerCase(username));
    }

    @Override
    @Transactional(readOnly = true)
    public Admin findByUsername(String username) {
        return adminDao.find("username", StringUtils.lowerCase(username));
    }


    @Override
    public Page<Admin> findPage(Pageable pageable, String username) {
        org.springframework.data.domain.Page<Admin> all = adminRepository.findAll((Specification<Admin>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate restrictions = criteriaBuilder.conjunction();
            if (StringUtils.isNotBlank(username)) {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("username"), "%"+username+"%"));
            }
            criteriaQuery.where(restrictions);
            return restrictions;
        }, org.springframework.data.domain.Pageable.ofSize(pageable.getPageSize()).withPage(pageable.getPageNumber()-1));

        return new Page<Admin>(all.getContent(),all.getTotalElements(),pageable);


    }
}