package com.hr.managementapp.controller;

import com.hr.managementapp.exception.domain.DepartmentNotFoundException;
import com.hr.managementapp.exception.domain.EmployeeNotFoundException;
import com.hr.managementapp.exception.domain.EmployeeNotHiredInDepartmentException;
import com.hr.managementapp.mapper.DepartmentMapper;
import com.hr.managementapp.request.CreateDepartmentRequest;
import com.hr.managementapp.request.UpdateDepartmentRequest;
import com.hr.managementapp.response.DepartmentBasicInfoResponse;
import com.hr.managementapp.response.DepartmentResponse;
import com.hr.managementapp.service.DepartmentService;
import com.hr.managementapp.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @Operation(summary = "Get all departments")
    @ApiResponse(responseCode = "200", description = "Departments successfully retrieved")
    @GetMapping
    public ResponseEntity<List<DepartmentBasicInfoResponse>> getDepartments() {
        List<DepartmentBasicInfoResponse> departmentsResponse = departmentService.getDepartments();
        return new ResponseEntity<>(departmentsResponse, OK);
    }

    @Operation(summary = "Get a department by its id")
    @ApiResponse(responseCode = "200", description = "Department successfully retrieved")
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartment(@PathVariable Long id)  {
        DepartmentResponse departmentResponse = departmentService.getDepartmentById(id);
        return new ResponseEntity<>(departmentResponse, OK);
    }

    @Operation(summary = "Create a new department")
    @ApiResponse(responseCode = "200", description = "Department successfully created")
    @PostMapping
    public ResponseEntity<DepartmentBasicInfoResponse> createDepartment(@RequestBody CreateDepartmentRequest createDepartmentRequest) {
        DepartmentBasicInfoResponse departmentResponse = departmentService.createDepartment(createDepartmentRequest);
        return new ResponseEntity<>(departmentResponse, CREATED);
    }

    @Operation(summary = "Delete a department by its id")
    @ApiResponse(responseCode = "200", description = "Department successfully deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable(name = "id") Long id) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @Operation(summary = "Update a department")
    @ApiResponse(responseCode = "200", description = "Department successfully updated")
    @PutMapping
    public ResponseEntity<DepartmentResponse> updateDepartment(@RequestBody UpdateDepartmentRequest request) {
        DepartmentResponse departmentResponse = departmentService.updateDepartment(request);
        return new ResponseEntity<>(departmentResponse, OK);
    }

}
