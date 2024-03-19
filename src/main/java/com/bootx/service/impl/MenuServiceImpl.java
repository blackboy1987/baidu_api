
package com.bootx.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.MenuDao;
import com.bootx.entity.Menu;
import com.bootx.repository.MenuRepository;
import com.bootx.service.MenuService;
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
public class MenuServiceImpl extends BaseServiceImpl<Menu, Long> implements MenuService {

    @Resource
    private MenuRepository menuRepository;

    @Resource
    private MenuDao menuDao;

    @Override
    public List<Map<String, Object>> tree() {

        List<Map<String, Object>> root = jdbcTemplate.queryForList("select id,name,(select count(id) from menu where menu.parent_id=parent.id) childrenCount from menu parent where parent_id is null order by orders asc ;");

        root.forEach(item->{
            if(!StringUtils.equalsIgnoreCase("0",item.get("childrenCount")+"")){
                item.put("children",getChildren(Long.valueOf(item.get("id")+""),true));
            }
            item.remove("childrenCount");
        });
        return root;
    }

    @Override
    public List<Menu> list(Menu parent) {
        return menuRepository.findAll((Specification<Menu>) (root, criteriaQuery, criteriaBuilder) -> {
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
            List<Map<String, Object>> list = jdbcTemplate.queryForList("select id,name,(select count(id) from menu where menu.parent_id=parent.id) childrenCount from menu parent where parent_id =? order by orders asc ;",id);
            list.forEach(item->{
                if(!StringUtils.equalsIgnoreCase("0",item.get("childrenCount")+"")){
                    item.put("children",getChildren(Long.valueOf(item.get("id")+""),b));
                }
                item.remove("childrenCount");
            });
            return list;
        }
        return jdbcTemplate.queryForList("select id,name from menu parent where parent_id=? order by orders asc ;",id);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Menu> findRoots() {
        return menuDao.findRoots(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findRoots(Integer count) {
        return menuDao.findRoots(count);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findParents(Menu menu, boolean recursive, Integer count) {
        return menuDao.findParents(menu, recursive, count);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findChildren(Menu menu, boolean recursive, Integer count) {
        return menuDao.findChildren(menu, recursive, count);
    }

    @Override
    public Page<Menu> findPage(Pageable pageable, String name) {
        return menuDao.findPage(pageable,name);
    }

    @Override
    @Transactional
    public Menu save(Menu menu) {
        Assert.notNull(menu, "[Assertion failed] - menu is required; it must not be null");

        setValue(menu);
        return super.save(menu);
    }

    @Override
    @Transactional
    public Menu update(Menu menu) {
        Assert.notNull(menu, "[Assertion failed] - menu is required; it must not be null");

        setValue(menu);
        for (Menu children : menuDao.findChildren(menu, true, null)) {
            setValue(children);
        }
        return super.update(menu);
    }

    @Override
    @Transactional
    public Menu update(Menu menu, String... ignoreProperties) {
        return super.update(menu, ignoreProperties);
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
    public void delete(Menu menu) {
        super.delete(menu);
    }

    /**
     * 设置值
     *
     * @param menu
     *            菜单
     */
    private void setValue(Menu menu) {
        if (menu == null) {
            return;
        }
        Menu parent = menu.getParent();
        if (parent != null) {
            menu.setTreePath(parent.getTreePath() + parent.getId() + Menu.TREE_PATH_SEPARATOR);
        } else {
            menu.setTreePath(Menu.TREE_PATH_SEPARATOR);
        }
        menu.setGrade(menu.getParentIds().length);
    }
}