package com.hr.managementapp.service;

import com.hr.managementapp.domain.Employee;
import com.hr.managementapp.exception.domain.DepartmentNotFoundException;
import com.hr.managementapp.exception.domain.DepartmentNotSelectedException;
import com.hr.managementapp.exception.domain.EmployeeNotFoundException;
import com.hr.managementapp.exception.domain.EmployeeNotHiredInDepartmentException;
import com.hr.managementapp.form.EmployeeFilterForm;
import com.hr.managementapp.request.CreateEmployeeRequest;
import com.hr.managementapp.request.UpdateEmployeeRequest;
import com.hr.managementapp.response.EmployeeBasicInfoResponse;
import com.hr.managementapp.response.EmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    Page<EmployeeBasicInfoResponse> getEmployees(Pageable pageable);

    /**
     * This method returns employees
     * @param filterForm includes parameters for searching
     */
    List<EmployeeBasicInfoResponse> findEmployeesBySearch(EmployeeFilterForm filterForm);

    EmployeeResponse getEmployeeById(Long id);

    EmployeeBasicInfoResponse createEmployee(CreateEmployeeRequest request);

    EmployeeResponse updateEmployee(UpdateEmployeeRequest request);

    void deleteEmployee(Long id) throws EmployeeNotFoundException;

}
