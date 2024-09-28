package com.hr.managementapp.controller;

import com.hr.managementapp.domain.Employee;
import com.hr.managementapp.exception.domain.*;
import com.hr.managementapp.form.EmployeeFilterForm;
import com.hr.managementapp.request.CreateEmployeeRequest;
import com.hr.managementapp.request.UpdateEmployeeRequest;
import com.hr.managementapp.response.EmployeeBasicInfoResponse;
import com.hr.managementapp.response.EmployeeResponse;
import com.hr.managementapp.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static com.hr.managementapp.constant.EmployeeConstant.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Get a page of employees")
    @ApiResponse(responseCode = "200", description = "Employees successfully retrieved")
    @GetMapping
    public ResponseEntity<Page<EmployeeBasicInfoResponse>> getEmployees(Pageable pageable) {
        Page<EmployeeBasicInfoResponse> page = employeeService.getEmployees(pageable);
        Page<EmployeeBasicInfoResponse> pageResponse = new PageImpl<>(page.toList(), pageable, page.getSize());
        return new ResponseEntity<>(pageResponse, OK);
    }

    @Operation(summary = "Get an employee by id")
    @ApiResponse(responseCode = "200", description = "Employee successfully retrieved")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@Parameter(description = "id of employee to be searched")
                                                            @PathVariable(name = "id") Long id) {
        EmployeeResponse employeeResponse = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employeeResponse, OK);
    }

    @Operation(summary = "Create a new employee")
    @ApiResponse(responseCode = "200", description = "Employee successfully created")
    @PostMapping
    public ResponseEntity<EmployeeBasicInfoResponse> createEmployee(@RequestBody CreateEmployeeRequest request) {
        EmployeeBasicInfoResponse employeeResponse = employeeService.createEmployee(request);
        return new ResponseEntity<>(employeeResponse, OK);
    }

    @Operation(summary = "Update an employee")
    @ApiResponse(responseCode = "200", description = "Employee successfully updated")
    @PutMapping
    public ResponseEntity<EmployeeResponse> updateEmployee(@RequestBody UpdateEmployeeRequest request) {
        EmployeeResponse employeeResponse = employeeService.updateEmployee(request);
        return new ResponseEntity<>(employeeResponse, OK);
    }

    @Operation(summary = "Delete an employee by its id")
    @ApiResponse(responseCode = "200", description = "Employee successfully deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(name = "id") Long id) throws EmployeeNotFoundException {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @Operation(summary = "Search for employees for the chosen criteria")
    @ApiResponse(responseCode = "200", description = "Employees successfully retrieved")
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeBasicInfoResponse>> findEmployeesBySearch(EmployeeFilterForm filterForm) throws NotValidFilterFormException {
        if(filterForm.isEmpty())
            throw new NotValidFilterFormException(EMPTY_EMPLOYEE_FILTER_FORM);

        List<EmployeeBasicInfoResponse> employeesResponse =  employeeService.findEmployeesBySearch(filterForm);
        return new ResponseEntity<>(employeesResponse, OK);
    }

}
