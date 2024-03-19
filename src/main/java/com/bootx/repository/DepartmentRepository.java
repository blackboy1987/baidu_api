package com.bootx.repository;

import com.bootx.entity.Department;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author black
 */
@Repository
public interface DepartmentRepository extends PagingAndSortingRepository<Department,Long>, JpaSpecificationExecutor<Department> {
}
