package com.hr.managementapp.service;

import com.hr.managementapp.domain.Department;
import com.hr.managementapp.domain.Employee;
import com.hr.managementapp.exception.domain.DepartmentNotFoundException;
import com.hr.managementapp.exception.domain.DepartmentNotSelectedException;
import com.hr.managementapp.exception.domain.EmployeeNotFoundException;
import com.hr.managementapp.exception.domain.EmployeeNotHiredInDepartmentException;
import com.hr.managementapp.form.EmployeeFilterForm;
import com.hr.managementapp.request.CreateEmployeeRequest;
import com.hr.managementapp.request.UpdateEmployeeRequest;
import com.hr.managementapp.response.EmployeeBasicInfoResponse;
import com.hr.managementapp.response.EmployeeResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testGetEmployees() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<EmployeeBasicInfoResponse> page = employeeService.getEmployees(pageable);
        Assert.assertEquals(5, page.get().count());
    }

    @Test
    public void testFindEmployeesBySearch() {
        EmployeeFilterForm employeeFilterForm = new EmployeeFilterForm("Sofija", 1L);
        List<EmployeeBasicInfoResponse> employees = employeeService.findEmployeesBySearch(employeeFilterForm);
        Assert.assertEquals(1, employees.size());
    }

    @Test
    public void testGetEmployeeById() throws EmployeeNotFoundException {
        EmployeeResponse employee = employeeService.getEmployeeById(1L);
        Assert.assertEquals("Nikola", employee.getName());
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void testGetEmployeeByIdShouldThrowExceptionIfNotFound() {
        employeeService.getEmployeeById(-100L);
    }

    @Test
    public void createEmployee() {
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setName("Anja");
        request.setDepartmentId(1L);
        EmployeeBasicInfoResponse createdEmployee = employeeService.createEmployee(request);
        Assert.assertEquals(request.getName(), createdEmployee.getName());
        Assert.assertEquals(request.getDepartmentId(), createdEmployee.getDepartmentId());
    }

    @Test(expected = DepartmentNotSelectedException.class)
    public void createEmployeeShouldThrowExceptionIfDepartmentIsNotSelected() {
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setName("Anja");
        request.setDepartmentId(null);
        employeeService.createEmployee(request);
    }

    @Test
    public void updateEmployee() {
        UpdateEmployeeRequest request = new UpdateEmployeeRequest();
        request.setId(3L);
        request.setPersonalId("737378");
        request.setName("Milovan");
        request.setDepartmentId(1L);
        EmployeeResponse updatedEmployee = employeeService.updateEmployee(request);
        Assert.assertEquals("Milovan", updatedEmployee.getName());
        Assert.assertEquals("737378", updatedEmployee.getPersonalId());
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void deleteEmployee() {
        employeeService.deleteEmployee(1L);
        employeeService.getEmployeeById(1L);
    }

}
