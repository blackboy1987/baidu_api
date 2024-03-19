
package com.bootx.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.AreaDao;
import com.bootx.entity.Area;
import com.bootx.service.AreaService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Service - 地区
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class AreaServiceImpl extends BaseServiceImpl<Area, Long> implements AreaService {

	@Resource
	private AreaDao areaDao;

	@Override
	@Transactional(readOnly = true)
	public List<Area> findRoots() {
		return areaDao.findRoots(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Area> findRoots(Integer count) {
		return areaDao.findRoots(count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Area> findParents(Area area, boolean recursive, Integer count) {
		return areaDao.findParents(area, recursive, count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Area> findChildren(Area area, boolean recursive, Integer count) {
		return areaDao.findChildren(area, recursive, count);
	}

	@Override
	public Page<Area> findPage(Pageable pageable, String name) {
		return areaDao.findPage(pageable,name);
	}

	@Override
	@Transactional
	public Area save(Area area) {
		Assert.notNull(area, "[Assertion failed] - area is required; it must not be null");

		setValue(area);
		return super.save(area);
	}

	@Override
	@Transactional
	public Area update(Area area) {
		Assert.notNull(area, "[Assertion failed] - area is required; it must not be null");

		setValue(area);
		for (Area children : areaDao.findChildren(area, true, null)) {
			setValue(children);
		}
		return super.update(area);
	}

	@Override
	@Transactional
	public Area update(Area area, String... ignoreProperties) {
		return super.update(area, ignoreProperties);
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
	public void delete(Area area) {
		super.delete(area);
	}

	/**
	 * 设置值
	 * 
	 * @param area
	 *            地区
	 */
	private void setValue(Area area) {
		if (area == null) {
			return;
		}
		Area parent = area.getParent();
		if (parent != null) {
			area.setFullName(parent.getFullName() + area.getName());
			area.setTreePath(parent.getTreePath() + parent.getId() + Area.TREE_PATH_SEPARATOR);
		} else {
			area.setFullName(area.getName());
			area.setTreePath(Area.TREE_PATH_SEPARATOR);
		}
		area.setGrade(area.getParentIds().length);
	}

}