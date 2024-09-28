package com.hr.managementapp.mapper;

import com.hr.managementapp.domain.Department;
import com.hr.managementapp.request.CreateDepartmentRequest;
import com.hr.managementapp.response.DepartmentBasicInfoResponse;
import com.hr.managementapp.response.DepartmentResponse;
import com.hr.managementapp.response.EmployeeBasicInfoResponse;

import java.util.stream.Collectors;

public class DepartmentMapper {

    public static Department mapToEntity(CreateDepartmentRequest createDepartmentRequest) {
        Department department = new Department();
        department.setName(createDepartmentRequest.getName());
        return department;
    }

    public static DepartmentResponse mapToResponse(Department department) {
        DepartmentResponse departmentResponse = new DepartmentResponse();
        departmentResponse.setId(department.getId());
        departmentResponse.setName(department.getName());
        departmentResponse.setEmployees( department.getEmployees().stream().map(EmployeeMapper::mapToBasicResponse).collect(Collectors.toList()));
        departmentResponse.setTeamLead(department.getTeamLead() != null ? EmployeeMapper.mapToBasicResponse(department.getTeamLead()) : null);
        return departmentResponse;
    }

    public static DepartmentBasicInfoResponse mapToBasicResponse(Department department) {
        DepartmentBasicInfoResponse departmentResponse = new DepartmentBasicInfoResponse();
        departmentResponse.setId(department.getId());
        departmentResponse.setName(department.getName());
        return departmentResponse;
    }

}
