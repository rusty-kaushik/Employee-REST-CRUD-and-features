package com.ak.Employee.controller;

import com.ak.Employee.dto.EmployeeDto;
import com.ak.Employee.model.Employee;
import com.ak.Employee.model.Enterprise;
import com.ak.Employee.model.EnterpriseOriginal;
import com.ak.Employee.service.EmployeeService;
import com.ak.Employee.service.EnterpriseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(EnterpriseController.class)
class EnterpriseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnterpriseService enterpriseService;
    Enterprise enterprise;
    EnterpriseOriginal enterpriseOriginal;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void uploadCSV() throws Exception {
        String csvContent = "Year,Industry_aggregation_NZSIOC,Industry_code_NZSIOC,Industry_name_NZSIOC,Units,Variable_code,Variable_name,Variable_category,Value,Industry_code_ANZSIC06\n"
                + "2021,Level 1,99999,All industries,Dollars (millions),H01,Total income,Financial performance,\"7,57,504\",\"ANZSIC06 divisions A-S (excluding classes K6330, L6711, O7552, O760, O771, O772, S9540, S9601, S9602, and S9603)\"\n"
                + "2021,Level 1,99999,All industries,Dollars (millions),H04,\"Sales, government funding, grants and subsidies\",Financial performance,\"6,74,890\",\"ANZSIC06 divisions A-S (excluding classes K6330, L6711, O7552, O760, O771, O772, S9540, S9601, S9602, and S9603)\"\n"
                + "2021,Level 1,99999,All industries,Dollars (millions),H05,\"Interest, dividends and donations\",Financial performance,\"49,593\",\"ANZSIC06 divisions A-S (excluding classes K6330, L6711, O7552, O760, O771, O772, S9540, S9601, S9602, and S9603)\"";

        // Convert CSV content to a mock MultipartFile
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.csv", "text/csv", csvContent.getBytes());

        // Mock the service method and verify its invocation
        doNothing().when(enterpriseService).save(mockMultipartFile);

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/enterprise/upload").file(mockMultipartFile))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Successfully uploaded the CSV file")));
    }

    @Test
    void uploadCSV_InvalidFileFormat() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "Invalid file content".getBytes());

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/enterprise/upload").file(mockMultipartFile))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Please upload a CSV file")));
    }

    @Test
    void uploadOriginalCSV() throws Exception {
        String csvContent = "Year,Industry_aggregation_NZSIOC,Industry_code_NZSIOC,Industry_name_NZSIOC,Units,Variable_code,Variable_name,Variable_category,Value,Industry_code_ANZSIC06\n"
                + "2021,Level 1,99999,All industries,Dollars (millions),H01,Total income,Financial performance,\"7,57,504\",\"ANZSIC06 divisions A-S (excluding classes K6330, L6711, O7552, O760, O771, O772, S9540, S9601, S9602, and S9603)\"\n"
                + "2021,Level 1,99999,All industries,Dollars (millions),H04,\"Sales, government funding, grants and subsidies\",Financial performance,\"6,74,890\",\"ANZSIC06 divisions A-S (excluding classes K6330, L6711, O7552, O760, O771, O772, S9540, S9601, S9602, and S9603)\"\n"
                + "2021,Level 1,99999,All industries,Dollars (millions),H05,\"Interest, dividends and donations\",Financial performance,\"49,593\",\"ANZSIC06 divisions A-S (excluding classes K6330, L6711, O7552, O760, O771, O772, S9540, S9601, S9602, and S9603)\"";

        // Convert CSV content to a mock MultipartFile
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.csv", "text/csv", csvContent.getBytes());

        // Mock the service method and verify its invocation
        doNothing().when(enterpriseService).saveOriginal(mockMultipartFile);

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/enterprise/upload/original").file(mockMultipartFile))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Successfully uploaded the CSV file")));
    }

    @Test
    void uploadOriginalCSV_InvalidFileFormat() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "Invalid file content".getBytes());

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/enterprise/upload/original").file(mockMultipartFile))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Please upload a CSV file")));
    }

    @Test
    void getAllEnterprisesByVariableCode() throws Exception {
        List<Enterprise> enterpriseList = new ArrayList<>();
        when(enterpriseService.getAllEnterprisesByVariableCode("H01")).thenReturn(enterpriseList);
        this.mockMvc.perform(get("/enterprise/variableCode/H01/enterprises")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getAllEnterprisesByVariableCode_NotEmpty() throws Exception {
        // Create a list of enterprises
        List<Enterprise> enterpriseList = new ArrayList<>();
        enterpriseList.add(new Enterprise());

        // Mock the service method to return the list of enterprises
        when(enterpriseService.getAllEnterprisesByVariableCode("H01")).thenReturn(enterpriseList);

        // Perform the request and expect status is OK
        this.mockMvc.perform(get("/enterprise/variableCode/H01/enterprises"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllEnterprises() {
    }
}