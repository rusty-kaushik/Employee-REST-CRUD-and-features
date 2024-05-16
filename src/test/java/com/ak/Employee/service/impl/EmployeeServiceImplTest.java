package com.ak.Employee.service.impl;

import com.ak.Employee.dto.EmployeeDto;
import com.ak.Employee.exception.EmployeeCreationFailedException;
import com.ak.Employee.exception.EmployeeDeletionFailedException;
import com.ak.Employee.exception.EmployeeNotFoundException;
import com.ak.Employee.exception.EmployeeUpdateFailedException;
import com.ak.Employee.model.Employee;
import com.ak.Employee.repository.EmployeeRepo;
import com.ak.Employee.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepo employeeRepo;
    private EmployeeService employeeService;
    AutoCloseable autoCloseable; // close all the resources when all the test cases are executed
    Employee employee;
    Employee employeeUpdated;


    @BeforeEach
    void setUp() throws ParseException {
        autoCloseable = MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeServiceImpl(employeeRepo);
        employee = new Employee("EMP1","Aniket",22,1212,"2022-12-12");
        employeeUpdated = new Employee("EMP1","Aniket Kaushik",22,1212,"2022-12-12");
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void createEmployee() {
        when(employeeRepo.save(any(Employee.class))).thenReturn(employee);
        EmployeeDto empDto = convertModelToDto(employee);
        assertThat(employeeService.createEmployee(empDto)).isEqualTo("Success");
        verify(employeeRepo, times(1)).save(any(Employee.class));
    }

    @Test
    void createEmployee_throwsException_whenValidationFails() {
        EmployeeDto invalidEmpDto = new EmployeeDto();
        assertThrows(EmployeeCreationFailedException.class, () -> employeeService.createEmployee(invalidEmpDto));
    }

    @Test
    void createEmployee_throwsException_whenRepoSaveFails() {
        EmployeeDto empDto = convertModelToDto(employee);
        when(employeeRepo.save(any(Employee.class))).thenThrow(new RuntimeException("Database error"));
        assertThrows(EmployeeCreationFailedException.class, () -> employeeService.createEmployee(empDto));
    }

    @Test
    void deleteEmployee() {
        when(employeeRepo.findById("EMP1")).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepo).deleteById("EMP1");
        String result = employeeService.deleteEmployee("EMP1");
        assertThat(result).isEqualTo("Successfully deleted the employee");
        verify(employeeRepo, times(1)).findById("EMP1");
        verify(employeeRepo, times(1)).deleteById("EMP1");
    }

    @Test
    void deleteEmployee_throwsException_whenEmployeeNotFound() {
        when(employeeRepo.findById("EMP1")).thenReturn(Optional.empty());
        assertThrows(EmployeeDeletionFailedException.class, () -> employeeService.deleteEmployee("EMP1"));
    }

    @Test
    void deleteEmployee_throwsException_whenRepoDeleteFails() {
        when(employeeRepo.findById("EMP1")).thenReturn(Optional.of(employee));
        doThrow(new RuntimeException("Database error")).when(employeeRepo).deleteById("EMP1");
        assertThrows(EmployeeDeletionFailedException.class, () -> employeeService.deleteEmployee("EMP1"));
    }


    @Test
    void updateEmployee() {
        when(employeeRepo.findById("EMP1")).thenReturn(Optional.ofNullable(employee));
        when(employeeRepo.save(any(Employee.class))).thenReturn(employeeUpdated);
        String result = employeeService.updateEmployee("EMP1", convertModelToDto(employeeUpdated));
        assertThat(result).isEqualTo("Success");
        verify(employeeRepo, times(1)).findById("EMP1");
        verify(employeeRepo, times(1)).save(any(Employee.class));
    }

    @Test
    void updateEmployee_throwsException_whenEmployeeNotFound() {
        when(employeeRepo.findById("EMP1")).thenReturn(Optional.empty());
        assertThrows(EmployeeUpdateFailedException.class, () -> employeeService.updateEmployee("EMP1", convertModelToDto(employeeUpdated)));
    }

    @Test
    void updateEmployee_throwsException_whenRepoSaveFails() {
        when(employeeRepo.findById("EMP1")).thenReturn(Optional.of(employee));
        when(employeeRepo.save(any(Employee.class))).thenThrow(new RuntimeException("Database error"));
        assertThrows(EmployeeUpdateFailedException.class, () -> employeeService.updateEmployee("EMP1", convertModelToDto(employeeUpdated)));
    }

    @Test
    void getEmployee() {
        when(employeeRepo.findById("EMP1")).thenReturn(Optional.ofNullable(employee));
        assertThat(employeeService.getEmployee("EMP1").getName()).isEqualTo(employee.getName());
    }

    @Test
    void getEmployee_throwsException_whenEmployeeNotFound() {
        when(employeeRepo.findById("EMP1")).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee("EMP1"));
    }

    @Test
    void getEmployee_throwsException_whenRepoFindFails() {
        when(employeeRepo.findById("EMP1")).thenThrow(new RuntimeException("Database error"));
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee("EMP1"));
    }


    public EmployeeDto convertModelToDto(Employee emp) {
        EmployeeDto empDto = new EmployeeDto();
        empDto.setId(emp.getId());
        empDto.setName(emp.getName());
        empDto.setAge(emp.getAge());
        empDto.setJoiningDate(emp.getJoiningDate());
        return empDto;
    }

}