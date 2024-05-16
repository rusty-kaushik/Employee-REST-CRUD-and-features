package com.ak.Employee.controller;

import com.ak.Employee.model.EnterpriseOriginal;
import com.ak.Employee.model.VariableCode;
import com.ak.Employee.service.VariableCodeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VariableCodeController.class)
class VariableCodeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VariableCodeService variableCodeService;

    VariableCode variableCode;

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
        doNothing().when(variableCodeService).save(mockMultipartFile);

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/VendorCode/upload").file(mockMultipartFile))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Successfully uploaded the CSV file")));
    }

    @Test
    void uploadCSV_InvalidFileFormat() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "Invalid file content".getBytes());

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/VendorCode/upload").file(mockMultipartFile))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Please upload a CSV file")));
    }
}