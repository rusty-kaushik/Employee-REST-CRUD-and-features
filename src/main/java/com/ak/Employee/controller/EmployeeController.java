package com.ak.Employee.controller;

import com.ak.Employee.dto.EmployeeDto;
import com.ak.Employee.kafkaProducer.EmployeeEventProducer;
import com.ak.Employee.response.ResponseHandler;
import com.ak.Employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Employee", description = "Employee management APIs")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService empService;

    @Autowired
    private EmployeeEventProducer employeeEventProducer;

    // CREATE A EMPLOYEE
    @Operation(
            summary = "Create a Employee",
            description = "Creates a Employee. The API takes id, name, age, salary and joining date in json.",
            tags = { "employee", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "422", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping
    public ResponseEntity<Object> createEmployee(@RequestBody EmployeeDto empDto){
        String result = empService.createEmployee(empDto);
        employeeEventProducer.sendEvent("CREATE", "Employee created: " + empDto.getId());
        return ResponseHandler.responseBuilder("Employee created successfully", HttpStatus.CREATED, result);
    }

    // DELETE A EMPLOYEE
    @Operation(
            summary = "Delete a Employee by Id",
            description = "Deletes a Employee  by specifying its id.",
            tags = { "employee", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/{employeeId}")
    public  String deleteEmployee(@PathVariable("employeeId") String employeeId)
    {
        String result = empService.deleteEmployee(employeeId);
        employeeEventProducer.sendEvent("DELETE", "Employee deleted: " + employeeId);
        return result;
    }

    // GET EMPLOYEE BY ID
    @Operation(
            summary = "Retrieve a Employee by Id",
            description = "Get a Employee  by specifying its id. The response is Employee object with id, name, age and joining date.",
            tags = { "employee", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = EmployeeDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/{employeeId}")
    public ResponseEntity<Object> getEmployee(@PathVariable("employeeId") String employeeId)
    {
        EmployeeDto result =  empService.getEmployee(employeeId);
        employeeEventProducer.sendEvent("GET", "Employee retrieved: " + employeeId);
        return ResponseHandler.responseBuilder("Here are the values of requested Employee", HttpStatus.OK,result);
    }

    // UPDATE EMPLOYEE BY ID
    @Operation(
            summary = "Update a Employee by Id",
            description = "Updates a Employee by specifying its id. The response is a success message.",
            tags = { "employee", "put" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PutMapping("/{employeeId}")
    public ResponseEntity<Object> updateEmployee(@PathVariable("employeeId") String employeeId, @RequestBody EmployeeDto empDto) {
        String result = empService.updateEmployee(employeeId, empDto);
        employeeEventProducer.sendEvent("UPDATE", "Employee updated: " + employeeId);
        return ResponseHandler.responseBuilder("Employee Updated Successfully", HttpStatus.OK, result);

    }

}
