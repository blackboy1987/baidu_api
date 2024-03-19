package com.bootx.repository;

import com.bootx.entity.Menu;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author black
 */
@Repository
public interface MenuRepository extends PagingAndSortingRepository<Menu,Long>, JpaSpecificationExecutor<Menu> {
}
