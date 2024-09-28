package com.hr.managementapp.service.impl;

import com.hr.managementapp.constant.DepartmentConstant;
import com.hr.managementapp.domain.Department;
import com.hr.managementapp.domain.Employee;
import com.hr.managementapp.exception.domain.DepartmentNotFoundException;
import com.hr.managementapp.exception.domain.EmployeeNotHiredInDepartmentException;
import com.hr.managementapp.mapper.DepartmentMapper;
import com.hr.managementapp.repository.DepartmentRepository;
import com.hr.managementapp.repository.EmployeeRepository;
import com.hr.managementapp.request.CreateDepartmentRequest;
import com.hr.managementapp.request.UpdateDepartmentRequest;
import com.hr.managementapp.response.DepartmentBasicInfoResponse;
import com.hr.managementapp.response.DepartmentResponse;
import com.hr.managementapp.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.stream.Collectors;

import static com.hr.managementapp.constant.DepartmentConstant.*;
import static com.hr.managementapp.constant.EmployeeConstant.*;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository   employeeRepository;

    @Override
    public List<DepartmentBasicInfoResponse> getDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(DepartmentMapper::mapToBasicResponse).collect(Collectors.toList());
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) throws DepartmentNotFoundException {
        return departmentRepository.findById(id).map(DepartmentMapper::mapToResponse)
                .orElseThrow(() -> new DepartmentNotFoundException(DepartmentConstant.NO_DEPARTMENT_FOUND));
    }

    @Override
    public DepartmentBasicInfoResponse createDepartment(CreateDepartmentRequest createDepartmentRequest) {
        Department newDepartment = departmentRepository.save(DepartmentMapper.mapToEntity(createDepartmentRequest));
        return DepartmentMapper.mapToBasicResponse(newDepartment);
    }

    @Override
    public void deleteDepartment(Long id) throws DepartmentNotFoundException {
        try {
            departmentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new DepartmentNotFoundException(NO_DEPARTMENT_FOUND);
        }
    }

    @Override
    public DepartmentResponse updateDepartment(UpdateDepartmentRequest request) throws DepartmentNotFoundException, EmployeeNotHiredInDepartmentException {
        Department department = departmentRepository.getById(request.getId());

        Employee teamLead = null;

        if( request.getTeamLeadId() != null) {
            teamLead = employeeRepository.getById(request.getTeamLeadId());
        }

        if(StringUtils.isNotEmpty(request.getName())) {
            department.setName(request.getName());
        }
        setTeamLeadLogic(teamLead, department);
        return DepartmentMapper.mapToResponse(departmentRepository.save(department));
    }

    /**
     * This method is for setting the team lead only
     * in the case he/she is already hired in that department
     */
    private void setTeamLeadLogic(Employee potentialTeamLead, Department department) throws EmployeeNotHiredInDepartmentException {
        if(potentialTeamLead == null || CollectionUtils.isEmpty(department.getEmployees())) {
            department.setTeamLead(null);
            return;
        }

        boolean hiredInThatDepartment = false;
        for(Employee employee : department.getEmployees()) {
            if(employee.getId().equals(potentialTeamLead.getId())) {
                department.setTeamLead(employee);
                hiredInThatDepartment = true;
                break;
            }
        }
        if(!hiredInThatDepartment)
            throw new EmployeeNotHiredInDepartmentException(EMPLOYEE_NOT_HIRED_IN_DEPARTMENT);
    }

}
