package com.hr.managementapp.controller;

import com.hr.managementapp.request.CreateDepartmentRequest;
import com.hr.managementapp.request.UpdateDepartmentRequest;
import com.hr.managementapp.response.DepartmentBasicInfoResponse;
import com.hr.managementapp.response.DepartmentResponse;
import com.hr.managementapp.service.DepartmentService;
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

    @GetMapping
    public ResponseEntity<List<DepartmentBasicInfoResponse>> getDepartments() {
        List<DepartmentBasicInfoResponse> departmentsResponse = departmentService.getDepartments();
        return new ResponseEntity<>(departmentsResponse, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable Long id) {
        DepartmentResponse departmentResponse = departmentService.getDepartmentById(id);
        return new ResponseEntity<>(departmentResponse, OK);
    }

    @PostMapping
    public ResponseEntity<DepartmentBasicInfoResponse> createDepartment(@RequestBody CreateDepartmentRequest createDepartmentRequest) {
        DepartmentBasicInfoResponse departmentResponse = departmentService.createDepartment(createDepartmentRequest);
        return new ResponseEntity<>(departmentResponse, CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable(name = "id") Long id) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<DepartmentResponse> updateDepartment(@RequestBody UpdateDepartmentRequest request) {
        DepartmentResponse departmentResponse = departmentService.updateDepartment(request);
        return new ResponseEntity<>(departmentResponse, OK);
    }

}
