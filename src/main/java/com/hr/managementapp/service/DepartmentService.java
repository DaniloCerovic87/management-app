package com.hr.managementapp.service;

import com.hr.managementapp.domain.Department;
import com.hr.managementapp.exception.domain.DepartmentNotFoundException;
import com.hr.managementapp.exception.domain.EmployeeNotHiredInDepartmentException;
import com.hr.managementapp.request.CreateDepartmentRequest;
import com.hr.managementapp.request.UpdateDepartmentRequest;
import com.hr.managementapp.response.DepartmentBasicInfoResponse;
import com.hr.managementapp.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {

    List<DepartmentBasicInfoResponse> getDepartments();

    DepartmentResponse getDepartmentById(Long id) throws DepartmentNotFoundException;

    DepartmentBasicInfoResponse createDepartment(CreateDepartmentRequest request);

    DepartmentResponse updateDepartment(UpdateDepartmentRequest request) throws DepartmentNotFoundException, EmployeeNotHiredInDepartmentException;

    void deleteDepartment(Long id) throws DepartmentNotFoundException;

}
