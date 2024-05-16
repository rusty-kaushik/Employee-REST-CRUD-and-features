package com.ak.Employee.controller;

import com.ak.Employee.dto.EmployeeDto;
import com.ak.Employee.exception.EmployeeCreationFailedException;
import com.ak.Employee.model.Employee;
import com.ak.Employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;
    private EmployeeDto employeeDto;
    Employee employee;
    Employee employeeTwo;
    EmployeeDto employeeDtoOne;
    EmployeeDto employeeDtoTwo;
    List<Employee> employeeList = new ArrayList<>();
    List<EmployeeDto> employeeDtosList = new ArrayList<>();

    @BeforeEach
    void setUp() throws ParseException {
        employee = new Employee("EMP1","Aniket",22,1212,"2022-12-12");
        employeeTwo = new Employee("EMP2","Shubham!@#$",23,2121,"3030-10-12");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createEmployee() throws Exception {
        employeeDtoOne = convertModelToDto(employee);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(employeeDtoOne);

        when(employeeService.createEmployee(employeeDto)).thenReturn("Success");

        this.mockMvc.perform(post("/employee").contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andDo(print())
                .andExpect(status().isCreated());
    }


    @Test
    void deleteEmployee() throws Exception {
        when(employeeService.deleteEmployee("EMP1")).thenReturn("Successfully deleted the employee");

        this.mockMvc.perform(delete("/employee/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getEmployee() throws Exception {
        when(employeeService.getEmployee("EMP1")).thenReturn(employeeDto);

        this.mockMvc.perform(get("/employee/EMP1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void updateEmployee() throws Exception {
        employeeDtoOne = convertModelToDto(employee);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(employeeDtoOne);

        when(employeeService.updateEmployee("EMP1",employeeDto)).thenReturn("Success");

        this.mockMvc.perform(put("/employee/EMP1").contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)).andDo(print())
                .andExpect(status().isOk());
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