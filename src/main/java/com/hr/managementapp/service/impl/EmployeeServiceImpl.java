package com.hr.managementapp.service.impl;

import com.hr.managementapp.domain.Department;
import com.hr.managementapp.domain.Employee;
import com.hr.managementapp.exception.domain.DepartmentNotFoundException;
import com.hr.managementapp.exception.domain.DepartmentNotSelectedException;
import com.hr.managementapp.exception.domain.EmployeeNotFoundException;
import com.hr.managementapp.exception.domain.EmployeeNotHiredInDepartmentException;
import com.hr.managementapp.form.EmployeeFilterForm;
import com.hr.managementapp.mapper.EmployeeMapper;
import com.hr.managementapp.repository.DepartmentRepository;
import com.hr.managementapp.repository.EmployeeRepository;
import com.hr.managementapp.request.CreateEmployeeRequest;
import com.hr.managementapp.request.UpdateEmployeeRequest;
import com.hr.managementapp.response.EmployeeBasicInfoResponse;
import com.hr.managementapp.response.EmployeeResponse;
import com.hr.managementapp.service.DepartmentService;
import com.hr.managementapp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.hr.managementapp.constant.DepartmentConstant.*;
import static com.hr.managementapp.constant.EmployeeConstant.*;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    @Override
    public Page<EmployeeBasicInfoResponse> getEmployees(Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        return employeePage.map(EmployeeMapper::mapToBasicResponse);
    }

    @Override
    public List<EmployeeBasicInfoResponse> findEmployeesBySearch(EmployeeFilterForm filterForm) {
        List<Employee> employees = employeeRepository.findBySearch(filterForm.getName(), filterForm.getDepartmentId());
        return employees.stream().map(EmployeeMapper::mapToBasicResponse).collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(NO_EMPLOYEE_FOUND));
        return EmployeeMapper.mapToResponse(employee);
    }

    @Override
    public EmployeeBasicInfoResponse createEmployee(CreateEmployeeRequest request) throws DepartmentNotFoundException, DepartmentNotSelectedException {
        if (request.getDepartmentId() == null)
            throw new DepartmentNotSelectedException(NO_DEPARTMENT_SELECTED);

        boolean departmentExists = false;

        List<Department> departments = departmentRepository.findAll();
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setPersonalId(request.getPersonalId());
        for (Department department : departments) {
            if (department.getId().equals(request.getDepartmentId())) {
                employee.setDepartment(department);
                departmentExists = true;
                break;
            }
        }

        if (!departmentExists)
            throw new DepartmentNotFoundException(NO_DEPARTMENT_FOUND);

        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToBasicResponse(savedEmployee);
    }

    @Override
    public EmployeeResponse updateEmployee(UpdateEmployeeRequest request) {
        if(request.getDepartmentId() == null)
            throw new DepartmentNotSelectedException(NO_DEPARTMENT_SELECTED);

        Department chosenDepartment = departmentRepository.getById(request.getDepartmentId());
        Employee employeeDB = employeeRepository.getById(request.getId());
        Department departmentDB = departmentRepository.getById(employeeDB.getDepartment().getId());

        if(!chosenDepartment.getId().equals(departmentDB.getId()) && employeeDB.isTeamLead()) {
            departmentDB.setTeamLead(null);
            departmentRepository.save(departmentDB);
        }
        employeeDB.setName(request.getName());
        employeeDB.setPersonalId(request.getPersonalId());
        Employee savedEmployee = employeeRepository.save(employeeDB);
        return EmployeeMapper.mapToResponse(savedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) throws EmployeeNotFoundException {
        try {
            employeeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EmployeeNotFoundException(NO_EMPLOYEE_FOUND);
        }
    }

}
