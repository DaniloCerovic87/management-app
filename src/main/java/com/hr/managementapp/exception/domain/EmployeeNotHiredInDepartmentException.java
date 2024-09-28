package com.hr.managementapp.exception.domain;

public class EmployeeNotHiredInDepartmentException extends RuntimeException{
    public EmployeeNotHiredInDepartmentException(String message) {
        super(message);
    }
}
