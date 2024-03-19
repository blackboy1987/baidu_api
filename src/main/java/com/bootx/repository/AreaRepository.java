package com.bootx.repository;

import com.bootx.entity.Area;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author black
 */
@Repository
public interface AreaRepository extends PagingAndSortingRepository<Area,Long>, JpaSpecificationExecutor<Area> {
}
