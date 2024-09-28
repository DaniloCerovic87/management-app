package com.hr.managementapp.repository;

import com.hr.managementapp.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e JOIN e.department WHERE " +
            " (:name is null OR lower(e.name) LIKE CONCAT('%',lower(:name),'%')) AND " +
            " (e.department.id is null OR e.department.id = :departmentId)")

    List<Employee> findBySearch(@Param("name") String name,
                                @Param("departmentId") Long departmentId);

}
