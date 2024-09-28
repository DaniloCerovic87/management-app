package com.hr.managementapp.response;

import com.hr.managementapp.domain.Department;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DepartmentResponse {

    private Long id;
    private String name;
    private List<EmployeeBasicInfoResponse> employees = new ArrayList<>();
    private EmployeeBasicInfoResponse teamLead;

}
