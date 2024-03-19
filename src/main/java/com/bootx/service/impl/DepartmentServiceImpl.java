
package com.bootx.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.DepartmentDao;
import com.bootx.entity.Department;
import com.bootx.repository.DepartmentRepository;
import com.bootx.service.DepartmentService;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * Service - 角色
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department, Long> implements DepartmentService {

    @Resource
    private DepartmentRepository departmentRepository;

    @Resource
    private DepartmentDao departmentDao;

    @Override
    public List<Map<String, Object>> tree() {

        List<Map<String, Object>> root = jdbcTemplate.queryForList("select id,name,(select count(id) from department where department.parent_id=parent.id) childrenCount from department parent where parent_id is null order by orders asc ;");

        root.forEach(item->{
            if(!StringUtils.equalsIgnoreCase("0",item.get("childrenCount")+"")){
                item.put("children",getChildren(Long.valueOf(item.get("id")+""),true));
            }
            item.remove("childrenCount");
        });
        return root;
    }

    @Override
    public List<Department> list(Department parent) {
        return departmentRepository.findAll((Specification<Department>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate restrictions = criteriaBuilder.conjunction();
            if (parent!=null) {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("parent"), parent));
            }else{
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("parent")));
            }
            criteriaQuery.where(restrictions);
            return restrictions;
        }, Sort.by(Sort.Direction.ASC,"order"));
    }

    private List<Map<String, Object>> getChildren(Long id, boolean b) {
        if(b){
            List<Map<String, Object>> list = jdbcTemplate.queryForList("select id,name,(select count(id) from department where department.parent_id=parent.id) childrenCount from department parent where parent_id =? order by orders asc ;",id);
            list.forEach(item->{
                if(!StringUtils.equalsIgnoreCase("0",item.get("childrenCount")+"")){
                    item.put("children",getChildren(Long.valueOf(item.get("id")+""),b));
                }
                item.remove("childrenCount");
            });
            return list;
        }
        return jdbcTemplate.queryForList("select id,name from department parent where parent_id=? order by orders asc ;",id);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Department> findRoots() {
        return departmentDao.findRoots(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Department> findRoots(Integer count) {
        return departmentDao.findRoots(count);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Department> findParents(Department department, boolean recursive, Integer count) {
        return departmentDao.findParents(department, recursive, count);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Department> findChildren(Department department, boolean recursive, Integer count) {
        return departmentDao.findChildren(department, recursive, count);
    }

    @Override
    public Page<Department> findPage(Pageable pageable, String name) {
        return departmentDao.findPage(pageable,name);
    }

    @Override
    @Transactional
    public Department save(Department department) {
        Assert.notNull(department, "[Assertion failed] - department is required; it must not be null");

        setValue(department);
        return super.save(department);
    }

    @Override
    @Transactional
    public Department update(Department department) {
        Assert.notNull(department, "[Assertion failed] - department is required; it must not be null");

        setValue(department);
        for (Department children : departmentDao.findChildren(department, true, null)) {
            setValue(children);
        }
        return super.update(department);
    }

    @Override
    @Transactional
    public Department update(Department department, String... ignoreProperties) {
        return super.update(department, ignoreProperties);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @Transactional
    public void delete(Long... ids) {
        super.delete(ids);
    }

    @Override
    @Transactional
    public void delete(Department department) {
        super.delete(department);
    }

    /**
     * 设置值
     *
     * @param department
     *            部门
     */
    private void setValue(Department department) {
        if (department == null) {
            return;
        }
        Department parent = department.getParent();
        if (parent != null) {
            department.setFullName(parent.getFullName() + department.getName());
            department.setTreePath(parent.getTreePath() + parent.getId() + Department.TREE_PATH_SEPARATOR);
        } else {
            department.setFullName(department.getName());
            department.setTreePath(Department.TREE_PATH_SEPARATOR);
        }
        department.setGrade(department.getParentIds().length);
    }
}