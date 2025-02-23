package com.hr.managementapp.config;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;


@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Employee and Department API")
                        .version("1.0")
                        .description("API documentation for managing employees and departments"))
                .paths(new Paths()
                        .addPathItem("/employees", generateEmployeesPathItem())
                        .addPathItem("/employees/{id}", generateEmployeesByIdPathItem())
                        .addPathItem("/employees/search", generateEmployeesSearchPathItem())
                        .addPathItem("/departments", generateDepartmentsPathItem())
                        .addPathItem("/departments/{id}", generateDepartmentsByIdPathItem())
                );
    }

    private PathItem generateEmployeesPathItem() {
        return new PathItem()
                .get(getEmployeesOperation())
                .post(createEmployeeOperation())
                .put(updateEmployeeOperation());
    }

    private Operation getEmployeesOperation() {
        return new Operation()
                .operationId("getEmployees")
                .summary("Get a page of employees")
                .description("Retrieve a paginated list of employees");
    }

    private Operation createEmployeeOperation() {
        return new Operation()
                .operationId("createEmployee")
                .summary("Create a new employee")
                .description("Create a new employee in the system")
                .responses(new ApiResponses()
                        .addApiResponse(String.valueOf(HttpStatus.CREATED.value()), new ApiResponse()
                                .description("Employee successfully created")
                                .content(new Content().addMediaType(
                                        "application/json",
                                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/EmployeeBasicInfoResponse"))
                                ))
                        )
                        .addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), new ApiResponse()
                                .description("Invalid input")));
    }

    private Operation updateEmployeeOperation() {
        return new Operation()
                .operationId("updateEmployee")
                .summary("Update an employee")
                .description("Update employee details")
                .responses(new ApiResponses()
                        .addApiResponse(String.valueOf(HttpStatus.OK.value()), new ApiResponse()
                                .description("Employee updated successfully")
                                .content(new Content()
                                        .addMediaType("application/json", new MediaType()
                                                .schema(new Schema<>()
                                                        .$ref("#/components/schemas/EmployeeResponse"))
                                        )
                                )
                        )
                        .addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), new ApiResponse()
                                .description("Invalid input")
                        ));
    }

    private PathItem generateEmployeesByIdPathItem() {
        return new PathItem()
                .get(getEmployeeByIdOperation())
                .delete(deleteEmployeeByIdOperation());
    }

    private Operation getEmployeeByIdOperation() {
        return new Operation()
                .operationId("getEmployeeById")
                .summary("Get an employee by ID")
                .description("Retrieve employee details based on employee ID")
                .addParametersItem(new Parameter()
                        .name("id")
                        .description("Employee ID")
                        .required(true)
                        .in(ParameterIn.PATH.toString()))
                .responses(new ApiResponses()
                        .addApiResponse(String.valueOf(HttpStatus.CREATED.value()), new ApiResponse()
                                .description("Employee details retrieved successfully")
                                .content(new Content().addMediaType(
                                        "application/json",
                                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/EmployeeResponse"))
                                ))
                        )
                        .addApiResponse(String.valueOf(HttpStatus.NOT_FOUND.value()), new ApiResponse()
                                .description("Employee not found")));
    }

    private Operation deleteEmployeeByIdOperation() {
        return new Operation()
                .operationId("deleteEmployee")
                .summary("Delete an employee by ID")
                .description("Delete an employee from the system")
                .addParametersItem(new Parameter()
                        .name("id")
                        .description("Employee ID")
                        .required(true)
                        .in(ParameterIn.PATH.toString()))
                .responses(new ApiResponses()
                        .addApiResponse(String.valueOf(HttpStatus.NO_CONTENT.value()),
                                new ApiResponse().description(("Employee successfully deleted")))
                        .addApiResponse(String.valueOf(HttpStatus.NOT_FOUND.value()),
                                new ApiResponse().description(("Employee not found"))));
    }

    private PathItem generateEmployeesSearchPathItem() {
        return new PathItem()
                .get(findEmployeesBySearchOperation());
    }

    private Operation findEmployeesBySearchOperation() {
        return new Operation()
                .operationId("findEmployeesBySearch")
                .summary("Find employees based on search filters")
                .description("Retrieve a list of employees based on provided filter criteria")
                .responses(new ApiResponses()
                        .addApiResponse(String.valueOf(HttpStatus.OK.value()), new ApiResponse()
                                .description("Employees successfully retrieved")
                                .content(new Content()
                                        .addMediaType("application/json", new MediaType()
                                                .schema(new ArraySchema()
                                                        .items(new Schema<>().$ref("#/components/schemas/EmployeeBasicInfoResponse"))
                                                )
                                        )
                                )
                        )
                        .addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), new ApiResponse()
                                .description("Invalid filter parameters")
                        )
                );
    }

    private PathItem generateDepartmentsPathItem() {
        return new PathItem()
                .get(getDepartmentsOperation())
                .post(createDepartmentOperation())
                .put(updateDepartmentOperation());
    }

    private Operation getDepartmentsOperation() {
        return new Operation()
                .operationId("getDepartments")
                .summary("Get all departments")
                .description("Retrieve a list of departments");
    }

    private Operation createDepartmentOperation() {
        return new Operation()
                .operationId("createDepartment")
                .summary("Create a new department")
                .description("Create a new department in the system")
                .responses(new ApiResponses()
                        .addApiResponse(String.valueOf(HttpStatus.CREATED.value()), new ApiResponse()
                                .description("Department successfully created")
                                .content(new Content().addMediaType(
                                        "application/json",
                                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/DepartmentBasicInfoResponse"))
                                ))
                        )
                        .addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), new ApiResponse()
                                .description("Invalid input")));
    }

    private Operation updateDepartmentOperation() {
        return new Operation()
                .operationId("updateDepartment")
                .summary("Update a department")
                .description("Update department details")
                .responses(new ApiResponses()
                        .addApiResponse(String.valueOf(HttpStatus.OK.value()), new ApiResponse()
                                .description("Department updated successfully")
                                .content(new Content()
                                        .addMediaType("application/json", new MediaType()
                                                .schema(new Schema<>()
                                                        .$ref("#/components/schemas/DepartmentResponse"))
                                        )
                                )
                        )
                        .addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), new ApiResponse()
                                .description("Invalid input")
                        ));
    }

    private PathItem generateDepartmentsByIdPathItem() {
        return new PathItem()
                .get(getDepartmentByIdOperation())
                .delete(deleteDepartmentByIdOperation());
    }

    private Operation getDepartmentByIdOperation() {
        return new Operation()
                .operationId("getDepartmentById")
                .summary("Get a department by ID")
                .description("Retrieve department details based on department ID")
                .addParametersItem(new Parameter()
                        .name("id")
                        .description("Department ID")
                        .required(true)
                        .in(ParameterIn.PATH.toString()))
                .responses(new ApiResponses()
                        .addApiResponse(String.valueOf(HttpStatus.CREATED.value()), new ApiResponse()
                                .description("Department details retrieved successfully")
                                .content(new Content().addMediaType(
                                        "application/json",
                                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/DepartmentResponse"))
                                ))
                        )
                        .addApiResponse(String.valueOf(HttpStatus.NOT_FOUND.value()), new ApiResponse()
                                .description("Department not found")));
    }

    private Operation deleteDepartmentByIdOperation() {
        return new Operation()
                .operationId("deleteDepartment")
                .summary("Delete a department by ID")
                .description("Delete a department from the system")
                .addParametersItem(new Parameter()
                        .name("id")
                        .description("Department ID")
                        .required(true)
                        .in(ParameterIn.PATH.toString()))
                .responses(new ApiResponses()
                        .addApiResponse(String.valueOf(HttpStatus.NO_CONTENT.value()),
                                new ApiResponse().description(("Department successfully deleted")))
                        .addApiResponse(String.valueOf(HttpStatus.NOT_FOUND.value()),
                                new ApiResponse().description(("Department not found"))));
    }

}
