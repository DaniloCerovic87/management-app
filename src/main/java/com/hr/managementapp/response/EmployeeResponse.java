package com.hr.managementapp.response;

import com.hr.managementapp.domain.Employee;
import lombok.Data;

@Data
public class EmployeeResponse {

    private Long id;
    private String personalId;
    private String name;
    private DepartmentBasicInfoResponse departmentBasicInfoResponse;

}
