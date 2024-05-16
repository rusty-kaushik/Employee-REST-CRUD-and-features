package com.ak.Employee.controller;

import com.ak.Employee.dto.EmployeeDto;
import com.ak.Employee.response.ResponseHandler;
import com.ak.Employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService empService;

    // CREATE A EMPLOYEE
    @PostMapping
    public ResponseEntity<Object> createEmployee(@RequestBody EmployeeDto empDto){
        String result = empService.createEmployee(empDto);
        return ResponseHandler.responseBuilder("Employee created successfully", HttpStatus.CREATED, result);
    }

    // DELETE A EMPLOYEE
    @DeleteMapping("/{employeeId}")
    public  String deleteEmployee(@PathVariable("employeeId") String employeeId)
    {
        return empService.deleteEmployee(employeeId);
    }

    // GET EMPLOYEE BY ID
    @GetMapping("/{employeeId}")
    public ResponseEntity<Object> getEmployee(@PathVariable("employeeId") String employeeId)
    {
        EmployeeDto result =  empService.getEmployee(employeeId);
        return ResponseHandler.responseBuilder("Here are the values of requested Employee", HttpStatus.OK,result);
    }

    // UPDATE EMPLOYEE BY ID
    @PutMapping("/{employeeId}")
    public ResponseEntity<Object> updateEmployee(@PathVariable("employeeId") String employeeId, @RequestBody EmployeeDto empDto) {
            return ResponseHandler.responseBuilder("Employee Updated Successfully", HttpStatus.OK,empService.updateEmployee(employeeId, empDto));

    }

}
