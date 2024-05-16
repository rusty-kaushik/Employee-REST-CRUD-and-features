package com.ak.Employee.service;

import com.ak.Employee.dto.EmployeeDto;

public interface EmployeeService {

    // CREATE AN EMPLOYEE
    public String createEmployee(EmployeeDto empDto);

    // DELETE AN EMPLOYEE
     public String deleteEmployee(String employeeId);

    // UPDATE AN EMPLOYEE
    public String updateEmployee(String employeeId, EmployeeDto empDto);

    // GET AN EMPLOYEE
    public EmployeeDto getEmployee(String employeeId);
}
