package com.hr.managementapp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDepartmentRequest {

    private Long id;
    private String name;
    private Long teamLeadId;

}
