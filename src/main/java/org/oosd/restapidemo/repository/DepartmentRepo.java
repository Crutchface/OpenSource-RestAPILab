package org.oosd.restapidemo.repository;

import org.oosd.restapidemo.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department, Long>{

};
