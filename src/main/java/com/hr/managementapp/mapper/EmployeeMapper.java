package com.hr.managementapp.mapper;

import com.hr.managementapp.domain.Employee;
import com.hr.managementapp.response.DepartmentBasicInfoResponse;
import com.hr.managementapp.response.EmployeeBasicInfoResponse;
import com.hr.managementapp.response.EmployeeResponse;

public class EmployeeMapper {

    public static EmployeeBasicInfoResponse mapToBasicResponse(Employee employee) {
        EmployeeBasicInfoResponse response = new EmployeeBasicInfoResponse();
        response.setId(employee.getId());
        response.setName(employee.getName());
        response.setDepartmentId(employee.getDepartment().getId());
        return response;
    }

    public static EmployeeResponse mapToResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setPersonalId(employee.getPersonalId());
        response.setName(employee.getName());
        response.setDepartmentBasicInfoResponse(DepartmentMapper.mapToBasicResponse(employee.getDepartment()));
        return response;
    }

}
