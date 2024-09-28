package com.hr.managementapp.service;

import com.hr.managementapp.exception.domain.DepartmentNotFoundException;
import com.hr.managementapp.exception.domain.EmployeeNotHiredInDepartmentException;
import com.hr.managementapp.request.CreateDepartmentRequest;
import com.hr.managementapp.request.UpdateDepartmentRequest;
import com.hr.managementapp.response.DepartmentBasicInfoResponse;
import com.hr.managementapp.response.DepartmentResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void testGetDepartments() {
        List<DepartmentBasicInfoResponse> departments = departmentService.getDepartments();
        Assert.assertEquals(2, departments.size());
    }

    @Test
    public void testGetDepartmentById() throws DepartmentNotFoundException {
        DepartmentResponse department = departmentService.getDepartmentById(1L);
        Assert.assertEquals("IT", department.getName());
    }

    @Test(expected = DepartmentNotFoundException.class)
    public void testGetDepartmentByIdShouldThrowExceptionIfNotFound() throws DepartmentNotFoundException {
        departmentService.getDepartmentById(3L);
    }

    @Test
    public void createDepartment() {
        CreateDepartmentRequest createDepartmentRequest = new CreateDepartmentRequest();
        createDepartmentRequest.setName("Telecommunications");
        DepartmentBasicInfoResponse createdDepartmentResponse = departmentService.createDepartment(createDepartmentRequest);
        Assert.assertEquals(createDepartmentRequest.getName(), createdDepartmentResponse.getName());
    }

    @Test
    public void updateDepartment() throws EmployeeNotHiredInDepartmentException, DepartmentNotFoundException {
        DepartmentResponse updatedDepartment = departmentService.updateDepartment(new UpdateDepartmentRequest(1L, "Cleaning", null));
        Assert.assertEquals("Cleaning", updatedDepartment.getName());
    }

    @Test(expected = EmployeeNotHiredInDepartmentException.class)
    public void updateDepartmentShouldThrowExceptionIfTeamLeadIsFromAnotherDepartment() {
        UpdateDepartmentRequest request = new UpdateDepartmentRequest();
        request.setId(1L);
        request.setName("Cleaning");
        request.setTeamLeadId(6L);
        departmentService.updateDepartment(request);
    }

    @Test(expected = DepartmentNotFoundException.class)
    public void deleteEmployee() throws DepartmentNotFoundException {
        departmentService.deleteDepartment(1L);
        departmentService.getDepartmentById(1L);
    }

}
