package com.hr.managementapp.controller;

import com.hr.managementapp.exception.domain.EmployeeNotFoundException;
import com.hr.managementapp.exception.domain.NotValidFilterFormException;
import com.hr.managementapp.form.EmployeeFilterForm;
import com.hr.managementapp.request.CreateEmployeeRequest;
import com.hr.managementapp.request.UpdateEmployeeRequest;
import com.hr.managementapp.response.EmployeeBasicInfoResponse;
import com.hr.managementapp.response.EmployeeResponse;
import com.hr.managementapp.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hr.managementapp.constant.EmployeeConstant.EMPTY_EMPLOYEE_FILTER_FORM;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeBasicInfoResponse>> getEmployees(Pageable pageable) {
        Page<EmployeeBasicInfoResponse> page = employeeService.getEmployees(pageable);
        Page<EmployeeBasicInfoResponse> pageResponse = new PageImpl<>(page.toList(), pageable, page.getSize());
        return new ResponseEntity<>(pageResponse, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable(name = "id") Long id) {
        EmployeeResponse employeeResponse = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employeeResponse, OK);
    }

    @PostMapping
    public ResponseEntity<EmployeeBasicInfoResponse> createEmployee(@RequestBody CreateEmployeeRequest request) {
        EmployeeBasicInfoResponse employeeResponse = employeeService.createEmployee(request);
        return new ResponseEntity<>(employeeResponse, CREATED);
    }

    @PutMapping
    public ResponseEntity<EmployeeResponse> updateEmployee(@RequestBody UpdateEmployeeRequest request) {
        EmployeeResponse employeeResponse = employeeService.updateEmployee(request);
        return new ResponseEntity<>(employeeResponse, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(name = "id") Long id) throws EmployeeNotFoundException {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeBasicInfoResponse>> findEmployeesBySearch(EmployeeFilterForm filterForm) throws NotValidFilterFormException {
        if (filterForm.isEmpty())
            throw new NotValidFilterFormException(EMPTY_EMPLOYEE_FILTER_FORM);

        List<EmployeeBasicInfoResponse> employeesResponse = employeeService.findEmployeesBySearch(filterForm);
        return new ResponseEntity<>(employeesResponse, OK);
    }

}
