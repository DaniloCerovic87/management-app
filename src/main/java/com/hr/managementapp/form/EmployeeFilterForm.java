package com.hr.managementapp.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
public class EmployeeFilterForm {

    private String name;
    private Long departmentId;

    @JsonIgnore
    public boolean isEmpty() {
        return StringUtils.isEmpty(name) && departmentId == null;
    }
}
