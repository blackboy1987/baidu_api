
package com.bootx.dao.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.MenuDao;
import com.bootx.entity.Menu;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Dao - 角色
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class MenuDaoImpl extends BaseDaoImpl<Menu, Long> implements MenuDao {
    @Override
    public List<Menu> findRoots(Integer count) {
        String jpql = "select menu from Menu menu where menu.parent is null order by menu.order asc";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        if (count != null) {
            query.setMaxResults(count);
        }
        return query.getResultList();
    }

    @Override
    public List<Menu> findParents(Menu menu, boolean recursive, Integer count) {
        if (menu == null || menu.getParent() == null) {
            return Collections.emptyList();
        }
        TypedQuery<Menu> query;
        if (recursive) {
            String jpql = "select menu from Menu menu where menu.id in (:ids) order by menu.grade asc";
            query = entityManager.createQuery(jpql, Menu.class).setParameter("ids", Arrays.asList(menu.getParentIds()));
        } else {
            String jpql = "select menu from Menu menu where menu = :menu";
            query = entityManager.createQuery(jpql, Menu.class).setParameter("menu", menu.getParent());
        }
        if (count != null) {
            query.setMaxResults(count);
        }
        return query.getResultList();
    }

    @Override
    public List<Menu> findChildren(Menu menu, boolean recursive, Integer count) {
        TypedQuery<Menu> query;
        if (recursive) {
            if (menu != null) {
                String jpql = "select menu from Menu menu where menu.treePath like :treePath order by menu.grade asc, menu.order asc";
                query = entityManager.createQuery(jpql, Menu.class).setParameter("treePath", "%" + Menu.TREE_PATH_SEPARATOR + menu.getId() + Menu.TREE_PATH_SEPARATOR + "%");
            } else {
                String jpql = "select menu from Menu menu order by menu.grade asc, menu.order asc";
                query = entityManager.createQuery(jpql, Menu.class);
            }
            if (count != null) {
                query.setMaxResults(count);
            }
            List<Menu> result = query.getResultList();
            sort(result);
            return result;
        } else {
            String jpql = "select menu from Menu menu where menu.parent = :parent order by menu.order asc";
            query = entityManager.createQuery(jpql, Menu.class).setParameter("parent", menu);
            if (count != null) {
                query.setMaxResults(count);
            }
            return query.getResultList();
        }
    }

    @Override
    public Page<Menu> findPage(Pageable pageable, String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Menu> criteriaQuery = criteriaBuilder.createQuery(Menu.class);
        Root<Menu> root = criteriaQuery.from(Menu.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("name"), name));
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }

    /**
     * 排序菜单
     *
     * @param areas
     *            菜单
     */
    private void sort(List<Menu> areas) {
        if (CollectionUtils.isEmpty(areas)) {
            return;
        }
        final Map<Long, Integer> orderMap = new HashMap<>();
        for (Menu menu : areas) {
            orderMap.put(menu.getId(), menu.getOrder());
        }
        Collections.sort(areas, new Comparator<Menu>() {
            @Override
            public int compare(Menu area1, Menu area2) {
                Long[] ids1 = (Long[]) ArrayUtils.add(area1.getParentIds(), area1.getId());
                Long[] ids2 = (Long[]) ArrayUtils.add(area2.getParentIds(), area2.getId());
                Iterator<Long> iterator1 = Arrays.asList(ids1).iterator();
                Iterator<Long> iterator2 = Arrays.asList(ids2).iterator();
                CompareToBuilder compareToBuilder = new CompareToBuilder();
                while (iterator1.hasNext() && iterator2.hasNext()) {
                    Long id1 = iterator1.next();
                    Long id2 = iterator2.next();
                    Integer order1 = orderMap.get(id1);
                    Integer order2 = orderMap.get(id2);
                    compareToBuilder.append(order1, order2).append(id1, id2);
                    if (!iterator1.hasNext() || !iterator2.hasNext()) {
                        compareToBuilder.append(area1.getGrade(), area2.getGrade());
                    }
                }
                return compareToBuilder.toComparison();
            }
        });
    }
}