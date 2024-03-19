
package com.bootx.dao.impl;

import com.bootx.dao.PermissionDao;
import com.bootx.entity.Menu;
import com.bootx.entity.Permission;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Dao - 角色
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class PermissionDaoImpl extends BaseDaoImpl<Permission, Long> implements PermissionDao {

    @Override
    public List<Permission> list(Menu menu) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Permission> criteriaQuery = criteriaBuilder.createQuery(Permission.class);
        Root<Permission> root = criteriaQuery.from(Permission.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if(menu!=null){
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("menu"), menu));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery);
    }
}