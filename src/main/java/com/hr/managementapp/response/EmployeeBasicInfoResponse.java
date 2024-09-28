package com.hr.managementapp.response;

import lombok.Data;

@Data
public class EmployeeBasicInfoResponse {

    private Long id;
    private String name;
    private Long departmentId;

}
