package com.hr.managementapp.request;

import lombok.Data;

@Data
public class CreateEmployeeRequest {

    private String name;
    private String personalId;
    private Long departmentId;

}
