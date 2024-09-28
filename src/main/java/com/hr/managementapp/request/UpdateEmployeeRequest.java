package com.hr.managementapp.request;

import lombok.Data;

@Data
public class UpdateEmployeeRequest {

    private Long id;
    private String name;
    private String personalId;
    private Long departmentId;

}
