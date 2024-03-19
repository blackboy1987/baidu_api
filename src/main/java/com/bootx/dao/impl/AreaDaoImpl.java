
package com.bootx.dao.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.AreaDao;
import com.bootx.entity.Area;
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
 * Dao - 地区
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class AreaDaoImpl extends BaseDaoImpl<Area, Long> implements AreaDao {

	@Override
	public List<Area> findRoots(Integer count) {
		String jpql = "select area from Area area where area.parent is null order by area.order asc";
		TypedQuery<Area> query = entityManager.createQuery(jpql, Area.class);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	@Override
	public List<Area> findParents(Area area, boolean recursive, Integer count) {
		if (area == null || area.getParent() == null) {
			return Collections.emptyList();
		}
		TypedQuery<Area> query;
		if (recursive) {
			String jpql = "select area from Area area where area.id in (:ids) order by area.grade asc";
			query = entityManager.createQuery(jpql, Area.class).setParameter("ids", Arrays.asList(area.getParentIds()));
		} else {
			String jpql = "select area from Area area where area = :area";
			query = entityManager.createQuery(jpql, Area.class).setParameter("area", area.getParent());
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	@Override
	public List<Area> findChildren(Area area, boolean recursive, Integer count) {
		TypedQuery<Area> query;
		if (recursive) {
			if (area != null) {
				String jpql = "select area from Area area where area.treePath like :treePath order by area.grade asc, area.order asc";
				query = entityManager.createQuery(jpql, Area.class).setParameter("treePath", "%" + Area.TREE_PATH_SEPARATOR + area.getId() + Area.TREE_PATH_SEPARATOR + "%");
			} else {
				String jpql = "select area from Area area order by area.grade asc, area.order asc";
				query = entityManager.createQuery(jpql, Area.class);
			}
			if (count != null) {
				query.setMaxResults(count);
			}
			List<Area> result = query.getResultList();
			sort(result);
			return result;
		} else {
			String jpql = "select area from Area area where area.parent = :parent order by area.order asc";
			query = entityManager.createQuery(jpql, Area.class).setParameter("parent", area);
			if (count != null) {
				query.setMaxResults(count);
			}
			return query.getResultList();
		}
	}

	@Override
	public Page<Area> findPage(Pageable pageable, String name) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Area> criteriaQuery = criteriaBuilder.createQuery(Area.class);
		Root<Area> root = criteriaQuery.from(Area.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("name"), name));
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	/**
	 * 排序地区
	 * 
	 * @param areas
	 *            地区
	 */
	private void sort(List<Area> areas) {
		if (CollectionUtils.isEmpty(areas)) {
			return;
		}
		final Map<Long, Integer> orderMap = new HashMap<>();
		for (Area area : areas) {
			orderMap.put(area.getId(), area.getOrder());
		}
		Collections.sort(areas, new Comparator<Area>() {
			@Override
			public int compare(Area area1, Area area2) {
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