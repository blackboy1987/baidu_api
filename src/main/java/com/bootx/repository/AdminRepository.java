package com.bootx.repository;

import com.bootx.entity.Admin;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author black
 */
@Repository
public interface AdminRepository extends PagingAndSortingRepository<Admin,Long>, JpaSpecificationExecutor<Admin> {
}
