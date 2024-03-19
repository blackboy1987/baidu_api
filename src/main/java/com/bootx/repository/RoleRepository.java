package com.bootx.repository;

import com.bootx.entity.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author black
 */
@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role,Long>, JpaSpecificationExecutor<Role> {
}
