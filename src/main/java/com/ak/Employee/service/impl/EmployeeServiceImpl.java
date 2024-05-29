package com.ak.Employee.service.impl;

import com.ak.Employee.dto.EmployeeDto;
import com.ak.Employee.exception.EmployeeCreationFailedException;
import com.ak.Employee.exception.EmployeeDeletionFailedException;
import com.ak.Employee.exception.EmployeeNotFoundException;
import com.ak.Employee.exception.EmployeeUpdateFailedException;
import com.ak.Employee.model.Employee;
import com.ak.Employee.repository.EmployeeRepo;
import com.ak.Employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepo empRepo;

    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.empRepo = employeeRepo;
    }

    @Override
    public String createEmployee(EmployeeDto empDto) {
        try{
            validateEmpDto(empDto);
            Employee employee = convertDtoToModel(empDto);
            empRepo.save(employee);
            return "Success";
        }catch(Exception e){
            throw new EmployeeCreationFailedException(e.getMessage(),e);
        }

    }

    @Override
    public String deleteEmployee(String employeeId) {
        try{
            if(empRepo.findById(employeeId).isPresent()){
                empRepo.deleteById(employeeId);
                return "Successfully deleted the employee";
            }else{
                throw new EmployeeNotFoundException("Employee not found");
            }
        }catch(Exception e){
            throw new EmployeeDeletionFailedException(e.getMessage(),e);
        }
    }

    @Override
    public String updateEmployee(String employeeId, EmployeeDto empDto) {
        try{
            if(empRepo.findById(employeeId).isPresent()){
                Employee employee = convertDtoToModel(empDto);
                empRepo.save(employee);
                return "Success";
            }else{
                throw new EmployeeNotFoundException("Employee not found");
            }
        }catch(Exception e){
            throw new EmployeeUpdateFailedException(e.getMessage(),e);
        }

    }

    @Override
    public EmployeeDto getEmployee(String employeeId) {
        try{
            if(empRepo.findById(employeeId).isPresent()){
                return convertModelToDto(empRepo.findById(employeeId).get());
            }else{
                throw new EmployeeNotFoundException("Employee not found");
            }
        }catch(Exception e){
            throw new EmployeeNotFoundException(e.getMessage(),e);
        }
    }

    public Employee convertDtoToModel(EmployeeDto empDto) {
        Employee emp = new Employee();
        emp.setId(empDto.getId());
        emp.setName(empDto.getName());
        emp.setAge(empDto.getAge());
        emp.setSalary(empDto.getSalary());
        emp.setJoiningDate(empDto.getJoiningDate());
        return emp;
    }

    public EmployeeDto convertModelToDto(Employee emp) {
        EmployeeDto empDto = new EmployeeDto();
        empDto.setId(emp.getId());
        empDto.setName(emp.getName());
        empDto.setAge(emp.getAge());
        empDto.setJoiningDate(emp.getJoiningDate());
        return empDto;
    }

    private void validateEmpDto(EmployeeDto empDto) {
        if (empDto == null) {
            throw new IllegalArgumentException("Employee data is required");
        }
        if (empDto.getId() == null || empDto.getId().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        if (empDto.getName() == null || empDto.getName().isEmpty() || !empDto.getName().matches("^[a-zA-Z0-9]*$")) {
            throw new IllegalArgumentException("Name must be non-empty and contain only alphanumeric characters");
        }
        if (empDto.getAge() < 18 || empDto.getAge() > 65) {
            throw new IllegalArgumentException("Age must be between 18 and 65");
        }
        if (empDto.getSalary() < 0) {
            throw new IllegalArgumentException("Age must be between 18 and 65");
        }
        if (empDto.getJoiningDate() == null || empDto.getJoiningDate().after(new Date())) {
            throw new IllegalArgumentException("Joining date must be in the past");
        }
        if (empRepo.existsById(empDto.getId())) {
            throw new IllegalArgumentException("Employee ID already exists");
        }
    }
}
